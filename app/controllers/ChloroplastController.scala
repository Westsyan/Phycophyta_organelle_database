package controllers

import dao.{ChloroplastDao, CpgeneDao, CpgenomeDao}
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import utils.{ExecCommand, TableUtils, Utils}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}


@Singleton
class ChloroplastController @Inject()(cc: ControllerComponents,
                                      chloroplastDao: ChloroplastDao,
                                      cpgenomeDao: CpgenomeDao,
                                      cpgeneDao: CpgeneDao)
                                     (implicit exec: ExecutionContext)
  extends AbstractController(cc) {

  def chloroplastPage = Action { implicit request =>
    Ok(views.html.english.chloroplast.genome())
  }


  def getAllChloroplast = Action.async { implicit request =>
    chloroplastDao.getAll.map { x =>
      val json = x.map { cp =>
        Json.obj("id" -> cp.id, "geneid" -> ("0" * (5 - cp.id.toString.length) + cp.id),
          "imgid" -> "",
          "species" -> cp.species,
          "phylum" -> cp.phylum,
          "class" -> cp.classes,
          "order" -> cp.order,
          "family" -> cp.family,
          "genus" -> cp.genus,
          "location" -> cp.position, "genbank" -> cp.genbank.trim, "bp" -> cp.bp,
          "configuration" -> cp.configuration,
          "ncbilink" -> "", "submiter" -> cp.submiter,
          "pubmed" -> cp.pubmed, "journal" -> cp.journal,
          "title" -> cp.title, "authors" -> cp.authors,
          "colInfo" -> cp.colInfo, "colDate" -> cp.colDate)
      }
      Ok(Json.toJson(json))
    }
  }

  def moreInfo(id: Int) = Action.async { implicit request =>
    chloroplastDao.getById(id).map { x =>
      Ok(views.html.english.chloroplast.moreinfo(x))
    }
  }

  def gbGenomePage = Action { implicit request =>
    Ok(views.html.english.chloroplast.gbGenome())
  }

  def getAllGbChloroplast = Action.async { implicit request =>
    cpgenomeDao.getAll.flatMap { x =>
      chloroplastDao.getAll.map { y =>

        val json = x.map { z =>
          val cp = y.find(_.id == z.cpid.toInt).get
          Json.obj("id" -> z.id, "geneid" -> ("0" * (5 - z.id.toString.length) + z.id),
            "name" -> z.name,
            "imgid" -> cp.id,
            "species" -> cp.species,
            "phylum" -> cp.phylum,
            "class" -> cp.classes,
            "order" -> cp.order,
            "family" -> cp.family,
            "genus" -> cp.genus,
            "location" -> cp.position, "genbank" -> cp.genbank.trim, "bp" -> cp.bp,
            "configuration" -> cp.configuration,
            "ncbilink" -> "", "submiter" -> cp.submiter,
            "pubmed" -> cp.pubmed, "journal" -> cp.journal,
            "title" -> cp.title, "authors" -> cp.authors,
            "colInfo" -> cp.colInfo, "colDate" -> cp.colDate)
        }
        Ok(Json.toJson(json))
      }
    }
  }


  def gbMoreInfo(id: Int) = Action.async { implicit request =>
    cpgenomeDao.getById(id: Int).flatMap { x =>
      chloroplastDao.getById(x.cpid.toInt).map { y =>
        Ok(views.html.english.chloroplast.gbmoreinfo(y, x))
      }
    }
  }

  def getAllGeneByGbId(id: Int) = Action.async { implicit request =>
    cpgeneDao.getByGb(id).map { x =>
      val json = x.map { y =>
        val geneid = "<a href=' /US/PODB/chloroplast/moreGeneInfoPage?id=" + y.id + "' target='_blank'>" + y.geneid + "</a>"
        Json.obj("id" -> y.id, "geneid" -> geneid, "start" -> y.start,
          "end" -> y.end, "strand" -> y.strand)
      }
      Ok(Json.toJson(json))
    }
  }


  def getWorldPostion(id: Int) = Action {
    val mmap = TableUtils.mmap
    val maxmap = TableUtils.maxmap
    val position = Await.result(chloroplastDao.getById(id), Duration.Inf).position

    val world = position.replaceAll(" and ", "、").split("、").flatMap { x =>
      if (maxmap.getOrElse(x.trim, "") != "") {
        maxmap(x.trim).map(_._3)
      } else {
        if (x.indexOf(":") != -1) {
          val w = mmap.filter(_._2 == x.split(":").last.trim).map(_._3)
          if (w.isEmpty) {
            mmap.filter(_._2 == x.split(":").head.trim).map(_._3)
          } else {
            w
          }
        } else {
          mmap.filter(_._2 == x.trim).map(_._3)
        }
      }
    }.distinct
    val json = world.map { y =>
      Json.obj("key" -> y)
    }

    Ok(Json.toJson(json))

  }

  def moreGeneInfoPage(id: Int) = Action.async { implicit request =>
    cpgeneDao.getById(id).flatMap { x =>
      cpgenomeDao.getById(x.gbid).flatMap { y =>
        chloroplastDao.getById(x.cpid).map { z =>
          Ok(views.html.english.chloroplast.moreGeneInfo(x, y.name, z.species))
        }
      }
    }
  }

  def getSeqs(gene: String, range: String) = Action { implicit request =>
    val exec = new ExecCommand()
    val cdsCmd = Utils.samtools + "faidx " + Utils.path + "/fasta/cpseqs/cds/" + gene + ".gb.cds " + range
    val pepCmd = Utils.samtools + "faidx " + Utils.path + "/fasta/cpseqs/pep/" + gene + ".gb.pep " + range
    println(cdsCmd)
    println(pepCmd)
    exec.exec(Array(cdsCmd, pepCmd))
    val out = exec.getOutStr.split(">")
    val cds = out(1).split("\n").tail.mkString
    val pep = out.last.split("\n").tail.mkString
    Ok(Json.obj("cds" -> cds, "pep" -> pep))
  }

}

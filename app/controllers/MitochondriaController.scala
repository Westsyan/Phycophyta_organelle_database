package controllers

import dao._
import javax.inject.Inject
import models.Tables.MitochogenomeRow
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import utils.TableUtils

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class MitochondriaController @Inject()(cc: ControllerComponents,
                                       mitochondriadao: mitochondriaDao,
                                       csvdao: csvDao,
                                       mitochogenedao: mitochogeneDao,
                                       mitochogenomeDao: mitochogenomeDao) extends AbstractController(cc) {


  def mitochondriaGenomeBefore: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.english.mitochondria.genome())
  }


  def getAllMitochondria = Action.async { implicit request =>
    mitochondriadao.getAll.flatMap { x =>
      mitochogenomeDao.getAll.map { y =>
            val json = y.map{z=>
              val mt = x.find(_.id == z.mtid.toInt).get

              Json.obj("id" -> z.id,"geneid" -> ("0"*(5-z.id.toString.length)+z.id),
                "imgid" -> mt.id,
                "name" -> z.name,
                "species" -> mt.species.split(" ").init.mkString(" "),
                "phylum" -> mt.phylum.split(" ").init.mkString(" "),
                "class" -> mt.classes.split(" ").init.mkString(" "),
                "order" -> mt.order.split(" ").init.mkString(" "),
                "family" -> mt.family.split(" ").init.mkString(" "),
                "genus" -> mt.genus.split(" ").init.mkString(" "),
                "location" -> mt.location, "genbank" -> mt.genbank, "bp" -> z.length,
                "ncbilink" -> mt.ncbilink, "author" -> mt.author,
                "ref_pubmed" -> mt.refPubmed, "ref_journal" -> mt.refJournal,
                "ref_title" -> mt.refTitle, "ref_authors" -> mt.refAuthors)

            }
        Ok(Json.toJson(json))
      }
/*      val json = x.map { x =>
        Json.obj("id" -> x.id,
          "species" -> x.species.split(" ").init.mkString(" "),
          "phylum" -> x.phylum.split(" ").init.mkString(" "),
          "class" -> x.classes.split(" ").init.mkString(" "),
          "order" -> x.order.split(" ").init.mkString(" "),
          "family" -> x.family.split(" ").init.mkString(" "),
          "genus" -> x.genus.split(" ").init.mkString(" "),
          "location" -> x.location, "genbank" -> x.genbank, "bp" -> x.bp, "ncbilink" -> x.ncbilink,
          "author" -> x.author, "ref_pubmed" -> x.refPubmed, "ref_journal" -> x.refJournal,
          "ref_title" -> x.refTitle, "ref_authors" -> x.refAuthors)
      }
      Ok(Json.toJson(json))*/
    }

  }

  def moreInfoUS(id: Int) = Action.async { implicit request =>
    mitochondriadao.getById(id).map { x =>
      Ok(views.html.english.mitochondria.moreinfo(x))
    }
  }

  def moreInfoByGeneIdUS(geneid: String) = Action.async { implicit request =>
    val id = TableUtils.mtGeneToId(geneid)
    mitochondriadao.getById(id).map { x =>
      Ok(views.html.english.mitochondria.moreinfo(x))
    }
  }

  def moreGeneInfo(gene: String) = Action.async { implicit request =>
    mitochogenedao.getById(
      gene match {
        case x if x.startsWith("ID") => gene.drop(2).toInt
        case x if x.startsWith("GeneId") => TableUtils.mtGeneToId(gene.drop(6))
      }
    ).map { x =>
      val y = Await.result(mitochondriadao.getById(x.species.toInt), Duration.Inf)
      Ok(views.html.english.mitochondria.moreGeneInfo(x, y.species.split(" ").init.mkString("_")))
    }
  }

  def getCsvBySpeciesId(id: String) = Action.async { implicit request =>
    csvdao.getBySpecies(id).map { x =>
      val row = x.groupBy(_.seqName).map { z =>
        val json =  z._2.map { y =>
          Json.obj("name" -> y.name, "types" -> y.types, "minimum" -> y.minimum, "maximun" -> y.maximun,
            "length" -> y.length, "direction" -> y.direction, "document" -> y.document, "seq" -> y.seq,
            "intervals" -> y.intervals, "track" -> y.track, "product" -> y.product, "translation" -> y.translation,
            "gene" -> y.gene, "seq_name" -> y.seqName, "db_xref" -> y.dbXref, "locustag" -> y.locustag, "proteinid" -> y.proteinid)
        }
        Json.obj("gb" -> z._1,"data" -> json)
      }
      Ok(Json.toJson(row))
    }
  }

  def getAllGeneBySpecies(species: String) = Action.async { implicit request =>
    mitochogenedao.getBySpecies(species).map { x =>
      val row = x.groupBy(_.genbank).map { z =>
        val json = z._2.map { y =>
          val geneid = "<a href='/US/PODB/mitochondria/moreGeneInfo?gene=ID" + y.id + "' target='_blank'>" + y.geneid + "</a>"
          Json.obj("id" -> y.id, "geneid" -> geneid, "genbank" -> y.genbank, "start" -> y.start,
            "end" -> y.end, "strand" -> y.strand)
        }
        Json.obj("gb" -> z._1, "tdata" -> json)
      }
      Ok(Json.toJson(row))
    }
  }

  def moreGeneInfoUS(id: Int) = Action { implicit request =>
    val x = Await.result(mitochogenedao.getById(id), Duration.Inf)
    val y = Await.result(mitochondriadao.getById(x.species.toInt), Duration.Inf)
    Ok(views.html.english.mitochondria.moreGeneInfo(x, y.species.split(" ").init.mkString("_")))
  }


  def jbBeforeUS = Action { implicit request =>
    Ok(views.html.english.mitochondria.jb())
  }

  def circleBeforeUS = Action { implicit request =>
    Ok(views.html.english.mitochondria.circle())
  }

  def orfBeforeUS = Action { implicit request =>
    Ok(views.html.english.mitochondria.orf())
  }

  def mapBefore(key:String) = Action { implicit request =>
    Ok(views.html.english.mitochondria.map(key))
  }


  def insert = Action.async { implicit request =>
    mitochondriadao.getAll.map{x=>
      val row = x.flatMap{y=>
        y.genbank.split(";").map{z=>
          MitochogenomeRow(0,z,y.id.toString,0,0,"0","0","0","0","0","0","0","0","0")
        }
      }
      Await.result(mitochogenomeDao.insert(row),Duration.Inf)
      Ok(Json.toJson("1"))
    }
  }


  def gbInfo(id:Int) = Action.async{implicit request=>
    mitochogenomeDao.getById(id).flatMap{x=>
      mitochondriadao.getById(x.mtid.toInt).map{y=>
        Ok(views.html.english.mitochondria.gbInfo(y,x))
      }
    }
  }

  def gbInfoByName(name:String) = Action.async{implicit request=>
    mitochogenomeDao.getByName(name).flatMap{x=>
      mitochondriadao.getById(x.mtid.toInt).map{y=>
        Ok(views.html.english.mitochondria.gbInfo(y,x))
      }
    }
  }

}

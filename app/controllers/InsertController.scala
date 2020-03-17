package controllers

import java.io.File

import dao.{ChloroplastDao, CpgeneDao, CpgenomeDao}
import javax.inject.{Inject, Singleton}
import models.Tables.{ChloroplastRow, CpgeneRow, CpgenomeRow}
import org.apache.commons.io.FileUtils
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents, Results}
import utils.ReadExcel._
import utils.Utils

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.collection.JavaConverters._

@Singleton
class InsertController @Inject()(chloroplastDao: ChloroplastDao,
                                 cpgenomeDao: CpgenomeDao,
                                 cpgeneDao: CpgeneDao,
                                 cc: ControllerComponents)
                                (implicit exec: ExecutionContext) extends AbstractController(cc) {


  def insertCp = Action {
    if (new File("C:/").exists()) {

      new File("D:\\藻类细胞器\\cp").listFiles().foreach { x =>
        val row = xlsx2Lines(x).map(_.replaceAll("\n", " ")).map { line =>
          val l = line.split("\t")
          val species = getOrNull(l, 5)
          val phylum = getOrNull(l, 0)
          val classes = getOrNull(l, 1)
          val order = getOrNull(l, 2)
          val family = getOrNull(l, 3)
          val genus = getOrNull(l, 4)
          val position = getOrNull(l, 6)
          val genbank = getOrNull(l, 7)
          val gl = genbank.indexOf('.')
          val gen = if (gl != -1) {
            genbank.take(gl + 2).trim + " " + genbank.drop(gl + 2).trim
          } else {
            genbank
          }
          val bp = getOrNull(l, 8)
          val conf = getOrNull(l, 9)
          val sub = getOrNull(l, 10)
          val pub = getOrNull(l, 11)
          val journal = getOrNull(l, 12)
          val title = getOrNull(l, 13)
          val authors = getOrNull(l, 14)
          val colInfo = getOrNull(l, 15)
          val colDate = getOrNull(l, 16)
          ChloroplastRow(0, species, phylum, classes, order, family, genus, position, gen, bp, conf,
            sub, pub, journal, title, authors, colInfo, colDate)
        }
        Await.result(chloroplastDao.insertCp(row), Duration.Inf)
      }

      Ok(Json.toJson(1))
    } else {
      Results.BadRequest
    }
  }


  def insertCpgenome = Action {

    val cp = Await.result(chloroplastDao.getAll, Duration.Inf)

    val fa = new File("D:\\藻类细胞器\\cpfa").listFiles()
    val row = cp.flatMap { x =>
      val gen = x.genbank.split(" ").map(_.trim)
      gen.map { g =>
        val f = new File("D:\\藻类细胞器\\cpfa/" + g + ".fasta")
        if (f.exists()) {
          val l = Utils.readLines("D:\\藻类细胞器\\cpfa/" + g + ".fasta")
          val seq = l.tail.mkString
          CpgenomeRow(0, g, x.id.toString, seq.length, getGC(seq).formatted("%.2f") + "%", "", "", "", "", "", "", "", "", "")
        } else {
          if (fa.count(_.getName.startsWith(g)) != 0) {
            val l = Utils.readLines(fa.find(_.getName.startsWith(g)).get)
            val seq = l.tail.mkString
            CpgenomeRow(0, g, x.id.toString, seq.length, getGC(seq).formatted("%.2f") + "%", "", "", "", "", "", "", "", "", "")
          } else {
            CpgenomeRow(0, g, x.id.toString, 0, "0", "", "", "", "", "", "", "", "", "")
          }

        }
      }

    }
    Await.result(cpgenomeDao.insert(row), Duration.Inf)
    Ok(Json.toJson("1"))

  }


  def getGC(seq: String) = {
    val array = seq.split("").map(_.toUpperCase())
    val c = array.count(_ == "C")
    val g = array.count(_ == "G")
    (c.toDouble + g.toDouble) * 100 / array.length.toDouble
  }


  def insertCpGene = Action.async {

    val gff = new File("D:\\藻类细胞器\\cpgff").listFiles()
    cpgenomeDao.getAll.map { x =>
      x.foreach { cp =>
        val f = gff.find(_.getName.startsWith(cp.name))

        if (f.isDefined) {
          val l = Utils.readLines(f.get).filter(_.indexOf("gene") != -1)
          val row = l.map { gene =>
            val garray = gene.split("\t")
            val geneid = garray.last.split(";").head.drop(3)
            CpgeneRow(0, geneid, cp.cpid.toInt, cp.id, garray(3).toInt, garray(4).toInt, garray(6))
          }
          Await.result(cpgeneDao.insert(row), Duration.Inf)
        }
      }
      Ok(Json.toJson("1"))

    }


  }


  def getBlastData = Action.async {
    val file = new File("D:\\藻类细胞器\\cpseqs\\pep/").listFiles()
    chloroplastDao.getAll.map { x =>
      x.groupBy(_.phylum).foreach { y =>
        y._2.flatMap { z =>
          z.genbank.split(" ")
        }.foreach { z =>


          if (file.exists(_.getName.startsWith(z))) {
            val line = FileUtils.readLines(file.find(_.getName.startsWith(z)).get).asScala

            val r = ">" + z + "\n" +  line.filter(!_.startsWith(">")).mkString + "\n"


            FileUtils.writeStringToFile(new File("D:\\藻类细胞器\\blast/" + y._1 + ".pep"), r, true)
          }

        }
      }
      Ok(Json.toJson("1"))
    }


  }

}

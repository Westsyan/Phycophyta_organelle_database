package controllers

import java.io.File

import dao.{mitochogeneDao, mitochondriaDao}
import javax.inject._
import models.Tables.MitochogeneRow
import play.api.libs.json.Json
import play.api.mvc._
import utils.Utils

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents,mitochogenedao:mitochogeneDao,
                               mitochondriadao:mitochondriaDao) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index : Action[AnyContent] = Action {implicit request=>
    Redirect(routes.HomeController.homeUS())
  }


  def homeUS : Action[AnyContent] = Action{implicit request=>
    Ok(views.html.english.home.home())
  }

  def homeCN : Action[AnyContent] = Action{implicit request=>
    Ok(views.html.chinese.home.home())
  }

  def insertGene = Action{implicit request=>
    val seqs = new File("F:\\database\\PODB\\download").listFiles()
    new File("D:\\藻类细胞器\\藻类细胞器数据库构建\\10个物种线粒体信息").listFiles().foreach{x=>
      val gff = x.listFiles().filter(_.getName == "genome.gff").head
      val name = x.getName.split(" ").init.mkString("_")
      val id = Await.result(mitochondriadao.getBySpecies(x.getName),Duration.Inf)
      val seq = seqs.filter(_.getName.contains(name))
      val cds = seq.filter(_.getName.endsWith("cds")).head
      val cdsMap = Utils.readFileToString(cds).split(">").tail.map{y=>
        val l = y.split("\n")
        (l.head.trim,l.tail.mkString)
      }.toMap
      val pep = seq.filter(_.getName.endsWith("pep")).head
      val pepMap = Utils.readFileToString(pep).split(">").tail.map{y=>
        val l = y.split("\n")
        (l.head.trim,l.tail.mkString)
      }.toMap
      val line = Utils.readLines(gff).filter(_.contains("mRNA"))
      val row = line.map{x=>
        val l = x.split("\t")
        val geneid = l.last.split("=").last
        MitochogeneRow(0,id.id.toString,geneid,l.head,l(3).toInt,l(4).toInt,l(6),cdsMap(geneid),pepMap(geneid))
      }
      Await.result(mitochogenedao.insert(row),Duration.Inf)
    }

   Ok(Json.toJson("1"))
  }

  def reference = Action{implicit request=>
    Ok(views.html.english.reference.reference())
  }

}

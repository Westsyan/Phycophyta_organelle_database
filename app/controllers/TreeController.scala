package controllers

import dao._
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import utils.TableUtils

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class TreeController @Inject()(mitochondriadao: mitochondriaDao,
                               mitpchogenomedao: mitochogenomeDao,
                               chloroplastDao: ChloroplastDao,
                               cpgenomeDao: CpgenomeDao) extends Controller {

  def tree = Action { implicit request =>
    Ok(views.html.english.tree.tree())
  }


  def getTreeJson = Action {
    val nodes = TableUtils.treeData
    Ok(Json.toJson(nodes))
  }

  def getInfoById(id: Int, sample: String) = Action {
    val (html, name) = if (sample == "Mitochondria") {
      val cp = Await.result(mitochondriadao.getById(id), Duration.Inf)
      val cpHtml =
        s"""
           |                    <tr>
           |                        <th>Species</th>
           |                        <td>${cp.species}</td>
           |                    </tr>
           |                    <tr>
           |                        <th>Phylum</th>
           |                        <td>${cp.phylum}</td>
           |                    </tr>
           |                    <tr>
           |                        <th>Classes</th>
           |                        <td>${cp.classes}</td>
           |                    </tr>
           |                    <tr>
           |                        <th>Order</th>
           |                        <td>${cp.order}</td>
           |                    </tr>
           |                    <tr>
           |                        <th>Family</th>
           |                        <td>${cp.family}</td>
           |                    </tr>
           |                    <tr>
           |                        <th>Genus</th>
           |                        <td>${cp.genus}</td>
           |                    </tr>
        """.stripMargin
      (cpHtml, cp.species)
    } else {
      ("", "")
    }
    Ok(Json.obj("html" -> html, "name" -> name))

  }
}

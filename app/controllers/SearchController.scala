package controllers

import dao.mitochondriaDao
import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

@Singleton
class SearchController @Inject()(mtdao: mitochondriaDao, cc: ControllerComponents)
                                (implicit exec: ExecutionContext) extends AbstractController(cc) {


  def searchBefore(db:String,input:String) = Action{implicit request=>
    Ok(views.html.english.search.search(db,input))
  }

  case class searchData(db: String, input: String)

  val searchForm = Form(
    mapping(
      "db" -> text,
      "input" -> text
    )(searchData.apply)(searchData.unapply)
  )


  def searchByDbInput(db:String,input:String) = Action { implicit request =>

    val option = Array("Species", "Phylum", "Class", "Order", "Family", "Genus", "Collection places", "Genbank",
      "Journal", "Title", "Authors")

    val keys = input.trim.split(" ")

    db match{
      case "all" => ""
      case "cp" => ""
      case "mt" => ""
    }

    val x = Await.result(mtdao.searchByInput(keys.head.trim), Duration.Inf)

    val flt = x.map(y => ((y.id, y.species,"Mitochondria"), Array(y.species, y.phylum, y.classes, y.order, y.family, y.genus,y.location,
      y.genbank,y.refJournal,y.refTitle,y.refAuthors)))

    val row = getSearchResult(flt, option, input)

    Ok(Json.toJson(row))
  }

  def getKeyWord(input: String, key: String) = {
    val index = input.toUpperCase.indexOf(key.toUpperCase)
    val value = input.slice(index, index + key.length)
    input.split("<span style='color:red;'>").map(_.split("</span>").
      map(_.replaceAll("(?i)" + key, "<span style='color:red;'>" + value + "</span>")).
      mkString("</span>")).mkString("<span style='color:red;'>")
  }

  def getKeyWordHead(input: String, key: String) = {
    val index = input.toUpperCase.indexOf(key.toUpperCase)
    val value = input.slice(index, index + key.length)
    input.replaceAll("(?i)" + key, "<span style='color:red;'>" + value + "</span>")
  }

  /**
    *
    * @param flts   第一次搜索结果： Int:结果ID，String:结果geneid，String:所属样品，Array[String]:需要展示的内容形成的数组
    * @param option ： 需要展示的内容的字段名
    * @param input  ： 根据空格分隔的关键词数组
    * @return ： 返回一个JSON对象，直接前端调用
    */
  def getSearchResult(flts: Seq[((Int, String,String), Array[String])], option: Array[String], input: String): Seq[JsObject] = {

    var results = Array[((Int, String,String), Int)]()

    val keys = input.trim.split(" ")
    var flt = flts

    if (keys.length > 1) {

      //过滤
      keys.tail.foreach { y =>
        flt = flt.filter(z => z._2.count(_.toUpperCase.contains(y.toUpperCase)) != 0)
      }

      //多次过滤结果，得到基因ID和对应的字段名的下标
      results = keys.flatMap { k =>
        flt.map { y =>
          (y._1, y._2.indexOf(y._2.filter(_.toUpperCase.contains(k.toUpperCase)).head))
        }
      }.distinct

    } else {
      results = flt.map { y =>
        (y._1, y._2.indexOf(y._2.filter(_.toUpperCase.contains(keys.head.toUpperCase)).head))
      }.distinct.toArray

    }

    val fltMap = flt.toMap


    //整合
    val rows = results.groupBy(_._1).map { y =>
      val re = y._2.distinct.map { z =>
        //得到结果json
        var resu = fltMap(y._1)(z._2)

        resu = getKeyWordHead(resu, keys.head)

        if (keys.length != 1) {
          keys.tail.foreach { k =>
            resu = getKeyWord(resu, k)
          }
        }

        Json.obj("option" -> option(z._2), "result" -> resu)
      }


      (y._1._2, Json.obj("id" -> y._1._1, "geneid" -> y._1._2, "species" -> y._1._3,  "result" -> re))
    }.toSeq.sortBy(_._1).map(_._2)


    rows
  }


}

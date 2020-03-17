package controllers

import java.io.File

import config.mapConfig
import dao.{locationDao, mitochondriaDao, worldDao}
import javax.inject.{Inject, Singleton}
import org.apache.commons.io.FileUtils
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import utils.Utils

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

@Singleton
class LocationController@Inject()(locationdao:locationDao,mtdao:mitochondriaDao,
                                  cc:ControllerComponents,worlddao:worldDao)
                                 (implicit exec:ExecutionContext)extends AbstractController(cc){

  def getMapData = Action{
    val json = FileUtils.readFileToString(new File(Utils.path,"config/china.json"))
    Ok(Json.parse(json))
  }


  def getInfoByLocation(location:String) = Action.async{
    worlddao.getByLocation(location).flatMap{x=>
      mtdao.getByIds(x.map(_.dataId)).map{y=>
        val json = y.map { z =>
          Json.obj("id" -> z.id,
            "species" -> z.species.split(" ").init.mkString(" "),
            "phylum" -> z.phylum.split(" ").init.mkString(" "),
            "class" -> z.classes.split(" ").init.mkString(" "),
            "order" -> z.order.split(" ").init.mkString(" "),
            "family" -> z.family.split(" ").init.mkString(" "),
            "genus" -> z.genus.split(" ").init.mkString(" "),
            "location" -> z.location, "genbank" -> z.genbank, "bp" -> z.bp, "ncbilink" -> z.ncbilink,
            "author" -> z.author, "ref_pubmed" -> z.refPubmed, "ref_journal" -> z.refJournal,
            "ref_title" -> z.refTitle, "ref_authors" -> z.refAuthors)
        }
        Ok(Json.toJson(json))
      }
    }
  }



  def getLocation(id:Int) = Action.async{
    worlddao.getByDataid(id).map{x=>

      val json = x.map{y=>
        Json.obj("key"-> y.location )
      }
      Ok(Json.toJson(json))
    }
  }

  def update = {
    mapConfig.loaction.foreach{x=>
      val row = Await.result(locationdao.getByLocation(x.take(2)),Duration.Inf)
      val id = Await.result(locationdao.getSidByLocation(x),Duration.Inf)
      Await.result(locationdao.updateNumber(id,row.length),Duration.Inf)
      mapConfig.addSpeciesInfo(id,row.size)
    }
  }

  def getWorldData = Action.async{implicit request=>
    worlddao.getAll.map{x=>
     Ok(Json.toJson(x.map{y=>
        Json.obj("key" -> y.location,"num" -> y.speciesNum)
      }))
    }
  }


}

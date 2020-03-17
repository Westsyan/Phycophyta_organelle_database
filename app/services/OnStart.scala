package services

import java.io.File

import dao._
import javax.inject._
import play.Logger
import play.api.libs.json.Json
import utils.{TableUtils, Utils}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

@Singleton
class OnStart @Inject()(mtdriadao: mitochondriaDao,
                        mitochogenomeDao:mitochogenomeDao,
                        mtgendao: mitochogeneDao,
                        cpDao:ChloroplastDao,
                        cpgenomeDao:CpgenomeDao) {


  Logger.info("开启成功！")

  TableUtils.mtMap = Await.result(mtdriadao.getAll, Duration.Inf)

  TableUtils.idToMt = TableUtils.mtMap.map(x=> (x.id,x.species)).toMap

  TableUtils.mtGeneMap = Await.result(mtgendao.getAll, Duration.Inf)

  Logger.info("wheatMap 添加成功")

  TableUtils.mtGeneToId = TableUtils.mtGeneMap.map(x => (x.geneid, x.id)).toMap

  Logger.info("geneidToId 添加成功")

  val js = new File(Utils.path + "/config/world.js")
  val line = Utils.readLines(js).filter(_.indexOf("type") != -1)
   TableUtils.mmap = line.map { x =>
    val p = x.indexOf("properties")
    val g = x.indexOf("geometry")
    val m = x.slice(p + 14, g - 4)
    val map = m.replaceAll("\"", "").split(",").map { y =>
      val e = y.split(":")
      (e.head.trim, e.last.trim)
    }.toMap
    (map("continent"), map("name"), map("hc-key"))
  }
  TableUtils.maxmap = TableUtils.mmap.groupBy(_._1)

  //getTreeData

  case class treeData(id: String, sample: String, species: String, phylum: String, classes: String,
                      order: String, family: String, genus: String)

  def getTreeData = {
    val mt = Await.result(mtdriadao.getAll, Duration.Inf)
    val genes = Await.result(mitochogenomeDao.getAll,Duration.Inf)

    val cp = Await.result(cpDao.getAll,Duration.Inf)
    val cpgenome = Await.result(cpgenomeDao.getAll,Duration.Inf)

    val m = genes.map{ y =>
      val x = mt.filter(_.id == y.mtid.toInt).head
      treeData(y.id.toString, "Mitochondria", x.species, x.phylum, x.classes, x.order, x.family, x.genus)
    }

    val c = cpgenome.map{x=>
      val y = cp.filter(_.id == x.cpid.toInt).head
      treeData(x.id.toString, "Chloroplast", y.species, y.phylum, y.classes, y.order, y.family, y.genus)
    }

    val x = c ++ m


    val gene = x.map{y=>

      val html = y.sample match{
        case "Mitochondria" => "<a onclick=\"getInfo(" + y.id + ",'Mitochondria')\"  style='color: inherit;'>MT" + "0"*(5-y.id.length) + y.id + "</a>"
        case "Chloroplast" => "<a onclick=\"getCpInfo(" + y.id + ",'Chloroplast')\"  style='color: inherit;'>CP" + "0"*(5-y.id.length) + y.id + "</a>"
      }

      (y.genus,y.species,Json.obj("text" -> html,"nodes" -> ""))
    }

    val species = x.map { y =>
      val html = y.sample match {
        case "Mitochondria" => "<a onclick=\"getInfo(" + y.id + ",'Mitochondria')\"  style='color: inherit;'>" + y.species + "</a>"
        case "Chloroplast" => "<a onclick=\"getCpInfo(" + y.id + ",'Chloroplast')\"  style='color: inherit;'>" + y.species + "</a>"
      }
      val node = gene.filter(_._1 == y.genus).filter(_._2 == y.species).map(_._3).distinct
      (y.family, y.genus, Json.obj("text" -> y.species, "tags" -> Array(node.size),"nodes" -> node))
    }

    val genus = x.map { y =>
      val node = species.filter(_._1 == y.family).filter(_._2 == y.genus).map(_._3).distinct
      (y.order, y.family, Json.obj("text" -> y.genus, "tags" -> Array(node.size), "nodes" -> node))
    }

    val family = x.map { y =>
      val node = genus.filter(_._1 == y.order).filter(_._2 == y.family).map(_._3).distinct
      (y.classes, y.order, Json.obj("text" -> y.family, "tags" -> Array(node.size), "nodes" -> node))
    }.distinct

    val order = x.map { y =>
      val node = family.filter(_._1 == y.classes).filter(_._2 == y.order).map(_._3).distinct
      (y.phylum,y.classes, Json.obj("text" -> y.order, "tags" -> Array(node.size), "nodes" -> node))
    }.distinct

    val classes = x.map{y=>
      val node = order.filter(_._1 == y.phylum).filter(_._2 == y.classes).map(_._3).distinct
      (y.sample,y.phylum, Json.obj("text" -> y.classes, "tags" -> Array(node.size), "nodes" -> node))
    }

    val phylum = x.map{y=>
      val node = classes.filter(_._1 == y.sample).filter(_._2 == y.phylum).map(_._3).distinct
      (y.sample, Json.obj("text" -> y.phylum, "tags" -> Array(node.size), "nodes" -> node))
    }

    val sample = x.map(_.sample).distinct

    val nodes = sample.map { y =>
      val node = phylum.filter(_._1 == y).map(_._2).distinct
      Json.obj("text" -> y, "tags" -> Array(node.size), "nodes" -> node)
    }

    TableUtils.treeData = nodes

  }
}




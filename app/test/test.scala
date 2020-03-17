package test

import java.io.File

import org.apache.commons.io.FileUtils
import utils.{ExecCommand, Utils}

import scala.collection.JavaConverters._

object test {

  def main(args: Array[String]): Unit = {


   val f = Utils.readFileToString("F:\\database\\PODB\\config/china.json")

   val j = Utils.jsonToMap(f)

    println(j("features"))
    val features = j("features").toList
 //   val w = Utils.jsonToMap(features)
  //  w.foreach(println)
  }


  def getGenome = {
    new File("F:\\database\\PODB\\blastData").listFiles().foreach{x=>
      val r = Utils.readLines(x)
      FileUtils.writeLines(new File("F:\\database\\PODB\\blastData/genome.fasta"),r.asJava,true)
    }

  }

  def filterCdsAndPep = {

    val p = "D:\\藻类细胞器\\藻类细胞器数据库构建\\10个物种线粒体信息"

    new File(p).listFiles().foreach{x=>
      x.listFiles().filter(x=> x.getName.endsWith("cds")|| x.getName.endsWith("pep")).foreach{y=>
        val r = Utils.readLines(y)
        val row = r.map{z=>
          if(z.startsWith(">")){
            z.split(" ").head
          }else{
            z
          }
        }
        FileUtils.writeLines(new File(y.getAbsolutePath + "1"),row.asJava)

      }
    }

  }

  def getPepAndCds = {
    val p = "D:\\藻类细胞器\\藻类细胞器数据库构建\\10个物种线粒体信息"

    new File(p).listFiles().foreach{x=>
      x.listFiles().filter(_.getName.endsWith("gb")).foreach{y=>
        val command1 = "perl D:/藻类细胞器/藻类细胞器数据库构建/genbank_parser_v4.0.pl --type cds \"" + y.getAbsolutePath + "\""
        val command2 = "perl D:/藻类细胞器/藻类细胞器数据库构建/genbank_parser_v4.0.pl --type pep \"" + y.getAbsolutePath + "\""

        println(command1)
        println(command2)
        val exec = new ExecCommand()
        exec.exect(Array(command1,command2),  x.getAbsolutePath )
      }
    }
  }
}

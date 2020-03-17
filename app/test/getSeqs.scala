package test

import java.io.File

import utils.ExecCommand

object getSeqs {


  def main(args: Array[String]): Unit = {


    getPepAndCds

  }

  def getPepAndCds = {
    val p = "D:\\藻类细胞器\\cpgb2"

    new File(p).listFiles().foreach{x=>

        val command1 = "perl D:/藻类细胞器/藻类细胞器数据库构建/genbank_parser_v4.0.pl --type cds " + x.getAbsolutePath + ""
        val command2 = "perl D:/藻类细胞器/藻类细胞器数据库构建/genbank_parser_v4.0.pl --type pep " + x.getAbsolutePath + ""

        println(command1)
        println(command2)
        val exec = new ExecCommand()
        exec.exect(Array(command1,command2),   "D:\\藻类细胞器\\cpgb2" )

    }
  }
}

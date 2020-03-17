package dao

import javax.inject.{Inject, Singleton}
import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CpgenomeDao  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                            (implicit exec:ExecutionContext)extends
  HasDatabaseConfigProvider[JdbcProfile]  {


  import profile.api._

  def insert(rows:Seq[CpgenomeRow]) : Future[Unit] = {
    db.run(Cpgenome ++= rows).map(_=>())
  }


  def getAll:Future[Seq[CpgenomeRow]] = {
    db.run(Cpgenome.result)
  }

  def getById(id:Int) : Future[CpgenomeRow] = {
    db.run(Cpgenome.filter(_.id === id).result.head)
  }

  def getByCpID(id:Int) : Future[Seq[CpgenomeRow]]= {
    db.run(Cpgenome.filter(_.cpid === id.toString).result)
  }

}

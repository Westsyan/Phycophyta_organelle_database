package dao

import javax.inject.{Inject, Singleton}
import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ChloroplastDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                              (implicit exec:ExecutionContext)extends
  HasDatabaseConfigProvider[JdbcProfile]  {

  import profile.api._

  def insertCp(row:Seq[ChloroplastRow]) : Future[Unit] = {
    db.run(Chloroplast ++= row).map(_=>())
  }

  def getAll : Future[Seq[ChloroplastRow]] = {
    db.run(Chloroplast.result)
  }

  def getById(id:Int) : Future[ChloroplastRow] = {
    db.run(Chloroplast.filter(_.id === id).result.head)
  }


}

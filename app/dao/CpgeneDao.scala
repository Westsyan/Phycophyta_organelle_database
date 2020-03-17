package dao

import javax.inject.Inject
import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class CpgeneDao   @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                           (implicit exec:ExecutionContext)extends
  HasDatabaseConfigProvider[JdbcProfile]  {

  import profile.api._


  def insert(row:Seq[CpgeneRow]) : Future[Unit] = {
    db.run(Cpgene ++= row).map(_=>())
  }

  def getByGb(gbid:Int) : Future[Seq[CpgeneRow]] = {
    db.run(Cpgene.filter(_.gbid === gbid).result)
  }

  def getById(id:Int) : Future[CpgeneRow] = {
    db.run(Cpgene.filter(_.id === id).result.head)
  }

}

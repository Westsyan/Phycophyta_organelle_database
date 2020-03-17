package dao

import javax.inject.Inject
import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class mitochogeneDao@Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit exec:ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]  {


  import profile.api._


  def insert(row:Seq[MitochogeneRow]) : Future[Unit] = {
    db.run(Mitochogene ++= row).map(_=>())
  }

  def getById(id:Int) : Future[MitochogeneRow] = {
    db.run(Mitochogene.filter(_.id === id).result.head)
  }

  def getAll:Future[Seq[MitochogeneRow]] = {
    db.run(Mitochogene.result)
  }

  def getBySpecies(species:String) : Future[Seq[MitochogeneRow]] = {
    db.run(Mitochogene.filter(_.species === species).result)
  }
}

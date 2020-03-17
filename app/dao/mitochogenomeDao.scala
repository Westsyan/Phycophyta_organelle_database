package dao

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import models.Tables._

import scala.concurrent.{ExecutionContext, Future}

class mitochogenomeDao@Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit exec:ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]  {


  import profile.api._

  def insert(row:Seq[MitochogenomeRow]) : Future[Unit] = {
    db.run(Mitochogenome ++= row).map(_=>())
  }

  def getAll : Future[Seq[MitochogenomeRow]] = {
    db.run(Mitochogenome.result)
  }


  def getById(id:Int) : Future[MitochogenomeRow] = {
    db.run(Mitochogenome.filter(_.id === id).result.head)
  }


  def getByName(name:String) : Future[MitochogenomeRow] = {
    db.run(Mitochogenome.filter(_.name === name).result.head)
  }

}

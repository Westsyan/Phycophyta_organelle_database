package dao

import javax.inject.Inject
import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class locationDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends
  HasDatabaseConfigProvider[JdbcProfile]  {


  import profile.api._

  def getByLocation(l:String) : Future[Seq[LocationRow]] = {
    db.run(Location.filter(_.location === l).result)
  }

  def getByDataid(dataid:Int) : Future[Seq[LocationRow]] ={
    db.run(Location.filter(_.dataId === dataid).result)
  }

  def getSidByLocation(location:String) : Future[Int] = {
    db.run(MtSpeciesNum.filter(_.location === location).map(_.id).result.head)
  }

  def updateNumber(id:Int,num:Int) : Future[Unit] = {
    db.run(MtSpeciesNum.filter(_.id === id).map(_.speciesNum).update(num)).map(_ =>())
  }


}

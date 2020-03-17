package dao

import javax.inject.Inject
import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class worldDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends
  HasDatabaseConfigProvider[JdbcProfile]  {


  import profile.api._

  def getByLocation(l:String) : Future[Seq[WorldLocationRow]] = {
    db.run(WorldLocation.filter(_.location === l).result)
  }

  def getByDataid(dataid:Int) : Future[Seq[WorldLocationRow]] ={
    db.run(WorldLocation.filter(_.dataId === dataid).result)
  }

  def getAll : Future[Seq[SpeciesNumRow]] = {
    db.run(SpeciesNum.result)
  }

  def getSidByLocation(location:String) : Future[Int] = {
    db.run(SpeciesNum.filter(_.location === location).map(_.id).result.head)
  }

  def updateNumber(id:Int,num:Int) : Future[Unit] = {
    db.run(SpeciesNum.filter(_.id === id).map(_.speciesNum).update(num)).map(_ =>())
  }


}

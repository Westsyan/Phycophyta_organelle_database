package dao

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import models.Tables._

import scala.concurrent.{ExecutionContext, Future}

class mitochondriaDao@Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit exec:ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {


  import profile.api._

  def getAll : Future[Seq[MitochondriaRow]] = {
    db.run(Mitochondria.result)
  }

  def searchByInput(input:String) : Future[Seq[MitochondriaRow]] = {
    val i = "%" + input + "%"

    db.run(Mitochondria.filter(x=> x.species.like(i) || x.phylum.like(i) || x.classes.like(i) ||
    x.order.like(i) || x.family.like(i) || x.genus.like(i) || x.location.like(i) || x.genbank.like(i) ||
    x.refJournal.like(i) || x.refTitle.like(i) || x.refAuthors.like(i)).result)
  }

  def getById(id:Int) : Future[MitochondriaRow] = {
    db.run(Mitochondria.filter(_.id === id).result.head)
  }

  def getByIds(ids:Seq[Int]) : Future[Seq[MitochondriaRow]] = {
    db.run(Mitochondria.filter(_.id.inSetBind(ids)).result)
  }

  def getBySpecies(species:String) : Future[MitochondriaRow] = {
    db.run(Mitochondria.filter(_.species === species).result.head)
  }


}

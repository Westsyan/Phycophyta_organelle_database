package dao


import javax.inject.Inject
import models.Tables._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext,Future}

class csvDao@Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit exec:ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  def getBySpecies(speciesId:String) : Future[Seq[CsvRow]] = {
    db.run(Csv.filter(_.species === speciesId).result)
  }

}

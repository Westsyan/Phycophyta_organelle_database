package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import com.github.tototoshi.slick.MySQLJodaSupport._
  import org.joda.time.DateTime
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(Chloroplast.schema, Cpgene.schema, Cpgenome.schema, Csv.schema, Location.schema, Mission.schema, Mitochogene.schema, Mitochogenome.schema, Mitochondria.schema, MtSpeciesNum.schema, SpeciesNum.schema, WorldLocation.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Chloroplast
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param species Database column species SqlType(VARCHAR), Length(255,true)
   *  @param phylum Database column phylum SqlType(TEXT)
   *  @param classes Database column classes SqlType(TEXT)
   *  @param order Database column order SqlType(TEXT)
   *  @param family Database column family SqlType(TEXT)
   *  @param genus Database column genus SqlType(TEXT)
   *  @param position Database column position SqlType(TEXT)
   *  @param genbank Database column genbank SqlType(TEXT)
   *  @param bp Database column bp SqlType(TEXT)
   *  @param configuration Database column configuration SqlType(TEXT)
   *  @param submiter Database column submiter SqlType(TEXT)
   *  @param pubmed Database column pubmed SqlType(TEXT)
   *  @param journal Database column journal SqlType(TEXT)
   *  @param title Database column title SqlType(TEXT)
   *  @param authors Database column authors SqlType(TEXT)
   *  @param colInfo Database column col_info SqlType(TEXT)
   *  @param colDate Database column col_date SqlType(TEXT) */
  case class ChloroplastRow(id: Int, species: String, phylum: String, classes: String, order: String, family: String, genus: String, position: String, genbank: String, bp: String, configuration: String, submiter: String, pubmed: String, journal: String, title: String, authors: String, colInfo: String, colDate: String)
  /** GetResult implicit for fetching ChloroplastRow objects using plain SQL queries */
  implicit def GetResultChloroplastRow(implicit e0: GR[Int], e1: GR[String]): GR[ChloroplastRow] = GR{
    prs => import prs._
    ChloroplastRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table chloroplast. Objects of this class serve as prototypes for rows in queries. */
  class Chloroplast(_tableTag: Tag) extends profile.api.Table[ChloroplastRow](_tableTag, Some("phycophyta_organelle_db"), "chloroplast") {
    def * = (id, species, phylum, classes, order, family, genus, position, genbank, bp, configuration, submiter, pubmed, journal, title, authors, colInfo, colDate) <> (ChloroplastRow.tupled, ChloroplastRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(species), Rep.Some(phylum), Rep.Some(classes), Rep.Some(order), Rep.Some(family), Rep.Some(genus), Rep.Some(position), Rep.Some(genbank), Rep.Some(bp), Rep.Some(configuration), Rep.Some(submiter), Rep.Some(pubmed), Rep.Some(journal), Rep.Some(title), Rep.Some(authors), Rep.Some(colInfo), Rep.Some(colDate))).shaped.<>({r=>import r._; _1.map(_=> ChloroplastRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get, _16.get, _17.get, _18.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column species SqlType(VARCHAR), Length(255,true) */
    val species: Rep[String] = column[String]("species", O.Length(255,varying=true))
    /** Database column phylum SqlType(TEXT) */
    val phylum: Rep[String] = column[String]("phylum")
    /** Database column classes SqlType(TEXT) */
    val classes: Rep[String] = column[String]("classes")
    /** Database column order SqlType(TEXT) */
    val order: Rep[String] = column[String]("order")
    /** Database column family SqlType(TEXT) */
    val family: Rep[String] = column[String]("family")
    /** Database column genus SqlType(TEXT) */
    val genus: Rep[String] = column[String]("genus")
    /** Database column position SqlType(TEXT) */
    val position: Rep[String] = column[String]("position")
    /** Database column genbank SqlType(TEXT) */
    val genbank: Rep[String] = column[String]("genbank")
    /** Database column bp SqlType(TEXT) */
    val bp: Rep[String] = column[String]("bp")
    /** Database column configuration SqlType(TEXT) */
    val configuration: Rep[String] = column[String]("configuration")
    /** Database column submiter SqlType(TEXT) */
    val submiter: Rep[String] = column[String]("submiter")
    /** Database column pubmed SqlType(TEXT) */
    val pubmed: Rep[String] = column[String]("pubmed")
    /** Database column journal SqlType(TEXT) */
    val journal: Rep[String] = column[String]("journal")
    /** Database column title SqlType(TEXT) */
    val title: Rep[String] = column[String]("title")
    /** Database column authors SqlType(TEXT) */
    val authors: Rep[String] = column[String]("authors")
    /** Database column col_info SqlType(TEXT) */
    val colInfo: Rep[String] = column[String]("col_info")
    /** Database column col_date SqlType(TEXT) */
    val colDate: Rep[String] = column[String]("col_date")

    /** Primary key of Chloroplast (database name chloroplast_PK) */
    val pk = primaryKey("chloroplast_PK", (id, species))
  }
  /** Collection-like TableQuery object for table Chloroplast */
  lazy val Chloroplast = new TableQuery(tag => new Chloroplast(tag))

  /** Entity class storing rows of table Cpgene
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param geneid Database column geneid SqlType(VARCHAR), Length(255,true)
   *  @param cpid Database column cpid SqlType(INT)
   *  @param gbid Database column gbid SqlType(INT)
   *  @param start Database column start SqlType(INT)
   *  @param end Database column end SqlType(INT)
   *  @param strand Database column strand SqlType(TEXT) */
  case class CpgeneRow(id: Int, geneid: String, cpid: Int, gbid: Int, start: Int, end: Int, strand: String)
  /** GetResult implicit for fetching CpgeneRow objects using plain SQL queries */
  implicit def GetResultCpgeneRow(implicit e0: GR[Int], e1: GR[String]): GR[CpgeneRow] = GR{
    prs => import prs._
    CpgeneRow.tupled((<<[Int], <<[String], <<[Int], <<[Int], <<[Int], <<[Int], <<[String]))
  }
  /** Table description of table cpgene. Objects of this class serve as prototypes for rows in queries. */
  class Cpgene(_tableTag: Tag) extends profile.api.Table[CpgeneRow](_tableTag, Some("phycophyta_organelle_db"), "cpgene") {
    def * = (id, geneid, cpid, gbid, start, end, strand) <> (CpgeneRow.tupled, CpgeneRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(geneid), Rep.Some(cpid), Rep.Some(gbid), Rep.Some(start), Rep.Some(end), Rep.Some(strand))).shaped.<>({r=>import r._; _1.map(_=> CpgeneRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column geneid SqlType(VARCHAR), Length(255,true) */
    val geneid: Rep[String] = column[String]("geneid", O.Length(255,varying=true))
    /** Database column cpid SqlType(INT) */
    val cpid: Rep[Int] = column[Int]("cpid")
    /** Database column gbid SqlType(INT) */
    val gbid: Rep[Int] = column[Int]("gbid")
    /** Database column start SqlType(INT) */
    val start: Rep[Int] = column[Int]("start")
    /** Database column end SqlType(INT) */
    val end: Rep[Int] = column[Int]("end")
    /** Database column strand SqlType(TEXT) */
    val strand: Rep[String] = column[String]("strand")

    /** Primary key of Cpgene (database name cpgene_PK) */
    val pk = primaryKey("cpgene_PK", (id, geneid))
  }
  /** Collection-like TableQuery object for table Cpgene */
  lazy val Cpgene = new TableQuery(tag => new Cpgene(tag))

  /** Entity class storing rows of table Cpgenome
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param cpid Database column cpid SqlType(TEXT)
   *  @param length Database column length SqlType(INT)
   *  @param gc Database column gc SqlType(VARCHAR), Length(255,true)
   *  @param atp Database column atp SqlType(TEXT)
   *  @param evt Database column evt SqlType(TEXT)
   *  @param nadh Database column nadh SqlType(TEXT)
   *  @param protein Database column protein SqlType(TEXT)
   *  @param orf Database column orf SqlType(TEXT)
   *  @param trna Database column tRna SqlType(TEXT)
   *  @param rrna Database column rRna SqlType(TEXT)
   *  @param other Database column other SqlType(TEXT)
   *  @param totle Database column totle SqlType(TEXT) */
  case class CpgenomeRow(id: Int, name: String, cpid: String, length: Int, gc: String, atp: String, evt: String, nadh: String, protein: String, orf: String, trna: String, rrna: String, other: String, totle: String)
  /** GetResult implicit for fetching CpgenomeRow objects using plain SQL queries */
  implicit def GetResultCpgenomeRow(implicit e0: GR[Int], e1: GR[String]): GR[CpgenomeRow] = GR{
    prs => import prs._
    CpgenomeRow.tupled((<<[Int], <<[String], <<[String], <<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table cpgenome. Objects of this class serve as prototypes for rows in queries. */
  class Cpgenome(_tableTag: Tag) extends profile.api.Table[CpgenomeRow](_tableTag, Some("phycophyta_organelle_db"), "cpgenome") {
    def * = (id, name, cpid, length, gc, atp, evt, nadh, protein, orf, trna, rrna, other, totle) <> (CpgenomeRow.tupled, CpgenomeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(name), Rep.Some(cpid), Rep.Some(length), Rep.Some(gc), Rep.Some(atp), Rep.Some(evt), Rep.Some(nadh), Rep.Some(protein), Rep.Some(orf), Rep.Some(trna), Rep.Some(rrna), Rep.Some(other), Rep.Some(totle))).shaped.<>({r=>import r._; _1.map(_=> CpgenomeRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column cpid SqlType(TEXT) */
    val cpid: Rep[String] = column[String]("cpid")
    /** Database column length SqlType(INT) */
    val length: Rep[Int] = column[Int]("length")
    /** Database column gc SqlType(VARCHAR), Length(255,true) */
    val gc: Rep[String] = column[String]("gc", O.Length(255,varying=true))
    /** Database column atp SqlType(TEXT) */
    val atp: Rep[String] = column[String]("atp")
    /** Database column evt SqlType(TEXT) */
    val evt: Rep[String] = column[String]("evt")
    /** Database column nadh SqlType(TEXT) */
    val nadh: Rep[String] = column[String]("nadh")
    /** Database column protein SqlType(TEXT) */
    val protein: Rep[String] = column[String]("protein")
    /** Database column orf SqlType(TEXT) */
    val orf: Rep[String] = column[String]("orf")
    /** Database column tRna SqlType(TEXT) */
    val trna: Rep[String] = column[String]("tRna")
    /** Database column rRna SqlType(TEXT) */
    val rrna: Rep[String] = column[String]("rRna")
    /** Database column other SqlType(TEXT) */
    val other: Rep[String] = column[String]("other")
    /** Database column totle SqlType(TEXT) */
    val totle: Rep[String] = column[String]("totle")

    /** Primary key of Cpgenome (database name cpgenome_PK) */
    val pk = primaryKey("cpgenome_PK", (id, name))
  }
  /** Collection-like TableQuery object for table Cpgenome */
  lazy val Cpgenome = new TableQuery(tag => new Cpgenome(tag))

  /** Entity class storing rows of table Csv
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param species Database column species SqlType(VARCHAR), Length(255,true)
   *  @param name Database column name SqlType(TEXT)
   *  @param types Database column types SqlType(TEXT)
   *  @param minimum Database column minimum SqlType(TEXT)
   *  @param maximun Database column maximun SqlType(TEXT)
   *  @param length Database column length SqlType(TEXT)
   *  @param direction Database column direction SqlType(TEXT)
   *  @param document Database column document SqlType(TEXT)
   *  @param seq Database column seq SqlType(TEXT)
   *  @param intervals Database column intervals SqlType(TEXT)
   *  @param track Database column track SqlType(TEXT)
   *  @param product Database column product SqlType(TEXT)
   *  @param translation Database column translation SqlType(TEXT)
   *  @param gene Database column gene SqlType(TEXT)
   *  @param seqName Database column seq_name SqlType(TEXT)
   *  @param dbXref Database column db_xref SqlType(TEXT)
   *  @param locustag Database column locustag SqlType(TEXT)
   *  @param proteinid Database column proteinid SqlType(TEXT) */
  case class CsvRow(id: Int, species: String, name: String, types: String, minimum: String, maximun: String, length: String, direction: String, document: String, seq: String, intervals: String, track: String, product: String, translation: String, gene: String, seqName: String, dbXref: String, locustag: String, proteinid: String)
  /** GetResult implicit for fetching CsvRow objects using plain SQL queries */
  implicit def GetResultCsvRow(implicit e0: GR[Int], e1: GR[String]): GR[CsvRow] = GR{
    prs => import prs._
    CsvRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table csv. Objects of this class serve as prototypes for rows in queries. */
  class Csv(_tableTag: Tag) extends profile.api.Table[CsvRow](_tableTag, Some("phycophyta_organelle_db"), "csv") {
    def * = (id, species, name, types, minimum, maximun, length, direction, document, seq, intervals, track, product, translation, gene, seqName, dbXref, locustag, proteinid) <> (CsvRow.tupled, CsvRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(species), Rep.Some(name), Rep.Some(types), Rep.Some(minimum), Rep.Some(maximun), Rep.Some(length), Rep.Some(direction), Rep.Some(document), Rep.Some(seq), Rep.Some(intervals), Rep.Some(track), Rep.Some(product), Rep.Some(translation), Rep.Some(gene), Rep.Some(seqName), Rep.Some(dbXref), Rep.Some(locustag), Rep.Some(proteinid))).shaped.<>({r=>import r._; _1.map(_=> CsvRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get, _16.get, _17.get, _18.get, _19.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column species SqlType(VARCHAR), Length(255,true) */
    val species: Rep[String] = column[String]("species", O.Length(255,varying=true))
    /** Database column name SqlType(TEXT) */
    val name: Rep[String] = column[String]("name")
    /** Database column types SqlType(TEXT) */
    val types: Rep[String] = column[String]("types")
    /** Database column minimum SqlType(TEXT) */
    val minimum: Rep[String] = column[String]("minimum")
    /** Database column maximun SqlType(TEXT) */
    val maximun: Rep[String] = column[String]("maximun")
    /** Database column length SqlType(TEXT) */
    val length: Rep[String] = column[String]("length")
    /** Database column direction SqlType(TEXT) */
    val direction: Rep[String] = column[String]("direction")
    /** Database column document SqlType(TEXT) */
    val document: Rep[String] = column[String]("document")
    /** Database column seq SqlType(TEXT) */
    val seq: Rep[String] = column[String]("seq")
    /** Database column intervals SqlType(TEXT) */
    val intervals: Rep[String] = column[String]("intervals")
    /** Database column track SqlType(TEXT) */
    val track: Rep[String] = column[String]("track")
    /** Database column product SqlType(TEXT) */
    val product: Rep[String] = column[String]("product")
    /** Database column translation SqlType(TEXT) */
    val translation: Rep[String] = column[String]("translation")
    /** Database column gene SqlType(TEXT) */
    val gene: Rep[String] = column[String]("gene")
    /** Database column seq_name SqlType(TEXT) */
    val seqName: Rep[String] = column[String]("seq_name")
    /** Database column db_xref SqlType(TEXT) */
    val dbXref: Rep[String] = column[String]("db_xref")
    /** Database column locustag SqlType(TEXT) */
    val locustag: Rep[String] = column[String]("locustag")
    /** Database column proteinid SqlType(TEXT) */
    val proteinid: Rep[String] = column[String]("proteinid")

    /** Primary key of Csv (database name csv_PK) */
    val pk = primaryKey("csv_PK", (id, species))
  }
  /** Collection-like TableQuery object for table Csv */
  lazy val Csv = new TableQuery(tag => new Csv(tag))

  /** Entity class storing rows of table Location
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param location Database column location SqlType(VARCHAR), Length(255,true)
   *  @param dataId Database column data_id SqlType(INT) */
  case class LocationRow(id: Int, location: String, dataId: Int)
  /** GetResult implicit for fetching LocationRow objects using plain SQL queries */
  implicit def GetResultLocationRow(implicit e0: GR[Int], e1: GR[String]): GR[LocationRow] = GR{
    prs => import prs._
    LocationRow.tupled((<<[Int], <<[String], <<[Int]))
  }
  /** Table description of table location. Objects of this class serve as prototypes for rows in queries. */
  class Location(_tableTag: Tag) extends profile.api.Table[LocationRow](_tableTag, Some("phycophyta_organelle_db"), "location") {
    def * = (id, location, dataId) <> (LocationRow.tupled, LocationRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(location), Rep.Some(dataId))).shaped.<>({r=>import r._; _1.map(_=> LocationRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column location SqlType(VARCHAR), Length(255,true) */
    val location: Rep[String] = column[String]("location", O.Length(255,varying=true))
    /** Database column data_id SqlType(INT) */
    val dataId: Rep[Int] = column[Int]("data_id")

    /** Primary key of Location (database name location_PK) */
    val pk = primaryKey("location_PK", (id, location))
  }
  /** Collection-like TableQuery object for table Location */
  lazy val Location = new TableQuery(tag => new Location(tag))

  /** Entity class storing rows of table Mission
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param missionname Database column missionName SqlType(VARCHAR), Length(255,true)
   *  @param userid Database column userId SqlType(INT)
   *  @param kind Database column kind SqlType(VARCHAR), Length(255,true)
   *  @param args Database column args SqlType(TEXT)
   *  @param starttime Database column startTime SqlType(DATETIME)
   *  @param endtime Database column endTime SqlType(DATETIME), Default(None)
   *  @param state Database column state SqlType(VARCHAR), Length(255,true) */
  case class MissionRow(id: Int, missionname: String, userid: Int, kind: String, args: String, starttime: DateTime, endtime: Option[DateTime] = None, state: String)
  /** GetResult implicit for fetching MissionRow objects using plain SQL queries */
  implicit def GetResultMissionRow(implicit e0: GR[Int], e1: GR[String], e2: GR[DateTime], e3: GR[Option[DateTime]]): GR[MissionRow] = GR{
    prs => import prs._
    MissionRow.tupled((<<[Int], <<[String], <<[Int], <<[String], <<[String], <<[DateTime], <<?[DateTime], <<[String]))
  }
  /** Table description of table mission. Objects of this class serve as prototypes for rows in queries. */
  class Mission(_tableTag: Tag) extends profile.api.Table[MissionRow](_tableTag, Some("phycophyta_organelle_db"), "mission") {
    def * = (id, missionname, userid, kind, args, starttime, endtime, state) <> (MissionRow.tupled, MissionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(missionname), Rep.Some(userid), Rep.Some(kind), Rep.Some(args), Rep.Some(starttime), endtime, Rep.Some(state))).shaped.<>({r=>import r._; _1.map(_=> MissionRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column missionName SqlType(VARCHAR), Length(255,true) */
    val missionname: Rep[String] = column[String]("missionName", O.Length(255,varying=true))
    /** Database column userId SqlType(INT) */
    val userid: Rep[Int] = column[Int]("userId")
    /** Database column kind SqlType(VARCHAR), Length(255,true) */
    val kind: Rep[String] = column[String]("kind", O.Length(255,varying=true))
    /** Database column args SqlType(TEXT) */
    val args: Rep[String] = column[String]("args")
    /** Database column startTime SqlType(DATETIME) */
    val starttime: Rep[DateTime] = column[DateTime]("startTime")
    /** Database column endTime SqlType(DATETIME), Default(None) */
    val endtime: Rep[Option[DateTime]] = column[Option[DateTime]]("endTime", O.Default(None))
    /** Database column state SqlType(VARCHAR), Length(255,true) */
    val state: Rep[String] = column[String]("state", O.Length(255,varying=true))
  }
  /** Collection-like TableQuery object for table Mission */
  lazy val Mission = new TableQuery(tag => new Mission(tag))

  /** Entity class storing rows of table Mitochogene
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param species Database column species SqlType(VARCHAR), Length(255,true)
   *  @param geneid Database column geneid SqlType(TEXT)
   *  @param genbank Database column genbank SqlType(TEXT)
   *  @param start Database column start SqlType(INT)
   *  @param end Database column end SqlType(INT)
   *  @param strand Database column strand SqlType(TEXT)
   *  @param cds Database column cds SqlType(TEXT)
   *  @param pep Database column pep SqlType(TEXT) */
  case class MitochogeneRow(id: Int, species: String, geneid: String, genbank: String, start: Int, end: Int, strand: String, cds: String, pep: String)
  /** GetResult implicit for fetching MitochogeneRow objects using plain SQL queries */
  implicit def GetResultMitochogeneRow(implicit e0: GR[Int], e1: GR[String]): GR[MitochogeneRow] = GR{
    prs => import prs._
    MitochogeneRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[Int], <<[Int], <<[String], <<[String], <<[String]))
  }
  /** Table description of table mitochogene. Objects of this class serve as prototypes for rows in queries. */
  class Mitochogene(_tableTag: Tag) extends profile.api.Table[MitochogeneRow](_tableTag, Some("phycophyta_organelle_db"), "mitochogene") {
    def * = (id, species, geneid, genbank, start, end, strand, cds, pep) <> (MitochogeneRow.tupled, MitochogeneRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(species), Rep.Some(geneid), Rep.Some(genbank), Rep.Some(start), Rep.Some(end), Rep.Some(strand), Rep.Some(cds), Rep.Some(pep))).shaped.<>({r=>import r._; _1.map(_=> MitochogeneRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column species SqlType(VARCHAR), Length(255,true) */
    val species: Rep[String] = column[String]("species", O.Length(255,varying=true))
    /** Database column geneid SqlType(TEXT) */
    val geneid: Rep[String] = column[String]("geneid")
    /** Database column genbank SqlType(TEXT) */
    val genbank: Rep[String] = column[String]("genbank")
    /** Database column start SqlType(INT) */
    val start: Rep[Int] = column[Int]("start")
    /** Database column end SqlType(INT) */
    val end: Rep[Int] = column[Int]("end")
    /** Database column strand SqlType(TEXT) */
    val strand: Rep[String] = column[String]("strand")
    /** Database column cds SqlType(TEXT) */
    val cds: Rep[String] = column[String]("cds")
    /** Database column pep SqlType(TEXT) */
    val pep: Rep[String] = column[String]("pep")

    /** Primary key of Mitochogene (database name mitochogene_PK) */
    val pk = primaryKey("mitochogene_PK", (id, species))
  }
  /** Collection-like TableQuery object for table Mitochogene */
  lazy val Mitochogene = new TableQuery(tag => new Mitochogene(tag))

  /** Entity class storing rows of table Mitochogenome
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param name Database column name SqlType(VARCHAR), Length(255,true)
   *  @param mtid Database column mtid SqlType(TEXT)
   *  @param length Database column length SqlType(INT)
   *  @param gc Database column gc SqlType(INT)
   *  @param atp Database column atp SqlType(TEXT)
   *  @param evt Database column evt SqlType(TEXT)
   *  @param nadh Database column nadh SqlType(TEXT)
   *  @param protein Database column protein SqlType(TEXT)
   *  @param orf Database column orf SqlType(TEXT)
   *  @param trna Database column tRna SqlType(TEXT)
   *  @param rrna Database column rRna SqlType(TEXT)
   *  @param other Database column other SqlType(TEXT)
   *  @param totle Database column totle SqlType(TEXT) */
  case class MitochogenomeRow(id: Int, name: String, mtid: String, length: Int, gc: Int, atp: String, evt: String, nadh: String, protein: String, orf: String, trna: String, rrna: String, other: String, totle: String)
  /** GetResult implicit for fetching MitochogenomeRow objects using plain SQL queries */
  implicit def GetResultMitochogenomeRow(implicit e0: GR[Int], e1: GR[String]): GR[MitochogenomeRow] = GR{
    prs => import prs._
    MitochogenomeRow.tupled((<<[Int], <<[String], <<[String], <<[Int], <<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table mitochogenome. Objects of this class serve as prototypes for rows in queries. */
  class Mitochogenome(_tableTag: Tag) extends profile.api.Table[MitochogenomeRow](_tableTag, Some("phycophyta_organelle_db"), "mitochogenome") {
    def * = (id, name, mtid, length, gc, atp, evt, nadh, protein, orf, trna, rrna, other, totle) <> (MitochogenomeRow.tupled, MitochogenomeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(name), Rep.Some(mtid), Rep.Some(length), Rep.Some(gc), Rep.Some(atp), Rep.Some(evt), Rep.Some(nadh), Rep.Some(protein), Rep.Some(orf), Rep.Some(trna), Rep.Some(rrna), Rep.Some(other), Rep.Some(totle))).shaped.<>({r=>import r._; _1.map(_=> MitochogenomeRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column name SqlType(VARCHAR), Length(255,true) */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column mtid SqlType(TEXT) */
    val mtid: Rep[String] = column[String]("mtid")
    /** Database column length SqlType(INT) */
    val length: Rep[Int] = column[Int]("length")
    /** Database column gc SqlType(INT) */
    val gc: Rep[Int] = column[Int]("gc")
    /** Database column atp SqlType(TEXT) */
    val atp: Rep[String] = column[String]("atp")
    /** Database column evt SqlType(TEXT) */
    val evt: Rep[String] = column[String]("evt")
    /** Database column nadh SqlType(TEXT) */
    val nadh: Rep[String] = column[String]("nadh")
    /** Database column protein SqlType(TEXT) */
    val protein: Rep[String] = column[String]("protein")
    /** Database column orf SqlType(TEXT) */
    val orf: Rep[String] = column[String]("orf")
    /** Database column tRna SqlType(TEXT) */
    val trna: Rep[String] = column[String]("tRna")
    /** Database column rRna SqlType(TEXT) */
    val rrna: Rep[String] = column[String]("rRna")
    /** Database column other SqlType(TEXT) */
    val other: Rep[String] = column[String]("other")
    /** Database column totle SqlType(TEXT) */
    val totle: Rep[String] = column[String]("totle")

    /** Primary key of Mitochogenome (database name mitochogenome_PK) */
    val pk = primaryKey("mitochogenome_PK", (id, name))
  }
  /** Collection-like TableQuery object for table Mitochogenome */
  lazy val Mitochogenome = new TableQuery(tag => new Mitochogenome(tag))

  /** Entity class storing rows of table Mitochondria
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param species Database column species SqlType(VARCHAR), Length(255,true)
   *  @param phylum Database column phylum SqlType(TEXT)
   *  @param classes Database column classes SqlType(TEXT)
   *  @param order Database column order SqlType(TEXT)
   *  @param family Database column family SqlType(TEXT)
   *  @param genus Database column genus SqlType(TEXT)
   *  @param location Database column location SqlType(TEXT)
   *  @param genbank Database column genbank SqlType(TEXT)
   *  @param bp Database column bp SqlType(TEXT)
   *  @param ncbilink Database column ncbilink SqlType(TEXT)
   *  @param author Database column author SqlType(TEXT)
   *  @param refPubmed Database column ref_pubmed SqlType(TEXT)
   *  @param refJournal Database column ref_journal SqlType(TEXT)
   *  @param refTitle Database column ref_title SqlType(TEXT)
   *  @param refAuthors Database column ref_authors SqlType(TEXT) */
  case class MitochondriaRow(id: Int, species: String, phylum: String, classes: String, order: String, family: String, genus: String, location: String, genbank: String, bp: String, ncbilink: String, author: String, refPubmed: String, refJournal: String, refTitle: String, refAuthors: String)
  /** GetResult implicit for fetching MitochondriaRow objects using plain SQL queries */
  implicit def GetResultMitochondriaRow(implicit e0: GR[Int], e1: GR[String]): GR[MitochondriaRow] = GR{
    prs => import prs._
    MitochondriaRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table mitochondria. Objects of this class serve as prototypes for rows in queries. */
  class Mitochondria(_tableTag: Tag) extends profile.api.Table[MitochondriaRow](_tableTag, Some("phycophyta_organelle_db"), "mitochondria") {
    def * = (id, species, phylum, classes, order, family, genus, location, genbank, bp, ncbilink, author, refPubmed, refJournal, refTitle, refAuthors) <> (MitochondriaRow.tupled, MitochondriaRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(species), Rep.Some(phylum), Rep.Some(classes), Rep.Some(order), Rep.Some(family), Rep.Some(genus), Rep.Some(location), Rep.Some(genbank), Rep.Some(bp), Rep.Some(ncbilink), Rep.Some(author), Rep.Some(refPubmed), Rep.Some(refJournal), Rep.Some(refTitle), Rep.Some(refAuthors))).shaped.<>({r=>import r._; _1.map(_=> MitochondriaRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get, _16.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column species SqlType(VARCHAR), Length(255,true) */
    val species: Rep[String] = column[String]("species", O.Length(255,varying=true))
    /** Database column phylum SqlType(TEXT) */
    val phylum: Rep[String] = column[String]("phylum")
    /** Database column classes SqlType(TEXT) */
    val classes: Rep[String] = column[String]("classes")
    /** Database column order SqlType(TEXT) */
    val order: Rep[String] = column[String]("order")
    /** Database column family SqlType(TEXT) */
    val family: Rep[String] = column[String]("family")
    /** Database column genus SqlType(TEXT) */
    val genus: Rep[String] = column[String]("genus")
    /** Database column location SqlType(TEXT) */
    val location: Rep[String] = column[String]("location")
    /** Database column genbank SqlType(TEXT) */
    val genbank: Rep[String] = column[String]("genbank")
    /** Database column bp SqlType(TEXT) */
    val bp: Rep[String] = column[String]("bp")
    /** Database column ncbilink SqlType(TEXT) */
    val ncbilink: Rep[String] = column[String]("ncbilink")
    /** Database column author SqlType(TEXT) */
    val author: Rep[String] = column[String]("author")
    /** Database column ref_pubmed SqlType(TEXT) */
    val refPubmed: Rep[String] = column[String]("ref_pubmed")
    /** Database column ref_journal SqlType(TEXT) */
    val refJournal: Rep[String] = column[String]("ref_journal")
    /** Database column ref_title SqlType(TEXT) */
    val refTitle: Rep[String] = column[String]("ref_title")
    /** Database column ref_authors SqlType(TEXT) */
    val refAuthors: Rep[String] = column[String]("ref_authors")

    /** Primary key of Mitochondria (database name mitochondria_PK) */
    val pk = primaryKey("mitochondria_PK", (id, species))
  }
  /** Collection-like TableQuery object for table Mitochondria */
  lazy val Mitochondria = new TableQuery(tag => new Mitochondria(tag))

  /** Entity class storing rows of table MtSpeciesNum
   *  @param id Database column id SqlType(INT)
   *  @param location Database column location SqlType(VARCHAR), Length(255,true)
   *  @param speciesNum Database column species_num SqlType(INT) */
  case class MtSpeciesNumRow(id: Int, location: String, speciesNum: Int)
  /** GetResult implicit for fetching MtSpeciesNumRow objects using plain SQL queries */
  implicit def GetResultMtSpeciesNumRow(implicit e0: GR[Int], e1: GR[String]): GR[MtSpeciesNumRow] = GR{
    prs => import prs._
    MtSpeciesNumRow.tupled((<<[Int], <<[String], <<[Int]))
  }
  /** Table description of table mt_species_num. Objects of this class serve as prototypes for rows in queries. */
  class MtSpeciesNum(_tableTag: Tag) extends profile.api.Table[MtSpeciesNumRow](_tableTag, Some("phycophyta_organelle_db"), "mt_species_num") {
    def * = (id, location, speciesNum) <> (MtSpeciesNumRow.tupled, MtSpeciesNumRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(location), Rep.Some(speciesNum))).shaped.<>({r=>import r._; _1.map(_=> MtSpeciesNumRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT) */
    val id: Rep[Int] = column[Int]("id")
    /** Database column location SqlType(VARCHAR), Length(255,true) */
    val location: Rep[String] = column[String]("location", O.Length(255,varying=true))
    /** Database column species_num SqlType(INT) */
    val speciesNum: Rep[Int] = column[Int]("species_num")

    /** Primary key of MtSpeciesNum (database name mt_species_num_PK) */
    val pk = primaryKey("mt_species_num_PK", (id, location))
  }
  /** Collection-like TableQuery object for table MtSpeciesNum */
  lazy val MtSpeciesNum = new TableQuery(tag => new MtSpeciesNum(tag))

  /** Entity class storing rows of table SpeciesNum
   *  @param id Database column id SqlType(INT)
   *  @param location Database column location SqlType(VARCHAR), Length(255,true)
   *  @param speciesNum Database column species_num SqlType(INT) */
  case class SpeciesNumRow(id: Int, location: String, speciesNum: Int)
  /** GetResult implicit for fetching SpeciesNumRow objects using plain SQL queries */
  implicit def GetResultSpeciesNumRow(implicit e0: GR[Int], e1: GR[String]): GR[SpeciesNumRow] = GR{
    prs => import prs._
    SpeciesNumRow.tupled((<<[Int], <<[String], <<[Int]))
  }
  /** Table description of table species_num. Objects of this class serve as prototypes for rows in queries. */
  class SpeciesNum(_tableTag: Tag) extends profile.api.Table[SpeciesNumRow](_tableTag, Some("phycophyta_organelle_db"), "species_num") {
    def * = (id, location, speciesNum) <> (SpeciesNumRow.tupled, SpeciesNumRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(location), Rep.Some(speciesNum))).shaped.<>({r=>import r._; _1.map(_=> SpeciesNumRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT) */
    val id: Rep[Int] = column[Int]("id")
    /** Database column location SqlType(VARCHAR), Length(255,true) */
    val location: Rep[String] = column[String]("location", O.Length(255,varying=true))
    /** Database column species_num SqlType(INT) */
    val speciesNum: Rep[Int] = column[Int]("species_num")

    /** Primary key of SpeciesNum (database name species_num_PK) */
    val pk = primaryKey("species_num_PK", (id, location))
  }
  /** Collection-like TableQuery object for table SpeciesNum */
  lazy val SpeciesNum = new TableQuery(tag => new SpeciesNum(tag))

  /** Entity class storing rows of table WorldLocation
   *  @param id Database column id SqlType(INT), AutoInc
   *  @param location Database column location SqlType(VARCHAR), Length(255,true)
   *  @param dataId Database column data_id SqlType(INT) */
  case class WorldLocationRow(id: Int, location: String, dataId: Int)
  /** GetResult implicit for fetching WorldLocationRow objects using plain SQL queries */
  implicit def GetResultWorldLocationRow(implicit e0: GR[Int], e1: GR[String]): GR[WorldLocationRow] = GR{
    prs => import prs._
    WorldLocationRow.tupled((<<[Int], <<[String], <<[Int]))
  }
  /** Table description of table world_location. Objects of this class serve as prototypes for rows in queries. */
  class WorldLocation(_tableTag: Tag) extends profile.api.Table[WorldLocationRow](_tableTag, Some("phycophyta_organelle_db"), "world_location") {
    def * = (id, location, dataId) <> (WorldLocationRow.tupled, WorldLocationRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(location), Rep.Some(dataId))).shaped.<>({r=>import r._; _1.map(_=> WorldLocationRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc */
    val id: Rep[Int] = column[Int]("id", O.AutoInc)
    /** Database column location SqlType(VARCHAR), Length(255,true) */
    val location: Rep[String] = column[String]("location", O.Length(255,varying=true))
    /** Database column data_id SqlType(INT) */
    val dataId: Rep[Int] = column[Int]("data_id")

    /** Primary key of WorldLocation (database name world_location_PK) */
    val pk = primaryKey("world_location_PK", (id, location))
  }
  /** Collection-like TableQuery object for table WorldLocation */
  lazy val WorldLocation = new TableQuery(tag => new WorldLocation(tag))
}

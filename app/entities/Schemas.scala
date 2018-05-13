package entities

import java.sql.Date

import entities.mappers.EntityMappers
import utils._

import slick.jdbc.H2Profile.api._

object Schemas extends EntityMappers {

  class Cats(tag: Tag) extends Table[Cat](tag, "cats") {
    def catId       = column[CatId]("id")
    def name        = column[String]("name")
    def pedigreeId  = column [PedigreeId]("pedigree_id")
    def gender      = column[Gender]("gender")
    def owner       = column[Option[OwnerId]]("owner_id")
    def dateOfBirth = column[Date]("date_of_birth")
    def dateOfDeath = column[Option[Date]]("date_of_death")

    override def *  =
      (catId, name, pedigreeId, gender, owner, dateOfBirth, dateOfDeath) <> (Cat.tupled, Cat.unapply)
  }

  class Owners(tag: Tag) extends Table[Owner](tag, "owners") {
    def ownerId     = column[OwnerId]("idX")
    def name        = column[String]("name")

    override def *  = (ownerId, name) <> (Owner.tupled, Owner.unapply)
  }

  class Pedigrees(tag: Tag) extends Table[Pedigree](tag, "pedigrees") {
    def id  = column[PedigreeId]("id")
    def pedigree    = column[String]("name")

    override def *  = (id, pedigree) <> (Pedigree.tupled, Pedigree.unapply)
  }

  val cats      = TableQuery[Cats]
  val owners    = TableQuery[Owners]
  val pedigress = TableQuery[Pedigrees]

}

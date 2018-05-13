package entities

import java.sql.Date

import utils.{CatId, OwnerId, PedigreeId}

case class Cat (
  catId: CatId,
  name: String,
  pedigree: PedigreeId,
  gender: Gender,
  owner: Option[OwnerId],
  dateOfBirth: Date,
  dateOfDeath: Option[Date]
)

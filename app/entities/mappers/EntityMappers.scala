package entities.mappers

import java.util.UUID

import entities.Gender
import utils._

import slick.jdbc.H2Profile.api._

trait EntityMappers extends IdsMapper with EnumMapper

trait EnumMapper {
  implicit val genderMapper: ColumnType[Gender] = MappedColumnType.base[Gender, String](
    gender => gender.entryName,
    genderString => Gender.withName(genderString)
  )
}

trait IdsMapper {
  implicit val catIdMapper: ColumnType[CatId] = MappedColumnType.base[CatId, String](
    CatId => CatId.id.toString,
    catIdString => CatId(UUID.fromString(catIdString))
  )

  implicit val ownerIdMapper: ColumnType[OwnerId] = MappedColumnType.base[OwnerId, String](
    ownerId => ownerId.id.toString,
    ownerIdString => OwnerId(UUID.fromString(ownerIdString))
  )

  implicit val pedigreeMapper: ColumnType[PedigreeId] = MappedColumnType.base[PedigreeId, String](
    pedigreeId => pedigreeId.id.toString,
    pedigreeIdString => PedigreeId(UUID.fromString(pedigreeIdString))
  )
}
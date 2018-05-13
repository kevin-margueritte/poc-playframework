package entities

import enumeratum.EnumEntry.Lowercase
import enumeratum._

sealed trait Gender extends EnumEntry with Lowercase

object Gender extends Enum[Gender] {
  val values = findValues

  case object Male    extends Gender
  case object Female  extends Gender
}

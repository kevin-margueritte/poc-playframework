package utils

case class ApiError(error: String) extends AnyVal

object ApiError {

  object Cats {
    val dateOfDeathBeforeDateOfBirth = ApiError("error.dateOfDeath.before.dateOfBirth")
    val catsDead = ApiError("error.cat.is.dead")
    val catNotFound = ApiError("error.cat.not.found")
    val catAlreadyAdopted = ApiError("error.cat.already.adopted")
  }

}

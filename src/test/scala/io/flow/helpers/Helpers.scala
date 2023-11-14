package io.flow.helpers

trait Helpers {

  def rightOrErrors[T](result: Either[Seq[String], T]): T = {
    result match {
      case Left(errors) => sys.error(s"Error: $errors")
      case Right(v) => v
    }
  }

  def validateError(
    message: String,
    result: Either[Seq[String], Any],
  ): Unit = {
    result match {
      case Left(errors) => {
        if (!errors.contains(message)) {
          sys.error(s"Expected error[$message] but got: $errors")
        }
      }
      case Right(_) => sys.error(s"Expected error[$message] but got successful result")
    }
  }

}

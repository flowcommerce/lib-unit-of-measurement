package io.flow.helpers

import cats.data.{Validated, ValidatedNec}
import cats.data.Validated.{Invalid, Valid}

trait Helpers {

  def rightOrErrors[T](result: Either[Seq[String], T]): T = {
    result match {
      case Left(errors) => sys.error(s"Error: $errors")
      case Right(v) => v
    }
  }

  def validateError(
    message: String,
    result: Either[Seq[String], Any]
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

  def expectValid[T](f: => Validated[_, T]): T = {
    f match {
      case Valid(r) => r
      case Invalid(error) => sys.error(s"Expected valid: $error")
    }
  }

  def expectInvalid[T](f: => Validated[T, _]): T = {
    f match {
      case Valid(_) => sys.error("Expected invalid")
      case Invalid(r) => r
    }
  }

  def expectInvalidNec[T](f: => ValidatedNec[T, _]): List[T] = {
    f match {
      case Valid(_) => sys.error("Expected invalid")
      case Invalid(r) => r.toNonEmptyList.toList
    }
  }
}
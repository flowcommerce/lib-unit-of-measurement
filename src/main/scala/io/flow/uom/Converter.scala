package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement
import io.flow.units.v0.models.{UnitOfLength, UnitOfVolume, UnitOfWeight}

import scala.math.BigDecimal.RoundingMode
import scala.util.{Failure, Success, Try}

case class Converter() {
  import InternalUnitOfMeasurement._

  def validateBigDecimal(value: String): Either[Seq[String], BigDecimal] = {
    Try {
      BigDecimal(value.trim)
    } match {
      case Failure(_) => Left(Seq(s"Invalid number[$value]"))
      case Success(num) => Right(num)
    }
  }

  def validatePositiveBigDecimal(value: String): Either[Seq[String], BigDecimal] = {
    validateBigDecimal(value) match {
      case Left(errors) => Left(errors)
      case Right(v) if v > 0 => Right(v)
      case Right(_) => Left(Seq(s"Invalid value[$value] - must be > 0"))
    }
  }

  /** Convert amount from amountUnits to targetUnits. You will get a Left if the units are not convertible (e.g mass to
    * length). Otherwise, we return a Right of the converted value.
    */
  def convert(
    amount: BigDecimal,
    amountUnits: UnitOfMeasurement,
    targetUnits: UnitOfMeasurement,
  ): Either[String, BigDecimal] = {
    if (amountUnits == targetUnits) {
      Right(amount)
    } else {
      UnitOfWeight.fromString(amountUnits.toString) match {
        case Some(weightUnit) => {
          UnitOfWeight.fromString(targetUnits.toString) match {
            case None => Left(s"Cannot convert $amountUnits to $targetUnits")
            case Some(target) => Right(v2.Weight(amount, weightUnit).convertTo(target).value)
          }
        }
        case None => {
          UnitOfLength.fromString(amountUnits.toString) match {
            case Some(lengthUnit) => {
              UnitOfLength.fromString(targetUnits.toString) match {
                case None => Left(s"Cannot convert $amountUnits to $targetUnits")
                case Some(target) => Right(v2.Length(amount, lengthUnit).convertTo(target).value)
              }
            }
            case None => {

              UnitOfVolume.fromString(amountUnits.toString) match {
                case Some(volumeUnit) =>
                  UnitOfVolume.fromString(targetUnits.toString) match {
                    case None => Left(s"Cannot convert $amountUnits to $targetUnits")
                    case Some(target) => Right(v2.Volume(amount, volumeUnit).convertTo(target).value)
                  }
                case None =>
                  Left(
                    s"Conversion only available for units of mass, length, and volume. $amountUnits is not a measurement of mass nor length nor volume. " +
                      s"Valid units: ${DefinedUnits.Mass.mkString(", ")}, ${DefinedUnits.Length
                          .mkString(", ")}, ${DefinedUnits.Volume.mkString(", ")}",
                  )
              }

            }
          }
        }
      }
    }
  }

  /** If uom is a valid unit of mass, returns its UnitOfMeasurement. otherwise returns a validation error
    */
  def validateUnitOfMass(uom: String): Either[Seq[String], UnitOfMeasurement] = {
    validateUnitOfMeasurement(uom) match {
      case Left(errors) => Left(errors)
      case Right(u) => validateUnitOfMass(u)(uom.trim)
    }
  }

  def validateUnitOfMass(
    uom: UnitOfMeasurement,
  )(implicit label: String = uom.toString): Either[Seq[String], UnitOfMeasurement] = {
    if (DefinedUnits.Mass.contains(uom)) {
      Right(uom)
    } else {
      Left(Seq(errorMessage("mass", label, DefinedUnits.Mass)))
    }
  }

  /** If uom is a valid unit of length, returns its UnitOfMeasurement. otherwise returns a validation error
    */
  def validateUnitOfLength(uom: String): Either[Seq[String], UnitOfMeasurement] = {
    validateUnitOfMeasurement(uom) match {
      case Left(errors) => Left(errors)
      case Right(u) => validateUnitOfLength(u)(uom.trim)
    }
  }

  def validateUnitOfLength(
    uom: UnitOfMeasurement,
  )(implicit label: String = uom.toString): Either[Seq[String], UnitOfMeasurement] = {
    if (DefinedUnits.Length.contains(uom)) {
      Right(uom)
    } else {
      Left(Seq(errorMessage("length", label, DefinedUnits.Length)))
    }
  }

  def validateUnitOfVolume(uom: String): Either[Seq[String], UnitOfMeasurement] = {
    validateUnitOfMeasurement(uom) match {
      case Left(errors) => Left(errors)
      case Right(u) => validateUnitOfVolume(u)(uom.trim)
    }
  }

  def validateUnitOfVolume(
    uom: UnitOfMeasurement,
  )(implicit label: String = uom.toString): Either[Seq[String], UnitOfMeasurement] = {
    if (DefinedUnits.Volume.contains(uom)) {
      Right(uom)
    } else {
      Left(Seq(errorMessage("volume", label, DefinedUnits.Volume)))
    }
  }

  def validateUnitOfMeasurement(uom: String): Either[Seq[String], UnitOfMeasurement] = {
    fromString(uom) match {
      case Some(units) => Right(units)
      case _ => Left(Seq(errorMessage("measurement", uom, UnitOfMeasurement.all)))
    }
  }

  def fromString(uom: String): Option[UnitOfMeasurement] = {
    AllInternalByName.get(uom.trim.toLowerCase)
  }

  def plural(uom: UnitOfMeasurement): String = {
    AllInternalByUom.getOrElse(uom, sys.error(s"Missing unit of measurement[$uom]")).plural
  }

  def singular(uom: UnitOfMeasurement): String = {
    AllInternalByUom.getOrElse(uom, sys.error(s"Missing unit of measurement[$uom]")).singular
  }

  def pluralize(value: BigDecimal, uom: UnitOfMeasurement): String = {
    if (value == 1) {
      "1 " + singular(uom)
    } else {
      val display = if (value == value.setScale(0, RoundingMode.HALF_UP)) {
        value.toInt.toString
      } else {
        value.toString
      }
      display + " " + plural(uom)
    }
  }

  private[this] def errorMessage(typ: String, uom: String, valid: List[UnitOfMeasurement]): String = {
    s"Invalid unit of $typ[${uom.trim}]. Must be one of: " + valid.map(_.toString).mkString(", ")
  }
}

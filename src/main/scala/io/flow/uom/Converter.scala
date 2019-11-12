package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement

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

  /**
    * Convert amount from amountUnits to targetUnits. You will get a
    * Left if the units are not convertible (e.g mass to
    * length). Otherwise, we return a Right of the converted value.
    */
  def convert(amount: BigDecimal, amountUnits: UnitOfMeasurement, targetUnits: UnitOfMeasurement): Either[String, BigDecimal] = {
    if (DefinedUnits.Mass.contains(amountUnits)) {
      convertMass(amount, amountUnits, targetUnits)
    } else if (DefinedUnits.Length.contains(amountUnits)) {
      convertLength(amount, amountUnits, targetUnits)
    } else {
      Left(s"Conversion only available for units of mass and length. $amountUnits is not a measurement of mass or length. " +
        s"Valid units: ${DefinedUnits.Mass.mkString(", ")}, ${DefinedUnits.Length.mkString(", ")}")
    }
  }

  private[this] def convertMass(amount: BigDecimal, amountUnits: UnitOfMeasurement, targetUnits: UnitOfMeasurement): Either[String, BigDecimal] = {
    exactlyConvert(amount, amountUnits, targetUnits) match {
      case Some(v) => Right(v)
      case None => {
        toGrams(amount, amountUnits) match {
          case Left(error) => {
            Left(error)
          }

          case Right(grams) => {
            targetUnits match {
              case UnitOfMeasurement.Millimeter => Left(s"Cannot convert $targetUnits to grams")
              case UnitOfMeasurement.Centimeter => Left(s"Cannot convert $targetUnits to grams")
              case UnitOfMeasurement.Inch => Left(s"Cannot convert $targetUnits to grams")
              case UnitOfMeasurement.Foot => Left(s"Cannot convert $targetUnits to grams")
              case UnitOfMeasurement.CubicInch => Left(s"Cannot convert $targetUnits to grams")
              case UnitOfMeasurement.CubicMeter => Left(s"Cannot convert $targetUnits to grams")
              case UnitOfMeasurement.Gram => Right(grams)
              case UnitOfMeasurement.Kilogram => Right(grams * .001)
              case UnitOfMeasurement.Meter => Left(s"Cannot convert $targetUnits to grams")
              case UnitOfMeasurement.Ounce => Right(grams * 0.03527392)
              case UnitOfMeasurement.Pound => Right(grams * 0.00220462)
              case UnitOfMeasurement.UNDEFINED(_) => Left(s"Cannot convert $targetUnits to grams")
            }
          }
        }
      }
    }
  }

  private[this] def convertLength(amount: BigDecimal, amountUnits: UnitOfMeasurement, targetUnits: UnitOfMeasurement): Either[String, BigDecimal] = {
    exactlyConvert(amount, amountUnits, targetUnits) match {
      case Some(v) => Right(v)
      case None => {
        toMillimeters(amount, amountUnits) match {
          case Left(error) => {
            Left(error)
          }

          case Right(millimeters) => {
            targetUnits match {
              case UnitOfMeasurement.Millimeter => Right(millimeters)
              case UnitOfMeasurement.Centimeter => Right(millimeters * .1)
              case UnitOfMeasurement.Inch => Right(millimeters * .0393701)
              case UnitOfMeasurement.Foot => Right(millimeters * .00328084)
              case UnitOfMeasurement.CubicInch => Left(s"Cannot convert $targetUnits to millimeters")
              case UnitOfMeasurement.CubicMeter => Left(s"Cannot convert $targetUnits to millimeters")
              case UnitOfMeasurement.Gram => Left(s"Cannot convert $targetUnits to millimeters")
              case UnitOfMeasurement.Kilogram => Left(s"Cannot convert $targetUnits to millimeters")
              case UnitOfMeasurement.Meter => Left(s"Cannot convert $targetUnits to millimeters")
              case UnitOfMeasurement.Ounce => Left(s"Cannot convert $targetUnits to millimeters")
              case UnitOfMeasurement.Pound => Left(s"Cannot convert $targetUnits to millimeters")
              case UnitOfMeasurement.UNDEFINED(_) => Left(s"Cannot convert $targetUnits to millimeters")
            }
          }
        }
      }
    }
  }

  private[this] def exactlyConvert(amount: BigDecimal, amountUnits: UnitOfMeasurement, targetUnits: UnitOfMeasurement): Option[BigDecimal] = {
    import UnitOfMeasurement._
    (amountUnits, targetUnits) match {
      case (Inch, Foot) => Some(amount / 12)
      case (Foot, Inch) => Some(amount * 12)
      case (Ounce, Pound) => Some(amount / 16)
      case (Pound, Ounce) => Some(amount * 16)
      case (_, _) => None
    }
  }

  def toGrams(amount: BigDecimal, amountUnits: UnitOfMeasurement): Either[String, BigDecimal] = {
    amountUnits match {
      case UnitOfMeasurement.Millimeter => Left(s"Cannot convert $amountUnits to grams")
      case UnitOfMeasurement.Centimeter => Left(s"Cannot convert $amountUnits to grams")
      case UnitOfMeasurement.Inch => Left(s"Cannot convert $amountUnits to grams")
      case UnitOfMeasurement.Foot => Left(s"Cannot convert $amountUnits to grams")
      case UnitOfMeasurement.CubicInch => Left(s"Cannot convert $amountUnits to grams")
      case UnitOfMeasurement.CubicMeter => Left(s"Cannot convert $amountUnits to grams")
      case UnitOfMeasurement.Gram => Right(amount)
      case UnitOfMeasurement.Kilogram => Right(amount * 1000)
      case UnitOfMeasurement.Meter => Left(s"Cannot convert $amountUnits to grams")
      case UnitOfMeasurement.Ounce => Right(amount * 28.3495)
      case UnitOfMeasurement.Pound => Right(amount * 453.592)
      case UnitOfMeasurement.UNDEFINED(_) => Left(s"Cannot convert $amountUnits to grams")
    }
  }

  def toMillimeters(amount: BigDecimal, amountUnits: UnitOfMeasurement): Either[String, BigDecimal] = {
    amountUnits match {
      case UnitOfMeasurement.Millimeter => Right(amount)
      case UnitOfMeasurement.Centimeter => Right(amount * 10)
      case UnitOfMeasurement.Inch => Right(amount * 25.4)
      case UnitOfMeasurement.Foot => Right(amount * 304.8)
      case UnitOfMeasurement.CubicInch => Left(s"Cannot convert $amountUnits to millimeters")
      case UnitOfMeasurement.CubicMeter => Left(s"Cannot convert $amountUnits to millimeters")
      case UnitOfMeasurement.Gram => Left(s"Cannot convert $amountUnits to millimeters")
      case UnitOfMeasurement.Kilogram => Left(s"Cannot convert $amountUnits to millimeters")
      case UnitOfMeasurement.Meter => Left(s"Cannot convert $amountUnits to millimeters")
      case UnitOfMeasurement.Ounce => Left(s"Cannot convert $amountUnits to millimeters")
      case UnitOfMeasurement.Pound => Left(s"Cannot convert $amountUnits to millimeters")
      case UnitOfMeasurement.UNDEFINED(_) => Left(s"Cannot convert $amountUnits to millimeters")
    }
  }

  /**
    * If uom is a valid unit of mass, returns its
    * UnitOfMeasurement. otherwise returns a validation error
    */
  def validateUnitOfMass(uom: String): Either[Seq[String], UnitOfMeasurement] = {
    validateUnitOfMeasurement(uom) match {
      case Left(errors) => Left(errors)
      case Right(u) => validateUnitOfMass(u)(uom.trim)
    }
  }

  def validateUnitOfMass(uom: UnitOfMeasurement)(implicit label: String = uom.toString): Either[Seq[String], UnitOfMeasurement] = {
    if (DefinedUnits.Mass.contains(uom)) {
      Right(uom)
    } else {
      Left(Seq(errorMessage("mass", label, DefinedUnits.Mass)))
    }
  }

  /**
    * If uom is a valid unit of length, returns its
    * UnitOfMeasurement. otherwise returns a validation error
    */
  def validateUnitOfLength(uom: String): Either[Seq[String], UnitOfMeasurement] = {
    validateUnitOfMeasurement(uom) match {
      case Left(errors) => Left(errors)
      case Right(u) => validateUnitOfLength(u)(uom.trim)
    }
  }

  def validateUnitOfLength(uom: UnitOfMeasurement)(implicit label: String = uom.toString): Either[Seq[String], UnitOfMeasurement] = {
    if (DefinedUnits.Length.contains(uom)) {
      Right(uom)
    } else {
      Left(Seq(errorMessage("length", label, DefinedUnits.Length)))
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

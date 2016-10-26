package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement
import scala.util.{Failure, Success, Try}

case class Converter() {

  val UnitsOfMass = UnitOfMeasurement.all.filter { uom =>
    uom match {
      case UnitOfMeasurement.Millimeter => false
      case UnitOfMeasurement.Centimeter => false
      case UnitOfMeasurement.Inch => false
      case UnitOfMeasurement.Foot => false
      case UnitOfMeasurement.CubicInch => false
      case UnitOfMeasurement.CubicMeter => false
      case UnitOfMeasurement.Gram => true
      case UnitOfMeasurement.Kilogram => true
      case UnitOfMeasurement.Meter => true
      case UnitOfMeasurement.Ounce => true
      case UnitOfMeasurement.Pound => true
      case UnitOfMeasurement.UNDEFINED(_) => false
    }
  }

  def validateBigDecimal(value: String): (Option[BigDecimal], Seq[String]) = {
    Try {
      BigDecimal(value.trim)
    } match {
      case Failure(_) => (None, Seq(s"Invalid number[$value]"))
      case Success(num) => (Some(num), Nil)
    }
  }

  def validatePositiveBigDecimal(value: String): (Option[BigDecimal], Seq[String]) = {
    validateBigDecimal(value) match {
      case (Some(num), Nil) => {
        num > 0 match {
          case true => (Some(num), Nil)
          case false => (None, Seq(s"Invalid value[$value] - must be > 0"))
        }
      }
      case (a, b) => {
        (a, b)
      }
    }
  }
  
  /**
    * Convert amount from amountUnits to targetUnits. You will get a
    * Left if the units are not convertible (e.g mass to
    * length). Otherwise, we return a Right of the converted value.
    */
  def convert(amount: BigDecimal, amountUnits: UnitOfMeasurement, targetUnits: UnitOfMeasurement): Either[String, BigDecimal] = {
    UnitsOfMass.contains(amountUnits) match {
      case true => convertMass(amount, amountUnits, targetUnits)
      case false => Left(s"Conversion only available for units of mass and $amountUnits is not a measurement of mass")
    }
  }

  private[this] def convertMass(amount: BigDecimal, amountUnits: UnitOfMeasurement, targetUnits: UnitOfMeasurement): Either[String, BigDecimal] = {
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

  /**
    * If uom is a valid unit of mass, returns its
    * UnitOfMeasurement. otherwise returns a validation error
    */
  def validateUnitOfMass(uom: String): (Option[UnitOfMeasurement], Seq[String]) = {
    validateUnitOfMeasurement(uom, valid = UnitsOfMass)
  }

  def validateUnitOfMeasurement(uom: String, valid: Seq[UnitOfMeasurement] = UnitOfMeasurement.all): (Option[UnitOfMeasurement], Seq[String]) = {
    import UnitOfMeasurement._

    val units = uom.trim.toLowerCase match {
      case "mm" | "millimeters" => Millimeter
      case "cm" | "centimeters" => Centimeter
      case "in" | "inches" => Inch
      case "ft" | "feet" => Foot
      case "g" | "grams" => Gram
      case "kg" | "kilograms" => Kilogram
      case "m" | "meters" => Meter
      case "oz" | "ounces" => Ounce
      case "lb" | "lbs" | "pounds" => Pound
      case other => UnitOfMeasurement(other)
    }

    valid.contains(units) match {
      case true => (Some(units), Nil)
      case false => (None, Seq(s"Invalid unit of measurement[${uom.trim}]. Must be one of: " + valid.mkString(", ")))
    }
  }

}

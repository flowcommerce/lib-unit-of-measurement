package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement
import scala.util.{Failure, Success, Try}

case class Converter() {

  val UnitsOfMass: List[UnitOfMeasurement] = UnitOfMeasurement.all.filter {
    case UnitOfMeasurement.Millimeter => false
    case UnitOfMeasurement.Centimeter => false
    case UnitOfMeasurement.Inch => false
    case UnitOfMeasurement.Foot => false
    case UnitOfMeasurement.CubicInch => false
    case UnitOfMeasurement.CubicMeter => false
    case UnitOfMeasurement.Gram => true
    case UnitOfMeasurement.Kilogram => true
    case UnitOfMeasurement.Meter => false
    case UnitOfMeasurement.Ounce => true
    case UnitOfMeasurement.Pound => true
    case UnitOfMeasurement.UNDEFINED(_) => false
  }

  val UnitsOfLength: List[UnitOfMeasurement] = UnitOfMeasurement.all.filter {
    case UnitOfMeasurement.Millimeter => true
    case UnitOfMeasurement.Centimeter => true
    case UnitOfMeasurement.Inch => true
    case UnitOfMeasurement.Foot => true
    case UnitOfMeasurement.CubicInch => false
    case UnitOfMeasurement.CubicMeter => false
    case UnitOfMeasurement.Gram => false
    case UnitOfMeasurement.Kilogram => false
    case UnitOfMeasurement.Meter => false
    case UnitOfMeasurement.Ounce => false
    case UnitOfMeasurement.Pound => false
    case UnitOfMeasurement.UNDEFINED(_) => false
  }

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
    if (UnitsOfMass.contains(amountUnits)) {
      convertMass(amount, amountUnits, targetUnits)
    } else if (UnitsOfLength.contains(amountUnits)) {
      convertLength(amount, amountUnits, targetUnits)
    } else {
      Left(s"Conversion only available for units of mass and length. $amountUnits is not a measurement of mass or length. " +
        s"Valid units: ${UnitsOfMass.mkString(", ")}, ${UnitsOfLength.mkString(", ")}")
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

  private[this] def convertLength(amount: BigDecimal, amountUnits: UnitOfMeasurement, targetUnits: UnitOfMeasurement): Either[String, BigDecimal] = {
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
    validateUnitOfMeasurement(uom, valid = UnitsOfMass)
  }

  /**
    * If uom is a valid unit of length, returns its
    * UnitOfMeasurement. otherwise returns a validation error
    */
  def validateUnitOfLength(uom: String): Either[Seq[String], UnitOfMeasurement] = {
    validateUnitOfMeasurement(uom, valid = UnitsOfLength)
  }

  def validateUnitOfMeasurement(uom: String, valid: Seq[UnitOfMeasurement] = UnitOfMeasurement.all): Either[Seq[String], UnitOfMeasurement] = {
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

    if (valid.contains(units)) {
      Right(units)
    } else {
      Left(
        Seq(s"Invalid unit of measurement[${uom.trim}]. Must be one of: " + valid.mkString(", "))
      )
    }
  }

}

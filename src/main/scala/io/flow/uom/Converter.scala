package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement
import scala.util.{Failure, Success, Try}

private[uom] case class InternalUnitOfMeasurement(
  uom: UnitOfMeasurement,
  plural: String,
  aliases: Seq[String],
  isMass: Boolean,
  isLength: Boolean
)

case class Converter() {

  private[this] val AllInternal = UnitOfMeasurement.all map {
    case uom@UnitOfMeasurement.Millimeter => InternalUnitOfMeasurement(
      uom = uom,
      plural = "millimeters",
      aliases = Seq("mm"),
      isMass = false,
      isLength = true
    )
    case uom@UnitOfMeasurement.Centimeter => InternalUnitOfMeasurement(
      uom = uom,
      plural = "centimeters",
      aliases = Seq("cm"),
      isMass = false,
      isLength = true
    )
    case uom@UnitOfMeasurement.Inch => InternalUnitOfMeasurement(
      uom = uom,
      plural = "inches",
      aliases = Seq("in"),
      isMass = false,
      isLength = true
    )
    case uom@UnitOfMeasurement.Foot => InternalUnitOfMeasurement(
      uom = uom,
      plural = "feet",
      aliases = Seq("ft"),
      isMass = false,
      isLength = true
    )
    case uom@UnitOfMeasurement.CubicInch => InternalUnitOfMeasurement(
      uom = uom,
      plural = "cubic_inches",
      aliases = Seq("cubic inch", "cubic inches"),
      isMass = false,
      isLength = true
    )
    case uom@UnitOfMeasurement.CubicMeter => InternalUnitOfMeasurement(
      uom = uom,
      plural = "cubic_meters",
      aliases = Seq("cubic meter", "cubic meters"),
      isMass = false,
      isLength = true
    )
    case uom@UnitOfMeasurement.Gram => InternalUnitOfMeasurement(
      uom = uom,
      plural = "grams",
      aliases = Seq("g"),
      isMass = true,
      isLength = false
    )
    case uom@UnitOfMeasurement.Kilogram => InternalUnitOfMeasurement(
      uom = uom,
      plural = "kilograms",
      aliases = Seq("kg", "kgs"),
      isMass = true,
      isLength = false
    )
    case uom@UnitOfMeasurement.Meter => InternalUnitOfMeasurement(
      uom = uom,
      plural = "meters",
      aliases = Seq("m"),
      isMass = false,
      isLength = true
    )
    case uom@UnitOfMeasurement.Ounce => InternalUnitOfMeasurement(
      uom = uom,
      plural = "ounces",
      aliases = Seq("oz"),
      isMass = true,
      isLength = false
    )
    case uom@UnitOfMeasurement.Pound => InternalUnitOfMeasurement(
      uom = uom,
      plural = "pounds",
      aliases = Seq("lb", "lbs"),
      isMass = true,
      isLength = false
    )
    case uom@UnitOfMeasurement.UNDEFINED(name) => InternalUnitOfMeasurement(
      uom = uom,
      plural = s"${name}s",
      aliases = Nil,
      isMass = false,
      isLength = false
    )
  }

  private[this] val AllInternalByName: Map[String, UnitOfMeasurement] = {
    AllInternal.flatMap { internal =>
      (Seq(internal.uom.toString, internal.plural) ++ internal.aliases).map { v =>
        v.toLowerCase.trim -> internal.uom
      }
    }.toMap
  }

  val UnitsOfMass: List[UnitOfMeasurement] = AllInternal.filter(_.isMass).map(_.uom)
  val UnitsOfLength: List[UnitOfMeasurement] = AllInternal.filter(_.isLength).map(_.uom)

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
    fromString(uom) match {
      case Some(units) if valid.contains(units) => Right(units)
      case _ => Left(
        Seq(s"Invalid unit of measurement[${uom.trim}]. Must be one of: " + valid.mkString(", "))
      )
    }
  }

  def fromString(uom: String): Option[UnitOfMeasurement] = {
    AllInternalByName.get(uom.trim.toLowerCase)
  }

}

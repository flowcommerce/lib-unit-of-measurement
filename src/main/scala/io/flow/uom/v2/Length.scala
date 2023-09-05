package io.flow.uom.v2

import io.flow.units.v0.models.UnitOfLength

case class Length(value: BigDecimal, unit: UnitOfLength) {
  def convertTo(targetUnits: UnitOfLength, scale: Int = 6): Length = {
    exactlyConvertTo(targetUnits).getOrElse {
      import UnitOfLength._


      val millis = toMillis
      def toLength(factor: BigDecimal): Length = {
        val v = (millis * factor).setScale(scale, BigDecimal.RoundingMode.HALF_UP)
        Length(v, targetUnits)
      }

      targetUnits match {
        case Millimeter => toLength(1)
        case Centimeter => toLength(.1)
        case Inch => toLength(1/25.4)
        case Foot => toLength(1.0/304.8)
        case Meter => toLength(1.0/304.8)
        case UNDEFINED(other) => sys.error(s"Invalid unit of length '$other'")
      }
    }
  }

  private[this] def exactlyConvertTo(targetUnit: UnitOfLength): Option[Length] = {
    import UnitOfLength._
    if (unit == targetUnit) {
      Some(this) // no need for conversion
    } else {
      (unit, targetUnit) match {
        case (Inch, Foot) => Some(Length(value / 12, Foot))
        case (Foot, Inch) => Some(Length(value * 12, Inch))
        case (_, _) => None
      }
    }
  }

  private[this] def toMillis: BigDecimal = {
    import UnitOfLength._
    unit match {
      case Millimeter => value
      case Centimeter => value * 10
      case Meter => value * 1000
      case Inch => value * 25.4
      case Foot => value * 304.8
      case UnitOfLength.UNDEFINED(other) => sys.error(s"Cannot convert $other to millimeters")
    }
  }
}

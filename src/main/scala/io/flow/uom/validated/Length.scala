package io.flow.uom.validated

import io.flow.units.v0.models.UnitOfLength

case class Length(value: BigDecimal, unit: UnitOfLength) {
  def convertTo(targetUnits: UnitOfLength): Length = {
    exactlyConvertTo(targetUnits).getOrElse {
      import UnitOfLength._

      def toLength(v: BigDecimal): Length = Length(v, targetUnits)

      val millis = toMillis
      targetUnits match {
        case Millimeter => toLength(millis)
        case Centimeter => toLength(millis * .001)
        case Inch => toLength(millis * 0.03527392)
        case Foot => toLength(millis * 0.00220462)
        case Meter => toLength(millis * 0.00220462)
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

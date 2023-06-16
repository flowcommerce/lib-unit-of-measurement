package io.flow.uom.validated

import io.flow.units.v0.models.UnitOfWeight

case class Weight(value: BigDecimal, unit: UnitOfWeight) {
  def convertTo(targetUnits: UnitOfWeight): Weight = {
    exactlyConvertTo(targetUnits).getOrElse {
      import UnitOfWeight._

      def toWeight(v: BigDecimal): Weight = Weight(v, targetUnits)

      val grams = toGrams
      println(s"$value $unit in grams is $grams. Target unit is $targetUnits")
      targetUnits match {
        case Gram => toWeight(grams)
        case Kilogram => toWeight(grams * .001)
        case Ounce => toWeight(grams * 0.03527392)
        case Pound => toWeight(grams * 0.00220462)
        case UNDEFINED(other) => sys.error(s"Invalid unit of weight '$other'")
      }
    }
  }

  private[this] def exactlyConvertTo(targetUnit: UnitOfWeight): Option[Weight] = {
    import UnitOfWeight._
    if (unit == targetUnit) {
      Some(this) // no need for conversion
    } else {
      (unit, targetUnit) match {
        case (Ounce, Pound) => Some(Weight(value / 16, targetUnit))
        case (Pound, Ounce) => Some(Weight(value * 16, targetUnit))
        case (_, _) => None
      }
    }
  }

  private[this] def toGrams: BigDecimal = {
    import UnitOfWeight._
    unit match {
      case Gram => value
      case Kilogram => (value * 1000)
      case Ounce => (value * 28.3495)
      case Pound => (value * 453.592)
      case UNDEFINED(_) => sys.error(s"Cannot convert $unit to grams")
    }
  }
}

package io.flow.uom.v2

import io.flow.units.v0.models.UnitOfVolume

case class Volume(value: BigDecimal, unit: UnitOfVolume) {
  def convertTo(targetUnits: UnitOfVolume, scale: Int = 6): Volume = {
    import UnitOfVolume._

    val cubicMillimeters = toCubicMillimeters

    def toVolume(factor: BigDecimal): Volume = {
      val v = (cubicMillimeters * factor).setScale(scale, BigDecimal.RoundingMode.HALF_UP)
      Volume(v, targetUnits)

    }

    targetUnits match {
      case CubicMillimeter => toVolume(1)
      case CubicCentimeter => toVolume(1.0 / 10 / 10 / 10)
      case CubicMeter => toVolume(1.0 / 1000 / 1000 / 1000)
      case CubicInch => toVolume(1.0 / 25.4 / 25.4 / 25.4)
      case CubicFoot => toVolume(1.0 / 304.8 / 304.8 / 304.8)
      case UNDEFINED(other) => sys.error(s"Invalid unit of volume $other")
    }

  }

  private[this] def toCubicMillimeters: BigDecimal = {
    import UnitOfVolume._
    unit match {
      case CubicMillimeter => value
      case CubicCentimeter => value * 10 * 10 * 10
      case CubicMeter => value * 1000 * 1000 * 1000
      case CubicInch => value * 25.4 * 25.4 * 25.4
      case CubicFoot => value * 304.8 * 304.8 * 304.8
      case UNDEFINED(other) => sys.error(s"Cannot convert $other to cubic millimeters")
    }
  }
}

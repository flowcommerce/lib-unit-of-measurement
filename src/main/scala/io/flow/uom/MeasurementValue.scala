package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement
import scala.util.{Failure, Success, Try}


case class MeasurementValue(value: BigDecimal, unit: UnitOfMeasurement) {
  def label(): String = Converter().pluralize(value, unit)
}

object MeasurementValue {
  private[this] val converter = Converter()

  def parse(value: String): Option[MeasurementValue] = {
    value.trim.split("\\s+").toList match {
      case a :: b :: Nil => {
        (toBigDecimalSafe(a), converter.validateUnitOfMeasurement(b)) match {
          case (Some(v), Right(uom)) => {
            Some(MeasurementValue(v, uom))
          }
          case (_, _) => None
        }
      }
      case _ => None
    }
  }

  def toBigDecimalSafe(value: String): Option[BigDecimal] = {
    Try {
      BigDecimal(value.trim)
    } match {
      case Success(v) => Some(v)
      case Failure(_) => None
    }
  }

}

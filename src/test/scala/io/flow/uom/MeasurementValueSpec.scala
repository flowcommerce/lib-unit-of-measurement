package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement._
import io.flow.helpers.Helpers
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class MeasurementValueSpec extends AnyFunSpec with Matchers with Helpers {

  it("parse") {
    MeasurementValue.parse("") should be(None)
    MeasurementValue.parse("5") should be(None)
    MeasurementValue.parse("feet") should be(None)
    MeasurementValue.parse("5 feet") should be(Some(MeasurementValue(5, Foot)))
    MeasurementValue.parse(" 5 FT ") should be(Some(MeasurementValue(5, Foot)))
    MeasurementValue.parse("1 feet") should be(Some(MeasurementValue(1, Foot)))
    MeasurementValue.parse("1.25 feet") should be(Some(MeasurementValue(1.25, Foot)))
  }

  it("label") {
    MeasurementValue(1, Foot).label() should be("1 foot")
    MeasurementValue(5, Foot).label() should be("5 feet")
  }

}

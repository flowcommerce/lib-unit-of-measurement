package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement
import org.scalatest.{FunSpec, Matchers}

class ConverterSpec extends FunSpec with Matchers {

  it("validateUnitOfMass") {
    Converter.validateUnitOfMass("in") should equal(
      (None, Seq("Invalid unit of measurement[in]. Must be one of: gram, kilogram, meter, ounce, pound"))
    )

    Converter.validateUnitOfMass("kg") should equal(
      (Some(UnitOfMeasurement.Kilogram), Nil)
    )

    Converter.UnitsOfMass.foreach { uom =>
      Converter.validateUnitOfMass(uom.toString) should equal(
        (Some(uom), Nil)
      )
    }
  }

  it("validateUnitOfMeasurement") {
    Converter.validateUnitOfMeasurement("mm") should be((Some(UnitOfMeasurement.Millimeter), Nil))
    Converter.validateUnitOfMeasurement("kg") should be((Some(UnitOfMeasurement.Kilogram), Nil))
    Converter.validateUnitOfMeasurement("grams") should be((Some(UnitOfMeasurement.Gram), Nil))
  }
  
  it("validateBigDecimal") {
    Converter.validateBigDecimal("1.234") should be((Some(1.234), Nil))
    Converter.validateBigDecimal("0") should be((Some(0), Nil))
    Converter.validateBigDecimal("1") should be((Some(1), Nil))
    Converter.validateBigDecimal("3.14159") should be((Some(3.14159), Nil))
    Converter.validateBigDecimal("-1") should be((Some(-1), Nil))
    Converter.validateBigDecimal("adslkfj") should be((None, (Seq("Invalid number[adslkfj]"))))
  }

  it("validatePositiveBigDecimal") {
    Converter.validatePositiveBigDecimal("1.234") should be((Some(1.234), Nil))
    Converter.validatePositiveBigDecimal("0") should be((None, (Seq("Invalid value[0] - must be > 0"))))
    Converter.validatePositiveBigDecimal("-1") should be((None, (Seq("Invalid value[-1] - must be > 0"))))
  }

  it("toGrams") {
    Converter.toGrams(2, UnitOfMeasurement.Gram) should be(Right(2))
    Converter.toGrams(2, UnitOfMeasurement.Ounce) should be(Right(56.699))
    Converter.toGrams(2, UnitOfMeasurement.Kilogram) should be(Right(2000))
    Converter.toGrams(2, UnitOfMeasurement.Pound) should be(Right(907.184))
  }

  it("convert") {
    Converter.convert(2, UnitOfMeasurement.Kilogram, UnitOfMeasurement.Gram) should be(Right(2000))
    Converter.convert(1500, UnitOfMeasurement.Gram, UnitOfMeasurement.Kilogram) should be(Right(1.5))
    Converter.convert(2.5, UnitOfMeasurement.Kilogram, UnitOfMeasurement.Pound) should be(Right(5.51155))
    Converter.convert(2.5, UnitOfMeasurement.Ounce, UnitOfMeasurement.Gram) should be(Right(70.87375))
  }

}

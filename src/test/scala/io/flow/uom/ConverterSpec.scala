package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement
import org.scalatest.{FunSpec, Matchers}

class ConverterSpec extends FunSpec with Matchers {

  val converter = Converter()

  it("validateUnitOfMass") {
    converter.validateUnitOfMass("in") should equal(
      (None, Seq("Invalid unit of measurement[in]. Must be one of: gram, kilogram, meter, ounce, pound"))
    )

    converter.validateUnitOfMass("kg") should equal(
      (Some(UnitOfMeasurement.Kilogram), Nil)
    )

    converter.UnitsOfMass.foreach { uom =>
      converter.validateUnitOfMass(uom.toString) should equal(
        (Some(uom), Nil)
      )
    }
  }

  it("validateUnitOfMeasurement") {
    converter.validateUnitOfMeasurement("mm") should be((Some(UnitOfMeasurement.Millimeter), Nil))
    converter.validateUnitOfMeasurement("kg") should be((Some(UnitOfMeasurement.Kilogram), Nil))
    converter.validateUnitOfMeasurement("grams") should be((Some(UnitOfMeasurement.Gram), Nil))
  }
  
  it("validateBigDecimal") {
    converter.validateBigDecimal("1.234") should be((Some(1.234), Nil))
    converter.validateBigDecimal("0") should be((Some(0), Nil))
    converter.validateBigDecimal("1") should be((Some(1), Nil))
    converter.validateBigDecimal("3.14159") should be((Some(3.14159), Nil))
    converter.validateBigDecimal("-1") should be((Some(-1), Nil))
    converter.validateBigDecimal("adslkfj") should be((None, (Seq("Invalid number[adslkfj]"))))
  }

  it("validatePositiveBigDecimal") {
    converter.validatePositiveBigDecimal("1.234") should be((Some(1.234), Nil))
    converter.validatePositiveBigDecimal("0") should be((None, (Seq("Invalid value[0] - must be > 0"))))
    converter.validatePositiveBigDecimal("-1") should be((None, (Seq("Invalid value[-1] - must be > 0"))))
  }

  it("toGrams") {
    converter.toGrams(2, UnitOfMeasurement.Gram) should be(Right(2))
    converter.toGrams(2, UnitOfMeasurement.Ounce) should be(Right(56.699))
    converter.toGrams(2, UnitOfMeasurement.Kilogram) should be(Right(2000))
    converter.toGrams(2, UnitOfMeasurement.Pound) should be(Right(907.184))
  }

  it("convert") {
    converter.convert(2, UnitOfMeasurement.Kilogram, UnitOfMeasurement.Gram) should be(Right(2000))
    converter.convert(1500, UnitOfMeasurement.Gram, UnitOfMeasurement.Kilogram) should be(Right(1.5))
    converter.convert(2.5, UnitOfMeasurement.Kilogram, UnitOfMeasurement.Pound) should be(Right(5.51155))
    converter.convert(2.5, UnitOfMeasurement.Ounce, UnitOfMeasurement.Gram) should be(Right(70.87375))
  }

}

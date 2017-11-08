package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement
import org.scalatest.{FunSpec, Matchers}

class ConverterSpec extends FunSpec with Matchers {

  val converter = Converter()

  it("validateUnitOfMass") {
    converter.validateUnitOfMass("in") should equal(
      (None,List("Invalid unit of measurement[in]. Must be one of: gram, kilogram, ounce, pound"))
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
    converter.convert(2, UnitOfMeasurement.Millimeter, UnitOfMeasurement.Millimeter) should be(Right(2))
    converter.convert(2, UnitOfMeasurement.Millimeter, UnitOfMeasurement.Inch) should be(Right(.0787402))
    converter.convert(2, UnitOfMeasurement.Inch, UnitOfMeasurement.Millimeter) should be(Right(50.8))
    converter.convert(6, UnitOfMeasurement.Inch, UnitOfMeasurement.Foot) should be(Right(.500000016))
    converter.convert(2, UnitOfMeasurement.Foot, UnitOfMeasurement.Inch) should be(Right(24.00001296))
    converter.convert(1000, UnitOfMeasurement.Millimeter, UnitOfMeasurement.Foot) should be(Right(3.28084))
    converter.convert(6, UnitOfMeasurement.Foot, UnitOfMeasurement.Millimeter) should be(Right(1828.8))
  }

  it("toMillimeters") {
    converter.toMillimeters(2, UnitOfMeasurement.Millimeter) should be(Right(2))
    converter.toMillimeters(2, UnitOfMeasurement.Centimeter) should be(Right(20))
    converter.toMillimeters(2, UnitOfMeasurement.Inch) should be(Right(50.8))
    converter.toMillimeters(2, UnitOfMeasurement.Foot) should be(Right(609.6))
  }

}

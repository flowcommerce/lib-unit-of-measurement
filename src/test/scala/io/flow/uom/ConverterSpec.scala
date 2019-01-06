package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement
import io.flow.helpers.Helpers
import org.scalatest.{FunSpec, Matchers}

class ConverterSpec extends FunSpec with Matchers with Helpers {

  private[this] val converter = Converter()

  it("validateUnitOfMass") {
    validateError(
      "Invalid unit of mass[in]. Must be one of: gram, kilogram, ounce, pound",
      converter.validateUnitOfMass("in")
    )

    validateError(
      "Invalid unit of mass[inch]. Must be one of: gram, kilogram, ounce, pound",
      converter.validateUnitOfMass(UnitOfMeasurement.Inch)
    )

    rightOrErrors {
      converter.validateUnitOfMass("kg")
    } should equal(UnitOfMeasurement.Kilogram)

    DefinedUnits.Mass.foreach { uom =>
      rightOrErrors {
        converter.validateUnitOfMass(uom)
      } should equal(uom)

      rightOrErrors {
        converter.validateUnitOfMass(uom.toString)
      } should equal(uom)
    }
  }

  it("validateUnitOfLength") {
    validateError(
      "Invalid unit of length[lb]. Must be one of: millimeter, centimeter, inch, foot, meter",
      converter.validateUnitOfLength("lb")
    )

    validateError(
      "Invalid unit of length[pound]. Must be one of: millimeter, centimeter, inch, foot, meter",
      converter.validateUnitOfLength(UnitOfMeasurement.Pound)
    )

    rightOrErrors {
      converter.validateUnitOfLength("in")
    } should equal(UnitOfMeasurement.Inch)

    DefinedUnits.Length.foreach { uom =>
      rightOrErrors {
        converter.validateUnitOfLength(uom)
      } should equal(uom)

      rightOrErrors {
        converter.validateUnitOfLength(uom.toString)
      } should equal(uom)
    }
  }

  it("validateUnitOfMeasurement") {
    rightOrErrors {
      converter.validateUnitOfMeasurement("mm")
    } should be(UnitOfMeasurement.Millimeter)

    rightOrErrors {
    converter.validateUnitOfMeasurement("kg")
    } should be(UnitOfMeasurement.Kilogram)

    rightOrErrors {
      converter.validateUnitOfMeasurement("grams")
    } should be(UnitOfMeasurement.Gram)
  }
  
  it("validateBigDecimal") {
    converter.validateBigDecimal("1.234") should be(Right(1.234))
    converter.validateBigDecimal("0") should be(Right(0))
    converter.validateBigDecimal("1") should be(Right(1))
    converter.validateBigDecimal("3.14159") should be(Right(3.14159))
    converter.validateBigDecimal("-1") should be(Right(-1))

    validateError(
      "Invalid number[adslkfj]",
      converter.validateBigDecimal("adslkfj")
    )
  }

  it("validatePositiveBigDecimal") {
    converter.validatePositiveBigDecimal("1.234") should be(Right(1.234))

    validateError(
      "Invalid value[0] - must be > 0",
      converter.validatePositiveBigDecimal("0")
    )
    validateError(
      "Invalid value[-1] - must be > 0",
      converter.validatePositiveBigDecimal("-1")
    )
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

  it("fromString") {
    Seq("lb", "lbs", "pound", "pounds", "LB", "LBS", "POUND", "POUNDS").foreach { unit =>
      converter.fromString(unit) should be(Some(UnitOfMeasurement.Pound))
    }

    converter.fromString("invalid pound unit") should be(None)
  }

  it("plural") {
    converter.plural(UnitOfMeasurement.Pound) should equal("pounds")
    converter.plural(UnitOfMeasurement.Gram) should equal("grams")
    converter.plural(UnitOfMeasurement.CubicInch) should equal("cubic_inches")
  }

  it("singular") {
    converter.singular(UnitOfMeasurement.Pound) should equal("pound")
    converter.singular(UnitOfMeasurement.Gram) should equal("gram")
    converter.singular(UnitOfMeasurement.CubicInch) should equal("cubic_inch")
  }

  it("pluralize") {
    converter.pluralize(1, UnitOfMeasurement.Pound) should equal("1 pound")
    converter.pluralize(BigDecimal(1.00), UnitOfMeasurement.Pound) should equal("1 pound")
    converter.pluralize(-1, UnitOfMeasurement.Pound) should equal("-1 pounds")
    converter.pluralize(BigDecimal(-1.00), UnitOfMeasurement.Pound) should equal("-1 pounds")
    converter.pluralize(BigDecimal(1.25), UnitOfMeasurement.Pound) should equal("1.25 pounds")

    converter.pluralize(0, UnitOfMeasurement.Inch) should equal("0 inches")
    converter.pluralize(1, UnitOfMeasurement.Inch) should equal("1 inch")
    converter.pluralize(10, UnitOfMeasurement.Inch) should equal("10 inches")
  }

}

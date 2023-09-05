package io.flow.uom.v2

import io.flow.helpers.Helpers
import io.flow.units.v0.models.UnitOfLength._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class LengthSpec extends AnyFunSpec with Matchers with Helpers {

  it("convertTo") {
    Length(2, Millimeter).convertTo(Millimeter) should be(Length(2, Millimeter))
    Length(254, Millimeter).convertTo(Inch) should be(Length(10, Inch))
    Length(2, Inch).convertTo(Millimeter) should be(Length(50.8, Millimeter))
    Length(1000, Millimeter).convertTo(Foot) should be(Length(3.28084, Foot))
    Length(6, Foot).convertTo(Millimeter) should be(Length(1828.8, Millimeter))
    Length(1, Foot).convertTo(Centimeter) should be (Length(30.48, Centimeter))
  }

  it("Exactly convertTo") {
    Length(6, Inch).convertTo(Foot) should be(Length(.5, Foot))
    Length(12, Inch).convertTo(Foot) should be(Length(1, Foot))
    Length(18, Inch).convertTo(Foot) should be(Length(1.5, Foot))
    Length(24, Inch).convertTo(Foot) should be(Length(2, Foot))
    Length(1, Foot).convertTo(Inch) should be(Length(12, Inch))
    Length(2, Foot).convertTo(Inch) should be(Length(24, Inch))
  }

  it("no-op if units match") {
    Length(3, Inch).convertTo(Inch) should be(Length(3, Inch))
    Length(3.14, Inch).convertTo(Inch) should be(Length(3.14, Inch))
  }
}

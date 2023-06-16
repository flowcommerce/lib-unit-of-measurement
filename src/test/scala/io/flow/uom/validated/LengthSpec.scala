package io.flow.uom.validated

import io.flow.helpers.Helpers
import io.flow.units.v0.models.UnitOfLength._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class LengthSpec extends AnyFunSpec with Matchers with Helpers {

  it("convertTo") {
    Length(2, Kilogram).convertTo(Gram) should be(Length(2000, Gram))
    Length(1500, Gram).convertTo(Kilogram) should be(Length(1.5, Kilogram))
    Length(2.5, Kilogram).convertTo(Pound) should be(Length(5.51155, Pound))
    Length(2.5, Ounce).convertTo(Gram) should be(Length(70.87375, Gram))
  }

  it("Exactly convertTo") {
    Length(8, Ounce).convertTo(Pound) should be(Length(.5, Pound))
    Length(16, Ounce).convertTo(Pound) should be(Length(1, Pound))
    Length(32, Ounce).convertTo(Pound) should be(Length(2, Pound))
    Length(1, Pound).convertTo(Ounce) should be(Length(16, Ounce))
  }

  it("no-op if units match") {
    Length(3, Pound).convertTo(Pound) should be(Length(3, Pound))
    Length(3.14, Pound).convertTo(Pound) should be(Length(3.14, Pound))
  }
}

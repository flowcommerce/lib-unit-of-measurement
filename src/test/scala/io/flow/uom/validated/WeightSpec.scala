package io.flow.uom.validated

import io.flow.units.v0.models.UnitOfWeight._
import io.flow.helpers.Helpers
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class WeightSpec extends AnyFunSpec with Matchers with Helpers {

  it("convertTo") {
    Weight(2, Kilogram).convertTo(Gram) should be(Weight(2000, Gram))
    Weight(1500, Gram).convertTo(Kilogram) should be(Weight(1.5, Kilogram))
    Weight(2.5, Kilogram).convertTo(Pound) should be(Weight(5.51155, Pound))
    Weight(2.5, Ounce).convertTo(Gram) should be(Weight(70.87375, Gram))
  }

  it("Exactly convertTo") {
    Weight(8, Ounce).convertTo(Pound) should be(Weight(.5, Pound))
    Weight(16, Ounce).convertTo(Pound) should be(Weight(1, Pound))
    Weight(32, Ounce).convertTo(Pound) should be(Weight(2, Pound))
    Weight(1, Pound).convertTo(Ounce) should be(Weight(16, Ounce))
  }

  it("no-op if units match") {
    Weight(3, Pound).convertTo(Pound) should be(Weight(3, Pound))
    Weight(3.14, Pound).convertTo(Pound) should be(Weight(3.14, Pound))
  }
}

package io.flow.uom.v2.readme

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

// Make sure examples in readme compile
class ReadmeSpec extends AnyFunSpec with Matchers {

  it("usage") {
    import io.flow.units.v0.models.{UnitOfLength, UnitOfWeight}
    import io.flow.uom.v2.{Length, Weight}

    val pounds = Weight(1, UnitOfWeight.Kilogram).convertTo(UnitOfWeight.Pound)
    println(s"1 kilogram is equivalent to ${pounds.value} pounds")

    val feet = Length(12, UnitOfLength.Inch).convertTo(UnitOfLength.Foot)
    println(s"12 inches is equivalent to ${feet.value} foot")
  }
}

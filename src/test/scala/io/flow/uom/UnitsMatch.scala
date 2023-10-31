package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement
import io.flow.units.v0.models._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class UnitsMatch extends AnyFunSpec with Matchers {

  def testAll(values: Seq[String]): Unit = {
    values.foreach { unit =>
      UnitOfMeasurement
        .fromString(unit)
        .getOrElse {
          sys.error(s"Missing unit: '$unit'")
        }
        .toString shouldBe unit
    }
  }

  it("UnitOfLength") {
    testAll(UnitOfLength.all.map(_.toString))
  }

  it("UnitOfWeight") {
    testAll(UnitOfWeight.all.map(_.toString))
  }

  it("UnitOfVolume") {
    testAll(UnitOfVolume.all.map(_.toString))
  }

  it("Every UnitOfMeasurement found in one of the other specific enums") {
    UnitOfMeasurement.all.filterNot { unit =>
      val v = unit.toString
      UnitOfLength.fromString(v).isDefined || UnitOfWeight.fromString(v).isDefined || UnitOfVolume
        .fromString(v)
        .isDefined
    } shouldBe Nil
  }

}

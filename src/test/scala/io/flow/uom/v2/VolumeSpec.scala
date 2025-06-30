package io.flow.uom.v2

import io.flow.helpers.Helpers
import io.flow.units.v0.models.UnitOfVolume._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class VolumeSpec extends AnyFunSpec with Matchers with Helpers {

  it("convertTo") {
    Volume(2, CubicMillimeter).convertTo(CubicMillimeter) should be(Volume(2, CubicMillimeter))
    Volume(254, CubicMillimeter).convertTo(CubicInch) should be(Volume(0.0155, CubicInch))
    Volume(2, CubicInch).convertTo(CubicMillimeter) should be(Volume(32774.128, CubicMillimeter))
    Volume(1000, CubicMillimeter).convertTo(CubicFoot) should be(Volume(0.000035, CubicFoot))
    Volume(6, CubicFoot).convertTo(CubicMillimeter) should be(Volume(169901079.552, CubicMillimeter))
    Volume(1, CubicFoot).convertTo(CubicCentimeter) should be(Volume(28316.846592, CubicCentimeter))
    Volume(1000, CubicMillimeter).convertTo(CubicMeter) should be(Volume(0.000001, CubicMeter))
  }

  it("no-op if units match") {
    Volume(3, CubicInch).convertTo(CubicInch) should be(Volume(3, CubicInch))
    Volume(3.14, CubicCentimeter).convertTo(CubicCentimeter) should be(Volume(3.14, CubicCentimeter))
  }

}

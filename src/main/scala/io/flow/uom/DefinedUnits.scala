package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement

object DefinedUnits {

  val Mass: List[UnitOfMeasurement] = InternalUnitOfMeasurement.AllInternal.collect { case u: InternalUnitOfMass =>
    u.uom
  }

  val Length: List[UnitOfMeasurement] = InternalUnitOfMeasurement.AllInternal.collect { case u: InternalUnitOfLength =>
    u.uom
  }

  val Volume: List[UnitOfMeasurement] = InternalUnitOfMeasurement.AllInternal.collect { case u: InternalUnitOfVolume =>
    u.uom
  }

}

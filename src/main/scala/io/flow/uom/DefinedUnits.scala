package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement

object DefinedUnits {

  val Mass: List[UnitOfMeasurement] = InternalUnitOfMeasurement.AllInternal.flatMap {
    case u: InternalUnitOfMass => Some(u.uom)
    case _ => None
  }

  val Length: List[UnitOfMeasurement] = InternalUnitOfMeasurement.AllInternal.flatMap {
    case u: InternalUnitOfLength => Some(u.uom)
    case _ => None
  }

}









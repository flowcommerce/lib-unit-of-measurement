package io.flow.uom

import io.flow.common.v0.models.UnitOfMeasurement

private[uom] sealed trait InternalUnitOfMeasurement {
  def uom: UnitOfMeasurement
  def singular: String
  def plural: String
  def aliases: Seq[String]
}

private[uom] case class InternalUnitOfMass(
  uom: UnitOfMeasurement,
  singular: String,
  plural: String,
  aliases: Seq[String],
) extends InternalUnitOfMeasurement

private[uom] case class InternalUnitOfVolume(
  uom: UnitOfMeasurement,
  singular: String,
  plural: String,
  aliases: Seq[String],
) extends InternalUnitOfMeasurement

private[uom] case class InternalUnitOfLength(
  uom: UnitOfMeasurement,
  singular: String,
  plural: String,
  aliases: Seq[String],
) extends InternalUnitOfMeasurement

private[uom] object InternalUnitOfMeasurement {

  private[uom] val AllInternal: List[InternalUnitOfMeasurement] = UnitOfMeasurement.all map {
    case uom: UnitOfMeasurement.Millimeter.type =>
      InternalUnitOfLength(
        uom = uom,
        singular = "millimeter",
        plural = "millimeters",
        aliases = Seq("mm"),
      )
    case uom: UnitOfMeasurement.Centimeter.type =>
      InternalUnitOfLength(
        uom = uom,
        singular = "centimeter",
        plural = "centimeters",
        aliases = Seq("cm"),
      )
    case uom: UnitOfMeasurement.Inch.type =>
      InternalUnitOfLength(
        uom = uom,
        singular = "inch",
        plural = "inches",
        aliases = Seq("in"),
      )
    case uom: UnitOfMeasurement.Foot.type =>
      InternalUnitOfLength(
        uom = uom,
        singular = "foot",
        plural = "feet",
        aliases = Seq("ft"),
      )
    case uom: UnitOfMeasurement.CubicInch.type =>
      InternalUnitOfVolume(
        uom = uom,
        singular = "cubic_inch",
        plural = "cubic_inches",
        aliases = Seq("cubic inch", "cubic inches"),
      )
    case uom: UnitOfMeasurement.CubicMeter.type =>
      InternalUnitOfVolume(
        uom = uom,
        singular = "cubic_meter",
        plural = "cubic_meters",
        aliases = Seq("cubic meter", "cubic meters"),
      )
    case uom: UnitOfMeasurement.Gram.type =>
      InternalUnitOfMass(
        uom = uom,
        singular = "gram",
        plural = "grams",
        aliases = Seq("g"),
      )
    case uom: UnitOfMeasurement.Kilogram.type =>
      InternalUnitOfMass(
        uom = uom,
        singular = "kilogram",
        plural = "kilograms",
        aliases = Seq("kg", "kgs"),
      )
    case uom: UnitOfMeasurement.Meter.type =>
      InternalUnitOfLength(
        uom = uom,
        singular = "meter",
        plural = "meters",
        aliases = Seq("m"),
      )
    case uom: UnitOfMeasurement.Ounce.type =>
      InternalUnitOfMass(
        uom = uom,
        singular = "ounce",
        plural = "ounces",
        aliases = Seq("oz"),
      )
    case uom: UnitOfMeasurement.Pound.type =>
      InternalUnitOfMass(
        uom = uom,
        singular = "pound",
        plural = "pounds",
        aliases = Seq("lb", "lbs"),
      )
    case UnitOfMeasurement.UNDEFINED(name) => sys.error(s"UnitOfMeasurement.UNDEFINED($name)")
  }

  val AllInternalByName: Map[String, UnitOfMeasurement] = {
    AllInternal.flatMap { internal =>
      (Seq(internal.uom.toString, internal.plural) ++ internal.aliases).map { v =>
        v.toLowerCase.trim -> internal.uom
      }
    }.toMap
  }

  val AllInternalByUom: Map[UnitOfMeasurement, InternalUnitOfMeasurement] = {
    AllInternal.map { internal =>
      internal.uom -> internal
    }.toMap
  }

}

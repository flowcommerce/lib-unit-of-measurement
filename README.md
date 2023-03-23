[![Build Status](https://travis-ci.org/flowcommerce/lib-unit-of-measurement.svg?branch=main)](https://travis-ci.org/flowcommerce/lib-unit-of-measurement)

Scala library to aid with conversion of units of measurement.

# Installation

```
   "io.flow" %% "lib-unit-of-measurement" % "0.0.96"
```

# Usage

```
  import io.flow.common.v0.models.UnitOfMeasurement
  import io.flow.uom.Converter

  val converter = Converter()

  converter.fromString("lbs") // => Some(UnitOfMeasurement.Pound)

  converter.convert(2, UnitOfMeasurement.Kilogram, UnitOfMeasurement.Gram) match {
    case Left(errors) => println(error)
    case Right(grams) => println(s"2 KG = $grams grams")
  }

  converter.toGrams(2, UnitOfMeasurement.Gram) match {
    case Left(errors) => println(error)
    case Right(grams) => println(s"$grams grams")
  }
```

# Known Units of Mass

Supports conversion among:

  - gram
  - kilogram
  - meter
  - ounce
  - pound

Units are as defined as by JSR 363 - see http://jscience.org/api/javax/measure/unit/SI.html and http://jscience.org/api/javax/measure/unit/NonSI.html

# Parsing strings into units of measurement

Library supports case insensitve parsing of strings into units of
measurement, including singular, plural and short hand forms.

Example:

```
  import io.flow.uom.Converter
  val converter = Converter()

  converter.validateUnitOfMeasurement("mm") match {
    case Left(errors) => println(error)
    case Right(uom) => println(s"mm maps to $uom")
  }

converter.validateUnitOfMass("kg") match {
    case Left(errors) => println(error)
    case Right(uom) => println(s"kg maps to $uom")
  }
```
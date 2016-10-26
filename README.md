[![Build Status](https://travis-ci.org/flowcommerce/lib-unit-of-measurement.svg?branch=master)](https://travis-ci.org/flowcommerce/lib-unit-of-measurement)

Scala library to aid with conversion of units of measurement.

# Installation

```
   "io.flow" %% "lib-unit-of-measurement" % "0.0.2"
```

# Usage:

```
  import io.flow.common.v0.models.UnitOfMeasurement
  val converter = new Converter()

  converter.convert(2, UnitOfMeasurement.Kilogram, UnitOfMeasurement.Gram) match {
    case Left(errors) => println(error)
    case Right(grams) => println(s"2 KG = $grams grams")
  }

  converter.toGrams(2, UnitOfMeasurement.Gram) match {
    case Left(errors) => println(error)
    case Right(grams) => println(s"$grams grams")
  }
```
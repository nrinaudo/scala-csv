---
layout: tutorial
title: "Decoding rows as collections"
section: tutorial
sort: 1
---
A simple but very common type of CSV data is rows of numerical values. This is something that kantan.csv tries to make
as as easy as possible to deal with.

First, we'll need some sample CSV data, which we'll get from this project's resources:

```scala
val rawData: java.net.URL = getClass.getResource("/nums.csv")
```

This is what we're trying to parse:

```scala
scala> scala.io.Source.fromURL(rawData).mkString
res0: String =
85.5, 54.0, 74.7, 34.2
63.0, 75.6, 46.8, 80.1
85.5, 39.6, 2.7, 38.7
```

In order to turn this into useful types, all we need to do is retrieve a [`CsvReader`] instance:

```scala
import kantan.csv.ops._ // Brings in the kantan.csv syntax.

val reader = rawData.asCsvReader[List[Float]](',', false)
```

The [`asCsvReader`] scaladoc can seem a bit daunting with all its implicit parameters, so let's demystify it.

The first thing you'll notice is that it takes a type parameter, which is the type into which each row will be
decoded. In our example, we requested each row to be decoded into a [`List[Float]`][`List`].

The first value parameter, `,`, is the character that should be used as a column separator. It's usually a comma, but
not all implementations agree on that - Excel, for instance, is infamous for using a system-dependent column separator.

Finally, the last value parameter is a boolean flag that, when set to `true`, will cause the first row to be skipped.
This is important for CSV data that contains a header row.

Now that we have our [`CsvReader`] instance, we can consume it - by, say, printing each row:

```scala
scala> reader.foreach(println _)
Success(List(85.5, 54.0, 74.7, 34.2))
Success(List(63.0, 75.6, 46.8, 80.1))
Success(List(85.5, 39.6, 2.7, 38.7))
```

Note that each result is wrapped in an instance of [`ReadResult`]. This allows decoding to be entirely safe - no
exception will be thrown, all error conditions are encoded at the type level. If safety is not a concern and you'd
rather let your code crash than deal with error conditions, you can use [`asUnsafeCsvReader`] instead.

Finally, observant readers might have noticed that we didn't bother closing the [`CsvReader`] - we're obviously dealing
with some sort of streamed resource, not closing it seems like a bug. In this specific case, however, it's not 
necessary: [`CsvReader`] will automatically close any underlying resource when it's been consumed entirely, or a fatal 
error occurs.

## What to read next
If you want to learn more about:

* [decoding rows as tuples](rows_as_tuples.html)
* [how `CsvReader` guessed how to turn CSV rows into `List[Float]` instances](cells_as_arbitrary_types.html) 
* [encoding collections as rows](collections_as_rows.html)
* [how we were able to turn a `URL` into CSV data](csv_sources.html)

[`List`]:http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.List
[syntax]:{{ site.baseurl }}/api/#kantan.csv.ops$
[`CsvReader`]:{{ site.baseurl }}/api/#kantan.csv.CsvReader
[`CellDecoder`]:{{ site.baseurl }}/api/#kantan.csv.package@CellDecoder[A]=kantan.codecs.Decoder[String,A,kantan.csv.DecodeError,kantan.csv.codecs.type]
[`ReadResult`]:{{ site.baseurl }}/api/#kantan.csv.package@ReadResult[A]=kantan.codecs.Result[kantan.csv.ReadError,A]
[`asCsvReader`]:{{ site.baseurl }}/api/#kantan.csv.ops$$CsvInputOps@asCsvReader[B](sep:Char,header:Boolean)(implicitevidence$3:kantan.csv.RowDecoder[B],implicitai:kantan.csv.CsvInput[A],implicite:kantan.csv.engine.ReaderEngine):kantan.csv.CsvReader[kantan.csv.ReadResult[B]]
[`asUnsafeCsvReader`]:{{ site.baseurl }}/api/#kantan.csv.ops$$CsvInputOps@asUnsafeCsvReader[B](sep:Char,header:Boolean)(implicitevidence$4:kantan.csv.RowDecoder[B],implicitai:kantan.csv.CsvInput[A],implicite:kantan.csv.engine.ReaderEngine):kantan.csv.CsvReader[B]
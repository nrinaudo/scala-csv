package tabulate

import ops._
import org.scalacheck.Gen

import org.scalatest.FunSuite
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import tabulate.laws.discipline.arbitrary._
import CsvDataTests._

class CsvRowsTests extends FunSuite with GeneratorDrivenPropertyChecks {
  private def asCsvRows(csv: List[List[String]]): CsvRows[List[String]] =
    write(csv).asCsvRows[List[String]](',', false).map(_.get)

  test("empty.next should throw an exception") {
    intercept[NoSuchElementException] { CsvRows.empty.next() }
    ()
  }

  test("empty.hasNext should be false") {
    assert(!CsvRows.empty.hasNext)
  }

  test("empty.close should do nothing") {
    CsvRows.empty.close()
  }


  val csvAndIndex: Gen[(List[List[String]], Int)] = for {
    data  <- csv.suchThat(_.length > 1)
    index <- Gen.choose(1, data.length - 1)
  } yield (data, index)

  test("drop should behave as expected") {
    forAll(csvAndIndex) { case (csv, index) =>
      assert(asCsvRows(csv).drop(index).toList == csv.drop(index))
    }
  }

  test("take should behave as expected") {
    forAll(csvAndIndex) { case (csv, index) =>
      assert(asCsvRows(csv).take(index).toList == csv.take(index))
    }
  }

  test("isTraversableAgain should return false") {
    forAll(csv) { csv =>
      assert(!asCsvRows(csv).isTraversableAgain)
    }
  }

  test("toStream should behave as expected") {
    forAll(csv) { csv =>
      assert(asCsvRows(csv).toStream.toList == csv)
    }
  }

  test("toTraversable should behave as expected") {
    forAll(csv) { csv =>
      assert(asCsvRows(csv).toTraversable.toList == csv)
    }
  }

  test("hasDefiniteSize should only return true for empty instances") {
    forAll(csv) { csv =>
      val rows = asCsvRows(csv)
      while(rows.hasNext) {
        assert(!rows.hasDefiniteSize)
        rows.next()
      }
      assert(rows.hasDefiniteSize)
    }
  }

  test("isEmpty should only return true for empty instances") {
    forAll(csv) { csv =>
      val rows = asCsvRows(csv)
      while(rows.hasNext) {
        assert(!rows.isEmpty)
        rows.next()
      }
      assert(rows.isEmpty)
    }
  }
}

/*
 * Copyright 2015 Nicolas Rinaudo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kantan.csv

import java.net.URI
import kantan.codecs.laws.discipline.SerializableTests
import kantan.csv.laws.discipline._
import kantan.csv.laws.discipline.arbitrary._
import org.scalatest.FunSuite
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.typelevel.discipline.scalatest.Discipline

class URICodecTests extends FunSuite with GeneratorDrivenPropertyChecks with Discipline {
  checkAll("CellEncoder[URI]", SerializableTests[CellEncoder[URI]].serializable)
  checkAll("CellDecoder[URI]", SerializableTests[CellDecoder[URI]].serializable)

  checkAll("RowEncoder[URI]", SerializableTests[RowEncoder[URI]].serializable)
  checkAll("RowDecoder[URI]", SerializableTests[RowDecoder[URI]].serializable)

  checkAll("CellCodec[URI]", CellCodecTests[URI].codec[String, Float])
  checkAll("RowCodec[URI]", RowCodecTests[URI].codec[String, Float])
}

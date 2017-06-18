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

package kantan.csv.laws.discipline

import kantan.codecs.laws.discipline.EncoderTests
import kantan.csv.codecs
import kantan.csv.laws._
import org.scalacheck.Arbitrary

object RowEncoderTests {
  def apply[A: RowEncoderLaws: Arbitrary](implicit al: Arbitrary[LegalRow[A]]): RowEncoderTests[A] =
    EncoderTests[Seq[String], A, codecs.type]
}

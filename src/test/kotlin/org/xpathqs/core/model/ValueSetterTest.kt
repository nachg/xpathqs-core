/*
 * Copyright (c) 2022 XPATH-QS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.xpathqs.core.model

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.xpathqs.core.reflection.SelectorParser
import org.xpathqs.gwt.GIVEN

class ValueSetterTest : AnnotationSpec() {
    init {
        SelectorParser(TestModelPage).parse()
    }

    /**
     * Checks #1 of [ValueSetter.init]
     */
    @Test
    fun init_r1() {
        GIVEN {
            TestModel()
        }.WHEN {
            ValueSetter(
                Model(TestModel::class.java, TestModelPage).associations,
                XpathExtractor()
            ).init(given)
        }.THEN {
            given.name1 shouldBe "//div"
            given.name2 shouldBe "//p"
        }
    }

}
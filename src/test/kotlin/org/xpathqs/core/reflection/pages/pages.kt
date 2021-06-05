/*
 * Copyright (c) 2021 Nikita A. Chegodaev
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

package org.xpathqs.core.reflection

import org.xpathqs.core.selector.Block
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.extensions.plus
import org.xpathqs.core.selector.extensions.repeat
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.selector.selector.SelectorProps
import org.xpathqs.core.util.SelectorFactory.tagSelector

open class Block2 : Block()

object PageWithBase : Block(
    Selector(
        props = SelectorProps(tag = "base")
    )
) {
    val s1 = Selector(props = SelectorProps(tag = "s1"))
}

object PageWithGroupBase : Block(
    tagSelector("div") + tagSelector("p")
) {
    val s1 = Selector(props = SelectorProps(tag = "s1"))
}

object PageWithInhGroup : Block2() {
    val s1 = tagSelector("div") + tagSelector("p")
}

object PageNoBase : Block() {
    val s1 = Selector(props = SelectorProps(tag = "s1"))
}

object PageWithBaseWithChainXpath : Block(tagSelector("div").repeat(3)) {
    val s1 = Selector(props = SelectorProps(tag = "s1"))
}

object PageWithBaseWithChain : Block(tagSelector("div") + tagSelector("div")) {
    val s1 = Selector(props = SelectorProps(tag = "s1"))
}

object PageWithBaseAndInnerObject : Block(
    Selector(
        props = SelectorProps(tag = "base")
    )
) {
    val s1_base = Selector(props = SelectorProps(tag = "base_tag"))

    object Inner : Block(
        Selector(
            props = SelectorProps(tag = "inner")
        )
    ) {
        object L2 : Block() {
            val asd = tagSelector("asd")
        }

        val s1_inner = Selector(props = SelectorProps(tag = "inner_tag"))
    }
}

object PageWithBaseAndInnerGroupObject : Block(
    Selector(
        props = SelectorProps(tag = "base")
    )
) {
    val s1_base = Selector(props = SelectorProps(tag = "base_tag"))

    object Inner : Block(
        tagSelector("div") + tagSelector("p")
    ) {
        val s1_inner = Selector(props = SelectorProps(tag = "inner_tag"))
    }
}

class SomeHolder : Block(tagSelector("hold")) {
    val sel1 = tagSelector("div")
}

open class HolderWithArgs(
    base: Selector,
    val sel1: BaseSelector,
    val sel2: BaseSelector
): Block(base)

object PageWithBlockMembers : Block(tagSelector("base")) {
    val holder1 = SomeHolder()
    val holder2 = SomeHolder()
}

object PageWithBlockArgMembers: Block() {
    val holder1 = HolderWithArgs(
        tagSelector("base"),
        tagSelector("s1"),
        tagSelector("s2")
    )

    val holder2 = HolderWithArgs(
        tagSelector("base_2"),
        tagSelector("s1"),
        tagSelector("s2")
    )
}

object PageWithInnerObjectClassArg: Block() {
    object Holder1: HolderWithArgs(
        tagSelector("base"),
        tagSelector("s1"),
        tagSelector("s2")
    )

    object Holder2: HolderWithArgs(
        tagSelector("base_2"),
        tagSelector("s1"),
        tagSelector("s2")
    )
}
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

package org.xpathqs.core.selector

import org.xpathqs.core.reflection.freeze
import org.xpathqs.core.reflection.setBase
import org.xpathqs.core.selector.base.BaseSelector
import org.xpathqs.core.selector.base.ISelector
import org.xpathqs.core.selector.base.SelectorState
import org.xpathqs.core.selector.extensions.clone
import org.xpathqs.core.selector.group.GroupSelector
import org.xpathqs.core.selector.selector.Selector
import org.xpathqs.core.selector.selector.SelectorProps

/**
 * Base class for the Selector container objects
 * @param isBlank points that the current selector was not initialized based on
 * some other selector via constructor
 */
open class Block(
    base: ISelector = NullSelector(),
    props: SelectorProps = SelectorProps(),
    selectorsChain: ArrayList<BaseSelector> = ArrayList(),
    name: String = "",
    internal val isBlank: Boolean = true,
) : GroupSelector(
    base = base,
    props = props,
    name = name,
    selectorsChain = selectorsChain
) {
    /**
     * Points to the original object-class instance.
     * The [GroupSelector.clone] method will update the [base] link of each
     * [children] selectors. And will restore it in the [fixChildrenSelectors] method.
     * Such weird implementation was introduces because of Kotlin object-class members implementation.
     * They all compiled to the static java objects, and there is no way to clone the whole object-class member,
     * with inner fields.
     * @see [GroupSelector.clone]
     */
    internal var originBlock: ISelector = NullSelector()

    /**
     * List of selector-based members of the current Block
     */
    internal var children: Collection<BaseSelector> = emptyList()

    /**
     * Initialization based on [Selector]
     */
    constructor(sel: Selector) : this(
        isBlank = false,
        base = sel.base.clone(),
        props = sel.props.clone(),
        selectorsChain = arrayListOf(sel.clone())
    )

    /**
     * Initialization based on [XpathSelector]
     */
    constructor(sel: XpathSelector) : this(
        isBlank = false,
        base = sel.base.clone(),
        props = SelectorProps(
            prefix = "",
            tag = sel.tag,
            args = sel.props.args
        ),
        selectorsChain = arrayListOf(sel.clone())
    )

    /**
     * Initialization based on [GroupSelector]
     */
    constructor(sel: GroupSelector) : this(
        isBlank = false,
        base = sel.base.clone(),
        props = SelectorProps(
            prefix = "",
            tag = sel.tag,
            args = sel.props.args
        ),
        selectorsChain = sel.selectorsChain
    )

    /**
     * Get Xpath query and restore link of [children] selectors
     * to the origin object
     */
    override fun toXpath(): String {
        if (isBlank) {
            return ""
        }

        val res = super.toXpath()

        fixChildrenSelectors()

        return res
    }

    /**
     * Restore [base] link of [children] selectors
     */
    protected fun fixChildrenSelectors() {
        if (state == SelectorState.CLONED) {
            children.forEach {
                it.setBase(originBlock)
            }
            freeze()
        }
    }
}
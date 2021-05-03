package org.nachg.xpathqs.core.reflection

import org.nachg.xpathqs.core.selector.Block
import org.nachg.xpathqs.core.selector.ISelector
import org.nachg.xpathqs.core.selector.Selector
import org.nachg.xpathqs.core.selector.SelectorProps

fun Any.isObject(): Boolean {
    return this.javaClass.declaredFields
        .find { it.name == "INSTANCE" } != null
}

fun Any.isSelector(): Boolean {
    return this is Selector
}

fun Class<*>.isSelectorSubtype(): Boolean {
    if(this.superclass == null) {
        return false
    }
    if(this == Selector::class.java) {
        return true
    }
    return Selector::class.java.isAssignableFrom(this.superclass)
}

fun Selector.setName(name: String) {
    SelectorReflection(this)
        .setName(name)
}

fun Selector.setBase(base: ISelector) {
    SelectorReflection(this)
        .setBase(base)
}

fun Selector.setProps(props: SelectorProps) {
    SelectorReflection(this)
        .setProps(props)
}

fun Selector.freeze(): Selector {
    SelectorReflection(this).freeze()
    return this
}

fun Selector.cloned(): Selector {
    SelectorReflection(this).cloned()
    return this
}

fun Block.setBlank(value: Boolean) {
    SelectorReflection(this).setProp("isBlank", value)
}
package com.nivabit.kuery.dml

interface Ordering : Iterable<Ordering> {

    val key: Any
    val asc: Boolean

    override fun iterator(): Iterator<Ordering> {
        return object : Iterator<Ordering> {
            var valid = true
            override fun hasNext(): Boolean {
                return valid
            }
            override fun next(): Ordering {
                valid = false
                return this@Ordering
            }
        }
    }

    class By(override val key: Any, override val asc: Boolean) : Ordering

}

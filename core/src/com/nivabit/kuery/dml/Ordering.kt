package com.nivabit.kuery.dml

interface Ordering {

    val key: Any
    val asc: Boolean

    open class By(override val key: Any, override val asc: Boolean) : Ordering, Iterable<Ordering> {
        override fun iterator(): Iterator<Ordering> {
            return object : Iterator<Ordering> {
                var valid = true
                override fun hasNext(): Boolean {
                    return valid
                }
                override fun next(): Ordering {
                    valid = false
                    return this@By
                }
            }
        }
    }

    class List(val list: Iterable<Ordering>) : Iterable<Ordering> {
        override fun iterator(): Iterator<Ordering> {
            return list.iterator()
        }

        operator fun rangeTo(ordering: Ordering): List {
            return List(list + ordering)
        }
    }

    operator fun rangeTo(ordering: Ordering): List {
        return List(listOf(this, ordering))
    }

}

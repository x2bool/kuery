package com.nivabit.kuery.dml

interface Projection {

    val projection: Any

    open class Value(override val projection: Any) : Projection, Iterable<Projection> {
        override fun iterator(): Iterator<Projection> {
            return object : Iterator<Projection> {
                var valid = true
                override fun hasNext(): Boolean {
                    return valid
                }
                override fun next(): Projection {
                    valid = false
                    return this@Value
                }
            }
        }
    }

    class List(val list: Iterable<Projection>) : Iterable<Projection> {
        override fun iterator(): Iterator<Projection> {
            return list.iterator()
        }

        operator fun rangeTo(projection: Projection): List {
            return List(list + projection)
        }
    }

    operator fun rangeTo(projection: Projection): List {
        return List(listOf(this, projection))
    }

}

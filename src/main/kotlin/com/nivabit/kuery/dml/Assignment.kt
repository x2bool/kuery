package com.nivabit.kuery.dml

interface Assignment {

    val key: Any
    val value: Any?

    open class Value(override val key: Any, override val value: Any?) : Assignment, Iterable<Assignment> {
        override fun iterator(): Iterator<Assignment> {
            return object : Iterator<Assignment> {
                var valid = true
                override fun hasNext(): Boolean {
                    return valid
                }
                override fun next(): Assignment {
                    valid = false
                    return this@Value
                }
            }
        }
    }

    class List(val list: Iterable<Assignment>) : Iterable<Assignment> {
        override fun iterator(): Iterator<Assignment> {
            return list.iterator()
        }

        operator fun plus(assignment: Assignment): List {
            return List(list + assignment)
        }
    }

    operator fun plus(assignment: Assignment): List {
        return List(listOf(this, assignment))
    }

}
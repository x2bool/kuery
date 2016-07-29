package com.nivabit.kuery.dml

import com.nivabit.kuery.Table

interface Assignment {

    val column: Table.Column
    val value: Any?

    open class Value(override val column: Table.Column, override val value: Any?) : Assignment, Iterable<Assignment> {
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
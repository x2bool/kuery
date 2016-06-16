package com.nivabit.kuery.ddl

import com.nivabit.kuery.*

interface Definition {

    val column: Table.Column
    val type: String

    open class Column(override val column: Table.Column, override val type: String) : Definition, Iterable<Definition> {
        override fun iterator(): Iterator<Definition> {
            return object : Iterator<Definition> {
                var valid = true
                override fun hasNext(): Boolean {
                    return valid
                }
                override fun next(): Definition {
                    valid = false
                    return this@Column
                }
            }
        }
    }

    class List(val list: Iterable<Definition>) : Iterable<Definition> {
        override fun iterator(): Iterator<Definition> {
            return list.iterator()
        }

        operator fun plus(definition: Definition): List {
            return List(list + definition)
        }
    }

    operator fun plus(definition: Definition): List {
        return List(listOf(this, definition))
    }

}
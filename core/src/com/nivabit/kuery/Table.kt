package com.nivabit.kuery

import com.nivabit.kuery.dml.*

open class Table(private val name: String) {

    inner class Column(val name: String) : Projection {

        val table: Table
            get() = this@Table

        val asc: Ordering
            get() = Ordering.By(this, true)

        val desc: Ordering
            get() = Ordering.By(this, false)

        operator fun invoke(column: Column): Assignment {
            return Assignment.Value(this, column)
        }

        operator fun invoke(value: String?): Assignment {
            return Assignment.Value(this, value)
        }

        operator fun invoke(value: Number): Assignment {
            return Assignment.Value(this, value)
        }

        operator fun invoke(value: Boolean): Assignment {
            return Assignment.Value(this, value)
        }

        override fun toString(): String {
            return name
        }
    }

    override fun toString(): String {
        return name
    }
}
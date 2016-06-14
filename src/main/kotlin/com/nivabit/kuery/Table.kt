package com.nivabit.kuery

import com.nivabit.kuery.ddl.*
import com.nivabit.kuery.dml.*

open class Table(private val name: String) {

    inner class Column(val name: String) : Projection {

        val table: Table
            get() = this@Table

        override val projection: Any
            get() = this

        val asc: Ordering.By
            get() = Ordering.By(this, true)

        val desc: Ordering.By
            get() = Ordering.By(this, false)

        operator fun invoke(value: Any?): Assignment.Value {
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
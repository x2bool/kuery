package com.nivabit.kuery.dml

import com.nivabit.kuery.*
import com.nivabit.kuery.sqlite.*

class InsertStatement<T: Table>(
        val assignments: Iterable<Assignment>,
        val subject: Statement<T>) {

    fun toString(dialect: Dialect): String {
        return dialect.build(this)
    }

    override fun toString(): String {
        return toString(SQLiteDialect)
    }
}
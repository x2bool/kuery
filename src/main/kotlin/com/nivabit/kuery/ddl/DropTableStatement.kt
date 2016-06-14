package com.nivabit.kuery.ddl

import com.nivabit.kuery.*
import com.nivabit.kuery.sqlite.*

class DropTableStatement<T: Table>(
        val subject: Statement<T>) {

    fun toString(dialect: Dialect): String {
        return dialect.build(this)
    }

    override fun toString(): String {
        return toString(SQLiteDialect)
    }
}
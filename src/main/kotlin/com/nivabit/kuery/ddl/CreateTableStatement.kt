package com.nivabit.kuery.ddl

import com.nivabit.kuery.*
import com.nivabit.kuery.sqlite.*

class CreateTableStatement<T: Table>(
        val definitions: Iterable<Definition>,
        val subject: Statement<T>) {

    fun toString(dialect: Dialect): String {
        return dialect.build(this)
    }

    override fun toString(): String {
        return toString(SQLiteDialect)
    }
}
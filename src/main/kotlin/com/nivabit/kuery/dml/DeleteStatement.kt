package com.nivabit.kuery.dml

import com.nivabit.kuery.*
import com.nivabit.kuery.sqlite.*

class DeleteStatement<T: Table>(
        val subject: Statement<T>,
        val whereClause: WhereClause<T>?) {

    fun toString(dialect: Dialect): String {
        return dialect.build(this)
    }

    override fun toString(): String {
        return toString(SQLiteDialect)
    }
}
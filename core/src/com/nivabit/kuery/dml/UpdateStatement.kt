package com.nivabit.kuery.dml

import com.nivabit.kuery.*

class UpdateStatement<T: Table>(
        val assignments: Iterable<Assignment>,
        val subject: Subject<T>,
        val whereClause: WhereClause<T>?) {

    fun toString(dialect: Dialect): String {
        return dialect.build(this)
    }
}
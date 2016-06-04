package com.nivabit.kuery.dml

import com.nivabit.kuery.Statement
import com.nivabit.kuery.Table

class UpdateStatement<T: Table>(
        val values: Iterable<ColumnValue>,
        val subject: Statement<T>,
        val whereClause: WhereClause<T>? = null) {

    override fun toString(): String {
        var sql = "UPDATE ${subject.table}"

        sql += " SET ${values.map { "\"${it.column.name}\" = ${it.value}" }.joinToString()}"
        if (whereClause != null) sql += " ${whereClause}"

        return sql
    }
}
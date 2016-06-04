package com.nivabit.kuery.dml

import com.nivabit.kuery.Statement
import com.nivabit.kuery.Table

class DeleteStatement<T: Table>(
        val subject: Statement<T>,
        val whereClause: WhereClause<T>? = null) {

    override fun toString(): String {
        var sql = "DELETE FROM $subject"

        if (whereClause != null) sql += " ${whereClause}"

        return sql
    }
}
package com.nivabit.kuery.dml

import com.nivabit.kuery.Statement
import com.nivabit.kuery.Table

class InsertStatement<T: Table>(
        val values: Iterable<ColumnValue>,
        val subject: Statement<T>) {

    override fun toString(): String {
        var sql = "INSERT INTO ${subject.table}(${values.map { "\"${it.column.name}\"" }.joinToString()})"
        sql += " VALUES (${values.map { "${it.value}" }.joinToString()})"
        return sql
    }
}
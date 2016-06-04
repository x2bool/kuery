package com.nivabit.kuery.dml

import com.nivabit.kuery.Statement
import com.nivabit.kuery.Table

class SelectStatement<T: Table>(
        val projection: Projection,
        val subject: Statement<T>,
        val whereClause: WhereClause<T>? = null,
        val orderClause: OrderClause<T>? = null,
        val limitClause: LimitClause<T>? = null,
        val offsetClause: OffsetClause<T>? = null) {

    override fun toString(): String {
        var sql = "SELECT ${projection.projection} FROM \"${subject.table}\""

        if (whereClause != null) sql += " $whereClause"
        if (orderClause != null) sql += " $orderClause"
        if (limitClause != null) sql += " $limitClause"
        if (offsetClause != null) sql += " $offsetClause"

        return sql
    }
}

class Select2Statement<T: Table, T2: Table>(
        val projection: Projection,
        val subject: Statement<T>,
        val joinOnClause: JoinOn2Clause<T, T2>,
        val whereClause: Where2Clause<T, T2>? = null,
        val orderClause: Order2Clause<T, T2>? = null,
        val limitClause: Limit2Clause<T, T2>? = null,
        val offsetClause: Offset2Clause<T, T2>? = null) {

    override fun toString(): String {
        var sql = "SELECT ${projection.projection} FROM \"${subject.table}\" $joinOnClause"

        if (whereClause != null) sql += " $whereClause"
        if (orderClause != null) sql += " $orderClause"
        if (limitClause != null) sql += " $limitClause"
        if (offsetClause != null) sql += " $offsetClause"

        return sql
    }
}
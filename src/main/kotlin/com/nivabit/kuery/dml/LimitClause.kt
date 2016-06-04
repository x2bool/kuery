package com.nivabit.kuery.dml

import com.nivabit.kuery.Statement
import com.nivabit.kuery.Table

class LimitClause<T: Table>(
        val subject: Statement<T>,
        val whereClause: WhereClause<T>?,
        val orderClause: OrderClause<T>?,
        val limit: Any) {

    inline fun offset(offset: () -> Any): OffsetClause<T> {
        return OffsetClause(
                subject,
                whereClause,
                orderClause,
                this,
                offset())
    }

    inline fun select(projection: (T) -> Projection): SelectStatement<T> {
        return SelectStatement(
                projection(subject.table),
                subject,
                whereClause,
                orderClause,
                this)
    }

    override fun toString(): String {
        return "LIMIT $limit"
    }
}

class Limit2Clause<T: Table, T2: Table>(
        val subject: Statement<T>,
        val joinOn2Clause: JoinOn2Clause<T, T2>,
        val whereClause: Where2Clause<T, T2>?,
        val orderClause: Order2Clause<T, T2>?,
        val limit: Any) {

    inline fun offset(offset: () -> Any): Offset2Clause<T, T2> {
        return Offset2Clause(
                subject,
                joinOn2Clause,
                whereClause,
                orderClause,
                this,
                offset())
    }

    inline fun select(projection: (T, T2) -> Projection): Select2Statement<T, T2> {
        return Select2Statement(
                projection(subject.table, joinOn2Clause.table2),
                subject,
                joinOn2Clause,
                whereClause,
                orderClause,
                this)
    }

    override fun toString(): String {
        return "LIMIT $limit"
    }

}

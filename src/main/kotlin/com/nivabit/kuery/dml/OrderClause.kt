package com.nivabit.kuery.dml

import com.nivabit.kuery.Statement
import com.nivabit.kuery.Table

class OrderClause<T: Table>(
        val subject: Statement<T>,
        val whereClause: WhereClause<T>?,
        val ordering: Ordering) {

    inline fun limit(limit: () -> Any): LimitClause<T> {
        return LimitClause(
                subject,
                whereClause,
                this,
                limit())
    }

    inline fun offset(offset: () -> Any): OffsetClause<T> {
        return OffsetClause(
                subject,
                whereClause,
                this,
                limit { "-1" },
                offset())
    }

    inline fun select(projection: (T) -> Projection): SelectStatement<T> {
        return SelectStatement(
                projection(subject.table),
                subject,
                whereClause,
                this)
    }

    override fun toString(): String {
        return "ORDER BY ${ordering.ordering}"
    }
}

class Order2Clause<T: Table, T2: Table>(
        val subject: Statement<T>,
        val joinOn2Clause: JoinOn2Clause<T, T2>,
        val whereClause: Where2Clause<T, T2>?,
        val ordering: Ordering) {

    inline fun limit(limit: () -> Any): Limit2Clause<T, T2> {
        return Limit2Clause(
                subject,
                joinOn2Clause,
                whereClause,
                this,
                limit())
    }

    inline fun offset(offset: () -> Any): Offset2Clause<T, T2> {
        return Offset2Clause(
                subject,
                joinOn2Clause,
                whereClause,
                this,
                limit { "-1" },
                offset())
    }

    inline fun select(projection: (T, T2) -> Projection): Select2Statement<T, T2> {
        return Select2Statement(projection(subject.table, joinOn2Clause.table2), subject, joinOn2Clause, whereClause, this)
    }

    override fun toString(): String {
        return "ORDER BY ${ordering.ordering}"
    }
}

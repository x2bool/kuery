package com.nivabit.kuery.dml

import com.nivabit.kuery.Expression
import com.nivabit.kuery.Statement
import com.nivabit.kuery.Table

class WhereClause<T: Table>(
        val subject: Statement<T>,
        val predicate: Expression<Boolean>) {

    inline fun orderBy(order: (T) -> Ordering): OrderClause<T> {
        return OrderClause(subject, this, order(subject.table))
    }

    inline fun limit(limit: () -> Any): LimitClause<T> {
        return LimitClause(
                subject,
                this,
                null,
                limit())
    }

    inline fun offset(offset: () -> Any): OffsetClause<T> {
        return OffsetClause(
                subject,
                this,
                null,
                limit { "-1" },
                offset())
    }

    inline fun select(projection: (T) -> Projection): SelectStatement<T> {
        return SelectStatement(projection(subject.table), subject, this)
    }

    inline fun update(value: (T) -> Iterable<ColumnValue>): UpdateStatement<T> {
        return UpdateStatement(value(subject.table), subject, this)
    }

    inline fun delete(): DeleteStatement<T> {
        return DeleteStatement(subject, this)
    }

    override fun toString(): String {
        return "WHERE $predicate"
    }
}

class Where2Clause<T: Table, T2: Table>(
        val subject: Statement<T>,
        val joinOn2Clause: JoinOn2Clause<T, T2>,
        val predicate: Expression<Boolean>) {

    inline fun orderBy(order: (T, T2) -> Ordering): Order2Clause<T, T2> {
        return Order2Clause(subject, joinOn2Clause, this, order(subject.table, joinOn2Clause.table2))
    }

    inline fun limit(limit: () -> Any): Limit2Clause<T, T2> {
        return Limit2Clause(
                subject,
                joinOn2Clause,
                this,
                null,
                limit())
    }

    inline fun offset(offset: () -> Any): Offset2Clause<T, T2> {
        return Offset2Clause(
                subject,
                joinOn2Clause,
                this,
                null,
                limit { "-1" },
                offset())
    }

    inline fun select(projection: (T, T2) -> Projection): Select2Statement<T, T2> {
        return Select2Statement(projection(joinOn2Clause.subject.table, joinOn2Clause.table2), subject, joinOn2Clause, this)
    }

    override fun toString(): String {
        return "WHERE $predicate"
    }
}
package com.nivabit.kuery.dml

import com.nivabit.kuery.Expression
import com.nivabit.kuery.Statement
import com.nivabit.kuery.Table

class Join2Clause<T: Table, T2: Table>(
        val subject: Statement<T>,
        val table2: T2,
        val type: JoinType = JoinType.INNER) {

    inline fun on(condition: (T, T2) -> Expression<Boolean>): JoinOn2Clause<T, T2> {
        return JoinOn2Clause(subject, table2, type, condition(subject.table, table2))
    }

}

class JoinOn2Clause<T: Table, T2: Table>(
        val subject: Statement<T>,
        val table2: T2,
        val type: JoinType,
        val condition: Expression<Boolean>) {

    inline fun where(predicate: (T, T2) -> Expression<Boolean>): Where2Clause<T, T2> {
        return Where2Clause(subject, this, predicate(subject.table, table2))
    }

    inline fun orderBy(order: (T, T2) -> Ordering): Order2Clause<T, T2> {
        return Order2Clause(subject, this, null, order(subject.table, table2))
    }

    inline fun select(projection: (T, T2) -> Projection): Select2Statement<T, T2> {
        return Select2Statement(projection(subject.table, table2), subject, this)
    }

    inline fun limit(limit: () -> Any): Limit2Clause<T, T2> {
        return Limit2Clause(
                subject,
                this,
                null,
                null,
                limit())
    }

    inline fun offset(offset: () -> String): Offset2Clause<T, T2> {
        return Offset2Clause(
                subject,
                this,
                null,
                null,
                limit { "-1" },
                offset())
    }

    override fun toString(): String {
        return "${if (type == JoinType.INNER) "" else "OUTER "}JOIN \"$table2\" ON $condition"
    }

}

enum class JoinType {
    INNER,
    OUTER
}
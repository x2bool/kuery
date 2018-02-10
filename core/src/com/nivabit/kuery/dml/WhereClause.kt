package com.nivabit.kuery.dml

import com.nivabit.kuery.Predicate
import com.nivabit.kuery.Subject
import com.nivabit.kuery.Table

class WhereClause<T: Table>(
        val predicate: Predicate,
        val subject: Subject<T>) {

    inline fun groupBy(group: (T) -> Iterable<Projection>): GroupClause<T> {
        return GroupClause(group(subject.table), subject, this)
    }

    inline fun orderBy(order: (T) -> Iterable<Ordering>): OrderClause<T> {
        return OrderClause(order(subject.table), subject, this, null, null)
    }

    inline fun limit(limit: () -> Any): LimitClause<T> {
        return LimitClause(
                limit(),
                subject,
                this,
                null,
                null,
                null)
    }

    inline fun offset(offset: () -> Any): OffsetClause<T> {
        return OffsetClause(
                offset(),
                limit { "-1" },
                subject,
                this,
                null,
                null,
                null)
    }

    inline fun select(projection: (T) -> Iterable<Projection>): SelectStatement<T> {
        return SelectStatement(
                projection(subject.table),
                subject,
                this,
                null,
                null,
                null,
                null,
                null
        )
    }

    inline fun selectAll(): SelectStatement<T> {
        return SelectStatement(
                listOf(),
                subject,
                this,
                null,
                null,
                null,
                null,
                null
        )
    }

    inline fun update(value: (T) -> Iterable<Assignment>): UpdateStatement<T> {
        return UpdateStatement(value(subject.table), subject, this)
    }

    inline fun delete(): DeleteStatement<T> {
        return DeleteStatement(subject, this)
    }
}

class Where2Clause<T: Table, T2: Table>(
        val predicate: Predicate,
        val joinOn2Clause: JoinOn2Clause<T, T2>) {

    inline fun groupBy(group: (T, T2) -> Iterable<Projection>): Group2Clause<T, T2> {
        return Group2Clause(group(joinOn2Clause.subject.table, joinOn2Clause.table2), joinOn2Clause, this)
    }

    inline fun orderBy(order: (T, T2) -> Iterable<Ordering>): Order2Clause<T, T2> {
        return Order2Clause(order(joinOn2Clause.subject.table, joinOn2Clause.table2), joinOn2Clause, this, null, null)
    }

    inline fun limit(limit: () -> Any): Limit2Clause<T, T2> {
        return Limit2Clause(
                limit(),
                joinOn2Clause,
                this,
                null,
                null,
                null)
    }

    inline fun offset(offset: () -> Any): Offset2Clause<T, T2> {
        return Offset2Clause(
                offset(),
                limit { "-1" },
                joinOn2Clause,
                this,
                null,
                null,
                null)
    }

    inline fun select(projection: (T, T2) -> Iterable<Projection>): Select2Statement<T, T2> {
        return Select2Statement(
                projection(joinOn2Clause.subject.table, joinOn2Clause.table2),
                joinOn2Clause,
                this,
                null,
                null,
                null,
                null,
                null
        )
    }
}

class Where3Clause<T: Table, T2: Table, T3: Table>(
        val predicate: Predicate,
        val joinOn3Clause: JoinOn3Clause<T, T2, T3>) {

    inline fun groupBy(group: (T, T2, T3) -> Iterable<Projection>): Group3Clause<T, T2, T3> {
        return Group3Clause(
                group(joinOn3Clause.joinOn2Clause.subject.table, joinOn3Clause.joinOn2Clause.table2, joinOn3Clause.table3), joinOn3Clause, this)
    }

    inline fun orderBy(order: (T, T2, T3) -> Iterable<Ordering>): Order3Clause<T, T2, T3> {
        return Order3Clause(
                order(joinOn3Clause.joinOn2Clause.subject.table, joinOn3Clause.joinOn2Clause.table2, joinOn3Clause.table3), joinOn3Clause, this, null, null)
    }

    inline fun limit(limit: () -> Any): Limit3Clause<T, T2, T3> {
        return Limit3Clause(
                limit(),
                joinOn3Clause,
                this,
                null,
                null,
                null)
    }

    inline fun offset(offset: () -> Any): Offset3Clause<T, T2, T3> {
        return Offset3Clause(
                offset(),
                limit { "-1" },
                joinOn3Clause,
                this,
                null,
                null,
                null)
    }

    inline fun select(projection: (T, T2, T3) -> Iterable<Projection>): Select3Statement<T, T2, T3> {
        return Select3Statement(
                projection(
                        joinOn3Clause.joinOn2Clause.subject.table,
                        joinOn3Clause.joinOn2Clause.table2,
                        joinOn3Clause.table3
                ),
                joinOn3Clause,
                this,
                null,
                null,
                null,
                null,
                null
        )
    }
}

class Where4Clause<T: Table, T2: Table, T3: Table, T4: Table>(
        val predicate: Predicate,
        val joinOn4Clause: JoinOn4Clause<T, T2, T3, T4>) {

    inline fun groupBy(group: (T, T2, T3, T4) -> Iterable<Projection>): Group4Clause<T, T2, T3, T4> {
        return Group4Clause(
                group(
                        joinOn4Clause.joinOn3Clause.joinOn2Clause.subject.table,
                        joinOn4Clause.joinOn3Clause.joinOn2Clause.table2,
                        joinOn4Clause.joinOn3Clause.table3,
                        joinOn4Clause.table4
                ),
                joinOn4Clause,
                this)
    }

    inline fun orderBy(order: (T, T2, T3, T4) -> Iterable<Ordering>): Order4Clause<T, T2, T3, T4> {
        return Order4Clause(
                order(
                        joinOn4Clause.joinOn3Clause.joinOn2Clause.subject.table,
                        joinOn4Clause.joinOn3Clause.joinOn2Clause.table2,
                        joinOn4Clause.joinOn3Clause.table3,
                        joinOn4Clause.table4
                ),
                joinOn4Clause,
                this,
                null,
                null)
    }

    inline fun limit(limit: () -> Any): Limit4Clause<T, T2, T3, T4> {
        return Limit4Clause(
                limit(),
                joinOn4Clause,
                this,
                null,
                null,
                null)
    }

    inline fun offset(offset: () -> Any): Offset4Clause<T, T2, T3, T4> {
        return Offset4Clause(
                offset(),
                limit { "-1" },
                joinOn4Clause,
                this,
                null,
                null,
                null)
    }

    inline fun select(projection: (T, T2, T3, T4) -> Iterable<Projection>): Select4Statement<T, T2, T3, T4> {
        return Select4Statement(
                projection(
                        joinOn4Clause.joinOn3Clause.joinOn2Clause.subject.table,
                        joinOn4Clause.joinOn3Clause.joinOn2Clause.table2,
                        joinOn4Clause.joinOn3Clause.table3,
                        joinOn4Clause.table4
                ),
                joinOn4Clause,
                this,
                null,
                null,
                null,
                null,
                null
        )
    }
}
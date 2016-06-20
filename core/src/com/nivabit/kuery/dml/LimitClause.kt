package com.nivabit.kuery.dml

import com.nivabit.kuery.*

class LimitClause<T: Table>(
        val limit: Any,
        val subject: Subject<T>,
        val whereClause: WhereClause<T>?,
        val orderClause: OrderClause<T>?) {

    inline fun offset(offset: () -> Any): OffsetClause<T> {
        return OffsetClause(
                offset(),
                this,
                subject,
                whereClause,
                orderClause)
    }

    inline fun select(projection: (T) -> Iterable<Projection>): SelectStatement<T> {
        return SelectStatement(
                projection(subject.table),
                subject,
                whereClause,
                orderClause,
                this,
                null)
    }
}

class Limit2Clause<T: Table, T2: Table>(
        val limit: Any,
        val joinOn2Clause: JoinOn2Clause<T, T2>,
        val where2Clause: Where2Clause<T, T2>?,
        val order2Clause: Order2Clause<T, T2>?) {

    inline fun offset(offset: () -> Any): Offset2Clause<T, T2> {
        return Offset2Clause(
                offset(),
                this,
                joinOn2Clause,
                where2Clause,
                order2Clause)
    }

    inline fun select(projection: (T, T2) -> Iterable<Projection>): Select2Statement<T, T2> {
        return Select2Statement(
                projection(joinOn2Clause.subject.table, joinOn2Clause.table2),
                joinOn2Clause,
                where2Clause,
                order2Clause,
                this,
                null)
    }

}

class Limit3Clause<T: Table, T2: Table, T3: Table>(
        val limit: Any,
        val joinOn3Clause: JoinOn3Clause<T, T2, T3>,
        val where3Clause: Where3Clause<T, T2, T3>?,
        val order3Clause: Order3Clause<T, T2, T3>?) {

    inline fun offset(offset: () -> Any): Offset3Clause<T, T2, T3> {
        return Offset3Clause(
                offset(),
                this,
                joinOn3Clause,
                where3Clause,
                order3Clause)
    }

    inline fun select(projection: (T, T2, T3) -> Iterable<Projection>): Select3Statement<T, T2, T3> {
        return Select3Statement(
                projection(joinOn3Clause.joinOn2Clause.subject.table, joinOn3Clause.joinOn2Clause.table2, joinOn3Clause.table3),
                joinOn3Clause,
                where3Clause,
                order3Clause,
                this,
                null)
    }
}

class Limit4Clause<T: Table, T2: Table, T3: Table, T4: Table>(
        val limit: Any,
        val joinOn4Clause: JoinOn4Clause<T, T2, T3, T4>,
        val where4Clause: Where4Clause<T, T2, T3, T4>?,
        val order4Clause: Order4Clause<T, T2, T3, T4>?) {

    inline fun offset(offset: () -> Any): Offset4Clause<T, T2, T3, T4> {
        return Offset4Clause(
                offset(),
                this,
                joinOn4Clause,
                where4Clause,
                order4Clause)
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
                where4Clause,
                order4Clause,
                this,
                null)
    }
}
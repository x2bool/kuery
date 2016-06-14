package com.nivabit.kuery.dml

import com.nivabit.kuery.*

class OffsetClause<T: Table>(
        val offset: Any,
        val limit: LimitClause<T>,
        val subject: Statement<T>,
        val whereClause: WhereClause<T>?,
        val orderClause: OrderClause<T>?) {

    inline fun select(projection: (T) -> Iterable<Projection>): SelectStatement<T> {
        return SelectStatement(
                projection(subject.table),
                subject,
                whereClause,
                orderClause,
                limit,
                this)
    }
}

class Offset2Clause<T: Table, T2: Table>(
        val offset: Any,
        val limit2Clause: Limit2Clause<T, T2>,
        val joinOn2Clause: JoinOn2Clause<T, T2>,
        val whereClause: Where2Clause<T, T2>?,
        val orderClause: Order2Clause<T, T2>?) {

    inline fun select(projection: (T, T2) -> Iterable<Projection>): Select2Statement<T, T2> {
        return Select2Statement(
                projection(joinOn2Clause.subject.table, joinOn2Clause.table2),
                joinOn2Clause,
                whereClause,
                orderClause,
                limit2Clause,
                this)
    }

}

class Offset3Clause<T: Table, T2: Table, T3: Table>(
        val offset: Any,
        val limit3Clause: Limit3Clause<T, T2, T3>,
        val joinOn3Clause: JoinOn3Clause<T, T2, T3>,
        val where3Clause: Where3Clause<T, T2, T3>?,
        val order3Clause: Order3Clause<T, T2, T3>?) {

    inline fun select(projection: (T, T2, T3) -> Iterable<Projection>): Select3Statement<T, T2, T3> {
        return Select3Statement(
                projection(joinOn3Clause.joinOn2Clause.subject.table, joinOn3Clause.joinOn2Clause.table2, joinOn3Clause.table3),
                joinOn3Clause,
                where3Clause,
                order3Clause,
                limit3Clause,
                this)
    }
}

class Offset4Clause<T: Table, T2: Table, T3: Table, T4: Table>(
        val offset: Any,
        val limit4Clause: Limit4Clause<T, T2, T3, T4>,
        val joinOn4Clause: JoinOn4Clause<T, T2, T3, T4>,
        val where4Clause: Where4Clause<T, T2, T3, T4>?,
        val order4Clause: Order4Clause<T, T2, T3, T4>?) {

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
                limit4Clause,
                this)
    }
}
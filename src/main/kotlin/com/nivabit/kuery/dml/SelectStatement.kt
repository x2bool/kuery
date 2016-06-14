package com.nivabit.kuery.dml

import com.nivabit.kuery.*
import com.nivabit.kuery.sqlite.SQLiteDialect

class SelectStatement<T: Table>(
        val projection: Iterable<Projection>,
        val subject: Statement<T>,
        val whereClause: WhereClause<T>?,
        val orderClause: OrderClause<T>?,
        val limitClause: LimitClause<T>?,
        val offsetClause: OffsetClause<T>?) {

    fun toString(dialect: Dialect): String {
        return dialect.build(this)
    }

    override fun toString(): String {
        return toString(SQLiteDialect)
    }
}

class Select2Statement<T: Table, T2: Table>(
        val projection: Iterable<Projection>,
        val joinOn2Clause: JoinOn2Clause<T, T2>,
        val where2Clause: Where2Clause<T, T2>?,
        val order2Clause: Order2Clause<T, T2>?,
        val limit2Clause: Limit2Clause<T, T2>?,
        val offset2Clause: Offset2Clause<T, T2>?) {

    fun toString(dialect: Dialect): String {
        return dialect.build(this)
    }

    override fun toString(): String {
        return toString(SQLiteDialect)
    }
}

class Select3Statement<T: Table, T2: Table, T3: Table>(
        val projection: Iterable<Projection>,
        val joinOn3Clause: JoinOn3Clause<T, T2, T3>,
        val where3Clause: Where3Clause<T, T2, T3>?,
        val order3Clause: Order3Clause<T, T2, T3>?,
        val limit3Clause: Limit3Clause<T, T2, T3>?,
        val offset3Clause: Offset3Clause<T, T2, T3>?) {

    fun toString(dialect: Dialect): String {
        return dialect.build(this)
    }

    override fun toString(): String {
        return toString(SQLiteDialect)
    }
}


class Select4Statement<T: Table, T2: Table, T3: Table, T4: Table>(
        val projection: Iterable<Projection>,
        val joinOn4Clause: JoinOn4Clause<T, T2, T3, T4>,
        val where4Clause: Where4Clause<T, T2, T3, T4>?,
        val order4Clause: Order4Clause<T, T2, T3, T4>?,
        val limit4Clause: Limit4Clause<T, T2, T3, T4>?,
        val offset4Clause: Offset4Clause<T, T2, T3, T4>?) {

    fun toString(dialect: Dialect): String {
        return dialect.build(this)
    }

    override fun toString(): String {
        return toString(SQLiteDialect)
    }
}
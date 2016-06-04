package com.nivabit.kuery

import com.nivabit.kuery.Table
import com.nivabit.kuery.ddl.CreateTableStatement
import com.nivabit.kuery.ddl.Definition
import com.nivabit.kuery.ddl.DropTableStatement
import com.nivabit.kuery.dml.*

class Statement<T: Table> {

    val table: T

    private constructor(table: T) {
        this.table = table
    }

    inline fun <T2: Table> join(table2: T2): Join2Clause<T, T2> {
        return Join2Clause(this, table2)
    }

    inline fun <T2: Table> outerJoin(table2: T2): Join2Clause<T, T2> {
        return Join2Clause(this, table2, JoinType.OUTER)
    }

    inline fun where(predicate: (T) -> Expression<Boolean>): WhereClause<T> {
        return WhereClause(this, predicate(table))
    }

    inline fun orderBy(order: (T) -> Ordering): OrderClause<T> {
        return OrderClause(this, null, order(table))
    }

    inline fun limit(limit: () -> String): LimitClause<T> {
        return LimitClause(
                this,
                null,
                null,
                limit())
    }

    inline fun offset(offset: () -> String): OffsetClause<T> {
        return OffsetClause(
                this,
                null,
                null,
                limit { "-1" },
                offset())
    }

    inline fun insert(insert: (T) -> Iterable<ColumnValue>): InsertStatement<T> {
        return InsertStatement(insert(table), this)
    }

    inline fun select(projection: (T) -> Projection): SelectStatement<T> {
        return SelectStatement(projection(table), this)
    }

    inline fun update(update: (T) -> Iterable<ColumnValue>): UpdateStatement<T> {
        return UpdateStatement(update(table), this)
    }

    inline fun delete(): DeleteStatement<T> {
        return DeleteStatement(this)
    }

    inline fun create(definition: (T) -> Definition): CreateTableStatement<T> {
        return CreateTableStatement(definition(table), this)
    }

    inline fun drop(): DropTableStatement<T> {
        return DropTableStatement(this)
    }

    override fun toString(): String {
        return table.toString()
    }

    companion object {
        fun <T: Table> on(table: T): Statement<T> {
            return Statement(table)
        }
    }
}
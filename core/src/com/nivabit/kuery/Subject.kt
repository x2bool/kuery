package com.nivabit.kuery

import com.nivabit.kuery.ddl.*
import com.nivabit.kuery.dml.*

open class Subject<T: Table> {

    val table: T

    private constructor(table: T) {
        this.table = table
    }

    override fun toString(): String {
        return table.toString()
    }

    class Over<T: Table>(table: T) : Subject<T>(table) {
        inline fun create(definition: (T) -> Iterable<Definition>): CreateTableStatement<T> {
            return CreateTableStatement(definition(table), this)
        }

        inline fun drop(): DropTableStatement<T> {
            return DropTableStatement(this)
        }
    }

    class From<T: Table>(table: T) : Subject<T>(table) {
        inline fun <T2: Table> join(table2: T2): Join2Clause<T, T2> {
            return Join2Clause(this, table2)
        }

        inline fun <T2: Table> outerJoin(table2: T2): Join2Clause<T, T2> {
            return Join2Clause(this, table2, JoinType.OUTER)
        }

        inline fun where(predicate: (T) -> Predicate): WhereClause<T> {
            return WhereClause(predicate(table), this)
        }

        inline fun groupBy(projection: (T) -> Iterable<Projection>): GroupClause<T> {
            return GroupClause(projection(table), this, null)
        }

        inline fun orderBy(order: (T) -> Iterable<Ordering>): OrderClause<T> {
            return OrderClause(order(table), this, null, null, null)
        }

        inline fun limit(limit: () -> String): LimitClause<T> {
            return LimitClause(
                    limit(),
                    this,
                    null,
                    null,
                    null,
                    null)
        }

        inline fun offset(offset: () -> String): OffsetClause<T> {
            return OffsetClause(
                    offset(),
                    limit { "-1" },
                    this,
                    null,
                    null,
                    null,
                    null)
        }

        inline fun select(projection: (T) -> Iterable<Projection>): SelectStatement<T> {
            return SelectStatement(
                    projection(table),
                    this,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null)
        }

        inline fun update(update: (T) -> Iterable<Assignment>): UpdateStatement<T> {
            return UpdateStatement(
                    update(table),
                    this,
                    null)
        }

        inline fun delete(): DeleteStatement<T> {
            return DeleteStatement(
                    this,
                    null
            )
        }
    }

    class Into<T: Table>(table: T) : Subject<T>(table) {
        inline fun insert(insert: (T) -> Iterable<Assignment>): InsertStatement<T> {
            return InsertStatement(insert(table), this)
        }
    }
}

inline fun <T: Table> over(table: T): Subject.Over<T> {
    return Subject.Over(table)
}

inline fun <T: Table> from(table: T): Subject.From<T> {
    return Subject.From(table)
}

inline fun <T: Table> into(table: T): Subject.Into<T> {
    return Subject.Into(table)
}
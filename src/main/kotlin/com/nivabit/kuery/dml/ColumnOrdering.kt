package com.nivabit.kuery.dml

import com.nivabit.kuery.Table

class ColumnOrdering(val column: Table.Column, val asc: Boolean) : Ordering {

    operator fun plus(ordering: ColumnOrdering): ColumnOrderings {
        return ColumnOrderings(arrayOf(this, ordering))
    }

    operator fun plus(orderings: ColumnOrderings): ColumnOrderings {
        return ColumnOrderings(arrayOf(this) + orderings.orderings)
    }

    operator fun plus(column: Table.Column): ColumnOrderings {
        return ColumnOrderings(arrayOf(this, column.asc))
    }

    operator fun plus(columns: Table.Columns): ColumnOrderings {
        return ColumnOrderings(arrayOf(this) + columns.columns.map { it.asc }.toTypedArray())
    }

    override val ordering: String
        get() = "\"${column.table}\".\"${column}\" ${if (asc) "ASC" else "DESC"}"
}

class ColumnOrderings(val orderings: Array<ColumnOrdering>) : Ordering {

    operator fun plus(ordering: ColumnOrdering): ColumnOrderings {
        return ColumnOrderings(orderings + arrayOf(ordering))
    }

    operator fun plus(orderings: ColumnOrderings): ColumnOrderings {
        return ColumnOrderings(this.orderings + orderings.orderings)
    }

    operator fun plus(column: Table.Column): ColumnOrderings {
        return ColumnOrderings(this.orderings + arrayOf(column.asc))
    }

    operator fun plus(columns: Table.Columns): ColumnOrderings {
        return ColumnOrderings(this.orderings + columns.columns.map { it.asc }.toTypedArray())
    }

    override val ordering: String
        get() = orderings.map { it.ordering }.joinToString(", ")
}
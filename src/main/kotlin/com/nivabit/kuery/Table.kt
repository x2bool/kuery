package com.nivabit.kuery

import com.nivabit.kuery.ddl.*
import com.nivabit.kuery.dml.*

open class Table(private val name: String) {

    inner class Column(val name: String) : Projection, Ordering, Definition {

        val table: Table
            get() = this@Table

        private var meta: ColumnMeta = ColumnMeta()

        private constructor(name: String, meta: ColumnMeta) : this(name) {
            this.meta = meta
        }

        val type: DataType
            get() = meta.type

        fun integer(): Column {
            return Column(name, meta.copy(type = DataType.INTEGER))
        }

        fun real(): Column {
            return Column(name, meta.copy(type = DataType.REAL))
        }

        fun text(): Column {
            return Column(name, meta.copy(type = DataType.TEXT))
        }

        fun blob(): Column {
            return Column(name, meta.copy(type = DataType.BLOB))
        }

        fun primaryKey(autoIncrement: Boolean = false): Column {
            return Column(name, meta.copy(primaryKeyConstraint = PrimaryKeyConstraint(autoIncrement)))
        }

        fun unique(): Column {
            return Column(name, meta.copy(uniqueConstraint = UniqueConstraint()))
        }

        fun notNull(): Column {
            return Column(name, meta.copy(notNullConstraint = NotNullConstraint()))
        }

        val asc: ColumnOrdering
            get() = ColumnOrdering(this, true)

        val desc: ColumnOrdering
            get() = ColumnOrdering(this, false)

        operator fun invoke(value: Any): ColumnValue {
            return ColumnValue(this, value)
        }

        operator fun plus(column: Column): Columns {
            return Columns(arrayOf(this, column))
        }

        operator fun plus(columns: Columns): Columns {
            return Columns(arrayOf(this) + columns.columns)
        }

        operator fun plus(ordering: ColumnOrdering): ColumnOrderings {
            return ColumnOrderings(arrayOf(asc, ordering))
        }

        operator fun plus(orderings: ColumnOrderings): ColumnOrderings {
            return ColumnOrderings(arrayOf(asc) + orderings.orderings)
        }

        override val projection: String
            get() = "\"${this@Table}\".\"$name\""

        override val ordering: String
            get() = "\"${this@Table}\".\"$name\" ASC"

        override val definition: String
            get() {
                var sql = "\"$name\" ${meta.type}"
                if (meta.primaryKeyConstraint != null) sql += " ${meta.primaryKeyConstraint}"
                if (meta.uniqueConstraint != null) sql += " ${meta.uniqueConstraint}"
                if (meta.notNullConstraint != null) sql += " ${meta.notNullConstraint}"
                return sql
            }

        override fun toString(): String {
            return name
        }
    }

    class Columns(val columns: Array<Column>) : Projection, Ordering, Definition {

        operator fun plus(column: Column): Columns {
            return Columns(columns + arrayOf(column))
        }

        operator fun plus(columns: Columns): Columns {
            return Columns(this.columns + columns.columns)
        }

        operator fun plus(ordering: ColumnOrdering): ColumnOrderings {
            return ColumnOrderings(columns.map { it.asc }.toTypedArray() + ordering)
        }

        operator fun plus(orderings: ColumnOrderings): ColumnOrderings {
            return ColumnOrderings(columns.map { it.asc }.toTypedArray() + orderings.orderings)
        }

        override val projection: String
            get() = columns.map { it.projection }.joinToString(", ")

        override val ordering: String
            get() = columns.map { it.ordering }.joinToString(", ")

        override val definition: String
            get() = columns.map { it.definition }.joinToString(", ")
    }

    private data class ColumnMeta(
            val type: DataType = DataType.BLOB,
            val primaryKeyConstraint: PrimaryKeyConstraint? = null,
            val uniqueConstraint: UniqueConstraint? = null,
            val notNullConstraint: NotNullConstraint? = null)

    override fun toString(): String {
        return name
    }
}
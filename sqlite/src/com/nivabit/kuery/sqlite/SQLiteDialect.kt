package com.nivabit.kuery.sqlite

import com.nivabit.kuery.*
import com.nivabit.kuery.ddl.*
import com.nivabit.kuery.dml.*

object SQLiteDialect : Dialect {

    override fun <T : Table> build(statement: CreateTableStatement<T>): String {
        val builder = StringBuilder()

        builder.append("CREATE TABLE ")
        appendTableName(builder, statement.subject.table)
        builder.append('(')

        var delim = ""

        for (definition in statement.definitions) {
            builder.append(delim)
            delim = ", "

            appendShortColumnName(builder, definition.column)
            builder.append(' ')
            builder.append(definition.type)

            if (definition is SQLiteDefinition) {
                if (definition.meta.primaryKeyConstraint != null) {
                    builder.append(" PRIMARY KEY")
                    if (definition.meta.primaryKeyConstraint.autoIncrement) builder.append(" AUTOINCREMENT")
                }

                if (definition.meta.foreignKeyConstraint != null) {
                    builder.append(" REFERENCES ")
                    appendFullColumnName(builder, definition.meta.foreignKeyConstraint.references)
                }

                if (definition.meta.uniqueConstraint != null) {
                    builder.append(" UNIQUE")
                }

                if (definition.meta.notNullConstraint != null) {
                    builder.append(" NOT NULL")
                }
            }
        }

        builder.append(')')

        return builder.toString()
    }

    override fun <T : Table> build(statement: DropTableStatement<T>): String {
        return "DROP TABLE \"${statement.subject.table}\""
    }

    override fun <T : Table> build(statement: SelectStatement<T>): String {
        val builder = StringBuilder()

        builder.append("SELECT ")
        appendProjection(builder, statement.projection, false)
        builder.append(" FROM ")
        appendTableName(builder, statement.subject.table)

        if (statement.whereClause != null) {
            builder.append(" WHERE ")
            appendPredicate(builder, statement.whereClause!!.predicate, false)
        }

        if (statement.orderClause != null) {
            builder.append(" ORDER BY ")
            appendOrdering(builder, statement.orderClause!!.orderings, false)
        }

        if (statement.limitClause != null) {
            builder.append(" LIMIT ")
            builder.append(statement.limitClause!!.limit)
        }

        if (statement.offsetClause != null) {
            builder.append(" OFFSET ")
            builder.append(statement.offsetClause!!.offset)
        }

        return builder.toString()
    }

    override fun <T : Table, T2 : Table> build(statement: Select2Statement<T, T2>): String {
        val builder = StringBuilder()

        builder.append("SELECT ")
        appendProjection(builder, statement.projection, true)
        builder.append(" FROM ")
        appendTableName(builder, statement.joinOn2Clause.subject.table)

        if (statement.joinOn2Clause.type == JoinType.OUTER) builder.append(" OUTER")
        builder.append(" JOIN ")
        appendTableName(builder, statement.joinOn2Clause.table2)
        builder.append(" ON ")
        appendPredicate(builder, statement.joinOn2Clause.condition, true)

        if (statement.where2Clause != null) {
            builder.append(" WHERE ")
            appendPredicate(builder, statement.where2Clause!!.predicate, true)
        }

        if (statement.order2Clause != null) {
            builder.append(" ORDER BY ")
            appendOrdering(builder, statement.order2Clause!!.orderings, true)
        }

        if (statement.limit2Clause != null) {
            builder.append(" LIMIT ")
            builder.append(statement.limit2Clause!!.limit)
        }

        if (statement.offset2Clause != null) {
            builder.append(" OFFSET ")
            builder.append(statement.offset2Clause!!.offset)
        }

        return builder.toString()
    }

    override fun <T : Table, T2 : Table, T3 : Table> build(statement: Select3Statement<T, T2, T3>): String {
        val builder = StringBuilder()

        builder.append("SELECT ")
        appendProjection(builder, statement.projection, true)
        builder.append(" FROM ")
        appendTableName(builder, statement.joinOn3Clause.joinOn2Clause.subject.table)

        if (statement.joinOn3Clause.joinOn2Clause.type == JoinType.OUTER) builder.append(" OUTER")
        builder.append(" JOIN ")
        appendTableName(builder, statement.joinOn3Clause.joinOn2Clause.table2)
        builder.append(" ON ")
        appendPredicate(builder, statement.joinOn3Clause.joinOn2Clause.condition, true)

        if (statement.joinOn3Clause.type == JoinType.OUTER) builder.append(" OUTER")
        builder.append(" JOIN ")
        appendTableName(builder, statement.joinOn3Clause.table3)
        builder.append(" ON ")
        appendPredicate(builder, statement.joinOn3Clause.condition, true)

        if (statement.where3Clause != null) {
            builder.append(" WHERE ")
            appendPredicate(builder, statement.where3Clause!!.predicate, true)
        }

        if (statement.order3Clause != null) {
            builder.append(" ORDER BY ")
            appendOrdering(builder, statement.order3Clause!!.orderings, true)
        }

        if (statement.limit3Clause != null) {
            builder.append(" LIMIT ")
            builder.append(statement.limit3Clause!!.limit)
        }

        if (statement.offset3Clause != null) {
            builder.append(" OFFSET ")
            builder.append(statement.offset3Clause!!.offset)
        }

        return builder.toString()
    }

    override fun <T : Table, T2 : Table, T3 : Table, T4 : Table> build(statement: Select4Statement<T, T2, T3, T4>): String {
        val builder = StringBuilder()

        builder.append("SELECT ")
        appendProjection(builder, statement.projection, true)
        builder.append(" FROM ")
        appendTableName(builder, statement.joinOn4Clause.joinOn3Clause.joinOn2Clause.subject.table)

        if (statement.joinOn4Clause.joinOn3Clause.joinOn2Clause.type == JoinType.OUTER) builder.append(" OUTER")
        builder.append(" JOIN ")
        appendTableName(builder, statement.joinOn4Clause.joinOn3Clause.joinOn2Clause.table2)
        builder.append(" ON ")
        appendPredicate(builder, statement.joinOn4Clause.joinOn3Clause.joinOn2Clause.condition, true)

        if (statement.joinOn4Clause.joinOn3Clause.type == JoinType.OUTER) builder.append(" OUTER")
        builder.append(" JOIN ")
        appendTableName(builder, statement.joinOn4Clause.joinOn3Clause.table3)
        builder.append(" ON ")
        appendPredicate(builder, statement.joinOn4Clause.joinOn3Clause.condition, true)

        if (statement.joinOn4Clause.type == JoinType.OUTER) builder.append(" OUTER")
        builder.append(" JOIN ")
        appendTableName(builder, statement.joinOn4Clause.table4)
        builder.append(" ON ")
        appendPredicate(builder, statement.joinOn4Clause.condition, true)

        if (statement.where4Clause != null) {
            builder.append(" WHERE ")
            appendPredicate(builder, statement.where4Clause!!.predicate, true)
        }

        if (statement.order4Clause != null) {
            builder.append(" ORDER BY ")
            appendOrdering(builder, statement.order4Clause!!.orderings, true)
        }

        if (statement.limit4Clause != null) {
            builder.append(" LIMIT ")
            builder.append(statement.limit4Clause!!.limit)
        }

        if (statement.offset4Clause != null) {
            builder.append(" OFFSET ")
            builder.append(statement.offset4Clause!!.offset)
        }

        return builder.toString()
    }

    override fun <T : Table> build(statement: InsertStatement<T>): String {
        val builder = StringBuilder()
        builder.append("INSERT INTO ")
        appendTableName(builder, statement.subject.table)
        builder.append(" (")

        var delim = "";

        for (assign in statement.assignments) {
            builder.append(delim)
            delim = ", "

            appendShortColumnName(builder, assign.column)
        }

        builder.append(") VALUES (")

        delim = "";

        for (assign in statement.assignments) {
            builder.append(delim)
            delim = ", "

            appendValue(builder, assign.value)
        }

        builder.append(")")

        return builder.toString()
    }

    override fun <T : Table> build(statement: UpdateStatement<T>): String {
        val builder = StringBuilder()
        builder.append("UPDATE ")
        appendTableName(builder, statement.subject.table)
        builder.append(" SET ")

        var delim = ""

        for (assign in statement.assignments) {
            builder.append(delim)
            delim = ", "

            appendShortColumnName(builder, assign.column)
            builder.append(" = ")
            appendValue(builder, assign.value)
        }

        if (statement.whereClause != null) {
            builder.append(" WHERE ")
            appendPredicate(builder, statement.whereClause!!.predicate, false)
        }

        return builder.toString()
    }

    override fun <T : Table> build(statement: DeleteStatement<T>): String {
        val builder = StringBuilder()
        builder.append("DELETE FROM ")
        appendTableName(builder, statement.subject.table)

        if (statement.whereClause != null) {
            builder.append(" WHERE ")
            appendPredicate(builder, statement.whereClause!!.predicate, false)
        }

        return builder.toString()
    }

    private fun appendPredicate(builder: StringBuilder, value: Any?, fullFormat: Boolean = true) {
        when (value) {
            is Table.Column -> if (fullFormat) appendFullColumnName(builder, value) else appendShortColumnName(builder, value)

            is NotExpression -> {
                builder.append("(NOT ")
                appendPredicate(builder, value.param, fullFormat)
                builder.append(")")
            }

            is AndExpression -> {
                builder.append('(')
                appendPredicate(builder, value.left, fullFormat)
                builder.append(" AND ")
                appendPredicate(builder, value.right, fullFormat)
                builder.append(')')
            }

            is OrExpression -> {
                builder.append('(')
                appendPredicate(builder, value.left, fullFormat)
                builder.append(" OR ")
                appendPredicate(builder, value.right, fullFormat)
                builder.append(')')
            }

            is EqExpression -> {
                builder.append('(')
                if (value.right != null) {
                    appendPredicate(builder, value.left, fullFormat)
                    builder.append(" = ")
                    appendPredicate(builder, value.right, fullFormat)
                } else {
                    appendPredicate(builder, value.left, fullFormat)
                    builder.append(" IS NULL")
                }
                builder.append(')')
            }

            is NeExpression -> {
                builder.append('(')
                if (value.right != null) {
                    appendPredicate(builder, value.left, fullFormat)
                    builder.append(" != ")
                    appendPredicate(builder, value.right, fullFormat)
                } else {
                    appendPredicate(builder, value.left, fullFormat)
                    builder.append(" IS NOT NULL")
                }
                builder.append(')')
            }

            is LtExpression -> {
                builder.append('(')
                appendPredicate(builder, value.left, fullFormat)
                builder.append(" < ")
                appendPredicate(builder, value.right, fullFormat)
                builder.append(')')
            }

            is LteExpression -> {
                builder.append('(')
                appendPredicate(builder, value.left, fullFormat)
                builder.append(" <= ")
                appendPredicate(builder, value.right, fullFormat)
                builder.append(')')
            }

            is GtExpression -> {
                builder.append('(')
                appendPredicate(builder, value.left, fullFormat)
                builder.append(" > ")
                appendPredicate(builder, value.right, fullFormat)
                builder.append(')')
            }

            is GteExpression -> {
                builder.append('(')
                appendPredicate(builder, value.left, fullFormat)
                builder.append(" <= ")
                appendPredicate(builder, value.right, fullFormat)
                builder.append(')')
            }

            else -> appendValue(builder, value)
        }
    }

    private fun appendProjection(builder: StringBuilder, projection: Iterable<Projection>, fullFormat: Boolean) {
        var delim = ""

        for (proj in projection) {
            builder.append(delim)
            delim = ", "

            if (proj.projection is Table.Column) {
                if (fullFormat) {
                    appendFullColumnName(builder, proj.projection as Table.Column)
                } else {
                    appendShortColumnName(builder, proj.projection as Table.Column)
                }
            } else {
                builder.append(proj.projection)
            }
        }
    }

    private fun appendOrdering(builder: StringBuilder, orderings: Iterable<Ordering>, fullFormat: Boolean) {
        var delim = ""

        for (order in orderings) {
            builder.append(delim)
            delim = ", "

            if (order.key is Table.Column) {
                if (fullFormat) {
                    appendFullColumnName(builder, order.key as Table.Column)
                } else {
                    appendShortColumnName(builder, order.key as Table.Column)
                }
            } else {
                builder.append(order.key)
            }

            builder.append(if (order.asc) " ASC" else " DESC")
        }
    }

    private inline fun appendTableName(builder: StringBuilder, table: Table) {
        builder.append("\"$table\"")
    }

    private inline fun appendShortColumnName(builder: StringBuilder, column: Table.Column) {
        builder.append("\"$column\"")
    }

    private inline fun appendFullColumnName(builder: StringBuilder, column: Table.Column) {
        builder.append("\"${column.table}\".\"$column\"")
    }

    private inline fun appendValue(builder: StringBuilder, value: Any?) {
        if (value == null) builder.append("NULL")
        else builder.append(value)
    }
}
package com.nivabit.kuery.sqlite

import com.nivabit.kuery.*
import com.nivabit.kuery.ddl.CreateTableStatement
import com.nivabit.kuery.ddl.DropTableStatement
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

        val where = statement.whereClause
        if (where != null) {
            builder.append(" WHERE ")
            appendPredicate(builder, where.predicate, false)
        }

        val group = statement.groupClause
        if (group != null) {
            builder.append(" GROUP BY ")
            appendProjection(builder, group.projection, false)
        }

        val having = statement.havingClause
        if (having != null) {
            builder.append(" HAVING ")
            appendPredicate(builder, having.predicate, false)
        }

        val order = statement.orderClause
        if (order != null) {
            builder.append(" ORDER BY ")
            appendOrdering(builder, order.orderings, false)
        }

        val limit = statement.limitClause
        if (limit != null) {
            builder.append(" LIMIT ")
            builder.append(limit.limit)
        }

        val offset = statement.offsetClause
        if (offset != null) {
            builder.append(" OFFSET ")
            builder.append(offset.offset)
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

        val where = statement.where2Clause
        if (where != null) {
            builder.append(" WHERE ")
            appendPredicate(builder, where.predicate, true)
        }

        val group = statement.group2Clause
        if (group != null) {
            builder.append(" GROUP BY ")
            appendProjection(builder, group.projection, true)
        }

        val having = statement.having2Clause
        if (having != null) {
            builder.append(" HAVING ")
            appendPredicate(builder, having.predicate, true)
        }

        val order = statement.order2Clause
        if (order != null) {
            builder.append(" ORDER BY ")
            appendOrdering(builder, order.orderings, true)
        }

        val limit = statement.limit2Clause
        if (limit != null) {
            builder.append(" LIMIT ")
            builder.append(limit.limit)
        }

        val offset = statement.offset2Clause
        if (offset != null) {
            builder.append(" OFFSET ")
            builder.append(offset.offset)
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

        val where = statement.where3Clause
        if (where != null) {
            builder.append(" WHERE ")
            appendPredicate(builder, where.predicate, true)
        }

        val group = statement.group3Clause
        if (group != null) {
            builder.append(" GROUP BY ")
            appendProjection(builder, group.projection, true)
        }

        val having = statement.having3Clause
        if (having != null) {
            builder.append(" HAVING ")
            appendPredicate(builder, having.predicate, true)
        }

        val order = statement.order3Clause
        if (order != null) {
            builder.append(" ORDER BY ")
            appendOrdering(builder, order.orderings, true)
        }

        val limit = statement.limit3Clause
        if (limit != null) {
            builder.append(" LIMIT ")
            builder.append(limit.limit)
        }

        val offset = statement.offset3Clause
        if (offset != null) {
            builder.append(" OFFSET ")
            builder.append(offset.offset)
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

        val where = statement.where4Clause
        if (where != null) {
            builder.append(" WHERE ")
            appendPredicate(builder, where.predicate, true)
        }

        val group = statement.group4Clause
        if (group != null) {
            builder.append(" GROUP BY ")
            appendProjection(builder, group.projection, true)
        }

        val having = statement.having4Clause
        if (having != null) {
            builder.append(" HAVING ")
            appendPredicate(builder, having.predicate, true)
        }

        val order = statement.order4Clause
        if (order != null) {
            builder.append(" ORDER BY ")
            appendOrdering(builder, order.orderings, true)
        }

        val limit = statement.limit4Clause
        if (limit != null) {
            builder.append(" LIMIT ")
            builder.append(limit.limit)
        }

        val offset = statement.offset4Clause
        if (offset != null) {
            builder.append(" OFFSET ")
            builder.append(offset.offset)
        }

        return builder.toString()
    }

    override fun <T : Table> build(statement: InsertStatement<T>): String {
        val builder = StringBuilder()
        builder.append("INSERT INTO ")
        appendTableName(builder, statement.subject.table)
        builder.append(" (")

        var delim = ""

        for (assign in statement.assignments) {
            builder.append(delim)
            delim = ", "

            appendShortColumnName(builder, assign.column)
        }

        builder.append(") VALUES (")

        delim = ""

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

        val where = statement.whereClause
        if (where != null) {
            builder.append(" WHERE ")
            appendPredicate(builder, where.predicate, false)
        }

        return builder.toString()
    }

    override fun <T : Table> build(statement: DeleteStatement<T>): String {
        val builder = StringBuilder()
        builder.append("DELETE FROM ")
        appendTableName(builder, statement.subject.table)

        val where = statement.whereClause
        if (where != null) {
            builder.append(" WHERE ")
            appendPredicate(builder, where.predicate, false)
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
        if ("SELECT".contentEquals(builder.toString().trim()) and projection.none()) {
            builder.append("*")
        } else {
            var delim = ""
            for (proj in projection) {
                builder.append(delim)
                delim = ", "

                if (proj is Table.Column) {
                    if (fullFormat) {
                        appendFullColumnName(builder, proj)
                    } else {
                        appendShortColumnName(builder, proj)
                    }
                } else {
                    builder.append(proj)
                }
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

    private fun appendTableName(builder: StringBuilder, table: Table) {
        builder.append("\"$table\"")
    }

    private fun appendShortColumnName(builder: StringBuilder, column: Table.Column) {
        builder.append("\"$column\"")
    }

    private fun appendFullColumnName(builder: StringBuilder, column: Table.Column) {
        builder.append("\"${column.table}\".\"$column\"")
    }

    private fun appendValue(builder: StringBuilder, value: Any?) {
        value?.let {
            builder.append(if (value is String) "\'$value\'" else value)
        } ?: builder.append("NULL")
    }
}
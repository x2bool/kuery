package com.nivabit.kuery.sqlite

import com.nivabit.kuery.*
import com.nivabit.kuery.ddl.*
import com.nivabit.kuery.dml.*

object SQLiteDialect : Dialect {

    override fun <T : Table> build(statement: CreateTableStatement<T>): String {
        var sql = "CREATE TABLE \"${statement.subject.table}\""

        sql += " (${statement.definitions.map {
            var def = "${buildShortKey(it.column)} ${it.type}"

            if (it is SQLiteDefinition) {
                if (it.meta.primaryKeyConstraint != null) {
                    def += " PRIMARY KEY${if (it.meta.primaryKeyConstraint.autoIncrement) " AUTOINCREMENT" else ""}"
                }

                if (it.meta.foreignKeyConstraint != null) {
                    val col = it.meta.foreignKeyConstraint.references
                    def += " REFERENCES \"${col.table}\"(${buildShortKey(col)})"
                }

                if (it.meta.uniqueConstraint != null) {
                    def += " UNIQUE"
                }

                if (it.meta.notNullConstraint != null) {
                    def += " NOT NULL"
                }
            }

            def
        }.joinToString(", ")})"

        return sql
    }

    override fun <T : Table> build(statement: DropTableStatement<T>): String {
        return "DROP TABLE \"${statement.subject.table}\""
    }

    override fun <T : Table> build(statement: SelectStatement<T>): String {
        var sql = "SELECT ${buildProjection(statement.projection, false)} FROM \"${statement.subject.table}\""

        if (statement.whereClause != null)
            sql += " WHERE ${buildPredicate(statement.whereClause!!.predicate, false)}"

        if (statement.orderClause != null)
            sql += " ORDER BY ${buildOrdering(statement.orderClause!!.orderings, false)}"

        if (statement.limitClause != null)
            sql += " LIMIT ${statement.limitClause!!.limit}"

        if (statement.offsetClause != null)
            sql += " OFFSET ${statement.offsetClause!!.offset}"

        return sql
    }

    override fun <T : Table, T2 : Table> build(statement: Select2Statement<T, T2>): String {
        var sql = "SELECT ${buildProjection(statement.projection, true)}"

        sql += " FROM \"${statement.joinOn2Clause.subject.table}\""

        if (statement.joinOn2Clause.type == JoinType.OUTER) sql += " OUTER"
        sql += " JOIN \"${statement.joinOn2Clause.table2}\""
        sql += " ON ${buildPredicate(statement.joinOn2Clause.condition, true)}"

        if (statement.where2Clause != null)
            sql += " WHERE ${buildPredicate(statement.where2Clause!!.predicate, true)}"

        if (statement.order2Clause != null)
            sql += " ORDER BY ${buildOrdering(statement.order2Clause!!.orderings, true)}"

        if (statement.limit2Clause != null)
            sql += " LIMIT ${statement.limit2Clause!!.limit}"

        if (statement.offset2Clause != null)
            sql += " OFFSET ${statement.offset2Clause!!.offset}"

        return sql
    }

    override fun <T : Table, T2 : Table, T3 : Table> build(statement: Select3Statement<T, T2, T3>): String {
        var sql = "SELECT ${buildProjection(statement.projection, true)}"

        sql += " FROM \"${statement.joinOn3Clause.joinOn2Clause.subject.table}\""

        if (statement.joinOn3Clause.joinOn2Clause.type == JoinType.OUTER) sql += " OUTER"
        sql += " JOIN \"${statement.joinOn3Clause.joinOn2Clause.table2}\""
        sql += " ON ${buildPredicate(statement.joinOn3Clause.joinOn2Clause.condition, true)}"

        if (statement.joinOn3Clause.type == JoinType.OUTER) sql += " OUTER"
        sql += " JOIN \"${statement.joinOn3Clause.table3}\""
        sql += " ON ${buildPredicate(statement.joinOn3Clause.condition, true)}"

        if (statement.where3Clause != null)
            sql += " WHERE ${buildPredicate(statement.where3Clause!!.predicate, true)}"

        if (statement.order3Clause != null)
            sql += " ORDER BY ${buildOrdering(statement.order3Clause!!.orderings, true)}"

        if (statement.limit3Clause != null)
            sql += " LIMIT ${statement.limit3Clause!!.limit}"

        if (statement.offset3Clause != null)
            sql += " OFFSET ${statement.offset3Clause!!.offset}"

        return sql
    }

    override fun <T : Table, T2 : Table, T3 : Table, T4 : Table> build(statement: Select4Statement<T, T2, T3, T4>): String {
        var sql = "SELECT ${buildProjection(statement.projection, true)}"

        sql += " FROM \"${statement.joinOn4Clause.joinOn3Clause.joinOn2Clause.subject.table}\""

        if (statement.joinOn4Clause.joinOn3Clause.joinOn2Clause.type == JoinType.OUTER) sql += " OUTER"
        sql += " JOIN \"${statement.joinOn4Clause.joinOn3Clause.joinOn2Clause.table2}\""
        sql += " ON ${buildPredicate(statement.joinOn4Clause.joinOn3Clause.joinOn2Clause.condition, true)}"

        if (statement.joinOn4Clause.joinOn3Clause.type == JoinType.OUTER) sql += " OUTER"
        sql += " JOIN \"${statement.joinOn4Clause.joinOn3Clause.table3}\""
        sql += " ON ${buildPredicate(statement.joinOn4Clause.joinOn3Clause.condition, true)}"

        if (statement.joinOn4Clause.type == JoinType.OUTER) sql += " OUTER"
        sql += " JOIN \"${statement.joinOn4Clause.table4}\""
        sql += " ON ${buildPredicate(statement.joinOn4Clause.condition, true)}"

        if (statement.where4Clause != null)
            sql += " WHERE ${buildPredicate(statement.where4Clause!!.predicate, true)}"

        if (statement.order4Clause != null)
            sql += " ORDER BY ${buildOrdering(statement.order4Clause!!.orderings, true)}"

        if (statement.limit4Clause != null)
            sql += " LIMIT ${statement.limit4Clause!!.limit}"

        if (statement.offset4Clause != null)
            sql += " OFFSET ${statement.offset4Clause!!.offset}"

        return sql
    }

    override fun <T : Table> build(statement: InsertStatement<T>): String {
        val keys = statement.assignments.map { buildShortKey(it.key) }
        val vals = statement.assignments.map { buildValue(it.value) }

        return "INSERT INTO \"${statement.subject.table}\" (${keys.joinToString(", ")}) VALUES (${vals.joinToString(", ")})"
    }

    override fun <T : Table> build(statement: UpdateStatement<T>): String {
        var sql = "UPDATE \"${statement.subject.table}\" SET"
        sql += " ${statement.assignments.map { "${buildShortKey(it.key)} = ${buildValue(it.value)}" }.joinToString(", ")}"

        if (statement.whereClause != null) {
            sql += " WHERE ${buildPredicate(statement.whereClause!!.predicate, false)}"
        }

        return sql
    }

    override fun <T : Table> build(statement: DeleteStatement<T>): String {
        var sql = "DELETE FROM \"${statement.subject.table}\""

        if (statement.whereClause != null) {
            sql += " WHERE ${buildPredicate(statement.whereClause!!.predicate, false)}"
        }

        return sql
    }

    private fun buildPredicate(value: Any?, fullFormat: Boolean = true): String {
        return when (value) {

            is Table.Column -> if (fullFormat) "\"${value.table}\".\"$value\"" else "\"$value\""

            is NotExpression -> "(NOT ${buildPredicate(value.param, fullFormat)})"

            is AndExpression -> "(${buildPredicate(value.left, fullFormat)} AND ${buildPredicate(value.right, fullFormat)})"

            is OrExpression -> "(${buildPredicate(value.left, fullFormat)} OR ${buildPredicate(value.right, fullFormat)})"

            is EqExpression -> if (value.right != null) {
                "${buildPredicate(value.left, fullFormat)} = ${buildPredicate(value.right, fullFormat)}"
            } else {
                "${buildPredicate(value.left, fullFormat)} IS NULL"
            }

            is NeExpression -> if (value.right != null) {
                "${buildPredicate(value.left, fullFormat)} != ${buildPredicate(value.right, fullFormat)}"
            } else {
                "${buildPredicate(value.left, fullFormat)} IS NOT NULL"
            }

            is LtExpression -> "${buildPredicate(value.left, fullFormat)} < ${buildPredicate(value.right, fullFormat)}"

            is LteExpression -> "${buildPredicate(value.left, fullFormat)} <= ${buildPredicate(value.right, fullFormat)}"

            is GtExpression -> "${buildPredicate(value.left, fullFormat)} > ${buildPredicate(value.right, fullFormat)}"

            is GteExpression -> "${buildPredicate(value.left, fullFormat)} >= ${buildPredicate(value.right, fullFormat)}"

            else -> if (value == null) "NULL" else value.toString()
        }
    }

    private fun buildProjection(projection: Iterable<Projection>, fullFormat: Boolean = true): String {
        return projection
                .map { if (fullFormat) buildFullKey(it.projection) else buildShortKey(it.projection) }
                .joinToString(", ")
    }

    private fun buildOrdering(orderings: Iterable<Ordering>, fullFormat: Boolean = true): String {
        return orderings
                .map { (if (fullFormat) buildFullKey(it.key) else buildShortKey(it.key)) +
                        if (it.asc) " ASC" else " DESC" }
                .joinToString(", ")
    }

    private fun buildShortKey(key: Any): String {
        return when (key) {
            is Table.Column -> "\"$key\""
            else -> key.toString()
        }
    }

    private fun buildFullKey(key: Any): String {
        return when (key) {
            is Table.Column -> "\"${key.table}\".\"$key\""
            else -> key.toString()
        }
    }

    private fun buildValue(value: Any?): String {
        if (value == null) return "NULL"
        return value.toString()
    }
}
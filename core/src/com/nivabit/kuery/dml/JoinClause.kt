package com.nivabit.kuery.dml

import com.nivabit.kuery.*

class Join2Clause<T: Table, T2: Table>(
        val subject: Subject<T>,
        val table2: T2,
        val type: JoinType = JoinType.INNER) {

    inline fun on(condition: (T, T2) -> Predicate): JoinOn2Clause<T, T2> {
        return JoinOn2Clause(
                subject,
                table2,
                type,
                condition(subject.table, table2)
        )
    }

}

class JoinOn2Clause<T: Table, T2: Table>(
        val subject: Subject<T>,
        val table2: T2,
        val type: JoinType,
        val condition: Predicate) {

    inline fun <T3: Table> join(table3: T3): Join3Clause<T, T2, T3> {
        return Join3Clause(this, table3)
    }

    inline fun <T3: Table> outerJoin(table3: T3): Join3Clause<T, T2, T3> {
        return Join3Clause(this, table3, JoinType.OUTER)
    }

    inline fun where(predicate: (T, T2) -> Predicate): Where2Clause<T, T2> {
        return Where2Clause(predicate(subject.table, table2), this)
    }

    inline fun groupBy(group: (T, T2) -> Iterable<Projection>): Group2Clause<T, T2> {
        return Group2Clause(group(subject.table, table2), this, null)
    }

    inline fun orderBy(order: (T, T2) -> Iterable<Ordering>): Order2Clause<T, T2> {
        return Order2Clause(order(subject.table, table2), this, null, null, null)
    }

    inline fun limit(limit: () -> Any): Limit2Clause<T, T2> {
        return Limit2Clause(
                limit(),
                this,
                null,
                null,
                null,
                null)
    }

    inline fun offset(offset: () -> String): Offset2Clause<T, T2> {
        return Offset2Clause(
                offset(),
                limit { "-1" },
                this,
                null,
                null,
                null,
                null)
    }

    inline fun select(projection: (T, T2) -> Iterable<Projection>): Select2Statement<T, T2> {
        return Select2Statement(
                projection(subject.table, table2),
                this,
                null,
                null,
                null,
                null,
                null,
                null)
    }

}

class Join3Clause<T: Table, T2: Table, T3: Table>(
        val joinOn2Clause: JoinOn2Clause<T, T2>,
        val table3: T3,
        val type: JoinType = JoinType.INNER) {

    inline fun on(condition: (T, T2, T3) -> Predicate): JoinOn3Clause<T, T2, T3> {
        return JoinOn3Clause(
                joinOn2Clause,
                table3,
                type,
                condition(joinOn2Clause.subject.table, joinOn2Clause.table2, table3)
        )
    }
}

class JoinOn3Clause<T: Table, T2: Table, T3: Table>(
        val joinOn2Clause: JoinOn2Clause<T, T2>,
        val table3: T3,
        val type: JoinType,
        val condition: Predicate) {

    inline fun <T4: Table> join(table4: T4): Join4Clause<T, T2, T3, T4> {
        return Join4Clause(this, table4)
    }

    inline fun <T4: Table> outerJoin(table4: T4): Join4Clause<T, T2, T3, T4> {
        return Join4Clause(this, table4, JoinType.OUTER)
    }

    inline fun where(predicate: (T, T2, T3) -> Predicate): Where3Clause<T, T2, T3> {
        return Where3Clause(predicate(joinOn2Clause.subject.table, joinOn2Clause.table2, table3), this)
    }

    inline fun groupBy(group: (T, T2, T3) -> Iterable<Projection>): Group3Clause<T, T2, T3> {
        return Group3Clause(
                group(joinOn2Clause.subject.table, joinOn2Clause.table2, table3),
                this,
                null
        )
    }

    inline fun orderBy(order: (T, T2, T3) -> Iterable<Ordering>): Order3Clause<T, T2, T3> {
        return Order3Clause(
                order(joinOn2Clause.subject.table, joinOn2Clause.table2, table3),
                this,
                null,
                null,
                null
        )
    }

    inline fun limit(limit: () -> Any): Limit3Clause<T, T2, T3> {
        return Limit3Clause(
                limit(),
                this,
                null,
                null,
                null,
                null)
    }

    inline fun offset(offset: () -> String): Offset3Clause<T, T2, T3> {
        return Offset3Clause(
                offset(),
                limit { "-1" },
                this,
                null,
                null,
                null,
                null)
    }

    inline fun select(projection: (T, T2, T3) -> Iterable<Projection>): Select3Statement<T, T2, T3> {
        return Select3Statement(
                projection(joinOn2Clause.subject.table, joinOn2Clause.table2, table3),
                this,
                null,
                null,
                null,
                null,
                null,
                null)
    }

}

class Join4Clause<T: Table, T2: Table, T3: Table, T4: Table>(
        val joinOn3Clause: JoinOn3Clause<T, T2, T3>,
        val table4: T4,
        val type: JoinType = JoinType.INNER) {

    inline fun on(condition: (T, T2, T3, T4) -> Predicate): JoinOn4Clause<T, T2, T3, T4> {
        return JoinOn4Clause(
                joinOn3Clause,
                table4,
                type,
                condition(joinOn3Clause.joinOn2Clause.subject.table, joinOn3Clause.joinOn2Clause.table2, joinOn3Clause.table3, table4)
        )
    }
}

class JoinOn4Clause<T: Table, T2: Table, T3: Table, T4: Table>(
        val joinOn3Clause: JoinOn3Clause<T, T2, T3>,
        val table4: T4,
        val type: JoinType,
        val condition: Predicate) {
    
    inline fun where(predicate: (T, T2, T3, T4) -> Predicate): Where4Clause<T, T2, T3, T4> {
        return Where4Clause(
                predicate(
                        joinOn3Clause.joinOn2Clause.subject.table,
                        joinOn3Clause.joinOn2Clause.table2,
                        joinOn3Clause.table3,
                        table4
                ),
                this)
    }

    inline fun groupBy(group: (T, T2, T3, T4) -> Iterable<Projection>): Group4Clause<T, T2, T3, T4> {
        return Group4Clause(
                group(
                        joinOn3Clause.joinOn2Clause.subject.table,
                        joinOn3Clause.joinOn2Clause.table2,
                        joinOn3Clause.table3,
                        table4
                ),
                this,
                null
        )
    }

    inline fun orderBy(order: (T, T2, T3, T4) -> Iterable<Ordering>): Order4Clause<T, T2, T3, T4> {
        return Order4Clause(
                order(
                        joinOn3Clause.joinOn2Clause.subject.table,
                        joinOn3Clause.joinOn2Clause.table2,
                        joinOn3Clause.table3,
                        table4
                ),
                this,
                null,
                null,
                null
        )
    }

    inline fun limit(limit: () -> Any): Limit4Clause<T, T2, T3, T4> {
        return Limit4Clause(
                limit(),
                this,
                null,
                null,
                null,
                null)
    }

    inline fun offset(offset: () -> String): Offset4Clause<T, T2, T3, T4> {
        return Offset4Clause(
                offset(),
                limit { "-1" },
                this,
                null,
                null,
                null,
                null)
    }

    inline fun select(projection: (T, T2, T3, T4) -> Iterable<Projection>): Select4Statement<T, T2, T3, T4> {
        return Select4Statement(
                projection(
                        joinOn3Clause.joinOn2Clause.subject.table,
                        joinOn3Clause.joinOn2Clause.table2,
                        joinOn3Clause.table3,
                        table4
                ),
                this,
                null,
                null,
                null,
                null,
                null,
                null)
    }

}

enum class JoinType {
    INNER,
    OUTER
}
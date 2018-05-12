package tel.egram.kuery.dml

import tel.egram.kuery.*

class InsertStatement<T: Table>(
        val assignments: Iterable<Assignment>,
        val subject: Subject<T>) {

    fun toString(dialect: Dialect): String {
        return dialect.build(this)
    }
}
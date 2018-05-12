package tel.egram.kuery.ddl

import tel.egram.kuery.*

class DropTableStatement<T: Table>(
        val subject: Subject<T>) {

    fun toString(dialect: Dialect): String {
        return dialect.build(this)
    }
}
package tel.egram.kuery.ddl

import tel.egram.kuery.*

class CreateTableStatement<T: Table>(
        val definitions: Iterable<Definition>,
        val subject: Subject<T>) {

    fun toString(dialect: Dialect): String {
        return dialect.build(this)
    }
}
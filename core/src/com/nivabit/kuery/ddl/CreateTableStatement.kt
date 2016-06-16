package com.nivabit.kuery.ddl

import com.nivabit.kuery.*

class CreateTableStatement<T: Table>(
        val definitions: Iterable<Definition>,
        val subject: Statement<T>) {

    fun toString(dialect: Dialect): String {
        return dialect.build(this)
    }
}
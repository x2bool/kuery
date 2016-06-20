package com.nivabit.kuery.ddl

import com.nivabit.kuery.*

class DropTableStatement<T: Table>(
        val subject: Subject<T>) {

    fun toString(dialect: Dialect): String {
        return dialect.build(this)
    }
}
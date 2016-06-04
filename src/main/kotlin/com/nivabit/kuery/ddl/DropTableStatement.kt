package com.nivabit.kuery.ddl

import com.nivabit.kuery.*

class DropTableStatement<T: Table>(
        val subject: Statement<T>) {

    override fun toString(): String {
        return "DROP TABLE \"${subject.table}\""
    }
}
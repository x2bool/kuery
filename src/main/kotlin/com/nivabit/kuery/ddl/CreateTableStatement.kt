package com.nivabit.kuery.ddl

import com.nivabit.kuery.Statement
import com.nivabit.kuery.Table

class CreateTableStatement<T: Table>(
        val definition: Definition,
        val subject: Statement<T>) {

    override fun toString(): String {
        return "CREATE TABLE \"${subject.table}\" (${definition.definition})"
    }
}
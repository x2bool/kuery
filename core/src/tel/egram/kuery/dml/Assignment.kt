package tel.egram.kuery.dml

import tel.egram.kuery.Table

interface Assignment : Iterable<Assignment> {

    val column: Table.Column
    val value: Any?

    override fun iterator(): Iterator<Assignment> {
        return object : Iterator<Assignment> {
            var valid = true
            override fun hasNext(): Boolean {
                return valid
            }
            override fun next(): Assignment {
                valid = false
                return this@Assignment
            }
        }
    }

    class Value(override val column: Table.Column, override val value: Any?) : Assignment

}
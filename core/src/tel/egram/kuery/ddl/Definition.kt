package tel.egram.kuery.ddl

import tel.egram.kuery.*

interface Definition : Iterable<Definition> {

    val column: Table.Column
    val type: String

    override fun iterator(): Iterator<Definition> {
        return object : Iterator<Definition> {
            var valid = true
            override fun hasNext(): Boolean {
                return valid
            }
            override fun next(): Definition {
                valid = false
                return this@Definition
            }
        }
    }

    open class Column(override val column: Table.Column, override val type: String) : Definition
}
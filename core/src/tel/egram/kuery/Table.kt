package tel.egram.kuery

import tel.egram.kuery.ddl.Definition
import tel.egram.kuery.dml.*

open class Table(private val name: String) {

    inner class Column(val name: String) : Projection {

        val table: Table
            get() = this@Table

        val asc: Ordering
            get() = Ordering.By(this, true)

        val desc: Ordering
            get() = Ordering.By(this, false)

        operator fun invoke(column: Column): Assignment {
            return Assignment.Value(this, column)
        }

        operator fun invoke(value: String?): Assignment {
            return Assignment.Value(this, value)
        }

        operator fun invoke(value: Number): Assignment {
            return Assignment.Value(this, value)
        }

        operator fun invoke(value: Boolean): Assignment {
            return Assignment.Value(this, value)
        }

        override fun toString(): String {
            return name
        }
    }

    override fun toString(): String {
        return name
    }
}

operator fun Table.Column.rangeTo(column: Table.Column): Iterable<Table.Column> {
    return listOf(this, column)
}

operator fun Iterable<Table.Column>.rangeTo(column: Table.Column): Iterable<Table.Column> {
    return this.plusElement(column)
}

operator fun Iterable<Ordering>.rangeTo(ordering: Ordering): Iterable<Ordering> {
    return if (this is Ordering) listOf(this, ordering) else this.plusElement(ordering)
}

operator fun Iterable<Assignment>.rangeTo(assignment: Assignment): Iterable<Assignment> {
    return if (this is Assignment) listOf(this, assignment) else this.plusElement(assignment)
}

operator fun Iterable<Definition>.rangeTo(definition: Definition): Iterable<Definition> {
    return if (this is Definition) listOf(this, definition) else this.plusElement(definition)
}
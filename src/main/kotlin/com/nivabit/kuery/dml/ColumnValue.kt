package com.nivabit.kuery.dml

import com.nivabit.kuery.Table

class ColumnValue(val column: Table.Column, val value: Any) : Iterable<ColumnValue> {

    override fun iterator(): Iterator<ColumnValue> {
        return object : Iterator<ColumnValue> {
            override fun hasNext(): Boolean {
                return true
            }

            override fun next(): ColumnValue {
                return this@ColumnValue
            }
        }
    }

    operator fun plus(value: ColumnValue): ColumnValues {
        return ColumnValues(arrayOf(this, value))
    }

    operator fun plus(values: ColumnValues): ColumnValues {
        return ColumnValues(arrayOf(this) + values.values)
    }
}

class ColumnValues(val values: Array<ColumnValue>) : Iterable<ColumnValue> {

    override fun iterator(): Iterator<ColumnValue> {
        return values.iterator()
    }

    operator fun plus(value: ColumnValue): ColumnValues {
        return ColumnValues(values + arrayOf(value))
    }

    operator fun plus(values: ColumnValues): ColumnValues {
        return ColumnValues(this.values + values.values)
    }
}
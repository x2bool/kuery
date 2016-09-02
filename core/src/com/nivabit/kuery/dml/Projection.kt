package com.nivabit.kuery.dml

interface Projection : Iterable<Projection> {
    override fun iterator(): Iterator<Projection> {
        return object : Iterator<Projection> {
            var valid = true
            override fun hasNext(): Boolean {
                return valid
            }
            override fun next(): Projection {
                valid = false
                return this@Projection
            }
        }
    }
}

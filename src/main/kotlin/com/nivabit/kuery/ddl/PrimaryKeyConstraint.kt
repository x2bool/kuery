package com.nivabit.kuery.ddl

class PrimaryKeyConstraint(val autoIncrement: Boolean = false) {
    override fun toString(): String {
        return "PRIMARY KEY${if (autoIncrement) " AUTOINCREMENT" else ""}"
    }
}
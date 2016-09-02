package com.nivabit.kuery

interface Predicate

/**
 * Not expression
 */
class NotExpression(val param: Any?) : Predicate

fun not(predicate: Predicate): NotExpression {
    return NotExpression(predicate)
}

/**
 * And expression
 */
class AndExpression(val left: Any?, val right: Any?) : Predicate

infix fun Predicate.and(predicate: Predicate): AndExpression {
    return AndExpression(this, predicate)
}

/**
 * Or expression
 */
class OrExpression(val left: Any?, val right: Any?) : Predicate

infix fun Predicate.or(predicate: Predicate): OrExpression {
    return OrExpression(this, predicate)
}

/**
 * Equals expression
 */
class EqExpression(val left: Any?, val right: Any?) : Predicate

infix fun Table.Column.eq(column: Table.Column): EqExpression {
    return EqExpression(this, column)
}

infix fun Table.Column.eq(str: String?): EqExpression {
    return EqExpression(this, str)
}

infix fun Table.Column.eq(num: Number): EqExpression {
    return EqExpression(this, num)
}

infix fun Table.Column.eq(flag: Boolean): EqExpression {
    return EqExpression(this, flag)
}

/**
 * Not equals expression
 */
class NeExpression(val left: Any?, val right: Any?) : Predicate

infix fun Table.Column.ne(column: Table.Column): NeExpression {
    return NeExpression(this, column)
}

infix fun Table.Column.ne(str: String?): NeExpression {
    return NeExpression(this, str)
}

infix fun Table.Column.ne(num: Number): NeExpression {
    return NeExpression(this, num)
}

infix fun Table.Column.ne(flag: Boolean): NeExpression {
    return NeExpression(this, flag)
}

/**
 * Less than expression
 */
class LtExpression(val left: Any?, val right: Any?) : Predicate

infix fun Table.Column.lt(column: Table.Column): LtExpression {
    return LtExpression(this, column)
}

infix fun Table.Column.lt(str: String?): LtExpression {
    return LtExpression(this, str)
}

infix fun Table.Column.lt(num: Number): LtExpression {
    return LtExpression(this, num)
}

/**
 * Less than or equal expression
 */
class LteExpression(val left: Any?, val right: Any?) : Predicate

infix fun Table.Column.lte(column: Table.Column): LteExpression {
    return LteExpression(this, column)
}

infix fun Table.Column.lte(str: String?): LteExpression {
    return LteExpression(this, str)
}

infix fun Table.Column.lte(num: Number): LteExpression {
    return LteExpression(this, num)
}

/**
 * Greater than expression
 */
class GtExpression(val left: Any?, val right: Any?) : Predicate

infix fun Table.Column.gt(column: Table.Column): GtExpression {
    return GtExpression(this, column)
}

infix fun Table.Column.gt(str: String?): GtExpression {
    return GtExpression(this, str)
}

infix fun Table.Column.gt(num: Number): GtExpression {
    return GtExpression(this, num)
}

/**
 * Greater than or equal expression
 */
class GteExpression(val left: Any?, val right: Any?) : Predicate

infix fun Table.Column.gte(column: Table.Column): GteExpression {
    return GteExpression(this, column)
}

infix fun Table.Column.gte(str: String?): GteExpression {
    return GteExpression(this, str)
}

infix fun Table.Column.gte(num: Number): GteExpression {
    return GteExpression(this, num)
}
package com.nivabit.kuery

abstract class Predicate

/**
 * Not expression
 */
class NotExpression(val param: Any?) : Predicate()

fun not(param: Any?): NotExpression {
    return NotExpression(param)
}

/**
 * And expression
 */
class AndExpression(val left: Any?, val right: Any?) : Predicate()

infix fun Any?.and(param: Any?): AndExpression {
    return AndExpression(this, param)
}

/**
 * Or expression
 */
class OrExpression(val left: Any?, val right: Any?) : Predicate()

infix fun Any?.or(param: Any?): OrExpression {
    return OrExpression(this, param)
}

/**
 * Equals expression
 */
class EqExpression(val left: Any?, val right: Any?) : Predicate()

infix fun Any?.eq(param: Any?): EqExpression {
    return EqExpression(this, param)
}

/**
 * Not equals expression
 */
class NeExpression(val left: Any?, val right: Any?) : Predicate()

infix fun Any?.ne(param: Any?): NeExpression {
    return NeExpression(this, param)
}

/**
 * Less than expression
 */
class LtExpression(val left: Any?, val right: Any?) : Predicate()

infix fun Any?.lt(param: Any?): LtExpression {
    return LtExpression(this, param)
}

/**
 * Less than or equal expression
 */
class LteExpression(val left: Any?, val right: Any?) : Predicate()

infix fun Any?.lte(param: Any?): LteExpression {
    return LteExpression(this, param)
}

/**
 * Greater than expression
 */
class GtExpression(val left: Any?, val right: Any?) : Predicate()

infix fun Any?.gt(param: Any?): GtExpression {
    return GtExpression(this, param)
}

/**
 * Greater than or equal expression
 */
class GteExpression(val left: Any?, val right: Any?) : Predicate()

infix fun Any?.gte(param: Any?): GteExpression {
    return GteExpression(this, param)
}
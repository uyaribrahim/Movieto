package com.ri.movieto.domain.mapper

@FunctionalInterface
interface Mapper<I, O> {
    open fun mapFrom(input: I): O
}
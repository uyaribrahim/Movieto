package com.ri.movieto.domain.mapper

@FunctionalInterface
interface Mapper<I, O> {
    fun mapFrom(input: I): O
}
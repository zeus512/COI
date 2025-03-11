package com.gouthamreddy.constitutionofindia.mappers
interface Mapper<in Input, out Output> {
    fun map(input: Input): Output
}
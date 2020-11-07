package com.wooooooak.lastcapture.mapper

interface Mapper<T, U> {
    fun map(to: T): U
}
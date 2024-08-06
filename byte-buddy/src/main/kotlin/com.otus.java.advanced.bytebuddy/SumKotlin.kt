package com.otus.java.advanced.bytebuddy

object SumKotlin {

    @JvmStatic
    fun main(args: Array<String>) {
        var sum = 0

        for (i in 1..10) {
            sum += i
        }

        println(sum)
    }
}
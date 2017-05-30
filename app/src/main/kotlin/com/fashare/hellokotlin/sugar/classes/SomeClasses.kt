package com.fashare.hellokotlin.sugar.classes

/**
 * Created by apple on 17-5-31.
 * 类与继承
 */
open class People(val name: String){}

class Coder(name: String, val language: String = "Kotlin"): People(name){

    override fun toString(): String {
        return "name: $name, language: $language"
    }
}

fun main(args: Array<String>) {
    val coder = Coder("Tom")

    println(coder)
}
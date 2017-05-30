package com.fashare.hellokotlin.feature.extension

import com.fashare.hellokotlin.sugar.lambda.myForEach

/**
 * Created by apple on 17-5-31.
 *
 * 函数拓展, 属性拓展
 */
fun main(args: Array<String>) {
    val list = listOf("1", "2", "3", "4")

    // 函数拓展
    list.myForEach { println(it) }

    // 属性拓展
    println("last: ${list.lastItem}")
}

/**
 * 拓展 List 类, 加一个自定义的遍历方法
 */
fun <T> List<T>.myForEach(doTask: (T) -> Unit){
    for(item in this)
        doTask(item)
}

/**
 * 拓展 List 类, 加一个自定义的长度属性
 */
val <T> List<T>.lastItem: T
        get() = get(size - 1)

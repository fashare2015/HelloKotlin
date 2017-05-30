package com.fashare.hellokotlin.feature.delegate


/**
 * Created by apple on 17-5-31.
 *
 * 属性代理
 */

val lazySum: Int by lazy {
    println("begin compute lazySum ...")
    var sum = 0
    for (i in 0..100)
        sum += i
    println("lazySum computed!\n")
    sum // 返回计算结果
}

fun main(args: Array<String>) {
    println(lazySum)
    println(lazySum)
}
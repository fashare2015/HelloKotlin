package com.fashare.hellokotlin.sugar.stream

/**
 * Created by apple on 17-5-30.
 *
 * 集合操作, 类似于 Java8 新增的 Stream
 */
fun main(args: Array<String>){
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    list.filter { it%2==0 }             // 取偶数
            .map{ it*it }               // 平方
            .sortedDescending()         // 降序排序
            .take(3)                    // 取前 3 个
            .forEach { println(it) }    // 遍历, 打印
}
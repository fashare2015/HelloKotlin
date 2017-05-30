package com.fashare.hellokotlin.sugar.lambda

/**
 * Created by apple on 17-5-30.
 *
 * lambda 表达式, 类似 java8 中的lambda
 */
fun main(args: Array<String>) {
    // new 一个线程
    val runnable = { println("I'm a Lambda") }
    Thread(runnable).start()

    // 自定义高阶函数, lambda 表达式 作为入参
    listOf("1", "2", "3", "4").myForEach { println(it) }

    // 自定义高阶函数, lambda 表达式 作为返回值
//    getLogger()("I'm a Closure")
    var logger = getLogger()
    logger("I'm a Closure")
}

/**
 * 接受一个 lambda 表达式, 作为遍历任务
 */
fun <T> List<T>.myForEach(doTask: (T) -> Unit){
    for(item in this)
        doTask(item)
}

/**
 * 返回一个 lambda 表达式(闭包), 如: 日志输出工具 logger
 */
fun getLogger(): (String) -> Unit{
//    return { println(it) }
    return fun (it: String){
        println(it)
    }
}
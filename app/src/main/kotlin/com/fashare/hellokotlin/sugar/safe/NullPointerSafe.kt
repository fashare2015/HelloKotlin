package com.fashare.hellokotlin.sugar.safe

/**
 * Created by apple on 17-5-30.
 *
 * 空安全, 编译时期的空检查, 类似 @Nullable, @NonNull
 */
class NullPointerSafe{
    var mNullable: String? = null
    var mNonNull: String = "mNonNull"

    fun testNull(){
        println("testNull: ")
        println(mNullable?.length)
        println(mNonNull.length)
        println()
    }

    /**
     * 入参严格非空, 编译时检查
     */
    fun printList(list: List<String>){
        println("printList: ")
        list.forEach{ println(it) }
        println()
    }
}

fun main(args: Array<String>){
    val nullPointerSafe = NullPointerSafe()

    nullPointerSafe.testNull()

    var listNonNull: List<String> = listOf("1", "2", "3", "4")
    nullPointerSafe.printList(listNonNull)  // 入参为非空List, 正常运行

    // 编译错误
    var listNullable: List<String>? = listOf("1", "2", "3", "4")
//    nullPointerSafe.printList(listNullable) // 入参为可空List, 编译错误
}
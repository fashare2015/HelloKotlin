package com.fashare.hellokotlin.sugar.interfaces

/**
 * Created by apple on 17-5-30.
 * 接口可以有默认实现, 类似 Java8 引入的 默认接口和 default 关键字
 */
interface A {
    fun foo() { println("A") }    // 默认实现, 打印"A"
    fun bar()
}

interface B {
    fun foo() { println("B") }
    fun bar() { println("bar") }
}

class C : A {
    override fun bar() { println("bar") }
}

class D : A, B {
    override fun foo() {
        super<A>.foo()
        super<B>.foo()
    }

    override fun bar() {
        super.bar()
    }
}

fun main(args: Array<String>){
    val d: D = D()
    d.foo()
    d.bar()
}



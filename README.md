# Kotlin的来历
Kotlin的作者是大名鼎鼎的Jetbrains公司。它有一系列耳熟能详的产品，诸如Android兄弟天天用的Android Studio, IntelliJ IDEA, 还有前端的WebStorm, PhpStorm。
- 2011年7月，JetBrains推出Kotlin项目。
- 2012年2月，JetBrains以Apache 2许可证开源此项目。
- 2016年2月15日，Kotlin v1.0（第一个官方稳定版本）发布。
- 2017 Google I/O 大会，Kotlin 转正。

我个人大概是在16年年底接触到这门语言，当时由于语法上的诸多相似性，它被誉为“Android中的Swift”。说来也巧，当时正在研究ButterKnife这个框架，偶然发现它还有Kotlin版本——[kotterknife](https://github.com/JakeWharton/kotterknife)，于是就打开了新世界的大门。

# 亮点
个人感觉，Kotlin最大的亮点便是可以和Java**无缝衔接**（虽然它Jvm上的兄弟Groovy,Scala也能做到）。这意味着，我们不但拥有原来java中所有的资源，还能体验到js般的编程体验（雾）。

另外，它的很多特性都分别对应java中的一个坑, 本文会从**语法糖**和**新特性**两个方面来介绍：
- 语法糖
  - 类的简化，隐式getter()、setter()
  - 接口的默认实现
  - lambda与高阶函数
  - 空指针安全，编译时期的空指针检查
  - 流式集合操作 map(), forEach()
- 新特性
  - 函数拓展、属性拓展
  - 属性代理
  
# 简要的环境配置
根目录/build.gradle:
```gradle 
// external 全局变量
ext{
    kotlinVersion = "1.0.0-rc-1036"
}
```
app/build.gradle
```gradle
// kotlin
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    }
}

apply plugin: 'kotlin-android'

android {
    // 建立一个与'src/main/java'同级的kotlin工作目录
    sourceSets {
        main.java.srcDirs += 'src/main/kotlin'
    }
}

dependencies {
    compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
}
```
![目录结构，与java混编](https://raw.githubusercontent.com/fashare2015/HelloKotlin/master/screen-record/project.png)

# 官方资料
[Kotlin 官方参考文档 中文版](https://hltj.gitbooks.io/kotlin-reference-chinese/content/)

# 详细介绍
## 1. 语法糖
### 1.1 类的简化
#### 1.1.1 类的定义
一切从一个简单的JavaBean说起吧。
```kotlin
// java bean
class People{
    private String name;
    
    public People(String name){
        this.name = name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
}

// kotlin bean
class People(val name: String){}
```
从我学java以来就接触了javabean这个概念，我也不懂为啥非要getter()、setter()这种东西，难道为了kpi? 可以看到同样的数据实体，在kotlin中简化至1行。变量定义和构造函数合并，同时提供隐式的getter()、setter()。

#### 1.1.2 继承
下面是一段简单的继承关系：
```kotlin
// 所有类默认final，要显式指定为open才可被继承
open class People(val name: String){}

// Code: People 表示继承关系
class Coder(name: String, val language: String = "Kotlin"): People(name){

    override fun toString(): String {
        return "name: $name, language: $language"
    }
}

// 可执行的main()方法
fun main(args: Array<String>) {
    val coder = Coder("Tom")

    println(coder)
}

// 输出： name: Tom, language: Kotlin
```

#### 1.1.3 数据对象 data class
在看Effective Java的时候，总是对如何正确重写hashCode(),equals(),clone()感到棘手。然而他们又是正确使用Collection的关键所在（无论是HashMap、ArrayList）。
Kotlin专门提供了一个`data class`,来自动生成hashCode(),equals(),clone()。
```kotlin
data class People(val name: String){}
```
<br/>

### 1.2 接口的默认实现
顾名思义，它便是指接口可以和抽象类一样，有方法体的默认实现。
我把它归结在语法糖里，是因为java8中早已有了一模一样的东西，对应的关键字叫`default`。

看起来抽象类好像可以退休了，实则不然。接口依然只是接口，不能拥有属性，只是在方法定义上更加灵活了。

```kotlin
interface A {
    fun foo() { println("A") }    // 默认实现, 打印"A"
    fun bar()
}

interface B {
    fun foo() { println("B") }
    fun bar() { println("bar") }
}

// 多继承时，显式指定 super<A>.foo() 以去冲突
class D : A, B {
    override fun foo() {
        super<A>.foo()
        super<B>.foo()
    }

    override fun bar() {
        super.bar()
    }
}
```
<br/>

### 1.3 lambda与高阶函数
#### 1.3.1 lambda
lambda也不是什么新鲜玩意，在gradle、js之类的其他语言中早就玩烂了。java8中也有，不过官方迟迟不上咱也没办法。
lambda本身是一个函数片段，作为一等公民，它可以作为高阶函数的参数或返回值。个人而言，我喜欢以匿名类来理解它。
```kotlin
// new 一个线程
// 匿名类写法
val runnable1 = object : Runnable{
    override fun run() {
        println("I'm an anonymous class")
    }
}

// 函数写法, 略像js
val runnable2 = fun (){
    println("I'm a function")
}

// lambda写法1
val runnable3 = Runnable { ->
    println("I'm a Lambda")
}

// lambda写法2
val runnable4 = { println("I'm a Lambda") }
Thread(runnable4).start()
```
平常习惯使用`lambda写法2`，省略匿名类的名字Runnable，以及无参数时省略箭头->。不过js那种函数写法也挺不错的。

#### 1.3.2 高阶函数
lambda本身作为一等公民，它是有类型的。如上例的runnable4的类型为`()->Unit`。再比如下面这个加法表达式sum的类型为`(Int, Int) -> Int`。
```kotlin
val sum: (Int, Int) -> Int = { x, y -> x+y }
```
一个变量有类型是再自然不过的事。而高阶函数的入参与返回值既然是lambda,那其类型奇怪一点也很正常。
```kotlin
fun main(args: Array<String>) {
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
```
PS: 看到getLogger()这种用法，你大概意识到可以像js那样写闭包了。

### 1.4 空指针安全
你也许会想空指针不是很简单的东西吗，加个if(xxx != null)判断就好了。事实上，空指针绝对是出现频率最高，最讨厌的问题。而**空安全**则是kotlin主打的一个特性之一。在java8中，我们可以借助Optional勉强做到这一点。

我们来看看是咋回事。
```kotlin
var mNullable: String? = null
var mNonNull: String = "mNonNull"

fun testNull(){
    println("testNull: ")
    println(mNullable?.length)
    println(mNonNull.length)
    println()
}

// 输出：
testNull: 
null
8
```
kotlin定义变量时区分两种类型：
- var mNullable: Any? = null 可空
- var mNonNull: Any = XXX 非空

1.对一个 mNullable 你可以像普通的java类一样使用:

```kotlin
// java 风格，判空
if(mNullable != null)
    mNullable.length
    
// kotlin 语法糖，判空(推荐)
mNullable?.length
```
2.对一个 mNonNull 则有严格的限制：

```kotlin
// 不必判空，因为必然非空
mNonNull.length
    
// 编译错误(试图给非空值赋予null)
mNonNull = null
// 编译错误(试图给非空值赋予可空值)
mNonNull = mNullable
```
我们可以体会到mNonNull天然的好处，永不会空指针。而mNullable加上?判断，也可以达到同样的效果。
<br/>

### 1.5 流式集合操作 map(), forEach()
这个我不知道怎么称呼，姑且叫流式集合操作符把。算是很普遍了，任何语言里都有，然而不支持函数式的话，写起来比较臃肿。
如下例子，一些操作符的衔接，使得操作逻辑十分清晰，之后需求变动，比如降序改为升序，也只需改动`.sortedDescending()`一行，十分灵活。
```kotlin
fun main(args: Array<String>){
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    list.filter { it%2==0 }             // 取偶数
            .map{ it*it }               // 平方
            .sortedDescending()         // 降序排序
            .take(3)                    // 取前 3 个
            .forEach { println(it) }    // 遍历, 打印
}

// 输出：
100
64
36
```
<br/>

## 2. 新特性
### 2.1 拓展
拓展这个东西，貌似是以装饰者模式来做的。它的效果是在不改源码的基础上，添加功能。比如我们要在Activity上加一个toast(),完全不用卸载基类里。这样简化了很多工作，尤其是对一些已打成jar包的类。
```kotlin
fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
```
别的例子：
```kotlin
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

// 输出：
1
2
3
4
last: 4
```
<br/>

### 2.2 属性代理
这个东西干嘛用呢？它把属性的get()、set()代理给了一个类，以便可以在get()和set()时做一些额外的操作。如：
- 懒加载
- 观察者（属性变化时，自动发出通知）
- 属性非空判断
- ...

以懒加载为例，lazySum可能需要复杂的运算，我们把它代理给`lazy`。
可以看到，只有第一次加载进行了计算，之后都是直接取值，提高了效率。
```kotlin
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

// 输出：
begin compute lazySum ...
lazySum computed!

5050
5050
```
另外，我们可以自定义属性代理，之前提到的[kotterknife](https://github.com/JakeWharton/kotterknife),便是一个很好的例子（实现了bindview）:
```kotlin
public class PersonView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
  val firstName: TextView by bindView(R.id.first_name)
  val lastName: TextView by bindView(R.id.last_name)

  // Optional binding.
  val details: TextView? by bindOptionalView(R.id.details)
}
```
<br/>

# Demo实战
用 kotlin 结合一些流行第三方库 + 知乎日报api 做了一个简易的demo，在模块（根目录/mydemo）中：
- Butter Knife
- Retrofit
- Gson
- Rxjava
- Glide
- 个人库 NoViewHolder

![MyDemo: kotlin小栗子](https://raw.githubusercontent.com/fashare2015/HelloKotlin/master/screen-record/mydemo.jpg)

<br/>

# 感谢
[Kotlin 官方参考文档 中文版](https://hltj.gitbooks.io/kotlin-reference-chinese/content/)

[为什么说Kotlin值得一试](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0226/4000.html)


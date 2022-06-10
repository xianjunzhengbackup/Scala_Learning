package lectures.beginner.part2.oop

object OOBasics extends App {
  val person = new Person("Jun",41) //这里叫类的初始化
  println(person)
  println(person.age)
  println(person.x)
  person.greet("Qian")
  person.greet()

  val mao=new Writer("Mao","zhedong",1899)
  val ms = new Novel("Mao wen",1949,mao)
  val ms2=ms.copy(1976)
  val jun=new Writer("jjj","jjj",1899)
  println(ms2.isWrittenBy(jun))
  println(ms2.isWrittenBy(mao))


  println(mao.fullName)
  println(ms.authorAge)
  println(ms2.authorAge)

  val c1=new Counter(20)
  println(c1.current)
  val c2=c1.inc
  println(c2.current)
  println(c1.current)

  val c3=c1.inc(4)
  println(c3.current)
  println(c1.current)

  val c4=c3.dec()
  println(c4.current)
//  val c5=c4.dec(3)
//  println(c5.current)
  println(c4.dec(2).dec(3))
}

//可以单独放在外面的，就只有class和object。面向对象用class来存储数据结构
//constructor
class Person(val name:String,val age:Int) {
  //body
  //在body里定义的所有的val和var都是field，都是可以被外面person调用的

  val x = 2

  //每次初始化类，都会把body里的东西运行一遍，包括下面这句打印
  println(1+3)

  def greet(name:String)=println(s"${this.name} says:Hi,$name")//这里的this.name就是为了区别函数里的parameter
  //overloading
  def greet()=println(s"Hi,I am $name")
  //假如在定义一个 def greet():Int，返回Int，那么person.greet，就不知道该调用哪一个。

  //multiple contructors
  //这样的overload contructor，必须也用contructor来定义
  def this(name:String)=this(name,0)

}

// 假如这样定义 class Person(name:String,age:Int)
// class parameters are not FIELDS,所以不能直接调用。person.name这是非法调用。要加val

//×××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××
//exercise1
/*
实现Novel（小说） and a Writer（作家）
Writer : first name, surname,year
  -method : fullName

Novel: name,year of release,author(Writer类型)
  -authorAge:返回作家写这本小说的时候的年纪
  -copy(new year of release)：new instance of Novel.返回一个包含再版时间的Novel实例
  -isWrittenBy(author)
 */

class Writer(firstName:String,surname:String,val year:Int){
  def fullName:String=s"$firstName $surname"
}
class Novel (name:String,year:Int,author:Writer){
  def authorAge:Int=year - author.year
  def copy(year:Int):Novel=new Novel(name, year, author)
  def isWrittenBy(author:Writer):Boolean={
    this.author == author
  }
}

//exercise 2
/*
Counter class
  - receives an int value(有一个初始值)
  - method current count
  - method to increment/decrement by 1 => new Counter(自增或自减 by 1，且返回一个新的Counter）
  - overload 上面那个函数，increment/decrement to receive an amount
 */

class Counter(start:Int){
  var c=start
  def current:Int=c
  def inc:Counter={
    c = c +1
    new Counter(c)
  }
  def inc(amount:Int):Counter={
    c = c + amount
    new Counter(c)
  }
//这里用了递归，第一个dec返回一个新的counter，新counter接着调用dec
  def dec():Counter={
  c = c -1
  new Counter(c)
  }
  def dec(amount:Int):Counter=
    if(amount<=0) this
    else dec.dec(amount-1)
}
package lectures.beginner.part2.oop

object MethodNotations extends App {
  //把class定义在object里面，没别的意思，就是因为在OOBasics里面已经定义了Person，所以为了避免冲突，写在里面
  class Person(val name:String,favouriteMovie:String,age:Int=0){
    def likes(movie:String):Boolean=movie==favouriteMovie
    def handOutWith(person:Person):String=s"${this.name} is handing out with ${person.name}"
    def +(person:Person):String=s"${this.name} is handing out with ${person.name}"

    def unary_! : String=s"$name, what the heck?" //pre-fix notation
    def isAlive:Boolean=true

    def apply():String=s"Hi,my name is $name and I like $favouriteMovie"

    //**************************************************************************************************************
    def +(str:String):Person=new Person(name+" ("+str+")",favouriteMovie)
    def unary_+():Person=new Person(name,favouriteMovie,age+1)
    def print()=println(s"Hi,my name is $name,my favourite movie is $favouriteMovie and I am $age years old")
    def learns(str:String):Person=new Person(name+" learns "+str,favouriteMovie,age)
    def learnsScala():Person=learns("Scala")
    def apply(n:Int):String=s"$name watched $favouriteMovie $n times"
  }

  val mary=new Person("Mary","Inception")
  println(mary.likes("Inception"))
  println(mary likes "Inception")
  //上面这两句println等价，第二句的写法叫infix notation，它适用于一个parameter的method

  //“operators“in scala
  val tom=new Person("Tom","Fight Club")
  println(mary handOutWith tom) //这还是刚才的写法，但handOutWith两边都是类实例，所以很像操作符。还可以将函数handOutWith 用+代替
  println(mary + tom) //这就过载了符号

  //ALL OPERATORS ARE METHODS
  println(1 + 2)
  println(1.+(2))

  //prefix notation
  val x= -1 //equivalent with 1.unary_- 前面的-，就是prefix notation
  val y=1.unary_- //unary_prefix only works with - + ~ !

  println(!mary)
  println(mary.unary_!)

  //postfix notation
  println(mary.isAlive)
  println(mary isAlive) //前者叫method，后面的写法叫postfix notation。postfix notation只适用于不带参数的method

  //apply
  println(mary.apply())
  println(mary()) //equivalent mary.apply()。将类实例当做函数来用

  //×××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××××
  //execrise 1:
  /*
  Overload the + operator
  mary + "the rockstar" => new person "Mary (the rockstar)"
   */

  val mary_rockstart=mary + "rockstar"
  println(mary_rockstart.name)

  /*
  execrise 2:
  Add an age to the Person class
  Add a unary + operator => new Person with the age + 1
  +mary=>mary with the age incrementer
   */
  val mary2= +mary
  mary2.print()

  /*
  execrise 3:
  Add a "learns" method in the Person class => "Mary learns something"
  Add a learnsScala method, call learns method with "Scala" as parameter
  Use it in postfix notation
   */
  val mary3=mary learns "Scala"
  val mary4=(mary learnsScala) print
  /*
  Overload the apply method
  mary.apply(2)=>"Mary watched Inception 2 times"
   */

  println(mary(3))
}

package lectures.beginner.part2.oop

object AnonymousClasses extends App{
  abstract class Animal{
    def eat:Unit
  }

  //anonymous class
  val funnyAnimal:Animal = new Animal{
    def eat=println("ahahahaaha")
  }
  /*
  equivalent with
  class AnonymousClasses$$anon$1 extends Animal{
      def eat=println("ahahahaaha")
      }
  val funnyAnimal:Animal = new AnonymousClasses$$anon$1
   */

  println(funnyAnimal.getClass)

  class Person(name:String){
    def sayHi=println(s"Hi,my name is $name,how can I help?")
  }
  //这也算匿名函数
  val jim=new Person("Jim"){
    override def sayHi: Unit = println(s"Hi, my name is Jim,how can I be of service")
  }
  jim.sayHi

}

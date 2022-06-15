package lectures.beginner.part2.oop

object Interitance extends App {
  //singe class inheritance.也就是说底下的子类Cat只能继承一个父类.
  class Animal {
    val creatureType="wild"
    //子类只能继承none-private member
    //假如 private eat=println("lllll")
    //则cat无法调用eat
    def eat=println("nomnom") //这是一种定义方式，public，cat作为Cat的Instance，可以直接调用eat
     //protected def eat=println("nomnom") //这是另外一种定义方式，它可以在子类中被调用，但不可以被子类的Instance调用
  }

  class Cat extends Animal{
    def crunch={
      eat
      println("crunch crunch")
    }
  }

  val cat= new Cat
  //cat eat  如果Animal中eat是protected 或者是public的，则可以这么调用
  cat crunch

  //overriding
//  class Dog extends Animal{
//    override  def eat: Unit = println("crunch,crunch")
//    override val creatureType: String = "domestic"
//    //also can override val
//  }
  //override creatureType in this way:
  class Dog(override val creatureType:String) extends Animal{
    override def eat: Unit = println("crunch,crunch")
  }
  val dog=new Dog("Kg")
  dog.eat
    println(dog.creatureType)

  //type substitution (broad:polymorphism)
  val unknownAnimal: Animal=new Dog("K9")
  unknownAnimal.eat

  //overRIDING VS overLOADING
  //overriding就是在子类中重新定义函数和成员
  //OVERLOADING说的是在同一个类中，针对同名的函数存在不同的parameter

  //preventing overrides
  //1 -- final,如果在Animal的eat函数前加final，则后续的子类无法override eat函数
  //2 -- 还是final，如果在Animal的类定义前加final，则Animal中所有函数以及成员都无法override
  //3 -- make sealed class
}

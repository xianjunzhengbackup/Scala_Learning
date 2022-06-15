package lectures.beginner.part2.oop


object AbstractDataTypeAndTrait extends App {
  //这就是虚函数的定义，它不一定需要定义里面的实现，且它本身不可以被实例化
  abstract class Animal{
    val creatureType:String="animal"  //这里定义了它的实现，那么这就是none-abstract member in abstract class
    def eat:Unit
  }

  class Dog extends Animal{
    override val creatureType: String = "Canine"
    def eat:Unit=println("crunch crunch")
  }
  //前面加不加override都无所谓

  //trait
  trait Carnivore{
    def eat(animal:Animal):Unit
    val preferredMeal:String="fresh meat"
  }//与前面的虚类Animal对应，都有一个虚函数，同时都有一个非虚的member。问题来了虚类与trait的区别在哪里
  //1--trait do not have constructor parameter
  //2--multiple traits may be inherited by the same class
  //3--traits=behavior,abstract class =type of things.trait一般用来表述行为，虚类用来表示东西.
  trait ColdBlood

  class Crocodile extends Animal with Carnivore with ColdBlood {
    override val creatureType: String = "croc"
    def eat:Unit=println("nomnomnom")
    def eat(animal: Animal)=println(s"I'm croc and I am eating ${animal.creatureType}")
  }

  val dog=new Dog
  val croc= new Crocodile
  croc.eat(dog)
  croc.eat
}

package lectures.beginner.part2.oop

object ScalaObject extends App {
  //SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY ("static") in class
  //所谓的CLASS-LEVEL FUNCTIONALITY说的是在类的定义中，可以有static 类型的成员，它们一般被当作全局变量来用。
  //当需要调用时，直接用类的名字.全局变量。
  //在scala中没有这种static全局变量的弄法。scala利用object来定义全局变量。
  object Person{
    //"static"/class-level function
    val N_EYES =2
    def canFly:Boolean=false

    //SINGLETON OBJECT经常用来定义factory method，返回一个COMPANIONS类的实例。
    def apply(father:Person,mother:Person):Person=new Person("Bobiie")
  }
  //通过object定义的Person，叫SINGLETON INSTANCE.等价于定义了一个类，同时初始化了类的实例，但这个类实例只有这一个
  //Scala object = SINGLETON INSTANCE
  //person1 和 person2都指向同一个SINGLETON INSTANCE
  val person1=Person
  val person2=Person
  println(person1==person2)

  //SINGLETON OBJECT的用法不只是用来定义全局变量，伴随SINGLETON OBJECT出现的还有同名的COMPANION CLASS，即同名的类
  class Person(val name:String){
    //这里定义的将是instance-level function
  }
  //COMPAINS CLASS
  //mary和john指向不同的instance
  val mary=new Person("Mary")
  val john=new Person("John")
  println(mary==john)

  println(Person.N_EYES)
  println(Person.canFly)

  val bobbie=Person(mary,john)  //这里调用的是Person.apply（mary，john），singleton object里的apply。返回的是COMPANION CLASS的类INSTANCE

}

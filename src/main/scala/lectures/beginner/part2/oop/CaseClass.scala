package lectures.beginner.part2.oop

object CaseClass extends App{
  /*
  case class的好处是它可以用来包装数据结构，同时
  它还有一些自带的函数，比如 equals,hashCode,toString
   */
  case class Person(name :String,age :Int)

  //1. class parameter are field.普通的class里面的参数如果不加val就不是field，也无法被在类外被调用
  val jim = new Person("Jim",34)
  println(jim.name)

  //2. 自带toString函数，以下两种写法都可以
  println(jim.toString)
  println(jim)

  //3. 自带equals和hashCode函数
  val jim2=new Person("Jim",34)
  println(jim2 == jim)

  //4. 自带copy函数
  val jim3=jim.copy(age = 45)
  println(jim3)

  //5. 自带companion object。当case class Person(name :String,age :Int)，其实它也同时定义了一个singleton object
  val thePerson = Person
  val mary = Person("Mary",45)  //这里调用了companion object的apply method。所以对case class来说兴建一个实例，直接就这么干，不再需要new

  //6.case class are serializable

  //7.case class have extractor patterns

  //case object 与case class唯一的不同是，case object没有companion object
  case object UnitedKingdom{
    def name: String="The UK of GB and NI"
  }
  //------------------------------------------------------------------------------------------------------------
  /*
  Expand MyList - use case classes and case objects
   */


}

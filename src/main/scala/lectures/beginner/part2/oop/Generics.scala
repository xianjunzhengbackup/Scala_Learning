package lectures.beginner.part2.oop

object Generics extends App{
  //这样定义出来的class就叫Generics Class
  class MyList[A] {
    //use the type A
  }
  //还可以多定义几个type
  class MyMap[Key,Value]

  val listOfIntegers=new MyList[Int]
  val listOfStrings=new MyList[String]

  //generic methods
  //generic type can be used for Generic Class,Generic trait, but not Singleton Object
  //Singleton object是一个实例，所以不可以带Generic type，但可以通过Generic method返回Companion class的实例
  object MyList{
    def empty[A] : MyList[A]=new MyList[A]
  }
  val emptyListOfIntegers=MyList.empty[Int]

  //variance problem
  //Covariant ---协变，即 a cat is an animal
  //Invariant ---不变，即 a cat is only a cat, not an animal
  //Contravariant --逆变，即 an animal is a cat
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  //1. yes List[Cat] extends List[Animal]  Covariant
  class CovariantList[+A]{
    def add[B >: A](element:B) = new CovariantList[B]

  }
  val animal:Animal = new Cat
  val animalList :CovariantList[Animal] = new CovariantList[Cat]

  //val catList: CovariantList[Cat] = new CovariantList[Animal] 这个就是错误的
  val catList: CovariantList[Cat] = new CovariantList[Cat]
  val catList2 = catList.add(new Animal)
  val catList3 = catList.add(new Dog)
  val catList4 = catList.add(new Cat)


  val animalList2=animalList.add(new Cat)
  val animalList3=animalList.add(new Dog)
  val animalList4=animalList.add(new Animal)

  //一只猫是一个动物，一群猫就是一群动物
  //Covariant的不足之处是
  // 当运行这句时 val animalList :CovariantList[Animal] = new CovariantList[Cat]，这是符合语法的。
  //假如ConvariantList类中有add的method，比如像这样的定义 def add[A](element:A) :CovariantList[A]={....}
  // 按照+A Convariant的定义，它可以添加任何A的子类，比如添加一头狗，animalList.add(new Dog)
  // 在一群猫当中添加了一只狗，语法没任何问题，但逻辑说不通
  //为了解决这一矛盾，于是就有了下面利用bounded method来定义的method ?????????
  //def add[B >: A](element:A) : CovariantList[B]  ?????
  //bounded method，就是在method可以调用的类上做了手脚，之前的问题是A是Animal类，add函数里面的参数只要是Animal的子类都是语法正确的。 ????
  //现在通过 add[B >:A](element:B),函数的参数如果是A的同类，。这样就避免在一群猫中添了一只狗。????

  //2. NO=INVARIANT
  class InvariantList[A]
  val invariantList : InvariantList[Animal] = new InvariantList[Animal]
  //一只猫就只能是一只猫，一个动物就只能是一个动物。如果写成这样就会报错 val invariantList : InvariantList[Animal] = new InvariantList[Cat]
  //上面的Class MyList[A] Generic Class就是Invariant

  //3. Hell,no！Contravariant逆变
  class ContravariantList[-A]
  val contravariantList : ContravariantList[Cat] = new ContravariantList[Animal]
  val contravariantList_2 : ContravariantList[Cat] = new ContravariantList[Cat]
  //逆变，动物是猫，反直觉
  class Trainer[-A]
  val trainerList : Trainer[Cat] = new Trainer[Animal]
  //能训动物的trainer，也能训猫

  //bounded types
  class Cage[A <: Animal] (animal : A)
  //Cage only accept type parameters which are subtypes of animal
  // class Cage[A >: Animal](animal :A) which means cage only accept something which is a supertype of animal

  val cage = new Cage(new Dog)
  val cage2= new Cage(new Animal)
}
//___________________________________________________________________________________________________
//exercise add Covariant type to last execrise

object ExecriseForCovariant extends App {
  abstract class MyList[+A]{
    /*
    实现一个自己的List类叫MyList，有如下函数接口
    head=first element of the list
    tail=remainder of the list
    isEmpty=is the list empty
    add(int) => new list with this element added
    toString => a string representation of the list
     */
    def head:A
    def tail:MyList[A]
    def isEmpty:Boolean
    def add[B >: A](element : B) : MyList [B]
    def toString:String
  }

  //Nothing is substitute of any type
  object emptyList extends MyList [Nothing]{
    def head: Nothing = throw new NoSuchElementException
    def tail : MyList [Nothing] = throw new NoSuchElementException
    def isEmpty:Boolean = true
    override def add[B >: Nothing](element: B): MyList[B] = new List(element,emptyList)
    override def toString:String = ""
  }
  class List[+A](val h:A,val t:MyList[A]) extends MyList[A]{
    def head:A = h
    def tail:MyList[A] = t
    def isEmpty:Boolean = false
    override def add[B >: A](element: B): MyList[B] = new List(element,this)
    override def toString:String = h.toString + " " + t.toString
  }

  val list = new List(1,emptyList)
  val list2 = emptyList.add(3)
  println(list2.head)
  println(list.add(2).add(3).add(4).head)
  println(list.add(2).add(3).add(4).tail.tail.tail.head)
  //  println(list.add(2).add(3).add(4).tail.tail.tail.tail.head)
  println(list.add(2).add(3).add(4).toString)

  val strlist = new List("A",emptyList)
  println(strlist.add("B").add("C").head)
  println(strlist.add("B").add("C").toString)
}
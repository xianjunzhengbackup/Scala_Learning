package Part3

object whatsAFunction extends App {
  //DREAM:use functions as first class elements
  //problem: oop
  /*
  它这里的意思是，目标是代码就是由一个个函数堆砌出来
  问题是OOP，面向对象，所有的东西都是类，每个类都需要实例话。因此为了让这些类的实例看起来像函数，scala弄出了generic class以及apply method。
   */
  val doubler=new MyFunction[Int,Int] {
    override def apply(element: Int): Int = element * 2
  }
  println(doubler(3)+5)
  /*
  通过定义Generic class中的apply method在形式上实现了函数式编程
   */

  /*
  在scala中，有默认的22个Function1-22。Function1实现一个A->B（1个type parameter），Function2实现[A,B]->C(2个type parameter）
   */
  val stringToInt=new Function[String,Int] {
    override def apply(v1: String): Int = v1.toInt
  }
  println(stringToInt("4")+6)
  val myadder :((Int,Int) => Int)=new Function2[Int,Int,Int] {
    override def apply(v1: Int, v2: Int): Int = v1+v2
  }
  println(myadder(3,4))

  //Function types Function2[A,B,R] === ((A,B) => R)
  //ALL SCALA FUNCTIONS ARE OBJECTS
  //((A,B) => R) 这就是FUNCTION TYPE

  //------------------------------------------------------------------------------------------
  /*
  1. a function which takes 2 strings and concatenates them
  2. transform the MyPredicate and MyTransformer into function types
  3. define a function which takes an int and returns another function which takes an int and return an int
   */
  def concatTwoString :((String,String) => String)=new Function2[String,String,String] {
    override def apply(v1:String,v2:String):String=v1+v2
  }
  println(concatTwoString("w","r"))

  trait MyPredicate[-T] {
    def test(element: T) :Boolean
  }

  trait MyTransformer[-A,B]{
    def transform(element:A):B
  }

  class EvenPredicate extends MyPredicate[Int]{
    override def test(element: Int): Boolean = {
      if(element % 2 == 0) true
      else false
    }
  }
  val evenPredicate = new EvenPredicate
  println(evenPredicate.test(3))
  println(evenPredicate.test(8))

  class StringToIntTransformer extends MyTransformer[String,Int]{
    override def transform(element: String): Int = element.toInt
  }
  val transformer=new StringToIntTransformer
  println(transformer.transform("123"))

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

    def map[B](transformer: MyTransformer[A,B]) : MyList[B]
    def map[B](myfunction: A => B) :MyList[B]
    def filter(predicate: MyPredicate[A]):MyList[A]
    def filter(predicate: A=>Boolean):MyList[A]
    //flatMap(transformer from A to MyList[B]) => MyList[B]
    def flatMap[B](transformer: MyTransformer[A,MyList[B]]):MyList[B]
    def flatMap[B](transformer: A=>MyList[B]):MyList[B]
  }

  //Nothing is substitute of any type
  object emptyList extends MyList [Nothing]{
    def head: Nothing = throw new NoSuchElementException
    def tail : MyList [Nothing] = throw new NoSuchElementException
    def isEmpty:Boolean = true
    override def add[B >: Nothing](element: B): MyList[B] = new List(element,emptyList)
    override def toString:String = ""

    override def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = this
    override def map[B](myfunction: Nothing=>B) :MyList[B] = this

    override def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = this

    override def filter(predicate: Nothing => Boolean): MyList[Nothing] = this

    //[1,2,3].flatMap(n => [n,n+1]) => [1,2,2,3,3,4]
    override def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = this
    override def flatMap[B](transformer: Nothing=>MyList[B]): MyList[B] = this
  }
  class List[+A](val h : A ,val t:MyList[A]) extends MyList[A]{
    def head : A = h
    def tail:MyList[A] = t
    def isEmpty:Boolean = false
    override def add[B >: A](element: B): MyList[B] = new List(element,this)
    override def toString:String = h.toString + " " + t.toString

    override def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
      val ATransformed = transformer.transform(h)
      new List(ATransformed,tail.map[B](transformer))
    }

    override def map[B](myfunction: A => B) : MyList[B] = {
      val AnewValue: B = myfunction(h)
      new List(AnewValue,tail.map[B](myfunction))
    }
    override def filter(predicate: MyPredicate[A]): MyList[A] = {
      val res=predicate.test(h)
      if(res==true) new List(h,tail.filter(predicate))
      else tail.filter(predicate)
    }

    override def filter(predicate: A=>Boolean): MyList[A] = {
      val res=predicate(h)
      if(res==true) new List(h,tail.filter(predicate))
      else tail.filter(predicate)


    }

    //flatMap(transformer from A to MyList[B]) => MyList[B]
    //[1,2,3].flatMap(n => [n,n+1]) => [1,2,2,3,3,4]
    /*
    [1,2,3].flatMap(n=>[n,n+1])
    [1,new List(2,[2,3].flatMap)]
    [1,new List(2,new List(2,new List(3,[3].flatMap))]
    [1,new List(2,new List(2,new List(3,new List(3,4)

     */
    override def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = {
      val res = transformer.transform(h)
      new List[B](res.head,new List[B](res.tail.head,tail.flatMap(transformer)))
    }

    override def flatMap[B](transformer: A=>MyList[B]): MyList[B] = {
      val res = transformer(h)
      new List[B](res.head,new List[B](res.tail.head,tail.flatMap(transformer)))
    }
  }


  val list=new List("1",new List("2",new List("3",emptyList)))

  println(list.toString)
  val listTransformed=list.map[Int](transformer)
  val listTransformedByFunction=list.map[Int](new Function[String,Int] {
    override def apply(v1: String): Int = v1.toInt})
  println(listTransformed)
  println(listTransformedByFunction)

  val newfunction: MyTransformer[Int,Int]=new MyTransformer[Int,Int] {
    override def transform(element: Int): Int = 2 * element + 1
  }
  println(listTransformed.map[Int](newfunction))
  println(listTransformed.map((element:Int)=>2*element+1))
  println(listTransformed.map(_ * 2))

  println(listTransformed.filter(evenPredicate))
  println(listTransformed.filter(new Function[Int,Boolean] {
    override def apply(element:Int) :Boolean=element % 2 ==0
  }))
  println(listTransformed.filter((element:Int)=>element%2==0))
  println(listTransformed.filter((_ % 2==0)))


  val newTransformList :MyTransformer[Int,MyList[Int]] = new MyTransformer[Int,MyList[Int]] {
    override def transform(element: Int): MyList[Int] = new List(element,new List(element+1,emptyList))
  }
  println(listTransformed.flatMap(newTransformList))

  println(listTransformed.flatMap(new Function[Int,MyList[Int]] {
    override def apply(v1:Int):MyList[Int]={
      new List(v1,new List(v1+1,emptyList))
    }
  }))
  println(listTransformed.flatMap((element:Int)=>new List(element,new List(element+1,emptyList))))


  //Function1[Int,Function1[Int,Int]]
  val superAdder :Function1[Int,Function1[Int,Int]] = new Function1[Int,Function1[Int,Int]]{
    override def apply(x: Int) :Function1[Int,Int]={
      new Function[Int,Int]{
        override def apply(y: Int): Int = x+y
      }
    }
  }
  val add3 = superAdder(3)
  println(add3(4))
  println(superAdder(3)(4))   //curried function
}

class MyFunction[A,B]{
  def apply(element:A): B = ???
  }

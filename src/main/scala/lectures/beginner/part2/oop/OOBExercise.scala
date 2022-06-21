package lectures.beginner.part2.oop

object OOBExercise extends App {
  /*
  1. Generic trait MyPredicate[-T] with a little method test(T) => Boolean
  2. Generic trait MyTransformer[-A,B] with a method transform(A) => B
  3. MyList:
    - map(transformer) => MyList
    - filter(predicate) => MyList
    - flatMap(transformer from A to MyList[B]) => MyList[B]

    class EvenPredicate extends MyPredicate[Int]
    class StringToIntTransformer extends MyTransformer[String,Int]

    [1,2,3].map(n * 2) => [2,4,6]
    [1,2,3,4].filter(n % 2) => [2,4]
    [1,2,3].flatMap(n => [n,n+1]) => [1,2,2,3,3,4]
   */
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
    def filter(predicate: MyPredicate[A]):MyList[A]
    //flatMap(transformer from A to MyList[B]) => MyList[B]
    def flatMap[B](transformer: MyTransformer[A,MyList[B]]):MyList[B]

  }

  //Nothing is substitute of any type
  object emptyList extends MyList [Nothing]{
    def head: Nothing = throw new NoSuchElementException
    def tail : MyList [Nothing] = throw new NoSuchElementException
    def isEmpty:Boolean = true
    override def add[B >: Nothing](element: B): MyList[B] = new List(element,emptyList)
    override def toString:String = ""

    override def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = this

    override def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = this

    //[1,2,3].flatMap(n => [n,n+1]) => [1,2,2,3,3,4]
    override def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = this
  }
  class List[+A](val h:A,val t:MyList[A]) extends MyList[A]{
    def head:A = h
    def tail:MyList[A] = t
    def isEmpty:Boolean = false
    override def add[B >: A](element: B): MyList[B] = new List(element,this)
    override def toString:String = h.toString + " " + t.toString

    override def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
      val ATransformed = transformer.transform(h)
      new List(ATransformed,tail.map[B](transformer))
    }

    override def filter(predicate: MyPredicate[A]): MyList[A] = {
      val res=predicate.test(h)
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
  }


  val list=new List("1",new List("2",new List("3",emptyList)))
  println(list.toString)
  val listTransformed=list.map[Int](transformer)
  println(listTransformed)

  val newfunction: MyTransformer[Int,Int]=new MyTransformer[Int,Int] {
    override def transform(element: Int): Int = 2 * element + 1
  }
  println(listTransformed.map[Int](newfunction))

  println(listTransformed.filter(evenPredicate))

  val newTransformList :MyTransformer[Int,MyList[Int]] = new MyTransformer[Int,MyList[Int]] {
    override def transform(element: Int): MyList[Int] = new List(element,new List(element+1,emptyList))
  }
  println(listTransformed.flatMap(newTransformList))
}

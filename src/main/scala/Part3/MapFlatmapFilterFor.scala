package Part3

import Part3.whatsAFunction.{MyPredicate, MyTransformer}

object MapFlatmapFilterFor extends App{
  val list = List(1,2,3)
  println(list.head)
  println(list.tail)

  //map
  println(list.map( _ +1))
  println(list.map(_+" is a number"))

  //filter
  println(list.filter(_%2==0))

  //flatMap
  println(list.flatMap(x=>List(x,x+1)))

  /*
  Exercise: print all combinations between two lists
   */
  val numbers = List(1,2,3,4)
  val chars = List("a","b","c","d")
  //List("a1","a2",.....,"d4")
 numbers.foreach(number=>(chars.foreach(char=>(print(char+number+" ")))))
  println
  numbers.map(number=>(chars.map(char=>char+number))).foreach(list=>list.foreach(x=>print(x+" ")))
  println(numbers.flatMap(x=>List(x)))
  println(numbers.map(number=>(chars.map(char=>char+number))).flatMap(list=>list.flatMap(x=>List(x))))
  //这是最优解，flatMap里面的那个HOF参数要返回一个recursive对象，刚好map返回一个List。
  println(numbers.flatMap(number=>(chars.map(char=>char+number))))
  //FP用flatMap，map来取代loop
  val colors = List("black","red","blue","purple")
  //3-level loop
  println(numbers.flatMap(number=>chars.flatMap(char=>colors.map(color=>char+number+"-"+color))))

  //above flatMap map code is hard to understand,that is why we got for comprehension which is easy to read
  val forCombinations = for{
    number <- numbers if number%2==0
    char <- chars
    color <- colors
  } yield char+number+"-"+color
  println(forCombinations)


  //syntax overload
  list.map {
    x=>
      2*x
  }

  /*
  Exercise:
  1. MyList supports for comprehensions?

   */

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

    def foreach(f :A => Unit)
    def sort(f:(A,A)=>Boolean):MyList[A]
    def zipWith[B,C >:A](list:MyList[C],f: (A,C)=>B):MyList[B]
    /*
    fold(start)(function) =>a value
    [1,2,3].fold(0)(x+y)=6
     */
    //    def fold[C >:A](start:C,f: (C,C)=>C): (C,C)=>C

    def fold[C >:A](start:C):((C,C)=>C)=>C

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

    override def foreach(f : Nothing => Unit): Unit = this

    override def sort(f: (Nothing, Nothing) => Boolean): MyList[Nothing] = this

    //    override def zipWith[B](list: MyList[Nothing], f: (Nothing, Nothing) => B): MyList[B] = null
    override def zipWith[B,C >:Nothing](list:MyList[C],f: (Nothing,C)=>B):MyList[B] = this
    /*
    fold(start)(function) =>a value
    [1,2,3].fold(0)(x+y)=6
     */
    //def fold[C >:A](start:C):((C,C)=>C)=>C
    override def fold[C >:Nothing](start:C):((C,C)=>C)=>C = f=>f(start,start)

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

    override def foreach(f: A => Unit): Unit = {
      f(h)
      if(!t.isEmpty) t.foreach(f)
      else Unit
    }

    /*
    f : (x,y)=>y<x
    [1,t(2,3)].sort(f)=>[2,t(1,3)]=>(1,3).sort

     */
    override def sort(f: (A, A) => Boolean): MyList[A] = {
      //      if(t.isEmpty) this
      //      else if(f(h,t.head)) {t.sort(f)}
      //      else {
      //        val newList= new List(t.head,new List(h,t.tail))
      //        newList.t.sort(f)
      //      }
      if(!t.isEmpty) {
        if (f(h, t.head)) new List(h, t.sort(f))
        else {
          val newList = new List(t.head, (new List(h, t.tail)).sort(f))
          newList
        }
      } else this
    }
    override def zipWith[B,C >:A](list:MyList[C],f: (A,C)=>B):MyList[B] =
    {
      if(list.isEmpty) emptyList
      else {
        val newValue =f(h,list.head)
        new List(newValue,t.zipWith(list.tail,f))
      }
    }
    /*
    fold(start)(function) =>a value
    [1,2,3].fold(0)(x+y)=6
     */
    override def fold[C >:A](start:C):((C,C)=>C)=>C = {
      if(!t.isEmpty) f => t.fold(f(start,h))(f)
      else f => f(start,h)
    }
  }
  val listNew=emptyList.add(1).add(2).add(3).add(4).add(5)
  val oneMoreList=new List(1,new List(2,new List(3,new List(4,new List(5,emptyList)))))

  /*
  要想支持for comprehension，必须满足以下条件
  map(f: A=>B):MyList[B]
  filter(p:A=>Boolean):MyList[A]
  flatMap(f:A=>MyList[B]):MyList[B]
  只有包含了以上几个函数（同样的signature)，才可以支持for comprehension，因为for comprehension就是编译器用以上几个函数改写的
  在for comprehension中每一个 <-就是一个loop
   */
  val zipList=for{
    e <- listNew
    f <- oneMoreList
  } yield e*f
  println(zipList)

  /*
  Exercise A small collection of at most one element - Maybe[+T]
  - map flatMap filter
   */
  abstract class Maybe[+T]{
    def map[B](f:T=>B):Maybe[B]
    def filter(f:T=>Boolean):Maybe[T]
    def flatMap[B](f:T=>Maybe[B]):Maybe[B]
  }

  case object MaybeNot extends Maybe[Nothing]{
    def map[B](f:Nothing=>B):Maybe[B] = this
    def filter(f:Nothing=>Boolean):Maybe[Nothing] = this
    def flatMap[B](f:Nothing=>Maybe[B]):Maybe[B] = this
  }

  case class Just[+T](value:T) extends Maybe[T]{
    def map[B](f:T=>B):Maybe[B] ={
      Just(f(value))
    }
    def filter(f:T=>Boolean):Maybe[T] ={
      if(f(value)) this
      else MaybeNot
    }
    def flatMap[B](f:T=>Maybe[B]):Maybe[B] ={
      f(value)
    }
  }
  val just3 = Just(3)
  println(just3)
  println(just3.map(_*2))
  println(just3.flatMap(x=>Just(x%2==0)))
  println(just3.filter(_%2==0))
}

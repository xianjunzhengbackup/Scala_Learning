package Part3

import Part3.whatsAFunction.{MyPredicate, MyTransformer}

object HOFsAndCurries extends App{
  val superFunction:(Int,(String,(Int => Boolean))=>Int)=>(Int=>Int) = null
  //higher order function (HOF)
  //所谓HOF，就是要么将函数用作parameter，要么返回一个函数

  //map,flatMap,filter in MyList

  // function that applies a function n times over a value x
  //nTimes(f,n,x)
  //nTimes(f,3,x) = f(f(f(x)))
  def nTimes(f:Int=>Int, n : Int, x : Int) : Int={
    if(n==1) f(x)
    else f(nTimes(f,n-1,x))
  }
  /*
  nTimes(f,3,x)
  f(nTimes(f,2,x))
  f(f(nTimes(f,1,x)))=>f(f(f(x)))
   */
  println(nTimes(x=>x+1,3,1))

  /*
  nTimesBetter(f,3)
  nTimesBetter(f,2)(f(x))
  nTimesBetter(f,1)(f(f(x))
  f(f(f(x)))
  f(
   */
  def nTimesBetter(f:Int=>Int,n:Int):Int=>Int={
    if(n==1) x => f(x)
    else x=>nTimesBetter(f,n-1)(f(x))
  }
  val plus10=nTimesBetter(x=>x+1,10)  //这里返回的是函数 x=>f(f(f.....(x))
  println(plus10(1))

  //curried functions
  val superAdder : Int => (Int => Int) = (x:Int) => (y:Int) =>x + y
  val add3 = superAdder(3) //y => 3+y
  println(superAdder(3)(10))

  //利用parameter list做了一个curried function出来
  //functions with multiple parameter lists
  def curriedFormatter(c:String)(x:Double) :String=c.format(x)

  val standardFormat:(Double => String)=curriedFormatter("%4.2f")
  println(standardFormat(Math.PI))
  println("%4.2f".format(Math.PI))

  println(List(31,2,67,90,1,53).sortWith((x,y)=>y<x))
  //--------------------------------------------------------------------------------------------------
  /*
  1. Expand MyList
    -- foreach method A=>Unit
    [1,2,3].foreach(x=>println(x))
    -- sort function ((A,A) =>Int) =>MyList
    [1,2,3].sort((x,y)=>y - x)=>[3,2,1]
    -- zipWith(list, (A,A) =>B) =>MyList[B]
    [1,2,3].zipWith([4,5,6],x*y) =>[1*4,2*5,3*6]
    -- fold(start)(function) =>a value
    [1,2,3].fold(0)(x+y)=6
   2. toCurry(f:(Int,Int)=>Int) => (Int =>Int=>Int)
   fromCurry(f:Int=>Int=>Int) =>(Int,Int)=>Int
   3. compose(f,g) => x=>f(g(x))
      andThen(f,g) => x=>g(f(x))

   */

  /*
  1. Expand MyList
    -- foreach method A=>Unit
    [1,2,3].foreach(x=>println(x))
    -- sort function ((A,A) =>Int) =>MyList
    [1,2,3].sort((x,y)=>y - x)=>[3,2,1]
    -- zipWith(list, (A,A) =>B) =>MyList[B]
    [1,2,3].zipWith([4,5,6],x*y) =>[1*4,2*5,3*6]
    -- fold(start)(function) =>a value
    [1,2,3].fold(0)(x+y)=6


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
  /*
  2. toCurry(f:(Int,Int)=>Int) => (Int => Int => Int)
     fromCurry(f:(Int => Int => Int)) => (Int,Int) => Int
   */
  def toCurry[A](f:(A,A)=>A): A=>A=>A ={
    x=>y=>f(x,y)
  }
  def fromCurry[A](f:A=>A=>A):(A,A)=>A ={
    (x,y) => f(x)(y)
  }

  val onemoreSuperAdder=toCurry[Int](_+_)
  val adder4 = onemoreSuperAdder(4)
  println(adder4(17))
  /*
  compose(f,g) => x =>f(g(x))
  andThen(f,g) => x =>g(f(x))
   */
  def compose[A](f:A=>A,g:A=>A) :A=>A={
    x => f(g(x))
  }
  def andThen[A](f:A=>A,g:A=>A) :A=>A={
    x => g(f(x))
  }
  val composeFunc= compose[Int](x=>x+1,y=>y*2)
  println(composeFunc(1))
  val andThenFunc=andThen[Int](x=>x+1,y=>y*2)
  println(andThenFunc(1))

  val list=emptyList.add(1).add(2).add(3).add(4).add(5)
  println(list.toString)
  println((list.map(x=>x+1)).toString)
  list.foreach(x=>println(x))
  val sortedList=list.sort((x,y)=>(y>x)).sort((x,y)=>(y>x)).sort((x,y)=>(y>x))
  sortedList.foreach(x=>print(x+" "))
  println
  println(list.zipWith[Int,Int](new List(1,new List(2,new List(3,new List(4,emptyList)))),(x,y)=>x*y))
  println(list.fold(0)((x,y)=>x+y))

  val curryFunc=toCurry[Int]((X,Y)=>X+Y)
  println(curryFunc(1)(2))
  val fromCurryFunc=fromCurry(curryFunc)
  println(fromCurryFunc(1,2))
}

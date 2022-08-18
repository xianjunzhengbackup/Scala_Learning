package More_About_Scala

object curry extends App{
  /*
  curry 就是所谓的柯里化
  scala允许函数定义多组参数列表，每组的参数列表写在一对圆括号内。当我们用少于定义数目的参数来调用函数的时候，将返回一个
  以余下的参数列表为参数的函数。这就是curry function
   */
  def filter(xs:List[Int],p:Int=>Boolean) :List[Int]= {
    if(xs.isEmpty) xs
    else if(p(xs.head)) xs.head :: filter(xs.tail,p)
    else filter(xs.tail,p)
  }
  //当我们调用modN时，只用了一组参数，那么就生成了curry function
  def modN(n:Int)(x:Int) = (x%n==0)

  val nums = (1 to 8).toList
  println(filter(nums,modN(2)))
  println(filter(nums,modN(3)))

  //部分施用函数.
  //这里的price不是partial function（偏函数）
  def price(product:String):Double=product match{
    case "apple"=>140
    case "orange"=>223
  }
  def withTax(cost:Double,state:String):Double= {
    state match {
      case "NY"=>cost*2
      case "FL"=>cost*3
    }
  }
  //通过这样的定义的方式弄也弄出一个curry function
  val locallyTaxed=withTax(_:Double,"NY")
  val costOfApples=locallyTaxed(price("apple" ))
  println(costOfApples)

  //关于偏函数 partial function以及case
  //这里的map里面定义的其实是一个generic function，即匿名函数，加了case的匿名函数
  val cities = Map("Atlanta"->"GA","New York"->"NY","Chicago"->"IL","San Francsico"->"CA")
  cities map {
    case(k,v)=>println(k+"->"+v)
  }

  //won't work 因为case的匿名函数还是一个函数，所以该函数没法处理“seven”，只会包match error
//  List(1,2,3,"seven") map {
//    case i:Int =>i + 1
//  }
  //同样的代码换成collect就不会出错的原因是collect 里面的这个case 函数，不是匿名函数，而是偏函数。PF只是对函数参数做了限制，
  //所以这里只处理Int 参数
  List(1,2,3,"seven") collect{
    case i:Int=>i+1
  }

  //用case定义的偏函数
  //传统的偏函数定义如下，它用的是partial function trait. apply负责函数的实现，isDefinedAt用于限制参数
  val answerUnits = new PartialFunction[Int,Int] {
    def apply(d:Int) = 42/d
    override def isDefinedAt(x: Int): Boolean = x!=0
  }
  //如果是用case来定义，就简单许多
  def pAnswerUnits:PartialFunction[Int,Int]={
    case d:Int if d!=0 =>42/d
  }

  def inc:PartialFunction[Any,Int] ={
    case i:Int =>i+1
  }
  println(inc(42))
//  println(inc("yyy")) //它会报match error
  println(List(41,"cat") collect inc)
}

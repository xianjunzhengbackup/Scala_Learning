package Part3

object AnonymousFunctions extends App{
  val oldDoubler=new Function[Int,Int] {
    override def apply(v1: Int): Int = 2*v1
  }
  //底下这个就是Anonymous Function (Lambda
  //(x:Int)=>2*x这个就是用来替代上面的apply method
  //(x:Int)一定要加括号
  val doubler=(x:Int) =>2*x   //等价于 val doubler:Int=>Int=x=>2*x

  //multiple parameters in a lambda
  val adder = (x:Int,y:Int)=>x+y
  val anotherAdder:(Int,Int)=>Int=(x,y)=>x+y

  val onemoreAdder=(x:Int)=>(y:Int)=>x+y
  println(onemoreAdder(3)(4))

  //no parameters
  val justDOSomething = ()=>3
  val anotherThing:()=>Int=()=>3

  println(justDOSomething)  //function itself
  println(justDOSomething()) //call

  //curly braces with lambdas
  val stringToInt ={
    (s:String)=>s.toInt
  }

  //MOAR syntactic sugar
  val niceincrementer :Int=>Int=_+1 //等价于val niceincrementer:Int=>Int=(x:Int)=>x+1
  val niceadder:(Int,Int)=>Int=_ + _ //等价于 val niceadder:(Int,Int)=>Int=(x:Int,y:Int)=>x+y


}

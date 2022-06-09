package lecture_beginner_basics

//这里extends App，隐式加入了Main函数，所以可以直接运行
object DefaultArgs extends App {
  def trFact(n:Int,acc:Int):Int={
    if(n<=1) acc
    else trFact(n-1,n*acc)
  }
  val fact10=trFact(10,1)
  println(fact10)
}

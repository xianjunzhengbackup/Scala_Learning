package More_About_Scala
import org.scalatest.Assertions._

object ByNameParameter extends App{
  /*
  所谓的ByNameParameter，说的是函数的参数。只有当函数内用到了该参数，才开始计算该参数;如果一直没用到该参数，那么该参数就不会被计算
  默认的函数参数都是ByValueParameter，就是最常见的参数。首先从左到右，挨个计算ByValueParameter的值，然后在函数体内进行替换
   */
  def addFirst(x: Int, y: => Int) = x + x
  //x就是传统的ByValueParameter
  //y就是ByNameParameter

  def infinite(): Int = 1 + infinite()
  //运行上面这个无限循环函数，系统会崩溃
  assertThrows[StackOverflowError] {
    addFirst(infinite(), 4)
  }
  assert(addFirst(4, infinite()) == 8)
  //但运行上面这个却不会崩溃，原因就在y参数是ByNameParameter，只有在用到y的时候才会去计算infinite（），因为在函数体内没有用到Y
}

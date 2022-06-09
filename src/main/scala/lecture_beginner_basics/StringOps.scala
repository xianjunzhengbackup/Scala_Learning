package lecture_beginner_basics

object StringOps extends App {
  val str:String="Hello, I am learning Scala"

  println(str.charAt(2))
  println(str.substring(7,11))
  println(str.split(" ").toList)
  println(str.startsWith("Hello"))
  println(str.replace(" ","-"))
  println(str.length)

  val aNumberString="2"
  val aNumber=aNumberString.toInt
  println(str.reverse)
  println(str.take(2))

  //scala-specific: String interpolators
  //interpolation  中文叫插值

  //S-interpolators
  val name="Jun"
  val age=41
  val greeting=s"Hello,my name is $name, and I am $age years old"
  //这里的s""表示这里将进行String的插入，$name,$age将被替换掉。如果不加s“”，那么将直接打印出$name,$age。下面这个例子将对expression进行植入,加的是{}
  val anotherGreeting=s"Hello,my name is $name, and I will be turning ${age+1} years old"
  println(anotherGreeting)

  //F-interpolators
  //F类型的插入，它就是c中的printf函数，%5.2f，第一个数字5表示整个浮点数（包括.在内）最少5个字符，第二个数字2表示浮点的精度为两位
  val speed=10.2f
  val speedInt=10
  println(f"$name can eat $speed%2.2f burgers per minute")
  println(f"$name can eat $speed%10.2f burgers per minute")
  println(f"$name can eat $speedInt%5d burgers per minute")

  //raw-interpolator
  //在raw插值中，\n不起作用,但是弄成变量就有点作用。
  println(raw"This is a \n newline")
  println("This is a \n newline")
  val escaped="This is a \n newline"
  println(raw"$escaped")
}

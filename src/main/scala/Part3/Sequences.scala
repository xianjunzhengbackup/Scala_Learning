package Part3

import scala.util.Random

object Sequences extends App{
  //seq
  val aSequence = Seq(4,3,2,1)
  println(aSequence)
  println(aSequence.reverse)
  println(aSequence(2))
  println(aSequence ++ Seq(8,9,10))
  println(aSequence.sorted)

  //range
  val aRange :Seq[Int]=1 to 10
  println((1 to 10).flatMap(x=>Seq(x,x*2)))

  //list
  val alist=List(1,2,3)
  println(42::alist)
  println(42 +: alist)
  println(42 +: alist :+ 89)

  val apples5=List.fill(5)("apple")
  println(apples5)
  println(alist.mkString("-|-"))

  //array
  val numbers = Array(1,2,3,4)
  val threeElements = Array.ofDim[Int](3) //给threeElements分配3个Int的空间，但其实它是有初始化值的。看下面
  println(threeElements)
  println(threeElements.mkString("-|-"))

  //mutation
  numbers(2)=0  //syntax sugar for numbers.update(2,0)
  println(numbers.mkString(" "))

  //arrays and seq
  val numberSeq: Seq[Int] = numbers //这里将一个array赋给了Seq，会发生implicit conversion
  println(numberSeq)

  //Vector
  val vector = Vector(1,2,3)
  println(vector)

  //vectors vs lists
  val maxRuns =1000
  val maxCapacity = 1000000

  def getWriteTime(collection: Seq[Int]) :Double={
    val r = new Random
    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity),r.nextInt())
      System.nanoTime() - currentTime
    }
    times.sum * 1.0 /maxRuns
  }

  val numberList = (1 to maxCapacity).toList
  val numberVector = (1 to maxCapacity).toVector
  println(getWriteTime(numberList))
  println(getWriteTime(numberVector))
  //可见Vector效率之高
}

package More_About_Scala

object fold_reduce extends App{
  /*
  reduceLeft 接受一个HOF作为参数，从最左边开始，假定HOF中的第一个参数就是最左边的元素，同时第一个参数也用作accumulator
  reduceRight 也是一样，从最右边开始，假定HOF中的第二个参数（也就是右边）就是最右边的元素，同时该参数也用作accumulator
  foldLeft的区别是它要提供一个初始化值
   */
  println((1 until 10) reduceLeft (_ + _))
  println(List.range(1,10).foldLeft(0)(_+_))
  /*
  8 - 9 =-1
  7 - (-1) = 8
  6 - 8 =-2
  5 - (-2) = 7
  4 - 7 =-3
  3-(-3) = 6
  2 - 6 = -4
  1 -(-4) = 5
   */
  println((1 until 10) reduceRight (_-_))

  //找出最长的字符串
  println(List("aaaa","bbbbbbbb","ccc","d","rrrr") reduceLeft ((accu,next)=>
    if(accu.length > next.length) accu
    else next))
}

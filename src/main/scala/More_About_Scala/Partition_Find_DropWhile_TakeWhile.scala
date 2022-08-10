package More_About_Scala

object Partition_Find_DropWhile_TakeWhile extends App{
  /*
  partition 将原集合的内容划分成两个集合
  下面这个例子将结果分成两块，一块可以被3整除，一块不可以被3整除
   */
  val res_partition=(1 until 10) partition (_%3==0)
  println(res_partition)

  /*
  find与filter类似，filter是返回所有适配的元素的集合，而find只返回第一个适配元素，但返回的结果是Option
   */
  val res_find = (1 until 10) find (_%3==0)
  println(res_find)

  /*
  takeWhile 从集合的头部开始，一直提取到第一个不满足条件的元素为止
  dropWhile 从集合的头部开始，一直丢弃满足条件的元素，直到遇到第一个非匹配项
   */
  val res_takewhile = (1 until 10) takeWhile (_<=3)
  println(res_takewhile)
  val res_dropwhile = (1 until 10) dropWhile (_<=3)
  println(res_dropwhile)
}

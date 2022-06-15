package lectures.beginner.part2.oop

object ExecriseForAbstractInheritanceTrait extends App {
  abstract class MyList{
    /*
    实现一个自己的List类叫MyList，有如下函数接口
    head=first element of the list
    tail=remainder of the list
    isEmpty=is the list empty
    add(int) => new list with this element added
    toString => a string representation of the list
     */
    def head:Int
    def tail:MyList
    def isEmpty:Boolean
    def add(element:Int):MyList
    def toString:String
  }
  object emptyList extends MyList{
    def head:Int = throw new NoSuchElementException
    def tail:MyList = throw new NoSuchElementException
    def isEmpty:Boolean = true
    override def add(element: Int): MyList = throw new NoSuchElementException
    override def toString:String = ""
  }
  class List(val h:Int,val t:MyList) extends MyList{
    def head:Int = h
    def tail:MyList = t
    def isEmpty:Boolean = false
    override def add(element: Int): MyList = new List(element,this)
    override def toString:String = h.toString + " " + t.toString
  }

  val list = new List(1,emptyList)
  println(list.add(2).add(3).add(4).head)
  println(list.add(2).add(3).add(4).tail.tail.tail.head)
//  println(list.add(2).add(3).add(4).tail.tail.tail.tail.head)
  println(list.add(2).add(3).add(4).toString)
}

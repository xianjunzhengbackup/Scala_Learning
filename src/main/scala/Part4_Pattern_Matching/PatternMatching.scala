package Part4_Pattern_Matching

import java.util.Random

object PatternMatching extends App{
  //switch on steroids
  val random = new Random()
  val x=random.nextInt(10)

  val description = x match {
    case 1 => "the ONE"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else"  // _ is wildcard
  }
  println(x)
  println(description)

  // 1. Decompose values
  case class Person(name:String,age:Int)
  val bob = Person("Bob",20)

  val greeting = bob match{
    case Person(n,a) if a<21 => s"Hi, my name is $n and I can't drink in US"
    case Person(n,a) =>s"Hi, my name is $n and I am $a years old"
    case _ => "I don't know who I am"
  }
  println(greeting)
  /*
  1. cases are matched in order
  2. what if no cases match? MatchError happened and crushed.
  3. type of the PM expression? unified type of all the types in all the cases
  4. PM works really well with case classes
   */

  //PM on sealed hierarchies
  sealed class Animal
  case class Dog(breed:String) extends Animal
  case class Parrot(greet:String) extends Animal

  val dog = Dog("Germanl Shepd")
  dog match{
    case Dog(breed) => println(s"It is a dog。 $breed")
//    case Parrot(greet) => println(greet)
  }
  //如果添加case Parrot(greet),那么sealed class会报警

  /*
  Exercise
  simple function uses PM
  takes an Expr => human readable form

  Sum(Number(1),Number(2)) = 1+ 2
  Sum(Number(1),Number(2),Number(3)) => 1+ 2+ 3
  Prod(Sum(Number(1),Number(2)),Number(3)) => (1+2)*3
  Sum(Prod(Number(1),Number(2)),Number(3)) => 1*2+3
   */

  trait Expr
  case class Number(n:Int) extends Expr
  case class Sum(e1:Expr,e2:Expr) extends Expr
  case class Prod(e1:Expr,e2:Expr) extends Expr

  def show(e1:Expr):String ={
    e1 match{
      case Sum(e1,e2) => show(e1)+" + " + show(e2)
      case Number(n) => n.toString
      case Prod(Sum(e1,e2),Sum(e3,e4)) => "(" + show(e1) + " + " +show(e2)+")" +" * " + "(" +show(e3)+" + "+show(e4)+")"
      case Prod(Sum(e1,e2),e3) =>"(" + show(e1) + " + " +show(e2)+")" +" * " + show(e3)
      case Prod(e1,Sum(e2,e3)) => show(e1)+" * " + "(" +show(e2)+" + "+show(e3)+")"
      case Prod(e1,e2) => {
        show(e1) + " * "+show(e2)
      }
    }
  }

  println(show(Sum(Number(1),Number(2))))
  println(show(Prod(Sum(Number(1),Number(2)),Number(3))))
  println(show(Sum(Prod(Number(1),Number(2)),Number(3))))

}

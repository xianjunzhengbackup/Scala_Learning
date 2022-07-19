package Part4_Pattern_Matching

//import lectures.beginner.part2.oop.OOBExercise.{List, MyList, emptyList}

object AllThePatterns extends App{
  // 1 - constants
  val x:Any ="scala"
  val constants =x match {
    case 1=>"a number"
    case "scala"=>"The scala"
    case true=>"The truth"
    case AllThePatterns=>"A singleton object"
    case _=>"other stuff"
  }
  println(constants)

  // 2 - match anything
  // 2.1 wildcard
  val matchAnything = x match {
    case _ =>
  }
  // 2.2 variable
  val matchAVariable = x match {
    case something => s"I've found $something"
  }
  println(matchAVariable)

  // 3 - tuples
  val aTuple = (1,2)
  val matchATuple = aTuple match {
    case (1,1) =>
    case (something,2)=>s"I'v found $something"
  }

  val nestedTuple=(1,(2,3))
  val matchANestedTuple=nestedTuple match {
    case (_,(2,v)) =>
  }
  //PM can be nested

  // 4 - case class  -- constructor pattern
//  val aList:MyList[Int]=List(1,List(2,emptyList))
//  val matchList= aList match{
//    case List(head,List(subhead,subtail)) =>
//    case emptyList =>
//  }

  // 5 - list patterns
  val aStandList = List(1,2,3,42)
  val standListMatch=aStandList match{
    case List(1,_,_,_) => //extractor
    case List(1,_*)=> //list of arbitrary length
    case 1 :: List() => //infix pattern
    case List(1,2,3) :+42 => //infix pattern
  }

  //6 - type specifiers
  val unknown:Any = 2
  val unknownMatch = unknown match{
    case list:List[Int]=> //explicit type specifier
    case _ =>
  }

  // 7 - name binding
//  val nameBindingMatch = aList match{
//    case nonEmptyList @ List(_,_) => //name binding=>use the name later
//    case List(1,rest @ List(2,_)) => //name binding inside nested patterns
//  }

  // 8 - multi-pattern
//  val multipattern = aList match{
//    case Empty | List(0,_) => //compound pattern (multi-pattern
//  }

  // 9 if guards
//  val secondElementSpecial = aList match{
//    case List(_,List(specialElement,_)) if specialElement %2==0 ==>
//  }

  /*
  Question
   */
  val numbers = List(1,2,3)
  val numberMatch = numbers match{
    case listOfString:List[String]=>"a list of string"
    case listOfInt:List[Int]=>"a list of int"
    case _=>""
  }
  println(numberMatch)
  /*
  这是Java向后兼容的结果。在jvm做type检查的时候，它只是把type全部抹掉。所以上面的代码就变成这样
  case listOfString:List=>"a list of string"
    case listOfInt:List=>"a list of int"
    case _=>""
   */
}

object Options{
  val aList:List[Int]=List(1,2,3)
  val aTransformedList=aList.map(x=>x+1)
  val aTransformedList_v2=aList.flatMap(x=>List(x,+1))
  val aFilteredList=aList.filter(x=>x%2==0)
  val combinedLists=for{
    num <-List(1,2,3)
    char<-List('a','b')
  } yield s"$num-$char"
  //上面这句for yield等同于 List[1,2,3].flatMap(num=>List('a','b').map(char=>s"$num-$char"))

  //Option = "List" with at most one element.他将option看成是最多一个元素的List
  val anOption:Option[Int]=Option(42) //等同于Some(42)
  val anOption_v2:Option[Int]=Some(42) //same as above
  val anEmptyOption:Option[Int]=Option.empty //same as None
  val anEmptyOption_v2:Option[Int]=None

  val aTransformedOption:Option[Int]=anOption.map(x=>x*10) //if you condsider Option as a List,then it is easy to understand
  val aTransformedOption_v2:Option[Int]=anOption.flatMap(x=>Option(x*10)) //如果写成这样 anOption.Map(x=>Option(x*10)) 就会变成Some(Some(420)),所以用flatmap
  val aFilteredOption:Option[Int]=anOption.filter(x=>x>100) //None
  val combinedOptions = for{
    num <- None
    str <- Option("Scala")
  } yield s"$str .... s$num"   //if num and str contain value,then return the string;if num or str is empty,then return None

  //Option API
  val checkEmpty = anOption.isEmpty
  val innerValue:Int=anOption.getOrElse(99) //99 is default value,if anOption is None then return 99,otherwise return its value
  val aChainedOption:Option[Int]=anEmptyOption.orElse(anOption) //if anEmptyOption contain value then return its value;if anEmptyOption is None, and anOption contain value,
                                                                //then return anOption's value;if anEmptyOption and anOption are None, then return None

  //Option describes the possible absence of a value
  def unsafeMethod(arg:Int):String = null
  //defensive code for user to use unsafeMethod
  val potentialValue = unsafeMethod(44)
  val myRealResult =
    if(potentialValue==null)
      "Error"
    else
      potentialValue.toUpperCase()

  val callToExternalService=unsafeMethod(55)
  val combinedResult=
    if(potentialValue==null)
      if(callToExternalService==null)
        "error1"
      else
        "error2"
    else
      if(callToExternalService==null)
          "error3"
      else
      potentialValue.toUpperCase()+callToExternalService.toUpperCase()
  //以上调用了两次unsafeMethod，为了将两次结果合并成一个，写了一堆代码，输出3个error。假如我们用option来实现
  val betterCombinedResult:Option[String]= for{
    firstValue <- Option(unsafeMethod(44))
    secondValue <- Option(unsafeMethod(55))
  } yield firstValue.toUpperCase()+secondValue.toUpperCase()  //if unsafeMethod return None, then yield None.这样过滤掉3种可以能的错误.
  val finalValue=betterCombinedResult.getOrElse("error")  //if betterCombinedResult is None,then return "error"

  //best practice 1: wrap unsafe APIs (which could return null) inside Options

  //best practice 2: resist the temptation to "get" the value inside an Option
  val firstValue = Option(unsafeMethod(44))
  val secondValue=Option(unsafeMethod(55))
  val combinedResult_v2 =
    if(firstValue.isEmpty)
      if(secondValue.isEmpty)
        "error1"
      else
        "error2"
    else
      if(secondValue.isEmpty)
        "error3"
      else
      firstValue.get.toUpperCase()+secondValue.get.toUpperCase()
   //如果是None,然后调用get，就有异常
  //just as bad as dealing with null

  //best practice 3(WARNING):NEVER CALL Some(null),Some()函数假定里面不是null


  def main(args:Array[String])={
    println(aTransformedList)
    println(aTransformedList_v2)
    println(combinedLists)
    println(aTransformedOption.get)
    println(combinedOptions)
    println(betterCombinedResult)
    println(finalValue)
  }
}

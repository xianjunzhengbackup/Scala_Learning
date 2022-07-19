package Part3

import scala.util.{Failure, Random, Success, Try}

object HandlingFailures extends App{
  /*
  sealed abstract class Try[+T]
  case class Failure[+T] extends Try[T]
  case class Success[+T] extends Try[T]
   */
  val aSuccess = Success(3)
  val aFailure=Failure(new RuntimeException("SUPER FAILURE"))
  println(aSuccess)
  println(aFailure)

  //Try object via the apply method
  def unsafeMethod():String=throw new RuntimeException("NO STRING FOR YOU")
  val potentialFailture=Try(unsafeMethod())
  println(potentialFailture)

  //syntax sugar
  val anotherFailure = Try{
    //code may crush
  }

  //utilities
  println(potentialFailture.isSuccess)

  //orElse
  def backupMethod():String="a valid result"
  val failbckTry=Try(unsafeMethod()).orElse(Try(backupMethod()))
  println(failbckTry)

  //IF you design API
  def betterUnsafeMethod():Try[String]=Failure(new RuntimeException)
  def betterBackupMethod():Try[String]=Success("A valid result")
  val betterFallback = betterUnsafeMethod() orElse betterBackupMethod()
  println(betterFallback)

  //map filter flatMap
  println(aSuccess.map(_*2))
  println(aSuccess.flatMap(x=>Success(x*10)))
  println(aSuccess.filter(_>10))

  //for-comprehension
  /*
  Exercise
   */
  val hostname="localhost"
  val port="8080"
  def renderHTML(page:String)=println(page)
  class Connection{
    def get(url:String):String={
      val random=new Random(System.nanoTime())
      if(random.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection interrupted")
    }
  }

  object HttpService{
    val random=new Random(System.nanoTime())
    def getConnection(host:String,port:String):Connection={
      if(random.nextBoolean()) new Connection
      else throw new RuntimeException("Someone else took the port")
    }
  }

  //if you get the html page from the connection, print it to the ocnsole i.e call renderHTML
  //一句话就是安全运行HttpService.getConnection().get()
  val page=for{
    connection <- Try(HttpService.getConnection(hostname,port))
    p <- Try(connection.get("llllll"))
  } yield p
  page.foreach(renderHTML)



}

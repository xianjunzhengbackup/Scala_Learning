package Part3

import java.util.Random

object Optional extends App{
  /*
  sealed class Option[+A]
  case class Some[+A] extends Option[A]
  case class None[+A] extends Option[A]

  Option的出现为了防止None pointer错误。
   */

  val myFirstOption :Option[Int] = Some(4)
  val noOption :Option[Int] = None
  println(myFirstOption)

  //unsafe APIs
  def unsafeMethod():String = null
  /*
  函数有可能返回null的结果。传统的写法都要检查函数的返回值，防止出现none pointer错误。scala用Option把这种可能性给整个打包了
  val result =Some(null) 这是错误的写法，正确的Option用法如下
   */
  val result = Option(unsafeMethod())
  println(result)

  //chained methods
  def backupMethod():String="A valid result"
  //如果unsafeMethod返回null，则orElse将返回backupMethod的返回值
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))

  //更好代码如下
  //DESIGN unsafe API
  def betterUnsafemethod():Option[String]=None
  def betterBackupMethod():Option[String]=Some("A valid result")
  val betterChainedResult = betterUnsafemethod() orElse betterBackupMethod()
  println(betterChainedResult)
  //返回Option,将None pointer的错误包裹在API里。同时可读性更强

  //functions on Options
  println(myFirstOption.isEmpty)
  println(myFirstOption.get) //UNSAFE - DO NOT USE if myFirstOption is None,none pointer error thrown

  //map  flatMap filter
  println(myFirstOption.map(_*2))
  println(myFirstOption.filter(_ > 10))
  println(myFirstOption.flatMap(x=>Option(x*10)))

  //for-comprehensions

  /*
  Exercise: 模拟网络链接。假定config来自其它读取，可能Map为空，也可能有确定的值。底下的Connection，可能能连上，也可能连不上
  现在来调用连接函数
   */
  val config :Map[String,String] =Map(
    "host" -> "176.4.5.6",
    "port" -> "80"
  )

  class Connection{
    def connect = "Connected" //connect to some server
  }
  object Connection{
    val random = new Random(System.nanoTime())
    def apply(host:String,port:String):Option[Connection]=
      if(random.nextBoolean()) Some(new Connection)
      else None
  }

  //try to establish a connection, if so - print the connect method
  val host = config.get("host") //Map.get返回的是Option[String]，这是safe method
  val port = config.get("port")
  //接下来的Connection apply method要用到host和port里面的value，如果直接host.get 还是会出现None pointer error
  /*
  它等价于如下写法
  if(h!= null)
    if(p!=null)
      return Connection(h,p)
  return null
   */
  val connection = host.flatMap(h=>port.flatMap(p=>Connection(h,p)))  //返回值是Option[Connection]
  val connectionStatus = connection.map(c=>c.connect) //同理，如果connection为null，则返回None，而不会报错
  println(connectionStatus)
  connectionStatus.foreach(println) //connectionStatus如果不是null，则调用println

  //for-comprehensions
  val forConnectionStatus = for{
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host,port)
  } yield connection.connect
  forConnectionStatus.foreach(println)
}

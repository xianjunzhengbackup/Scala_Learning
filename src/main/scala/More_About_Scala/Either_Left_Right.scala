package More_About_Scala

object Either_Left_Right extends App{
  def divideXByY(x:Int,y:Int):Either[String,Int]={
    if(y==0) Left("Error: y is 0")
    else Right(x/y)
  }

  println(divideXByY(10,0))
  println(divideXByY(10,2))

  val res =divideXByY(10,0) match {
    case Left(s) => 0
    case Right(r) => r
  }
  println(res)


}

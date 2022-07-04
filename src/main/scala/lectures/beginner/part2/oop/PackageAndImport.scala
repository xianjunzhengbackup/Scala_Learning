package lectures.beginner.part2.oop

object PackageAndImport extends App{

  //package members are accessible by the their simple name，也就是直接用
  val writer = new Writer("Jun","Zheng",1981)
  println(writer.fullName)


}

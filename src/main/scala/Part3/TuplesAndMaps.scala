package Part3

object TuplesAndMaps extends App{
  //tuples = finite ordered "lists"
  val aTuple=Tuple2(3,"scala")
  val secondTuple = (3,"scala") //Tuple2[Int,String]=(Int,String)

  println(aTuple._1)
  println(aTuple.copy(_2="goodbye java"))
  println(aTuple.swap)

  //Maps - keys -> values
  val aMap :Map[String,Int]=Map()
  val phonebook = Map(("Jim",555),("Daniel",789)).withDefaultValue(-1)
  val phonebook2 =Map(("Jim",555),"Daniel"->789)
  // a->b is syntax sugar for (a,b)
  println("ddd"->789)
  println(phonebook)

  //map ops
  println(phonebook.contains("Jim"))
  println(phonebook("Jim"))
  println(phonebook("mary"))  //without withDefaultValue, this will crush

  //add a paring
  val newPhonebook = phonebook + ("mary" -> 567)
  println(newPhonebook)

  //functions on maps
  //map flatMap filter
  println(phonebook.map(pair=>pair._1.toLowerCase()->pair._2))

  //filterKeys
  println(phonebook.filterKeys(_.startsWith("J")))
  //mapValues
  println(phonebook.mapValues(number=>"021-"+number))

  //conversions to other collection
  println(phonebook.toList)
  println(List(("Daniel",555),("Jim",678)).toMap)
  val names = List("Bob","James","Angela","Mary","Daniel","Jim")
  println(names.groupBy(name=>name.charAt(0)))

  /*
  Exercise
  1. what whould happen if I had two original entires "jim"->555 and "JIM"->900,then
  map(pair=>pair._1.toLowerCase()->pair._2)
  !!! careful with mapping keys, if not careful you may lose data
  2. Overly simplifed social network based on maps
  Person = String
  - add a person to the network
  - remove
  - friend (mutual)
  - unfriend

  - number of friends of a person
  - person with most friends
  - how many people have NO friend
  - if there is a social connection between two people (direct or not)
   */

  val modifiedPhonebook = phonebook + ("JIM"->900)
  println(modifiedPhonebook)
  println(modifiedPhonebook.map(pair=>pair._1.toLowerCase->pair._2))

  abstract class net[T] {
    def add(name:T):net[T]
    def add(name:T, friendList:List[T]) :net[T]
    def remove(name :T) :net[T]
    def friend(adder: T,friend:T) :net[T]
    def unfriend(remover:T,friend:T) :net[T]

    def numOfFriends():Map[T,Int]
    def personWithMostFiend:T
    def numOfNoFriends() : Int
    def socialConnectionDirect(person1:T,person2:T) :Boolean
    def socialConnection(person1:T,person2:T):Boolean
  }

  case class netWork[T](networkMap: Map[T,List[T]]) extends net[T] {

    override def add(name: T): net[T] = {
      val newnetworkMap = networkMap + (name -> List[T]())
      netWork(newnetworkMap)
    }

    override def add(name: T, friendList: List[T]): net[T] = {
      val newnetworkMap = networkMap + (name -> friendList)
      netWork(newnetworkMap)
    }

    override def remove(name: T): net[T] = {
      val newnetworkMap = networkMap.filter(pair => pair._1 != name)
      netWork(newnetworkMap)
    }

    override def friend(adder: T, friend: T): net[T] = {
      val res = networkMap.filter(pair => pair._1 == adder)
      if (!res.isEmpty) {
        val newList = res(adder) ++ List(friend)
        remove(adder).add(adder, newList)
      } else this
    }

    override def unfriend(remover: T, friend: T): net[T] = {
      val res = networkMap.filter(pair => pair._1 == remover)
      if (!res.isEmpty) {
        val newList = res(remover).filter(_ != friend)
        remove(remover).add(remover, newList)
      } else this
    }

    override def numOfFriends(): Map[T, Int] = {
      networkMap.map(pair => (pair._1 -> (pair._2.length)))
    }

    override def personWithMostFiend: T = {
      val numList = numOfFriends()
      val maxNumber = numList.values.max
      val key = numList.filter(pair => pair._2 == maxNumber).keys.toList(0)
      key
    }

    override def numOfNoFriends(): Int = {
      val personsWithNoFriends = networkMap.filter(pair => pair._2.isEmpty)
      personsWithNoFriends.size
    }

    override def socialConnectionDirect(person1:T,person2:T) :Boolean={
      if(!networkMap.contains(person1) | !networkMap.contains(person2))
        false
      else{
        val p1List = networkMap(person1)
        val p2List = networkMap(person2)
        p1List.contains(person2) | p2List.contains(person1)
      }
    }
    override def socialConnection(person1:T,person2:T):Boolean={
      if(socialConnectionDirect(person1, person2)) true
      else{
        val p1List=networkMap(person1)
        (for{
          person <- p1List
        } yield socialConnection(person,person2) ).foldLeft(false)(_ | _)
      }
    }
  }

  val socialNet = netWork[String](Map()).add("Jun").add("Jim")
  println(socialNet)
  val newSocialNet=socialNet.add("Tom").friend("Jun","Jim").friend("Jun","Tom")
  .friend("Tom","Jim").unfriend("Jun","Tom").unfriend("Jim","Tom")
    .add("Frank").friend("Frank","Tom").friend("Frank","Jim").friend("Frank","Jun")
    .add("Bill")
  println(newSocialNet)
  println(newSocialNet.numOfFriends())
  println(newSocialNet.personWithMostFiend)
  println(newSocialNet.numOfNoFriends())
  println(newSocialNet.socialConnectionDirect("Jim","Jun"))
  println(newSocialNet.socialConnection("Tom","Jim"))
  println(newSocialNet.socialConnection("Frank","Bill"))
  println(newSocialNet.socialConnection("Bill","Jim"))
}

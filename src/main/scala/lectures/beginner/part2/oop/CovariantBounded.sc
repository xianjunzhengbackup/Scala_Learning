def mydouble(myfunction:(Int=>Int),element:Int):Int={
  myfunction(element)
}

def anotherdouble(element:Int):Int=2*element

println(mydouble(anotherdouble(),3))
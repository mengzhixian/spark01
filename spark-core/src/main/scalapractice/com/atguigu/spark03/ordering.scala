package com.atguigu.spark03

object ordering01 {
  def main(args: Array[String]): Unit = {
    implicit val ord: Ordering[User] = new Ordering[User] {
      override def compare(x: User, y: User) = x.age-y.age
    }
    println(max(User(10, "tt"), User(120, "tt")))


}
  def max[T:Ordering](a:T,b:T):Boolean={
    val ord: Ordering[T] = implicitly[Ordering[T]]
    if (a.equals(b)) true else false
  }
}


case class User(age:Int,name:String)


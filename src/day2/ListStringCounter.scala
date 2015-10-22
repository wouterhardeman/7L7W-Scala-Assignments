package day2

/**
 * Created by wouterhardeman on 22/10/15.
 */
object ListStringCounter extends App {

  val stringList = List("String 1", "String 2", "String 3")
  val stringLength = stringList.foldLeft(0)((carryOver, e) => carryOver + e.length)
  print(stringLength.toString)

}

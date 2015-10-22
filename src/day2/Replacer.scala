package day2

import scala.collection.immutable.HashMap

trait Censor {

  val map = io.Source.fromFile("cursewords.txt").getLines().foldLeft(new HashMap[String, String])((carry, e) =>
    try {
      carry.updated(toHashMap(e.split(", "))._1, toHashMap(e.split(", "))._2)
    } catch {
      case ex: ArrayIndexOutOfBoundsException => {
        carry
      }
    }
  )

  def addToMap(currentMap : HashMap[String, String], newCurse : (String, String)) = currentMap.updated(newCurse._1, newCurse._2)

  def toHashMap(words : Array[String]) = words(0) -> words(1)

  def censor(word:String) = map.foldLeft(word)((curse, mapValue)=>
    curse.replaceAll(mapValue._1, mapValue._2))
}

class Text(s:String) extends Censor {
  //Returns the censored value
  def value = s
  def censoredValue = censor(s)
}

object Replacer extends App{
  val text = new Text("Shoot, Darn and some normal words")
  println("Uncensored: " + text.value)
  println("Censored: " + text.censoredValue)
}
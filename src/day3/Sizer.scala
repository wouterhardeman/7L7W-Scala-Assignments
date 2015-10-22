package day3

import scala.io._
import scala.actors._
import Actor._

object objectSizer {

  def main(args : Array[String]) {
    println("Sequential run:")
    timeMethod { getPageSizeSequentially }
    println("Concurrent run")
    timeMethod { getPageSizeConcurrently }
  }

  object PageLoader {
    def getPageSize(source : String) = source.length
    def getPage(url : String) = Source.fromURL(url).mkString
    def getCountLinks(source : String) = "(?i)<a([^>]+)>(.+?)</a>".r.findAllMatchIn(source).length
  }

  val urls = List("http://www.baslijnse.nl/", "https://www.twitter.com/" ,
    "http://www.google.com/" , "http://www.cnn.com/" )

  def timeMethod(method: () => Unit) = {
    val start = System.nanoTime
    method()
    val end = System.nanoTime
    println("Method took " + (end - start)/1000000000.0 + " seconds.")
  }

  def getPageSizeSequentially() = { for(url <- urls) {
    val source = PageLoader.getPage(url)
    println("Size for " + url + ": " + PageLoader.getPageSize(source))
    println("Count links for " + url + ": " + PageLoader.getCountLinks(source))
  }
  }

  def getPageSizeConcurrently() = {
    val caller = self
    for(url <- urls) {
      val source = PageLoader.getPage(url)
      actor { caller ! (url, PageLoader.getPageSize(source), PageLoader.getCountLinks(source) ) }
    }
    for(i <- 1 to urls.size) { receive {
      case (url, size, countLinks) => {
        println("Size for " + url + ": " + size)
        println("Amount links for " + url + ": " + countLinks)
      }
    } }
  }

}

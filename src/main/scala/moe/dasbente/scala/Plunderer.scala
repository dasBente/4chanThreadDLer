package moe.dasbente.scala
import java.io.File
import java.net.URL

/**
 * @author dasBente
 */
class Plunderer(val threadurl: String, val savedir: File) {
  var dledImages: Set[String] = Set()
  plunder()
  
  // Plunder a threads posted images, if not previously downloaded
  def plunder() {
    // Locate all post images anchor tags and extract image URL's
    val html = io.Source.fromURL(threadurl).mkString.split("<").toList
    val imageTags = html filter (_ startsWith "a class=\"fileThumb\"")
    
    import sys.process._
    for (l <- imageTags) {
      val link = "https:"+ l.split(" ")(2).substring(6).takeWhile(_ != '"') 
      
      // Check whether link is contained in the Set of downloaded Images, if not, download it
      if (!(dledImages contains link)) {
        dledImages += link
        
        // Generate filename from link. Prepend absolute file path
        val filename = savedir.toPath +"\\"+ link.split("/").last
        
        println("Downloading from "+ link)
        // Download image from image URL
        new URL(link) #> new File(filename) !!
      }
    }
  }
}
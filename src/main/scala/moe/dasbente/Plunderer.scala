package moe.dasbente

import java.io.File
import java.net.URL

/**
 * @author dasBente
 */

object Plunderer {
  def plunder(url: String, dir: String, verbose: Boolean) {
    // Locate all post images anchor tags and extract image URL's
    val html = io.Source.fromURL(new URL(url)).mkString.split("<").toList
    val imageTags = html filter (_ startsWith "a class=\"fileThumb\"")
    
    import sys.process._
    for (l <- imageTags) {
      val link = "https:"+ l.split(" ")(2).substring(6).takeWhile(_ != '"') 
      
      // Generate filename from link. Prepend absolute file path
      val filename = new File(dir).toPath +
        (if (System.getProperty("os.name").startsWith("Windows")) "\\" else "/") + 
          link.split("/").last
      
      if (verbose)
        println("Downloading from "+ link +" to "+ filename)
      
      // Download image from image URL
      new URL(link) #> new File(filename) !!
    }
  }

  def plunder(url: String, dir: String): Unit = plunder(url, dir, false)

  def plunder(url: String): Unit = plunder(url, ".")
}
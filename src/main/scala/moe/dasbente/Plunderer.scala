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
    for (l <- imageTags) 
      download("https:"+ l.split(" ")(2).substring(6).takeWhile(_ != '"'), dir, verbose) 
  }
	
  def plunder(url: String, dir: String) { plunder(url, dir, false) }
  def plunder(url: String) { plunder(url, ".") }
	
	def download(url: String, dir: String, filename: String, verbose: Boolean) {
		var path = if (dir startsWith ".") 
			new File("").getAbsolutePath() + dir.tail
		else if (dir startsWith "~")
			System.getProperty("user.home") + dir.tail
		else dir // TODO: Add support for .. !
		
		path = if (System.getProperty("os.name").startsWith("Windows")) 
			path + "\\" + filename 
		else 
			path + "/" + filename
		
		if (verbose) 
			println("Downloading "+ filename +" from "+ url)
		
		// Download File from URL to Path
		import sys.process._
		new URL(url) #> new File(path) !!
	}
	
	def download(url: String, dir: String, filename: String) {
		download(url, dir, filename, false)
	}
	
	def download(url: String, dir: String, verbose: Boolean) {
		val filename = url.split("/").last
		download(url, dir, filename, verbose)
	}
	
	def download(url: String, dir: String) { download(url, dir, false) }
	def download(url: String, verbose: Boolean) { download(url, ".", false) }
	def download(url: String) { download(url, ".", false) }
	
}

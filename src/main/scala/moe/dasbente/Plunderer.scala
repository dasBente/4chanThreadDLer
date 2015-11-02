package moe.dasbente

import java.io.File
import java.net.URL

/**
 * @author dasBente
 */
object Plunderer {
	def plunder(url: String, dir: String, verbose: Boolean, 
							crawler: URL => List[URL]) {				 
    // Feeds the given URL into the crawler
		val links = crawler(new URL(url))
		
		// Downloads the list of resulting image links
    import sys.process._
    for (l <- links) 
      download(l, dir, verbose) 
  }
	
	def plunder(url: String, dir: String, verbose: Boolean) {
		plunder(url, dir, verbose, { url: URL =>
			val html = io.Source.fromURL(url).mkString.split("<").toList
			val tags = html filter (_ startsWith "a class=\"fileThumb\"")
			
			for (t <- tags) yield {
				new URL("https:"+ t.split(" ")(2).substring(6).takeWhile(_ != '"'))
			}
		})
	}
	
  def plunder(url: String, dir: String) { plunder(url, dir, false) }
  def plunder(url: String) { plunder(url, ".") }
	
	def download(url: URL, dir: String, filename: String, verbose: Boolean) {
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
		url #> new File(path) !!
	}
	
	def download(url: URL, dir: String, filename: String) {
		download(url, dir, filename, false)
	}
	
	def download(url: URL, dir: String, verbose: Boolean) {
		val filename = url.toString.split("/").last
		download(url, dir, filename, verbose)
	}
	
	def download(url: URL, dir: String) { download(url, dir, false) }
	def download(url: URL, verbose: Boolean) { download(url, ".", false) }
	def download(url: URL) { download(url, ".", false) }
}
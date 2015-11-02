package moe.dasbente.plunderer

import java.io.File
import java.net.URL

/**
 * @author dasBente
 */
object Plunderer {
	def plunder(url: String, dir: String, verbose: Boolean)
				(implicit crawler: URL => List[URL]) {				 
    for (l <- crawler(new URL(url))) download(l, dir, verbose) 
  }
	
  def plunder(url: String, dir: String)(implicit crawler: URL => List[URL]) { 
		plunder(url, dir, false)(crawler)
	}
  
	def plunder(url: String)(implicit crawler: URL => List[URL]) { 
		plunder(url, ".")(crawler) 
	}
	
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
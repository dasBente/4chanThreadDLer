package moe.dasbente.plunderer

import java.net.URL

object CrawlerPreference {
	implicit def crawl4chan(url: URL): List[URL] = {
		val html = io.Source.fromURL(url).mkString.split("<").toList
		val tags = html filter (_ startsWith "a class=\"fileThumb\"")
		
		for (t <- tags) yield {
			new URL("https:"+ t.split(" ")(2).substring(6).takeWhile(_ != '"'))
		}
	}
}
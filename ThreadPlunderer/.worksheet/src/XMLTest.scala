object XMLTest {
	import scala.xml._
	import java.net.URL;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(159); 
	
	//val code = XML.load(new URL("http://www.google.de"))
  println("Welcome to the Scala worksheet");$skip(63); 
  
  val raw = io.Source.fromURL("https://4chan.org").mkString;System.out.println("""raw  : String = """ + $show(raw ))}
}

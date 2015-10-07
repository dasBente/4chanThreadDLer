package moe.dasbente.gui

import scala.swing._
import java.net.URL

class ThreadChooser extends BorderPanel {
  // GUI Components
  
  // TODO: Maybe exclude/seperate NSFW boards (e.g. with a seperate check box?)
  val cbBoards = new ComboBox(boards)
  
  // Positioning of GUI Components
  import BorderPanel.Position._
  layout(cbBoards) = West
  
  // Generate a URL from chosen board and thread
  def url = "https://4chan.org"+ cbBoards.selection.item // + ... // TODO: Thread Chooser

  // Get a list of all existing boards
  val boards: List[String] = {
    // Use /a/ as reference // TODO: Maybe something more general?
    val html = io.Source.fromURL(new URL("https://4chan.org/a/")).mkString
      .split("<").toList

    // Reduce HTML-Code to the boardList span
    val boardListSpan = html dropWhile {
      x: String => !x.startsWith("span class=\"boardList\"") 
    } takeWhile {
      x: String => !x.startsWith("/span")
    }

    // Extract board tags
    for (b <- boardListSpan; if (b startsWith "a")) yield
      b drop 8 takeWhile (_ != '"')
  }
}

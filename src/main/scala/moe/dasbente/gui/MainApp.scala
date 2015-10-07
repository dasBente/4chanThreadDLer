package moe.dasbente.gui

import scala.swing._
import scala.swing.event._
import java.io.File
import moe.dasbente.Plunderer

/**
 * @author dasBente
 */
object MainApp extends SimpleSwingApplication {
  val btnSaveTo = new Button("Save to...") {
    reactions += {
      case ButtonClicked(_) => {
        val temp = chooseDirectory
        
        if (temp == None) println("Directory does not exist!")
        else lastFile = temp
      }
    }
  }
  
  val tfURL = new TextField // TODO Exchange with proper interface
  
  val btnPlunder = new Button("Plunder!") {
    reactions += {
      case ButtonClicked(_) => {
        if(lastFile == None) println("Invalid Directory!") 
        else {
          val Some(file) = lastFile
          new Plunderer(tfURL.text, file)
        }
      }
    }
  }
  
  listenTo(btnSaveTo)
  listenTo(btnPlunder)
  
  var lastFile: Option[File] = None
  
  def top = new MainFrame {
    contents = new BorderPanel {
      import BorderPanel.Position._
      
      layout(btnSaveTo) = North
      
      layout(new BorderPanel { // TODO Exchange with proper interface
        layout(new Separator(Orientation.Horizontal)) = North
        layout(new Label("URL of the Thread")) = Center
        layout(tfURL) = South
      }) = Center
      
      layout(btnPlunder) = South
    } 
  }
  
  def chooseDirectory: Option[File] = {
    val chooser = new FileChooser(null) {
      fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly
      title = "Choose a directory to save images to!"
    }
    
    val res = chooser.showOpenDialog(null)
    if (res == FileChooser.Result.Approve)
      Some(chooser.selectedFile)
    else None
  }
}

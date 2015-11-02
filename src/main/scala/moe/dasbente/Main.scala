package moe.dasbente
 
import moe.dasbente.plunderer.Plunderer

object Main {
  def main(args: Array[String]) {
    if (args.length == 0)
      printHelp() // Print help
    else if (args.length == 1)
      Plunderer.plunder(args(0)) // Download to local directory
    else if (args.length == 2)
      Plunderer.plunder(args(0), args(1)) // Download to given directory
    else
      processArguments(args.toList) // Process argument list
  }

  def processArguments(args: List[String]) {
    var directory = "."
    var threadurl = ""
    var verbose = false

    args match { // TODO: Needs some fail-safes
      case "--help" :: xs =>
        printHelp()
      case f if (f == "-v" || f == "--verbose") =>
        verbose = true
      case f :: v :: xs => {
        if (f == "-d" || f == "--directory") 
          directory = v
        else if (f == "-u" || f == "-url") 
          threadurl = v
        else { // Assume that the first argument is the URL and the second is the directory
          directory = v
          threadurl = f
        }

        // Proceed with evaluating the argument list
        processArguments(xs)
      }
    }

    Plunderer.plunder(threadurl, directory, verbose)
  }

  def printHelp() {
    println("To be implemented")
  }
}

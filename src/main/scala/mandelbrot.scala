import org.apache.pdfbox.util.PDFTextStripper
import org.apache.pdfbox.pdmodel.PDDocument
import java.io.File


object Mandelbrot {
  val defaultDirectory = "./pdf"

  val resumeFileName = "./pdf/Patrick McGuire Resume.pdf"

  def main(args: Array[String]) {
    if (0 == args.length) {
      print(readMultiple(args))
    } else {
      val dir = new File(defaultDirectory)
      val files = dir.listFiles
      print(readMultiple(files.map(_.getPath)))
    }
  }

  def readMultiple(args: Array[String]) = {
    val stripper = new PDFTextStripper
    args.map{(filename: String) => 
      try {
        val doc = PDDocument.load(filename)
        val text = stripper.getText(doc)
        doc.close()
        text
      } catch {
        case e: Exception => null
      }
    } 
  }

  def defaultFileNames = {
    val dir = new File(defaultDirectory)
    dir.listFiles.map(_.getPath)
  }

  def fetchObjects = {
    val fileNames = defaultFileNames
    fileNames.map(PDDocument.load(_))
  }

  def openResume = {
    PDDocument.load(resumeFileName)
  }

}

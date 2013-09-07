import java.io.File
import scala.collection.JavaConversions._
import org.apache.pdfbox.cos._
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper


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

  def treeIt(doc: PDDocument) = {
    val cosDoc = doc.getDocument
    val objects = cosDoc.getObjects.toList
    val useableObjects = objects.map(_.getObject)
    useableObjects
  }

  def recur(objects: List[COSBase]) = {
    objects.map { (cos: COSBase) => objectMap(cos) }
  }

  def objectMap(cos: COSBase) = {
    cos match {
      case (string: COSString) => string
      case (array: COSArray) => 
        val subObjects: List[COSBase] = array.toList.toList
        recur(subObjects)
      case (int: COSInteger) => int
      case (stream: COSStream) => stream
      case (dict: COSDictionary) => 
        val keys = dict.keyList.toList
        val subObjects = keys.map(dict.getItem(_))
        objectMap(subObjects)
    }
  }
}

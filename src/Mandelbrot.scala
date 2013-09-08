import java.io.File
import scala.collection.JavaConversions._
import org.apache.pdfbox.cos._
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper
import pdftree._;

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

  def recur(objects: List[COSBase]):List[PDFNode] = {
    objects.map { (cos: COSBase) => objectMap(cos) }
  }

  def objectMap(cos: COSBase):PDFNode = {
    cos match {
      case (string: COSString) => new PDFString(string)
      case (array: COSArray) => 
        val subSeq =  for (i <- 0 until array.size) yield array.get(i)
        val subList = subSeq.toList
        new PDFChildren(recur(subList))
      case (name: COSName) => new PDFName(name)
      case (int: COSInteger) => new PDFInt(int)
      case (stream: COSStream) => new PDFStream(stream)
      case (ob: COSObject) => objectMap(ob.getObject)
      case (dict: COSDictionary) => extractDictionary(dict)
    }
  }
  def extractDictionary(dict: COSDictionary) = {
    println(dict)
    Thread.sleep(1000)
    val keyList = dict.keyList.toList
    val stringKeys = keyList.map(_.getName)
    val tuples = stringKeys.map((k: String) => (k, objectMap(dict.getItem(k))))
    val map = tuples.toMap
    new PDFDict(map)
  }
} 

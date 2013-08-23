import org.apache.pdfbox.util.PDFTextStripper
import org.apache.pdfbox.pdmodel.PDDocument

object Mandelbrot {
  def main(args: Array[String]) {
    readMultiple(args)
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
}

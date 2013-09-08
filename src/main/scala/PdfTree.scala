package pdftree;

import org.apache.pdfbox.cos._

abstract class PDFNode
case class PDFStream(val stream: COSStream) extends PDFNode
case class PDFString(val string: COSString) extends PDFNode
case class PDFArray(val array: COSArray) extends PDFNode
case class PDFInt(val int: COSInteger) extends PDFNode
case class PDFDict(val map: Map[String,PDFNode]) extends PDFNode
case class PDFChildren(val list: List[PDFNode]) extends PDFNode
case class PDFName(val name: COSName) extends PDFNode
case class PDFKeyValue(val string: String, val subNode: PDFNode) extends PDFNode

name := "mandlebrot-pdf"

version := "0.1"

scalaVersion := "2.10.2"

libraryDependencies += "org.apache.pdfbox" % "pdfbox" % "1.8.2"
// and these are all the "optional" dependencies

libraryDependencies += "org.bouncycastle" % "bcprov-jdk15" % "1.44"

libraryDependencies += "org.bouncycastle" % "bcmail-jdk15" % "1.44"

libraryDependencies += "com.ibm.icu" % "icu4j" % "3.8"


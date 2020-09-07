import java.io.FileWriter
import java.nio.charset.CodingErrorAction
import scala.io.Codec

object HMCookBook extends App {

  implicit val codec = Codec("UTF-8")
  codec.onMalformedInput(CodingErrorAction.REPLACE)
  codec.onUnmappableCharacter(CodingErrorAction.REPLACE)


  val srcName = "C:/poems/13177-8.txt"
  val dstName = "c:/poems/fixedRecipes.txt"
  def openSource(fName:String) = {

    val filePointer = scala.io.Source.fromFile(srcName)
    val myLines = filePointer.getLines.toSeq

    filePointer.close()
    myLines
  }

  def processSeq(mySeq:Seq[String])= {
    var newSeq: Seq[String] = Seq()
    println(newSeq.indexOf(newSeq.find(_ contains("    "))))
    newSeq
  }

  def saveSeq(destName:String, mySeq:Seq[String]) = {
    println(s"Saving my Sequence to file $destName")
    mySeq.foreach(println)
    val fw = new FileWriter(destName)
    mySeq.map(_ + "\n").foreach(fw.write)
    fw.close()
  }


  val mySeq = openSource(srcName)
  val filteredSeq = processSeq(mySeq)
  saveSeq(dstName,filteredSeq)
}
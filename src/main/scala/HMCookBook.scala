import java.io.FileWriter
import java.nio.charset.CodingErrorAction
import scala.io.Codec
import scala.util.matching.Regex

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


    def processSeq(mySeq: Seq[String]): Seq[String] = {

      val FirstIndex = mySeq.indexOf("(All measurements should be level.)")
      val LastIndex = mySeq.indexOf("WALTER BAKER & CO., Ltd.")
      val slicedText = mySeq.slice(FirstIndex,LastIndex)
      var newSeq: Seq[String] = slicedText.filter(s => s.startsWith("    ") || s == s.toUpperCase)
      newSeq
    }
// I tried to find indexes of filtered lines so I could extend only the needed once, but my code didn't work
  //println(myLines.indexOf(myLines.find(_ contains("    "))))
  // Why does it return a string with "    " but doesn't find it's index?

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
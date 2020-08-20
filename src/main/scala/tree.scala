import scala.io.StdIn.readLine

object tree extends App {
  println("Make a tree by writing the number from 1 to 5 of it's height!")
  val trHeight = readLine().toInt
var space = trHeight
def tree = " "*(trHeight-space) + "*"*(space*2-1)
def levels { println(tree)
space -= 1 }
levels
  levels
  levels
  levels
  levels
}



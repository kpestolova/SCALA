package TicTacToe


import scala.annotation.tailrec

import scala.io.StdIn
import scala.util.{Failure, Random, Success, Try}

object TheLastOne extends App {

  import Player._
  type Field = Seq[Option[Player]]

  val field: Field = IndexedSeq.fill(9)(None)

  val win =
    IndexedSeq(
      IndexedSeq(0, 1, 2),
      IndexedSeq(3, 4, 5),
      IndexedSeq(6, 7, 8),
      IndexedSeq(0, 3, 6),
      IndexedSeq(1, 4, 7),
      IndexedSeq(2, 5, 8),
      IndexedSeq(0, 4, 8),
      IndexedSeq(2, 4, 6))
  @tailrec
  def game(next: Player.Value, field: Field): String = {
   def horLines(f: Field) =
     IndexedSeq(
       f.slice(0, 3),
       f.slice(3, 6),
       f.slice(6, 9))

   def move(p: Option[Player]) = p.map(v => s"$v").getOrElse(" ") 
   def newField(f: Seq[Field]) = f.map(_.map(move).mkString(" | ")).mkString(" ", " \n---|---|---\n ", "\n\n") //B - the type of the values associated with keys.

    def printField(f: Field): Unit = println(newField(horLines(f)))
    printField(field)

    def emptyCells[T](f: Seq[Option[T]]) =
      f.zipWithIndex.collect {
      case (value, index) if value.isEmpty => index
    }

val randomCell = Random.shuffle(emptyCells(field)).head

    @tailrec
    def playerMove(field: Field): Int = {
        print("It's your turn! Enter your number from 0 to 8: ")
        Try(StdIn.readInt()) match {
          case Success(v) =>
            if (emptyCells(field).contains(v)) v
            else {
              println("The cell is already taken. Try another one: ")
              playerMove(field)
            }
          case Failure(_) =>
            println("It's not an integer. Try again! Enter your number from 0 to 8: ")
            playerMove(field)
        }
      }

    if (emptyCells(field).isEmpty) {
      "It's draw! If you want to play again, restart the program"
    } else {
      val newField = if (next == o) {
        field.updated(randomCell,Some(o))
      } else {
        field.updated(playerMove(field), Some(x))
      }
      def allEqual[A](first: A, seq: Seq[A]): Boolean = seq.forall(_ == first) // checks if all parameters of the seq are first (equal)
      def hasWon(p: Player) = {win.foldLeft(false)((i, s) => i || allEqual(Some(p), s.map(newField(_))))
      }

      if (hasWon(x)) {
        printField(newField)
        "X Wins"
      } else if (hasWon(o)) {
        printField(newField)
        "O Wins"
      } else {
        game(if (next == o) x else o, newField)
      }
    }
  }
  println(game(Player.x, field))
}

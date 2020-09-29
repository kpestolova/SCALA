package Hangman


import scala.io.StdIn.readLine
import scala.util.Random
import scala.io.Source

object NewHangman extends App {

println("Hey! You're playing Hangman!")
  var wins = 0
  var loss = 0
  Game

def Game:Any = {

  /**
   * Extracts words from the file and filters them
   */

  val fName = Source.fromResource("Words.txt")
  val listOfWords = fName.getLines.toList
  var level = List("")
  val easyWords = listOfWords.filter(_.length < 4)
  val mediumWords = listOfWords.filter(x => x.length > 4 && x.length < 10)
  val hardWords = listOfWords.filter(_.length > 10)
  fName.close()



  val input = readLine("\nDo you have 1 player or 2? ")
  var word = ""
  var guesses  = 6
  val minGuesses = 0

   /**
   * Checks player quantity and gets the guessing word
    * Asks the difficulty level
   */

  input match {
    case "1" => val lvl = readLine("\nCool! Let's play together. Choose the difficulty level: [E]asy, [M]edium or [H]ard ").replaceAll("\\s", "")
      lvl match {
        case "E"|"e"|"easy"|"Easy" => level = easyWords
        case "M"|"m"|"medium"|"Medium" => level = mediumWords
        case "H"|"h"|"hard"|"Hard" => level = hardWords
        case _ => println("\nNext time write a letter! Try again")
          Game
      }
      word = Random.shuffle(level).head.toUpperCase
    case "2" =>  println("\nOkay, nice to see you both! \n" +
      "Now one of you should close their eyes.")
      word = readLine("\nPlease enter your word: ").toUpperCase
      println("\n" *10)
    case _ => println("Sorry, I can't handle this. " +
      "Restart the programme and try again")
    Game
  }

  fName.close

  /**
   * Makes the word's structure for the game
   */

  val wordList = word.replaceAll("\\s", "").toList
  var lowerList = ("_" *  wordList.length).toList
  println(lowerList.mkString(" "))

  /**
   * Game process:
   * Counts guesses, wins and losses
   * Checks if the guessing word contains the input letter
   * Stops when player runs out of tries or when player guesses the word
   *
   */
  var usedLetters : Set[Char] = Set()
  while (guesses != minGuesses) {
    val inLetter: Char = readLine("\nType the letter: ").toUpperCase.head
        usedLetters += inLetter
    if (lowerList.contains(inLetter)) {
      println("You already used this letter!")
    }
    else if (wordList.contains(inLetter)) {
      lowerList = lowerList.zip(wordList).map({ case (g, h) => if (inLetter == h) h else g })
      println(lowerList.mkString(" ") + " "* 5 + " Guesses left: " + guesses)

        if (lowerList == wordList) {
          wins += 1
          println("\nYou won! Congrats!")
          guesses = minGuesses
      }
    } else {
      guesses -= 1
      println(lowerList.mkString(" ") + " "* 5 +" Guesses left: " + guesses)
      if (minGuesses == guesses) {
        loss += 1
        println("\nSorry, you lost :( \nThe word was: " + wordList.mkString(""))
      }
    }
    println(usedLetters.mkString(", "))

  }


  /**
   *Asks if player wants to play again
   *Depending on the input it starts a new game or stops
   */

  println("Wins: " +wins+" Losses: " + loss)
  val tryAgain = readLine("\nDo you want to try again? ")

    if (tryAgain.startsWith("Y") || tryAgain.startsWith("y")) {
      guesses = 6
      println("Then let's continue!")
      Game
    }
    else println("Okay, see you next time!")

}


}




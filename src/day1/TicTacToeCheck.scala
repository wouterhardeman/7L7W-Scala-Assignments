package day1

object TicTacToeCheck {
  val p1 = 'X'
  val p2 = 'O'
  val board = List(' ', p1, p2, p1, p1, ' ', p2, p1, p2)
  val winningLines = List((0,1,2), (3,4,5), (6,7,8), (0,3,6), (1,4,7), (2,5,8), (0,4,8), (2,4,6))

  def main(args: Array[String]) {
    print

    if(isWinner(p1)) println(p1 + " wins!")
    else if(isWinner(p2)) println(p2 + " wins!")
    else println("It's a draw!")

  }

  def print() {
    board.grouped(3).foreach(row => println(row(0) + " | "
      + row(1) + " | "
      + row(2)
      + "\n---------"))
  }

  def isWinner(winner : Char) =
    winningLines.exists{case (i,j,k) => board(i) == winner && board(j) == winner && board(k) == winner}

}
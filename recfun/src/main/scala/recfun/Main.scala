package recfun

import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (r <= 0 || c <= 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def isBalanced(chars: List[Char], openBracketIndicator: Int): Boolean = {
      if(chars.isEmpty) openBracketIndicator == 0
      else if (chars.head == '(') isBalanced(chars.tail, openBracketIndicator + 1)
      else if (chars.head == ')') openBracketIndicator > 0 && isBalanced(chars.tail, openBracketIndicator - 1)
      else isBalanced(chars.tail, openBracketIndicator)
    }
    isBalanced(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def count(_money: Int, _coins: List[Int]): Int = {
      if(_money == 0) 1
      else if ((_money < 0) || (_coins.isEmpty && _money>=1 )) 0
      else count(_money, _coins.tail) + count(_money - _coins.head, _coins)
    }
    count(money, coins)
  }
}

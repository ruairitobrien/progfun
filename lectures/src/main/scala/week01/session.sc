object session {
  def abs(x: Double) = if (x < 0) -x else x

  def sqrt(x: Double) = {
    def sqrtIter(guess: Double): Double =
      if (isGoodEnough(guess)) guess
      else sqrtIter(improve(guess))
    def isGoodEnough(guess: Double) =
      abs(guess * guess - x) / x < 0.001
    def improve(guess: Double) =
      (guess + x / guess) / 2
    sqrtIter(1.0)
  }

  def test(x: Int): Int = ???


  sqrt(0.001)
  sqrt(0.1e-20)
  sqrt(1.0e20)
  sqrt(1.0e50)

  def factorial(n: Int): Int =
    if (n==0) 1 else n * factorial(n - 1)

  factorial(4)

  def factorialTailRec(n: Int): Int = {
    def loop(acc: Int, n: Int): Int =
    if(n == 0) acc
      else loop(acc * n, n - 1)
    loop(1, n)
  }

  factorialTailRec(4)

}
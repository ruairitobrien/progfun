package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton s1 contains 1")
      assert(contains(s2, 2), "Singleton s2 contains 2")
      assert(contains(s3, 3), "Singleton s3 contains 3")
      assert(!contains(s1, 2), "Singleton s1 doesn't contain 2")
      assert(!contains(s2, 3), "Singleton s2 doesn't contain 3")
      assert(!contains(s3, 1), "Singleton s3 doesn't contain 1")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val us1 = union(s1, s2)
      val us2 = union(s2, s3)
      val us3 = union(s1, s3)
      assert(contains(us1, 1), "Union 1")
      assert(contains(us1, 2), "Union 2")
      assert(!contains(us1, 3), "Union 3")
      assert(contains(us2, 2), "Union 4")
      assert(contains(us2, 3), "Union 5")
      assert(!contains(us2, 1), "Union 6")
      assert(contains(us3, 1), "Union 7")
      assert(contains(us3, 3), "Union 8")
      assert(!contains(us3, 2), "Union 9")
    }
  }

  test("Intersection contains correct elements") {
    new TestSets {
      val is1 = intersect(s1, s1)
      val is2 = intersect(s2, s2)
      val is3 = intersect(s3, s3)
      assert(contains(is1, 1), "Intersection 1")
      assert(contains(is2, 2), "Intersection 2")
      assert(contains(is3, 3), "Intersection 3")
      assert(!contains(is1, 4), "Intersection 4")
      assert(!contains(is2, 5), "Intersection 5")
      assert(!contains(is3, 6), "Intersection 6")
    }
  }

  test("Diff contains correct elements") {
    new TestSets {
      val ds1 = diff(s1, s2)
      val ds2 = diff(s2, s3)
      val ds3 = diff(s1, s3)
      assert(contains(ds1, 1), "Diff 1")
      assert(!contains(ds1, 2), "Diff 2")
      assert(contains(ds2, 2), "Diff 3")
      assert(!contains(ds2, 3), "Diff 4")
      assert(contains(ds3, 1), "Diff 5")
      assert(!contains(ds3, 3), "Diff 6")
    }
  }

  test("Filter contains correct elements") {
    new TestSets {
      var fs1 = filter(s1, union(s1, s2))
      var fs2 = filter(s2, diff(s2, s3))
      var fs3 = filter(s3, x => x < 3)
      var fs4 = filter(s3, x => x < 4)

      assert(contains(fs1, 1), "Filter 1")
      assert(!contains(fs1, 3), "Filter 2")
      assert(contains(fs2, 2), "Filter 3")
      assert(!contains(fs2, 3), "Filter 4")
      assert(!contains(fs3, 3), "Filter 5")
      assert(contains(fs4, 3), "Filter 6")
    }
  }

  test("forall implemented") {
    new TestSets {
      assert(forall(s1, x => x == 1), "Forall 1")
      assert(forall(s2, x => x == 2), "Forall 2")
      assert(forall(s3, x => x == 3), "Forall 3")
      assert(!forall(s1, x => x == 3), "Forall 4")
      assert(!forall(s2, x => x == 1), "Forall 5")
      assert(!forall(s3, x => x == 2), "Forall 6")
    }
  }

  test("exists implemented") {
    new TestSets {
      assert(exists(s1, x => x == 1), "Exists 1")
      assert(exists(s2, x => x == 2), "Exists 2")
      assert(exists(s3, x => x == 3), "Exists 3")
      assert(!exists(s1, x => x == 3), "Exists 4")
      assert(!exists(s2, x => x == 1), "Exists 5")
      assert(!exists(s3, x => x == 2), "Exists 6")
    }
  }

  test("map implemented") {
    new TestSets {
      val ms1 = map(s1, x => x * 2)
      val ms2 = map(s2, x => x * 2)
      val ms3 = map(s3, x => x * 2)

      assert(contains(ms1, 2), "Map 1")
      assert(contains(ms2, 4), "Map 2")
      assert(contains(ms3, 6), "Map 3")
      assert(!contains(ms1, 1), "Map 4")
      assert(!contains(ms2, 2), "Map 5")
      assert(!contains(ms3, 3), "Map 6")

    }
  }

}

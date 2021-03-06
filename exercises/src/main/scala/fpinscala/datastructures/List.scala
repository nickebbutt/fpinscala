package fpinscala.datastructures

sealed trait List[+A] // `List` data type, parameterized on a type, `A`
case object Nil extends List[Nothing] // A `List` data constructor representing the empty list
case class Cons[+A](head: A, tail: List[A]) extends List[A] // Another data constructor, representing nonempty lists. Note that `tail` is another `List[A]`, which may be `Nil` or another `Cons`.

object List {
  // `List` companion object. Contains functions for creating and working with lists.
  def sum(ints: List[Int]): Int = ints match {
    // A function that uses pattern matching to add up a list of integers
    case Nil => 0 // The sum of the empty list is 0.
    case Cons(x, xs) => x + sum(xs) // The sum of a list starting with `x` is `x` plus the sum of the rest of the list.
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x, xs) => x * product(xs)
  }

  def apply[A](as: A*): List[A] = // Variadic function syntax
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  val x = List(1, 2, 3, 4, 5) match {
    case Cons(x, Cons(2, Cons(4, _))) => x
    case Nil => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t) => h + sum(t)
    case _ => 101
  }

  def append[A](a1: List[A], a2: List[A]): List[A] =
    a1 match {
      case Nil => a2
      case Cons(h, t) => Cons(h, append(t, a2))
    }

  //  def foldRight[A,B](as: List[A], z: B)(f: (A, B) => B): B = // Utility functions
  //    as match {
  //      case Nil => z
  //      case Cons(x, xs) => f(x, foldRight(xs, z)(f))
  //    }

  def sum2(ns: List[Int]) =
    foldRight(ns, 0)((x, y) => x + y)

  def product2(ns: List[Double]) =
    foldRight(ns, 1.0)(_ * _) // `_ * _` is more concise notation for `(x,y) => x * y`; see sidebar


  def tail[A](l: List[A]): List[A] =
    l match {
      case Cons(_, t) => t
      case Nil => sys.error("tail of empty list")
    }

  def setHead[A](l: List[A], h: A): List[A] = {
    Cons(h, tail(l))
  }

  def drop[A](l: List[A], n: Int): List[A] = {
    def doTail(i: Int, l: List[A]): List[A] = {
      if (i == 1) l
      else doTail(i - 1, tail(l))
    }
    doTail(n, l)
  }

  def dropWhile[A](l: List[A], f: A => Boolean): List[A] =
    l match {
      case Cons(h, t) if f(h) => dropWhile(t, f)
      case Nil => Nil
    }

  def init[A](l: List[A]): List[A] = l match {
    case Nil => Nil
    case Cons(_, Nil) => Nil
    case Cons(h, t) => Cons(h, init(t))
  }

  def foldRight[A, B](l: List[A], z: B)(f: (A, B) => B): B = l match {
    case Nil => z
    case Cons(h, t) => f(h, foldRight(t, z)(f))
  }

  def length[A](l: List[A]): Int = {
    foldRight(l, 0)((a: A, b: Int) => b + 1)
  }

  @annotation.tailrec
  def foldLeft[A, B](l: List[A], z: B)(f: (B, A) => B): B = l match {
    case Nil => z
    case Cons(h, t) => foldLeft(t, f(z, h))(f)
  }

  def sumByFold(l: List[Int]): Int = {
    foldLeft(l, 0)(_ + _)
  }

  def productByFold(l: List[Int]): Int = {
    foldLeft(l, 1)(_ * _)
  }

  def reverseByFold[A](l: List[A]): List[A] = {
    foldLeft(l, List[A]())((a, b) => Cons(b, a))
  }

  def append[A](item: A, l: List[A]): List[A] = {
    foldRight(l, List(item))((a: A, b: List[A]) => Cons(a, b))
  }

  //  def map[A,B](l: List[A])(f: A => B): List[B] =
  //    foldRight(l, Nil:List[B])((h,t) => Cons(f(h),t))

  //  def map[A,B](l: List[A])(f: A => B): List[B] = {
  //    foldLeft[A, List[B]](l, Nil) ((listB: List[B], a: A) => Cons(f(a), listB))
  //  }

  def appendViaFoldRight[A](l: List[A], r: List[A]): List[A] =
    foldRight(l, r)(Cons(_, _))

  def concatenateListOfLists[A](l: List[List[A]]): List[A] = {
    foldRight(l, Nil: List[A])(append)
  }

  def doubleToString(l: List[Double]): List[String] = {
    foldRight(l, Nil: List[String])((dbl, strings) => Cons(dbl.toString, strings))
  }

  def addOneToEachElement(l: List[Integer]): List[Integer] = {
    foldRight(l, Nil: List[Integer])((i, results) => Cons(i + 1, results))
  }

  def map[A, B](from: List[A])(f: A => B): List[B] = {
    foldRight(from, Nil: List[B])((a, results) => Cons(f(a), results))
  }

  def filter[A](from: List[A])(f: A => Boolean): List[A] = {
    foldRight(from, Nil: List[A])((a, results) => {
      if (f(a)) Cons(a, results)
      else results
    })
  }

  def flatMap[A, B](from: List[A])(f: A => List[B]): List[B] = {
    foldLeft(from, Nil: List[B])((results, a) => appendViaFoldRight(results, f(a)))
  }

  def concat[A](l: List[List[A]]): List[A] =
    foldRight(l, Nil: List[A])(append)

  /*
This could also be implemented directly using `foldRight`.
*/
  def flatMap2[A, B](l: List[A])(f: A => List[B]): List[B] =
    concat(map(l)(f))


  def addPairwise(a: List[Int], b: List[Int]): List[Int] = (a, b) match {
    case(Cons(h1, t1), Cons(h2, t2)) => Cons(h1 + h2, addPairwise(t1, t2))
    case (Nil, Nil) => Nil
  }

  def zipWith[A,B,C](a: List[A], b: List[B])( combine : (A, B) => C ): List[C] = (a, b) match {
    case(Cons(h1, t1), Cons(h2, t2)) => Cons(combine(h1, h2), zipWith(t1, t2)(combine))
    case (Nil, Nil) => Nil
  }

  def startsWith[A](sup: List[A], sub: List[A]): Boolean = (sup, sub) match {
    case(_, Nil) => true
    //case(Nil, Cons(_,_)) => false
    case(Cons(h1,t1), Cons(h2,t2)) if h1 == h2 => startsWith(t1, t2)
    case _ => false
  }

  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = sup match {
    case(Nil) => false
    case(Cons(_, t)) if startsWith(sup, sub) => true
    case(Cons(_, t)) => hasSubsequence(t, sub)
  }
}
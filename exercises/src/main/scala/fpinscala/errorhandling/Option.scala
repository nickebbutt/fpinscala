package fpinscala.errorhandling

import scala.{Option => _, Some => _, Either => _, _} // hide std library `Option`, `Some` and `Either`, since we are writing our own in this chapter

sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(g) => Some(f(g))
  }

  def getOrElse[B>:A](default: => B): B = this match {
    case None => default
    case Some(v) => v
  }

  def flatMap[B](f: A => Option[B]): Option[B] = this match {
    case None => None
    case Some(g) => f(g)
  }

  def orElse[B>:A](ob: => Option[B]): Option[B] = this match {
    case None => ob
    case Some(g) => this
  }

  def filter(f: A => Boolean): Option[A] = this match {
    case Some(g) if f(g) => this
    case _ => None
  }
}
case class Some[+A](get: A) extends Option[A]
case object None extends Option[Nothing]

object Option {
  def failingFn(i: Int): Int = {
    val y: Int = throw new Exception("fail!") // `val y: Int = ...` declares `y` as having type `Int`, and sets it equal to the right hand side of the `=`.
    try {
      val x = 42 + 5
      x + y
    }
    catch { case e: Exception => 43 } // A `catch` block is just a pattern matching block like the ones we've seen. `case e: Exception` is a pattern that matches any `Exception`, and it binds this value to the identifier `e`. The match returns the value 43.
  }

  def failingFn2(i: Int): Int = {
    try {
      val x = 42 + 5
      x + ((throw new Exception("fail!")): Int) // A thrown Exception can be given any type; here we're annotating it with the type `Int`
    }
    catch { case e: Exception => 43 }
  }

  def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length)

  def variance(xs: Seq[Double]): Option[Double] = {
    mean(xs) flatMap (m => mean(xs.map(a => math.pow(a - m, 2))))
  }

  def lift[A,B](f: A => B): Option[A] => Option[B] = _ map f

  def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = {
    a.flatMap( aa => b.map( bb => f(aa,bb)))
  }

  def sequence[A](a: List[Option[A]]): Option[List[A]] = {
    a.foldRight(Some(Nil:List[A]):Option[List[A]])((a, optionalListOfA) => map2(a, optionalListOfA)(_ :: _))
  }

  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = {
    a.foldRight(Some(Nil:List[B]):Option[List[B]])((a, optionalListOfB) => map2(f(a), optionalListOfB) (_ ::_))
  }

  def sequence2[A](a: List[Option[A]]): Option[List[A]] = {
    traverse(a)(a => a)
  }
}
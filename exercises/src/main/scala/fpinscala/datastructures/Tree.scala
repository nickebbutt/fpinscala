package fpinscala.datastructures

sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]


object Tree {

  def size[A](t: Tree[A]) : Int = t match {
    case Leaf(_) => 1
    case Branch(l: Tree[A], r: Tree[A]) => 1 + size(l) + size(r)
  }

  def fold[A,B](t: Tree[A], identity: B)(f: (A, B) => B) : B = t match {
    case Leaf(a) => f(a, identity)
    case Branch(l, r) => {
      fold(r, fold(l, identity)(f))(f)
    }
  }

  def maximum(t: Tree[Int]) : Int = {
    fold(t, Integer.MIN_VALUE)(_.max(_))
  }

  def toList(t: Tree[Int]) : List[Int] = {
    fold(t, Nil:List[Int])((i : Int, l: List[Int]) => List.append(l, Cons(i, Nil)))
  }




}
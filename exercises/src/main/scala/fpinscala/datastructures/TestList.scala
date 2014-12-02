package fpinscala.datastructures

import org.scalatest.{Matchers, FunSuite, FlatSpec}

/**
 * Created by nick on 03/09/2014.
 */
class TestListFunctions extends FlatSpec with Matchers {

  "a tree with 3 branches and 4 leaves " should " have a size of 7 " in  {
    var t = Branch( Branch(Leaf(1), Leaf(2)), Branch(Leaf(3), Leaf(4)))
    Tree.size(t) should equal ( 7 )
  }

  "a tree with 2 branches and 3 leaves " should " have a size of 5 " in  {
    var t = Branch( Branch(Leaf(1), Leaf(2)), Leaf(3))
    Tree.size(t) should equal ( 5 )
  }

  "a tree with a max leaf value of 7 " should " return a max value 7 " in {
    Tree.maximum(Branch( Branch(Leaf(1), Leaf(7)), Leaf(3))) should equal ( 7 )
  }

  "a tree with 3 leaf nodes " should " be converted to a List with 3 elements " in {
    Tree.toList(Branch( Branch(Leaf(1), Leaf(7)), Leaf(3))) should equal ( List(1,7,3))
  }

}

package fpinscala.datastructures

import org.scalatest.{Matchers, FunSuite, FlatSpec}

/**
 * Created by nick on 03/09/2014.
 */
class TestListFunctions extends FlatSpec with Matchers {

  "init " should " return all the items in a list apart from the last" in  {
    var l = List(1,2,3,4)
    var newList = List.init(l)
    newList should equal ( List(1,2,3) )
  }


  //length
  "length" should " be zero for an empty list" in {
    List.length(List()) should equal (0)
  }

  "length" should " be 3 for a list with three items" in {
    List.length(List(1,2,3)) should equal (3)
  }

  //sum
  "Sum by fold left " should " sum all items in a list" in {
    var l = List(1,2,3,4)
    List.sumByFold(l) should equal ( 10 )
  }

  "Product by fold left " should " multiply all items in a list" in {
    var l = List(1,2,3,4)
    List.productByFold(l) should equal ( 24 )
  }

  "Reverse by fold " should " reverse all items in a list" in {
    var l = List(1,2,3,4)
    List.reverseByFold(l) should equal (List(4,3,2,1))
  }

  "Append " should " add one item to list " in {
    var l = List(1,2,3,4)
    List.append(5, l) should equal (List(1,2,3,4,5))
  }

  "Test concat two lists " should " combine them both " in {
    val lists: List[Int] = List.concatenateListOfLists(List(List(1, 2, 3), List(4, 5, 6)))
    lists should equal (List(1,2,3,4,5,6))
  }

  "Test add one to each element " should " produce a list with incremented values " in {
    List.addOneToEachElement(List(2,4,6)) should equal (List(3, 5, 7))
  }

  "Test map " should " produce a new list of mapped items " in {
     List.map(List(1,2,3)) ( (a : Int) => a.toString ) should equal (List("1", "2", "3"))
  }

  "Test filter " should " remove items matching predicate " in {
    List.filter(List(1,2,3,4,5,6))((a) => (a % 2) == 0) should equal (List(2,4,6))
  }

  "Test flat map " should " flatten and add function results " in {
    val mapped = List.flatMap(List(1, 2, 3, 4, 5, 6))((a) => Cons(a, Cons(a * 2, Nil)))
    mapped should equal (List(1,2,2,4,3,6,4,8,5,10,6,12))
  }

  "Test add pairwise " should " zip together list contents " in {
    val zipped = List.addPairwise(List(1,2,3), List(2,4,6))
    zipped should equal (List(3,6,9))
  }

  "Test zip " should " zip together list contents " in {
    val zipped = List.zipWith(List(1,2,3), List(2,4,6))( _+_ )
    zipped should equal (List(3,6,9))
  }

}

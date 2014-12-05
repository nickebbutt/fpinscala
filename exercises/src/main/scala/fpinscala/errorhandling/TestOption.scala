package fpinscala.errorhandling

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by nick on 03/09/2014.
 */
class TestOption extends FlatSpec with Matchers {

  "option flat map " should " return the alternative option if None" in  {
    Some(1).flatMap( (a : Int) => Some(a + 1) ) should equal ( Some(2))
  }

  "option map " should " return the alternative option if None" in  {
    Some(1).map( (a : Int) => a + 1 ) should equal ( Some(2))
  }

  "option filter " should " return only results where some value passes the criteria " in {
    Some(2).filter( _ < 10) should equal (Some(2))
    Some(11).filter( _ < 10 ) should equal (None)
  }

  "or else " should " return an alternative optional value if the optional is None" in {
    None.orElse(Some(2)) should equal (Some(2))
    Some(1).orElse(Some(3)) should equal (Some(1))
  }

  "get or else " should " return the optional value or an alternative value if the option is None " in {
    None.getOrElse(2) should equal (2)
    Some(1).getOrElse(2) should equal (1)
  }

  "test lift " should " lift a function on ints" in {
    val optionalAbs : Option[Double] => Option[Double] = Option.lift(math.abs(_))
    optionalAbs(Some(1)).getOrElse("wibble") should equal(1)
    optionalAbs(None).getOrElse("wibble") should equal("wibble")
  }

  "test map with 2 options as arguments " should " return a new option only if both inputs are Some " in {
    Option.map2(Some(2), Some(3))(_*_) should equal (Some(6))
    Option.map2(None:Option[Int], Some(3))(_*_) should equal (None)
  }

  "test sequence " should " convert a list of optional values to an optional list " in {
    Option.sequence(Some(1) :: Some(2) :: Some(3) :: Nil) should equal (Some(1 :: 2 :: 3:: Nil))
    Option.sequence(Some(1) :: None :: Some(3) :: Nil) should equal (None)

    Option.sequence2(Some(1) :: Some(2) :: Some(3) :: Nil) should equal (Some(1 :: 2 :: 3:: Nil))
    Option.sequence2(Some(1) :: None :: Some(3) :: Nil) should equal (None)
  }

  
}

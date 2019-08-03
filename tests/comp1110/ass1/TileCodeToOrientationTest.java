package comp1110.ass1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertTrue;

public class TileCodeToOrientationTest {
  @Rule
  public Timeout globalTimeout = Timeout.millis(1000);

  private void test(int in, Direction expected) {
    Direction out = Tile.tileCodeToOrientation(in);
    assertTrue("Expected " + expected + " for input " + in + ", but got " + out + ".", out == expected);
  }

  @Test
  public void testSimple1() {
    test(0, Direction.NORTH);
  }

  @Test
  public void testSimple2() {
    test(1, Direction.EAST);
  }

  @Test
  public void testSimple3() {
    test(2, Direction.SOUTH);
  }

  @Test
  public void testShifted1() {
    test(16, Direction.NORTH);
  }

  @Test
  public void testShifted2() {
    test(35, Direction.WEST);
  }

}

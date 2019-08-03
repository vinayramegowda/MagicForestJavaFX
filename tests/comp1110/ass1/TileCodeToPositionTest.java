package comp1110.ass1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertTrue;

public class TileCodeToPositionTest {
  @Rule
  public Timeout globalTimeout = Timeout.millis(1000);

  private void test(int in, int expected) {
    int out = Tile.tileCodeToPosition(in);
    assertTrue("Expected " + expected + " for input " + in + ", but got " + out + ".", out == expected);
  }


  @Test
  public void testSimple1() {
    test(0, 6);
  }

  @Test
  public void testSimple2() {
    test(20, 13);
  }

  @Test
  public void testSimple3() {
    test(24, 16);
  }

  @Test
  public void testRotated1() {
    test(3, 6);
  }

  @Test
  public void testRotated2() {
    test(35, 18);
  }

}

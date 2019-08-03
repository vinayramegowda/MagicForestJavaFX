package comp1110.ass1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertTrue;

public class GetNeighbourTest {
  @Rule
  public Timeout globalTimeout = Timeout.millis(1000);

  private void test(int in, Direction direction, int expected) {
    int out = Node.getNeighbour(in, direction);
    assertTrue("Expected " + expected + " for input " + in + " and direction " + direction.name() + ".", out == expected);
  }

  @Test
  public void test1() {
    test(6, Direction.NORTH, 1);
  }

  @Test
  public void test2() {
    test(12, Direction.EAST, 13);
  }

  @Test
  public void test3() {
    test(12, Direction.SOUTH, 17);
  }

  @Test
  public void test4() {
    test(11, Direction.WEST, 10);
  }

  @Test
  public void test5() {
    test(22, Direction.SOUTH, -1);
  }
}

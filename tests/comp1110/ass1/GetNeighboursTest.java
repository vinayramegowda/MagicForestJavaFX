package comp1110.ass1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertTrue;

public class GetNeighboursTest {
  @Rule
  public Timeout globalTimeout = Timeout.millis(1000);

  private boolean isInList(int value, int[] list) {
    for (int i = 0; i < list.length; i++) {
      if (list[i] == value)
        return true;
    }
    return false;
  }

  private void test(int in, int[] expected) {
    int[] out = Node.getNeighbours(in);
    assertTrue("Null array returned for input " + in + ".", out != null);
    assertTrue("Wrong number of neighbours.  Expected " + expected.length + " for input " + in + ", but got " + out.length + ".", expected.length == out.length);
    for (int i = 0; i < expected.length; i++) {
      assertTrue("Position " + expected[i] + " was not in neighours of " + in + ", but should have been.", isInList(expected[i], out));
    }
  }

  @Test
  public void test1() {
    int[] expected = {1, 5, 7, 11};
    test(6, expected);
  }

  @Test
  public void test2() {
    int[] expected = {3, 7, 9, 13};
    test(8, expected);
  }

  @Test
  public void test3() {
    int[] expected = {11, 15, 17, 21};
    test(16, expected);
  }

  @Test
  public void test4() {
    int[] expected = {13, 17, 19, 23};
    test(18, expected);
  }

  @Test
  public void test5() {
    int[] expected = {7, 11, 13, 17};
    test(12, expected);
  }

}

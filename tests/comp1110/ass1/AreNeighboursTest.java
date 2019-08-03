package comp1110.ass1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertTrue;

public class AreNeighboursTest {
  @Rule
  public Timeout globalTimeout = Timeout.millis(1000);

  private void test(int a, int b, boolean expected) {
    boolean out = Node.areNeighbours(a, b);
    assertTrue("Expected output " + expected + " for inputs " + a + " and " + b + ", but got " + out + ".", out == expected);
  }

  @Test
  public void test1() {
    test(6, 7, true);
  }

  @Test
  public void test2() {
    test(6, 12, false);
  }

  @Test
  public void test3() {
    test(6, 11, true);
  }

  @Test
  public void test4() {
    test(18, 17, true);
  }

  @Test
  public void test5() {
    test(18, 6, false);
  }
}

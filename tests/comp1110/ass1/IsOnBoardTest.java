package comp1110.ass1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertTrue;

public class IsOnBoardTest {
  @Rule
  public Timeout globalTimeout = Timeout.millis(1000);

  private void test(int in, boolean expected) {
    boolean out = Node.isOnBoard(in);
    assertTrue("Expected " + expected + ", but got " + out + " for input " + in + ".", out == expected);
  }

  @Test
  public void test1() {
    test(0, false);
  }

  @Test
  public void test2() {
    test(24, false);
  }

  @Test
  public void test3() {
    test(19, false);
  }

  @Test
  public void test4() {
    test(18, true);
  }

  @Test
  public void test5() {
    test(11, true);
  }

}

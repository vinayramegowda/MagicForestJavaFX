package comp1110.ass1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertTrue;

public class UpdateAndCheckTest {
  @Rule
  public Timeout globalTimeout = Timeout.millis(1000);

  private void test(int objective, char[] tileState, boolean expected) {
    MagicForest mf = new MagicForest(Objective.OBJECTIVES[objective]);
    boolean out = mf.updateAndCheck(tileState);
    String ts = "{" + ((int) tileState[0]);
    for (int i = 1; i < tileState.length; i++)
      ts += ", " + ((int) tileState[i]);
    ts += "}";

    assertTrue("Check completion returned " + out + ", but expected " + expected + " for objective number " + (objective + 1) + " and tile state " + ts + ".", out == expected);
  }

  @Test
  public void testSimpleIncomplete() {
    char[] ts = {255, 255, 255, 255, 255, 255, 12, 16, 20};
    test(0, ts, false);
  }

  @Test
  public void testSimpleTwo() {
    char[] ts = {0, 4, 8, 32, 20, 16, 12, 24, 28};
    test(0, ts, true);
  }

  @Test
  public void testSimpleThree() {
    char[] ts = {28, 32, 12, 24, 20, 16, 8, 0, 4};
    test(3, ts, true);
  }

  @Test
  public void testThree() {
    char[] ts = {24, 20, 18, 12, 4, 0, 28, 8, 32};
    test(13, ts, true);
  }

  @Test
  public void testNine() {
    char[] ts = {0, 4, 21, 29, 24, 32, 8, 12, 16};
    test(43, ts, false);
  }
}

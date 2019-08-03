package comp1110.ass1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertTrue;

public class IconPairsFromStringTest {
  @Rule
  public Timeout globalTimeout = Timeout.millis(1000);

  private boolean containsPair(Icon[][] list, Icon[] pair) {
    for (int i = 0; i < list.length; i++) {
      if (list[i][0] == pair[0] && list[i][1] == pair[1])
        return true;
      if (list[i][0] == pair[1] && list[i][1] == pair[0])
        return true;
    }
    return false;
  }

  private void doTest(String in, Icon[][] expected) {
    Icon[][] out = Objective.iconPairsFromString(in);
    assertTrue("Null array returned.  Was expecting an array of pairs for input '" + in + "'.", out != null);
    assertTrue("Wrong number of pairs.  Expected " + expected.length + " pairs, but got " + out.length + " for input '" + in + "'.", expected.length == out.length);
    for (int p = 0; p < expected.length; p++) {
      assertTrue("Result does not include the pair [" + expected[p][0].name() + " (" + expected[p][0].getKey() + "), " + expected[p][1].name() + " (" + expected[p][1].getKey() + ")], for input '" + in + "'.", containsPair(out, expected[p]));
    }
  }

  @Test
  public void testSimplePair() {
    Icon[][] expected = {{Icon.FROG, Icon.HAT}};
    doTest("QV", expected);
  }

  @Test
  public void testTwoPairs() {
    Icon[][] expected = {{Icon.SKELETON, Icon.CAT}, {Icon.SKELETON, Icon.BROOM}};
    doTest("YIYU", expected);
    doTest("YUYI", expected);
  }

  @Test
  public void testThreePairs() {
    Icon[][] expected = {{Icon.WITCH, Icon.CAULDRON}, {Icon.WITCH, Icon.PUMPKIN}, {Icon.WITCH, Icon.MUSHROOMS}};
    doTest("RZRWRT", expected);
    doTest("RTRZRW", expected);
  }

  @Test
  public void testFourPairs() {
    Icon[][] expected = {{Icon.HAT, Icon.CAT}, {Icon.HAT, Icon.BROOM}, {Icon.SKELETON, Icon.BROOM}, {Icon.SKELETON, Icon.CAT}};
    doTest("VIVUUYIY", expected);
    doTest("UYIYVIVU", expected);
  }

  @Test
  public void testFivePairs() {
    Icon[][] expected = {{Icon.HAT, Icon.BOOKS}, {Icon.HAT, Icon.PUMPKIN}, {Icon.FROG, Icon.SKELETON}, {Icon.FROG, Icon.BROOM}, {Icon.FROG, Icon.RAVEN}};
    doTest("VSVWQYQUQX", expected);
    doTest("VWVSQYQUQX", expected);
    doTest("QUVWVSQYQX", expected);
  }

}

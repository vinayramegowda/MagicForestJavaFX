package comp1110.ass1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertTrue;

public class GetSolutionsTest {
  @Rule
  public Timeout globalTimeout = Timeout.millis(5000);

  private void checkCount(int problem, int expected) {
    MagicForest mf = new MagicForest(Objective.OBJECTIVES[problem-1]);
    char[][] sol = mf.getSolutions();
    assertTrue("Unexpected number of solutions for problem number "+problem+", expected "+expected+" but got "+sol.length+".", sol.length == expected);
  }

  private String solToStr(char[] sol) {
    String rtn = "{";
    for (int i = 0; i < 9; i++) {
      if (i != 0)
        rtn += ",";
      rtn += " "+((int) sol[i]);
    }
    rtn += " }";
    return rtn;
  }

  private void checkContains(int problem, char[] expected) {
    MagicForest mf = new MagicForest(Objective.OBJECTIVES[problem-1]);
    char[][] sol = mf.getSolutions();
    boolean found = false;
    for (int i = 0; i < sol.length; i++) {
      for (int j = 0; j < 9; j++) {
        if (sol[i][j] != expected[j])
          break;
        else if (j == 8)
          found = true;
      }
    }
    String expectedstr = solToStr(expected);
    assertTrue("Did not find solution "+expectedstr+" in answer for problem number "+problem+".", found);
  }

  @Test
  public void testCount1() {
    checkCount(1, 34872);
  }

  @Test
  public void testCount2() {
    checkCount(2, 21036);
  }

  @Test
  public void testCount44() {
    checkCount(44, 2);
  }

  @Test
  public void testContains15() {
    char[] ex = { 9, 12, 17, 21, 0, 28, 24, 32, 4 };
    checkContains(15, ex);
  }

  @Test
  public void testContains39() {
    char[] ex = { 4, 9, 25, 32, 12, 16, 0, 20, 28 };
    checkContains(39, ex);
  }
}

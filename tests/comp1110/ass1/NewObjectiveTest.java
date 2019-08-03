package comp1110.ass1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.HashSet;

import static org.junit.Assert.assertTrue;

public class NewObjectiveTest {
  @Rule
  public Timeout globalTimeout = Timeout.millis(1000);

  private int getDifficulty(Objective objective) {
    for (int i = 0; i < Objective.OBJECTIVES.length; i++) {
      if (objective == Objective.OBJECTIVES[i])
        return i / 12;
    }
    return -1;
  }

  private int countObjectives(Objective[] objectives) {
    HashSet<Objective> set = new HashSet<>();
    for (Objective o : objectives)
      set.add(o);
    return set.size();
  }

  private void doTest(int difficulty) {
    Objective[] out = new Objective[12];
    for (int i = 0; i < out.length; i++) {
      out[i] = Objective.newObjective(difficulty);
      int diff = getDifficulty(out[i]);
      assertTrue("Expected difficulty " + difficulty + ", but " + (diff == -1 ? "did not get one from the prepared objectives" : "got one of difficulty " + diff) + ": problem number " + out[i].getProblemNumber() + ".", diff == difficulty);
    }
    int unique = countObjectives(out);
    assertTrue("Expected at least 3 different objectives after calling newObjective() 12 times, but only got " + unique + ".", unique >= 3);
  }

  @Test
  public void testStarter() {
    doTest(0);
  }

  @Test
  public void testJunior() {
    doTest(1);
  }

  @Test
  public void testExpert() {
    doTest(2);
  }

  @Test
  public void testMaster() {
    doTest(3);
  }

}

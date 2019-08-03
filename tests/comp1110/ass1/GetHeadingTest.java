package comp1110.ass1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertTrue;

public class GetHeadingTest {
  @Rule
  public Timeout globalTimeout = Timeout.millis(1000);
  private void test(int src, int dst, Direction expected) {
    Direction out = Node.getHeading(src, dst);
    assertTrue("Expected " + expected.name() + ", but got " + out.name() + " for movement from " + src + " to " + dst + ".", out == expected);
  }

  @Test
  public void test1() {
    test(11, 6, Direction.NORTH);
  }

  @Test
  public void test2() {
    test(11, 16, Direction.SOUTH);
  }

  @Test
  public void test3() {
    test(13, 12, Direction.WEST);
  }

  @Test
  public void test4() {
    test(11, 12, Direction.EAST);
  }

  @Test
  public void test5() {
    test(12, 17, Direction.SOUTH);
  }
}

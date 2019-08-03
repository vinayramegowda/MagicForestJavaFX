package comp1110.ass1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertTrue;

public class NextPositionTest {
  @Rule
  public Timeout globalTimeout = Timeout.millis(1000);

  private boolean isInList(int value, int[] list) {
    for (int i = 0; i < list.length; i++) {
      if (list[i] == value)
        return true;
    }
    return false;
  }

  private void test(TileType tt, int position, int from, Direction orientation, int[] expected) {
    int[] out = tt.nextPositions(position, from, orientation);
    assertTrue("Got a null array when calling nextPositions(" + position + ", " + from + ", " + orientation + ").", out != null);
    assertTrue("Expected " + expected.length + " positions, but got " + out.length + " when calling nextPositions(" + position + ", " + from + ", " + orientation + ").", out.length == expected.length);
    for (int i = 0; i < expected.length; i++) {
      assertTrue("Did not find " + expected[i] + " in answer when calling nextPositions(" + position + ", " + from + ", " + orientation + ").", isInList(expected[i], out));
    }
  }

  @Test
  public void testBridge() {
    int[] expected = {7};
    test(TileType.BRIDGE, 12, 17, Direction.NORTH, expected);
  }

  @Test
  public void testBridgeRotated() {
    int[] expected = {13};
    test(TileType.BRIDGE, 12, 11, Direction.EAST, expected);
  }

  @Test
  public void testIntersection() {
    int[] expected = {2, 6, 8};
    test(TileType.INTERSECTION, 7, 12, Direction.EAST, expected);
  }

  @Test
  public void testCurve() {
    int[] expected = {10};
    test(TileType.CURVE, 11, 16, Direction.NORTH, expected);
  }

  @Test
  public void testCurveRotated() {
    int[] expected = {10};
    test(TileType.CURVE, 11, 6, Direction.EAST, expected);
  }

}

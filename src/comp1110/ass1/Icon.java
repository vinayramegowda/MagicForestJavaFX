package comp1110.ass1;

/**
 * This class represents the 11 icons that make up the game.
 *
 * 10 of the icons are fixed, and one (the CAT) can move.   The fixed
 * icons are located at fixed positions and are permanently connected
 * to either one or two other positions.   The MUSHROOMS and PUMPKIN
 * icons are connected to two positions, while the other 8 fixed icons
 * are only connected to one.
 */
public enum Icon {
  CAT('I'),
  FROG('Q', 5, 6),
  WITCH('R', 10, 11),
  BOOKS('S', 15, 16),
  MUSHROOMS('T', 21, 16, 17),
  BROOM('U', 23, 18),
  HAT('V', 19, 18),
  PUMPKIN('W', 9, 8, 13),
  RAVEN('X', 3, 8),
  SKELETON('Y', 2, 7),
  CAULDRON('Z', 1, 6);

  private final char key;          // The char used to encode this icon
  private final int position;      // The position of this icon, or -1 if fixed
  private final int[] connections; // The fixed connections for this icon (or null, in the case of the CAT)

  /**
   * Constructor for CAT, which does not have a fixed position
   *
   * @param key The character key (which must be 'I')
   */
  Icon(char key) {
    assert key == 'I';
    this.key = key;
    this.position = -1;
    this.connections = null;
  }

  /**
   * Constructor for any of the eight the fixed icons with just one connection.
   *
   * @param key      The character key for the icon
   * @param position The (fixed) position of the icon
   * @param a        The position to which the icon is connected
   */
  Icon(char key, int position, int a) {
    assert key == 'Q' || key == 'R' || key == 'S' || key == 'U' || key == 'V' || key == 'X' || key == 'Y' || key == 'Z';
    this.key = key;
    this.position = position;
    this.connections = new int[1];
    this.connections[0] = a;
  }

  /**
   * Constructor for the MUSHROOMS and PUMPKIN, which have two connections.
   *
   * @param key      The character key for the icon
   * @param position The (fixed) position of the icon
   * @param a        A position to which the icon is connected
   * @param b        A position to which the icon is connected
   */
  Icon(char key, int position, int a, int b) {
    assert key == 'T' || key == 'W';
    this.key = key;
    this.position = position;
    this.connections = new int[2];
    this.connections[0] = a;
    this.connections[1] = b;
  }

  /** @return the character key for this icon */
  char getKey() {
    return key;
  }

  /** @return the position for this icon */
  int getPosition() {
    return position;
  }

  /** @return the set of fixed connections for this icon */
  int[] getFixedConnections() {
    assert this != CAT;
    return connections;
  }

  /** @return true if this is a fixed icon */
  boolean isFixed() {
    return this != CAT;
  }

  /**
   * Get an Icon, given a character key
   * @param key the character corresponding to the icon desired.
   * @return an icon given the character key
   */
  static Icon fromKey(char key) {
    assert key == 'I' || (key >= 'Q' && key <= 'Z');
    if (key == 'I')
      return CAT;
    else
      return values()[1 + key - 'Q'];
  }

  /**
   * Determine the set of next positions for a fixed icon.   Only MUSHROOMS
   * and PUMPKIN have a next position.   The other fixed icons are dead
   * ends, so they return null.
   *
   * @param from The position that the path is coming from
   * @return The next position given arrival at the icon from the position from.
   */
  int[] nextPosition(int from) {
    assert Node.isOnBoard(from);
    assert this != CAT;
    if (this == MUSHROOMS || this == PUMPKIN) {
      /* these have just two connections, therefore we can only go on place: the place we did not come from */
      int[] rtn = new int[1];
      if (from == connections[0])
        rtn[0] = connections[1];
      else
        rtn[0] = connections[0];
      return rtn;
    } else
      /* all the other icons are dead ends */
      return null;
  }
}

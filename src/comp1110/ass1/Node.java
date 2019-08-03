package comp1110.ass1;

/**
 * This class represents a node.
 * <p>
 * Nodes consist of the ten fixed nodes (the ten icons on the perimeter of the
 * board), and nine tiles (the nine tiles that may be placed in the center of
 * board.
 * <p>
 * The 25 positions with their position numbers:
 * <p>
 *   0  1  2  3  4
 *   5  6  7  8  9
 *   10 11 12 13 14
 *   15 16 17 18 19
 *   20 21 22 23 24
 * <p>
 * Tiles may be placed in any of the nine positions depicted below:
 * <p>
 * <p>
 *   6  7  8
 *   11 12 13
 *   16 17 18
 * <p>
 * <p>
 * The 10 fixed icons are located at positions 1, 2, 3, 5, 9, 10, 15, 19, 21 and 23.
 * The four corner positions (0, 4, 20, 24) are unoccupied, as are positions 14 and
 * 22.   The following diagram illustrates the locations of the 10 fixed icons,
 * which have character codes 'Q' ... 'Z' (see the Icon class for more info).
 * Permanently unoccupied positions are marked '.'.
 * <p>
 *   .  Z  Y  X  .
 *   Q  6  7  8  W
 *   R 11 12 13  .
 *   S 16 17 18  V
 *   .  T  .  U  .
 */
public class Node {
  private int[] reachable; // The list of positions of all icons reachable from this node
  Icon icon;               // The icon associated with this node, if any
  Tile tile;               // The tile associated with this node, if any

  /**
   * Construct a node from a fixed icon (any icon except the cat, which is also a tile)
   *
   * @param icon The icon associated with this node (any fixed icon)
   */
  Node(Icon icon) {
    assert !(icon == null || icon == Icon.CAT);
    this.icon = icon;
  }

  /**
   * Construct a node for a tile at a particular position
   *
   * @param tileID   The tile ID for the tile (0 .. 8)
   * @param tileCode An encoding of the tile's location
   */
  Node(int tileID, int tileCode) {
    this.tile = new Tile(tileID, tileCode);
    if (tile.isCat()) // the cat is both an icon and a tile
      this.icon = Icon.CAT;
  }

  /**
   * @return the position (0 .. 24) of this tile.
   */
  int getPosition() {
    assert tile != null || icon != null;

    if (tile != null)
      return tile.getPosition();
    else
      return icon.getPosition();
  }

  /**
   * The start of a path.
   * <p>
   * Find the set positions that can be reached from this node.
   * This applies to icons only (since paths can only start at
   * icons).
   *
   * @return An array of positions which this node is connected to.
   */
  int[] nextPosition() {
    assert icon != null;  // can only start at an icon node

    if (tile != null)    // the CAT is the only one that is both an icon and a tile
      return getNeighbours(tile.getPosition());
    else
      return icon.getFixedConnections();
  }

  /**
   * The next step in a path.
   * <p>
   * Find the set of positions that can be reached from this node,
   * having arrived from position 'from'.
   *
   * @param from The position from which the node was visited
   * @return An array of positions to which this node can move next.
   */
  int[] nextPosition(int from) {
    if (tile != null)
      return tile.nextPositions(from);
    else
      return icon.nextPosition(from);
  }

  /**
   * Return true if the position is on the central board, where
   * tiles may be placed.   Tiles may be placed in the following
   * positions:
   * <p>
   *   6  7  8
   *   11 12 13
   *   16 17 18
   *
   * @param position A position (0 .. 24)
   * @return true if the position is one of the nine central positions.
   */
  static boolean isOnBoard(int position) {
    assert position >= 0 && position <= 24;
    boolean result=false;
    int positionArray[][]={{6,7,8},{11,12,13},{16,17,18}};
    int rows=3,columns=3;
    for (int i=0;i<rows;i++)
    {
      for(int j=0;j<columns;j++) {
        if (position ==positionArray[i][j]) {
            result = true;
        }
      }}
    return result; // TODO Task 5
  }

  /**
   * Return the four neighbours of a given position.  The input position must be
   * on the board:
   * <p>
   *   6  7  8
   *   11 12 13
   *   16 17 18
   * <p>
   * Neighbours are defined to be adjacent positions in the four
   * cardinal directions (NORTH, EAST, SOUTH, WEST).
   *
   * @param position The position whose neighbours are to be returned
   * @return An array of integers reflecting the neighbours of the given position
   */
  static int[] getNeighbours(int position) {
    assert isOnBoard(position);
    int[][] array={{0,1,2,3,4},{5,6,7,8,9},{10,11,12,13,14},{15,16,17,18,19},{20,21,22,23,24}};
    int rows=5,columns=5;
    int[] neighbours={0,0,0,0};
    int index=0;
    for (int i=0;i<rows;i++)
    {
      for(int j=0;j<columns;j++)
      {
        if(array[i][j]==position){
         // if(i-1<0||i+1>rows || j-1<0||j+1>columns)

          neighbours[index]=array[i-1][j];
          index++;
          neighbours[index]=array[i+1][j];
          index++;

              neighbours[index] = array[i][j - 1];
          index++;

              neighbours[index] = array[i][j + 1];
          index++;
          break;
        }
      }
    }
    return neighbours;  // TODO Task 7
  }

  /**
   * Get the neighbour of a position in a particular direction, and
   * return -1 if the answer is off the board.   For example, if the
   * position was 1 and the direction was EAST, the neighbour would be
   * 2, but if the direction was SOUTH, the neighbour would be 6, and
   * if the direction was NORTH, the result would be -1 (off the board).
   *
   * @param position  The reference position
   * @param direction The direction of the neighbour
   * @return The position of the neighbour in the given direction or -1 if it is off the board.
   */
  static int getNeighbour(int position, Direction direction) {
    assert position >= 0 && position <= 24;
    int[][] array={{0,1,2,3,4},{5,6,7,8,9},{10,11,12,13,14},{15,16,17,18,19},{20,21,22,23,24}};
    int neighbour=0;
    int rows=5,columns=5;
    for (int i=0;i<rows;i++)
    {
      for(int j=0;j<columns;j++) {
        if(array[i][j]==position){
        if (i - 1 >= 0 && i + 1 < rows && j - 1 >= 0 && j + 1 < columns)
          {
          if (direction == Direction.SOUTH) {

            neighbour = array[i + 1][j];
          } else if (direction == Direction.NORTH)

            neighbour = array[i - 1][j];
          else if (direction == Direction.EAST)

            neighbour = array[i][j + 1];
          else if (direction == Direction.WEST)

            neighbour = array[i][j - 1];
        }
        else
          neighbour= -1;
      }}
    }
    return neighbour; // TODO Task 8
  }

  /**
   * Return true if positions a and b are neighbours, meaning that
   * they can be reached by one horizontal or one vertical move on
   * the board.
   *
   * @param a A position
   * @param b Another position
   * @return true if the positions are neighbours
   */
  static boolean areNeighbours(int a, int b) {
    boolean result=false;
    int positionArray[][]={{6,7,8},{11,12,13},{16,17,18}};
    int rows=3,columns=3;
    for (int i=0;i<rows;i++) {
      for (int j = 0; j < columns; j++) {
        if(a==positionArray[i][j]) {
            if(i - 1 >= 0 )
              if (b == positionArray[i - 1][j])
                result=true;
            if(i + 1 < rows )
              if (b == positionArray[i + 1][j])
                result=true;
            if(j + 1 < columns)
              if ( b == positionArray[i][j + 1])
                result=true;
            if(j - 1 >= 0 )
              if (b == positionArray[i][j - 1])
                result=true;
        }
      }
    }
    return result; // TODO Task 9
  }

  /**
   * Get the direction required to move between neighboring positions
   * src and dst.   For example, if src is 12 and dst is 7, then the
   * direction is NORTH, but if the src is 7 and dst is 12 then direction
   * is SOUTH.
   *
   * @param src The source position (where the movement is from)
   * @param dst The destination position (where the move is to)
   * @return direction
   */
  static Direction getHeading(int src, int dst) {
    assert areNeighbours(src, dst);
    Direction direction= Direction.NORTH;
    if(src-dst==5)
      direction=Direction.NORTH;
    else if (src-dst==-5)
      direction=Direction.SOUTH;
    else if(src-dst==1)
      direction=Direction.WEST;
    else if(src-dst==-1)
      direction=Direction.EAST;
    return direction;// TODO Task 10
  }

  /**
   * Set the list of positions of all icons reachable from this node
   *
   * @param reachable A list of positions of icons reachable from this node
   */
  public void setReachable(int[] reachable) {
    assert this.icon != null;
    this.reachable = reachable;
  }

  /**
   * Determine whether the given icon is reachable from this node (where
   * this node is an icon).
   *
   * @param other The node whose reachability needs to be determined.
   * @return true if this node is connected to other
   */
  public boolean isConnected(Node other) {
    assert this.icon != null && other.icon != null;
    if (reachable != null) {
      for (int i : reachable) {
        if (i == other.getPosition())
          return true;
      }
    }
    return false;
  }
}

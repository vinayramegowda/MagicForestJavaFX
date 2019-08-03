package comp1110.ass1;

/**
 * This class encapsulates the nine tiles in the Magic Forest game.
 */
public class Tile {

  private TileType tileType;
  private Direction orientation;
  private int tileID;
  private int position;

  /**
   * Constructor for a tile.
   *
   * @param tileID   The tile ID, a number from 0 ... 8 which encodes which tile
   *                 is being constructed.  0..3 are the curve tiles (A, B, C, D).
   *                 4 and 5 are the bridge tiles (E, F), 6 and 7 are the plain
   *                 intersection tiles (G, H), and 8 is the CAT tile (I).
   * @param tileCode The tile code encodes the placement of the tile, encoding
   *                 the position and orientation (as described below).
   *                 <p>
   *                 The tile code encodes the tile index and tile rotation as follows:
   *                 <p>
   *                 tilecode = tilerotation + (4 * tileindex)
   *                 <p>
   *                 where tilerotation is a number 0..4 representing orientations
   *                 NORTH (0), EAST (1), SOUTH (2), and WEST (3), and tileindex is a
   *                 number 0..8 representing which of the nine positions a tile
   *                 may be placed in.  Tile position 0 corresponds to board position 6,
   *                 while tile position 4 corresponds to board position 12.
   *                 <p>
   *                 tile index:
   *                 0  1  2
   *                 3  4  5
   *                 6  7  8
   *                 <p>
   *                 board positions:
   *                 6  7  8
   *                 11 12 13
   *                 16 17 18
   */
  public Tile(int tileID, int tileCode) {
    this.tileID = tileID;
    position = tileCodeToPosition(tileCode);
    orientation = tileCodeToOrientation(tileCode);
    tileType = TileType.fromTileID(tileID);
  }

  /**
   * Return the set of possible next positions if encountering this tile
   * from position 'from'.  The next positions depend on the shape and
   * orientation of the tile.   For bridge tiles, the orientation does
   * not matter---the next tile will be the same regardless.   For curve
   * tiles, the orientation will determine which tile is next.  For both
   * curve and bridge tiles, there is only one possible next position.
   * However, for intersection tiles, there are three possible next positions
   * (four minus the position 'from').
   *
   * @param from The position from which the path as come.
   * @return An array of possible next positions.
   */
  public int[] nextPositions(int from) {
    return tileType.nextPositions(position, from, orientation);
  }


  /**
   * Given a tile code, decode the tile's rotation, using the description
   * of tile encoding above.
   * <p>
   * For example, if the tile code is 0, then the function should return NORTH.
   * If the tile code is 1, then the function should return EAST.  If the tile
   * code is 7, then the function should return WEST etc.
   *
   * @param tileCode The encoded tile rotation and position
   * @return the corresponding direction of rotation (where NORTH is upright).
   */
  static Direction tileCodeToOrientation(int tileCode) {
    Direction direction = null;
    if (tileCode % 2 == 0 && tileCode % 4 == 0) {
      direction = Direction.NORTH;
    } else if (tileCode % 2 == 0 && tileCode % 4 != 0) {
      direction = Direction.SOUTH;
    }
    for (int index = 1; index < 37; index = index + 4) {
      if (index == tileCode) {
        direction = direction.EAST;
      }
    }
    for (int index = 3; index < 37; index = index + 4) {
      if (index == tileCode) {
        direction = direction.WEST;
      }
    }
      return direction;  // TODO Task 2
    }


  /**
   * Given a tile code, decode the tile's position, using the description
   * of tile encoding above.
   * <p>
   * For example, if the tile code is 3, then the tile index is 0 (3/4),
   * which represents the top left tile index, which is position 6, so the
   * function should return 6.   If the tile code were 33, then the tile
   * index is 8 (33/4), which represents the bottom right tile index, which
   * is position 18, so the function should return 18.
   *
   * @param tileCode The encoded tile rotation and position
   * @return the corresponding board position.
   */
  static int tileCodeToPosition(int tileCode) {
    int position=-1;
    if(tileCode/4==0)
    {position=6;}
    if(tileCode/4==1)
    {position=7;}
    if(tileCode/4==2)
    {position=8;}
    if(tileCode/4==3)
    {position=11;}
    if(tileCode/4==4)
    {position=12;}
    if(tileCode/4==5)
    {position=13;}
    if(tileCode/4==6)
    {position=16;}
    if(tileCode/4==7)
    {position=17;}
    if(tileCode/4==8)
    {position=18;}
    return position;   // TODO Task 3
  }

  /**
   * @return the position
   */
  public int getPosition() {
    return position;
  }

  /**
   * @return true if this tile is the CAT
   */
  public boolean isCat() {
    return tileID == 8;
  }
}


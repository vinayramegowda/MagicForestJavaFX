package comp1110.ass1;

/**
 * This enum represents the three kinds of tiles (curves, bridges and intersections),
 * and implements some of their behavior.
 */
public enum TileType {
  CURVE,
  BRIDGE,
  INTERSECTION;

  /**
   * Determine the set of next positions upon reaching a given tile.
   * <p>
   * Curve tiles will return a set of size one, containing the neighbour
   * that is connected to the neighbour last visited.   This will
   * depend on the orientation of the curve tile.   For example, if a
   * curve tile was at position 18 in the upright (NORTH) orientation
   * and was visited from position 13, then the array { 19 } should be
   * returned since the destination would be 19.   On the other hand,
   * if the tile were rotated 90 degrees, in the EAST orientation, then
   * the array { 17 } would be returned, since the destination would be
   * 17.
   * <p>
   * Bridge tiles will return a set of size one, containing the
   * neighbour that is connected to the neighbour last visited. This
   * does not depend on the orientation of the bridge tile.   For
   * example, if a bridge tile was at position 11, and was visited from
   * position 16, then the array { 6 } would be returned, since the
   * desitnation would be 6.   On the other hand, if it were visited
   * from 10, then the array { 12 } would be returned.
   * <p>
   * Intersection tiles will return a set of size three containing
   * all of their neighbours excluding the tile that this visit came
   * from.   For example, if an intersection tile was at position 6,
   * and was visited from position 11, then this function would return
   * the array { 1, 5, 7} since they are the three direct neighbours
   * of 6 excluding 11.
   *
   * @param position
   * @param from
   * @param orientation
   * @return
   */
  //private final String tileType;
  //private TileType(String tileType) { this.tileType = tileType; }
  //public String  getValue(){return tileType;}get indexed og=f position and form and then comapre the values dats all
  int[] nextPositions(int position, int from, Direction orientation) {
    int[][] array={{0,1,2,3,4},{5,6,7,8,9},{10,11,12,13,14},{15,16,17,18,19},{20,21,22,23,24}};
    int rows=5,columns=5;
    int i,j;
    int positionI=0,positionJ=0;
    int fromI=0,fromJ=0;
    int a[]=new int[0];

    for (i = 0; i < rows; i++) {
      for (j = 0; j < columns; j++) {
        if (position == array[i][j] ) {
          positionI = i;
          positionJ = j;
        }
        if(from==array[i][j]){
          fromI=i;
          fromJ=j;
        }}}

    switch (this) {
      case BRIDGE:
        a=new int[1];
        if (fromI == positionI + 1 && fromJ==positionJ ) {
          if(positionI-1>=0)
          a[0] = array[positionI - 1][positionJ];
        } else if (fromI == positionI - 1 && fromJ==positionJ) {
          if(positionI+1<rows)
          a[0] = array[positionI + 1][positionJ];
        } else if ((fromJ == positionJ + 1 && fromI==i)) {
          if(positionJ-1>=0)
          a[0] = array[positionI][positionJ - 1];
        } else if ((fromJ == positionJ - 1 && fromI==positionI)) {
          if(positionJ+1<columns)
          a[0] = array[positionI][positionJ + 1];
        }break;
      case INTERSECTION:
        a=new int[3];
        if (fromI == positionI + 1 && fromJ==positionJ ){
          if(positionI-1>=0 && positionI + 1 < rows && positionJ - 1 >= 0 && positionJ - 1 >= 0 && positionJ + 1 < columns ){
            a[0] = array[positionI - 1][positionJ];
            a[1] = array[positionI][positionJ + 1];
            a[2] = array[positionI][positionJ - 1];
          }}
          else if (fromI == positionI - 1 && fromJ==positionJ) {
            if (positionI-1>=0 && positionI + 1 < rows && positionJ - 1 >= 0 && positionJ - 1 >= 0 && positionJ + 1 < columns ) {
              a[0] = array[positionI + 1][positionJ];
              a[1] = array[positionI][positionJ - 1];
              a[2] = array[positionI][positionJ + 1];
            }
          }
          else if ((fromJ == positionJ + 1 && fromI==i)) {
            if (positionI-1>=0 && positionI + 1 < rows && positionJ - 1 >= 0 && positionJ - 1 >= 0 && positionJ + 1 < columns ){
              a[0] = array[positionI + 1][positionJ];
              a[1] = array[positionI - 1][positionJ];
              a[2] = array[positionI][positionJ - 1];
            }
          }
          else if ((fromJ == positionJ - 1 && fromI==positionI)) {
            if (positionI-1>=0 && positionI + 1 < rows && positionJ - 1 >= 0 && positionJ - 1 >= 0 && positionJ + 1 < columns ){
              a[0] = array[positionI + 1][positionJ];
              a[1] = array[positionI - 1][positionJ];
              a[2] = array[positionI][positionJ + 1];
            }
          } break;
      case CURVE:
        a=new int[1];
        if(orientation==Direction.NORTH || orientation==Direction.SOUTH)
        {
          if(fromI==positionI-1)
            a[0]=array[positionI][positionJ+1];
          else if(fromJ==positionJ+1)
            a[0]=array[positionI-1][positionJ];
          else if(fromI==positionI+1)
            a[0]=array[positionI][positionJ-1];
          else if(fromJ==positionJ-1)
            a[0]=array[positionI+1][positionJ];
        }
        else
        {
          if(fromI==positionI-1)
            a[0]=array[positionI][positionJ-1];
          else if(fromJ==positionJ+1)
            a[0]=array[positionI-1][positionJ];
          else if(fromI==positionI+1)
            a[0]=array[positionI][positionJ+1];
          else if(fromJ==positionJ-1)
            a[0]=array[positionI+1][positionJ];
        }break;
    }
    return a;
  }

     // TODO Task 11


  /**
   * Given a tile ID, return the correct TileType
   *
   * @param id The tile ID, a number from 0 ... 8.
   * @return The TileType corresponding to the id.
   */
  static TileType fromTileID(int id) {
    if (id >= 0 && id <= 3)
      return TileType.CURVE;
    else if (id >= 4 && id <= 5)
      return TileType.BRIDGE;
    else if (id >= 6 && id <= 8)
      return TileType.INTERSECTION;
    else
      assert false;
    return null;
  }
}

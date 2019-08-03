package comp1110.ass1;

/**
 * This class represents a game of 'magicForest', which is based directly on a children's game
 * from 'SmartGames' called "Magic Forest"
 * <p>
 * http://www.smartgames.eu/en/smartgames/magic-forest
 * <p>
 * The class and the those that it refer to provide the core logic of
 * the game, which is used by the GUI, which runs the game in a window.
 * <p>
 * The game uses the following encodings to represent game state.
 */

public class MagicForest {

  /*
   * The game is modeled using an array of 25 Node objects (some of which will be null).
   *
   * See the documentation in the Node class to understand why there are 25 elements in
   * the array.
   *
   * The array is initialized by the initializeNodeGraph() method, below, which is called
   * when the game is created.
   */
  Node[] nodes;

  /* The objective represents the problem to be solved in this instance of the game. */
  private Objective objective;


  /**
   * Construct a game with a given objective, and initialize the node graph.
   *
   * @param objective The objective of this game.
   */
  MagicForest(Objective objective) {
    this.objective = objective;
    initializeNodeGraph();
  }


  /**
   * Construct a game for a given level of difficulty.   This creates a new instance
   * of the game at the given level of difficulty.   It chooses a new objective and then
   * initializes the node graph.
   *
   * @param difficulty The difficulty of the game.
   */
  public MagicForest(int difficulty) {
    this(Objective.newObjective(difficulty));
  }


  /**
   * Initialize the node graph, creating the structure and inserting the ten fixed
   * icons.   This is only called once, at the beginning of the program, from the
   * constructor.
   * <p>
   * The 10 fixed icons are located at positions 1, 2, 3, 5, 9, 10, 15, 19, 21 and 23.
   * The four corner positions (0, 4, 20, 24) are unoccupied, as are positions 14 and
   * 22.
   * <p>
   * The following diagram illustrates the locations of the 10 fixed icons,
   * which have character codes 'Q' ... 'Z' (see the Icon class for more info).
   * Permanently unoccupied positions are marked '.'.
   * <p>
   *   .  Z  Y  X  .
   *   Q  6  7  8  W
   *   R 11 12 13  .
   *   S 16 17 18  V
   *   .  T  .  U  .
   * <p>
   * Note that the CAT icon ('I') is not placed.   This is because it is
   * not a fixed icon.  Only the ten fixed icons are placed.
   * <p>
   * See the Node class for more information.
   */
  private void initializeNodeGraph() {
    nodes = new Node[25];
    for (Icon icon : Icon.values()) {
      if (icon.isFixed()) {
        nodes[icon.getPosition()] = new Node(icon);
      }
    }
    nodes[14] = nodes[9];  // pumpkin is also linked from 14 (because pumpkin effectively occupies both 9 and 14)
    nodes[22] = nodes[21]; // mushrooms are also linked from 22 (because mushrooms effectively occupies both 22 and 23)
  }

  /**
   * Update the game with a new tile state (a new placement of tiles), and return true
   * if the game is complete.
   *
   * @param tileState A new placement of tiles, represented as an array of nine
   *                  chars, where each element represents the state of one of
   *                  the nine tiles, and is either Game.NOT_PLACED, or is an encoding
   *                  of the placement of that tile (see the constructor for Tile for
   *                  a full explanation of the tile encoding).   The index into the
   *                  tileState array corresponds to the tile ID.  Thus elements 0 .. 3
   *                  of the tileState array correspond to the four curved tiles,
   *                  'A' .. 'D', element 8 is tile 'I' (the CAT), etc.
   * @return true if the game is complete, false otherwise
   */
  public boolean updateAndCheck(char[] tileState) {
    if (update(tileState))
      return checkCompletion();
    else
      return false;
  }

  /**
   * Take a tileState array and update the board array, returning
   * true if all tiles are placed.
   *
   * @param tileState An array of char representing the placement of each of the
   *                  9 tiles (see above and the constructor for Tile for more
   *                  information).   Each element corresponds to a particular
   *                  tile.  Element 0 corresponds to tile 0, element 8 corresponds
   *                  to tile 8.   So, for example, element 8 indicates the location
   *                  of the CAT tile (tile number 8, 'I').
   * @return true if all tiles are placed
   */
  public boolean update(char[] tileState) {
    assert tileState.length == 9;

    return false; // TODO Task 12
  }

  /**
   * @return true if the current board state completes the objective
   */
  public boolean checkCompletion() {
    return false; // TODO Task 12
  }

  /**
   * Return the set of all solutions to the current objective.
   */
  public char[][] getSolutions() {
    char[][] aSillySolution = {{ 1, 8, 13, 16, 24, 28, 20, 32, 4}};
    return aSillySolution; // TODO Task 13
  }

  /**
   * Return a tileState string that reflects a good solution to the current
   * objective, given the current state of the board.
   *
   * @return a tileState string that reflects a good solution to the current
   * objective given the current state of the board.
   */
  public char[] getSolution() {
    return getSolutions()[0]; // TODO Task 14
  }

  /**
   * @return the problem number for the current objective (a number from 1 .. 48,
   * corresponding to the 48 sample problems in the original game.
   */
  public int getObjectiveNumber() {
    return objective.getProblemNumber();
  }

}

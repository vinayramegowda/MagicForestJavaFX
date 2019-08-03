package comp1110.ass1.gui;

import comp1110.ass1.MagicForest;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class Game extends Application {

  /* board layout */
  private static final int SQUARE_SIZE = 120;
  private static final int TILES_PANEL_SIZE = 3 * SQUARE_SIZE;
  private static final int MAIN_PANEL_SIZE = 5 * SQUARE_SIZE;
  private static final int MARGIN_X = 40;
  private static final int MAIN_PANEL_X = MARGIN_X + TILES_PANEL_SIZE;
  private static final int BOARD_X = MARGIN_X + TILES_PANEL_SIZE + SQUARE_SIZE;
  private static final int MARGIN_Y = SQUARE_SIZE;
  private static final int BOARD_Y = MARGIN_Y;
  private static final int GAME_WIDTH = TILES_PANEL_SIZE + MAIN_PANEL_SIZE + MARGIN_X;
  private static final int GAME_HEIGHT = MAIN_PANEL_SIZE;

  /* where to find media assets */
  private static final String URI_BASE = "assets/";
  private static final String BASEBOARD_URI = Game.class.getResource(URI_BASE + "baseboard.png").toString();

  /* Loop in public domain CC 0 http://www.freesound.org/people/oceanictrancer/sounds/211684/ */
  private static final String LOOP_URI = Game.class.getResource(URI_BASE + "211684__oceanictrancer__classic-house-loop-128-bpm.wav").toString();
  private AudioClip loop;

  /* game variables */
  private boolean loopPlaying = false;

  /* marker for unplaced tiles */
  public static final char NOT_PLACED = 255;

  /* node groups */
  private final Group root = new Group();
  private final Group tiles = new Group();
  private final Group solution = new Group();
  private final Group board = new Group();
  private final Group controls = new Group();
  private final Group exposed = new Group();
  private final Group objective = new Group();

  /* the difficulty slider */
  private final Slider difficulty = new Slider();

  /* message on completion */
  private final Text completionText = new Text("Well done!");

  /* the state of the tiles */
  char[] tileState = new char[9];   //  all off screen to begin with

  /* The underlying game */
  MagicForest magicForest;

  /* Define a drop shadow effect that we will appy to tiles */
  private static DropShadow dropShadow;

  /* Static initializer to initialize dropShadow */
  {
    dropShadow = new DropShadow();
    dropShadow.setOffsetX(2.0);
    dropShadow.setOffsetY(2.0);
    dropShadow.setColor(Color.color(0, 0, 0, .4));
  }

  /**
   * An inner class that represents tiles used in the game.
   * Each of these is a visual representation of an underlying tile.
   */
  class Tile extends ImageView {
    int tile;

    /**
     * Construct a particular playing tile
     *
     * @param tile The letter representing the tile to be created.
     */
    Tile(char tile) {
      if (tile >= 'J') {
        throw new IllegalArgumentException("Bad tile: \"" + tile + "\"");
      }
      setImage(new Image(Game.class.getResource(URI_BASE + tile + ".png").toString()));
      this.tile = tile - 'A';
      setFitHeight(SQUARE_SIZE);
      setFitWidth(SQUARE_SIZE);
      setEffect(dropShadow);
    }

    /**
     * Constructor used to place the objective tile.
     *
     * @param tile The tile to be displayed (one of 48 objectives)
     * @param x The x position of the tile
     * @param y The y position of the tile
     */
    Tile(int tile, int x, int y) {
      if (!(tile <= 48 && tile >= 0)) {
        throw new IllegalArgumentException("Bad tile: \"" + tile + "\"");
      }

      String t = String.format("%02d", tile);
      setImage(new Image(Game.class.getResource(URI_BASE + t + ".png").toString()));
      this.tile = tile;
      setFitHeight(SQUARE_SIZE);
      setFitWidth(SQUARE_SIZE);
      setEffect(dropShadow);

      setLayoutX(x);
      setLayoutY(y);
    }
  }

  /**
   * This class extends Tile with the capacity for it to be dragged and dropped,
   * and snap-to-grid.
   */
  class DraggableTile extends Tile {
    int homeX, homeY;           // the position in the window where the mask should be when not on the board
    double mouseX, mouseY;      // the last known mouse positions (used when dragging)


    /**
     * Construct a draggable tile
     *
     * @param tile The tile identifier ('A' - 'I')
     */
    DraggableTile(char tile) {
      super(tile);
      tileState[tile - 'A'] = NOT_PLACED; // start out off board
      homeX = MARGIN_X / 2 + ((tile - 'A') % 3) * SQUARE_SIZE;
      setLayoutX(homeX);
      homeY = (int) (1.5 * MARGIN_Y) + ((tile - 'A') / 3) * SQUARE_SIZE;
      setLayoutY(homeY);

      /* event handlers */
      setOnScroll(event -> {            // scroll to change orientation
        hideCompletion();
        rotate();
        event.consume();
      });
      setOnMousePressed(event -> {      // mouse press indicates begin of drag
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
      });
      setOnMouseDragged(event -> {      // mouse is being dragged
        hideCompletion();
        toFront();
        double movementX = event.getSceneX() - mouseX;
        double movementY = event.getSceneY() - mouseY;
        setLayoutX(getLayoutX() + movementX);
        setLayoutY(getLayoutY() + movementY);
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
        event.consume();
      });
      setOnMouseReleased(event -> {     // drag is complete
        snapToGrid();
      });
    }


    /**
     * Snap the tile to the nearest grid position (if it is over the grid)
     */
    private void snapToGrid() {

      if (onBoard() && (!alreadyOccupied())) {
        if ((getLayoutX() >= (BOARD_X - (SQUARE_SIZE / 2))) && (getLayoutX() < (BOARD_X + (SQUARE_SIZE / 2)))) {
          setLayoutX(BOARD_X);
        } else if ((getLayoutX() >= BOARD_X + (SQUARE_SIZE / 2)) && (getLayoutX() < BOARD_X + 1.5 * SQUARE_SIZE)) {
          setLayoutX(BOARD_X + SQUARE_SIZE);
        } else if ((getLayoutX() >= BOARD_X + 1.5 * SQUARE_SIZE) && (getLayoutX() < BOARD_X + 2.5 * SQUARE_SIZE)) {
          setLayoutX(BOARD_X + 2 * SQUARE_SIZE);
        }

        if ((getLayoutY() >= (BOARD_Y - (SQUARE_SIZE / 2))) && (getLayoutY() < (BOARD_Y + (SQUARE_SIZE / 2)))) {
          setLayoutY(BOARD_Y);
        } else if ((getLayoutY() >= BOARD_Y + (SQUARE_SIZE / 2)) && (getLayoutY() < BOARD_Y + 1.5 * SQUARE_SIZE)) {
          setLayoutY(BOARD_Y + SQUARE_SIZE);
        } else if ((getLayoutY() >= BOARD_Y + 1.5 * SQUARE_SIZE) && (getLayoutY() < BOARD_Y + 2.5 * SQUARE_SIZE)) {
          setLayoutY(BOARD_Y + 2 * SQUARE_SIZE);
        }
        setPosition();
      } else {
        snapToHome();
      }
      updateAndCheck();
    }


    /**
     * @return true if the mask is on the board
     */
    private boolean onBoard() {
      return getLayoutX() > (BOARD_X - (SQUARE_SIZE / 2)) && (getLayoutX() < (BOARD_X + 2.5 * SQUARE_SIZE))
              && getLayoutY() > (MARGIN_Y - (SQUARE_SIZE / 2)) && (getLayoutY() < (MARGIN_Y + 2.5 * SQUARE_SIZE));
    }


    /**
     * a function to check whether the current destination cell is already occupied by another tile
     *
     * @return true if the destination cell for the current tile is already occupied, and false otherwise
     */
    private boolean alreadyOccupied() {
      int x = (int) (getLayoutX() + (SQUARE_SIZE / 2) - BOARD_X) / SQUARE_SIZE;
      int y = (int) (getLayoutY() + (SQUARE_SIZE / 2) - BOARD_Y) / SQUARE_SIZE;
      int idx = y * 3 + x;

      for (int i = 0; i < 9; i++) {
        int tIdx = tileState[i] / 4;
        if (tIdx == idx)
          return true;
      }
      return false;
    }


    /**
     * Snap the mask to its home position (if it is not on the grid)
     */
    private void snapToHome() {
      setLayoutX(homeX);
      setLayoutY(homeY);
      setRotate(0);
      tileState[tile] = NOT_PLACED;
    }


    /**
     * Rotate the tile by 90 degrees (unless this is mask zero and there is a constraint on mask zero)
     */
    private void rotate() {
      setRotate((getRotate() + 90) % 360);
      setPosition();
      updateAndCheck();
    }


    /**
     * Determine the grid-position of the origin of the tile
     * or -1 if it is off the grid, taking into account its rotation.
     */
    private void setPosition() {
      int x = (int) (getLayoutX() - BOARD_X) / SQUARE_SIZE;
      int y = (int) (getLayoutY() - BOARD_Y) / SQUARE_SIZE;
      int rotate = (int) getRotate() / 90;
      if (x < 0)
        tileState[tile] = NOT_PLACED;
      else {
        char val = (char) ((y * 3 + x) * 4 + rotate);
        tileState[tile] = val;
      }
    }


    /**
     * @return the mask placement represented as a string
     */
    public String toString() {
      return "" + tileState[tile];
    }
  }

  /**
   * Set up event handlers for the main game
   *
   * @param scene The Scene used by the game.
   */
  private void setUpHandlers(Scene scene) {
    /* create handlers for key press and release events */
    scene.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.M) {
        toggleSoundLoop();
        event.consume();
      } else if (event.getCode() == KeyCode.Q) {
        Platform.exit();
        event.consume();
      } else if (event.getCode() == KeyCode.SLASH) {
        solution.setOpacity(1.0);
        tiles.setOpacity(0);
        event.consume();
      }
    });
    scene.setOnKeyReleased(event -> {
      if (event.getCode() == KeyCode.SLASH) {
        solution.setOpacity(0);
        tiles.setOpacity(1.0);
        event.consume();
      }
    });
  }


  /**
   * Set up the sound loop (to play when the 'M' key is pressed)
   */
  private void setUpSoundLoop() {
    try {
      loop = new AudioClip(LOOP_URI);
      loop.setCycleCount(AudioClip.INDEFINITE);
    } catch (Exception e) {
      System.err.println(":-( something bad happened (" + LOOP_URI + "): " + e);
    }
  }


  /**
   * Turn the sound loop on or off
   */
  private void toggleSoundLoop() {
    if (loopPlaying)
      loop.stop();
    else
      loop.play();
    loopPlaying = !loopPlaying;
  }

  /**
   * Set up the group that represents the solution (and make it transparent)
   *
   * @param solution The solution as an array of chars.
   */
  private void makeSolution(char[] solution) {
    this.solution.getChildren().clear();
    if (solution == null) return;

    if (solution.length != 9) {
      throw new IllegalArgumentException("Solution incorrect length: " + solution);
    }
    for (int i = 0; i < solution.length; i++) {
      Tile tile = new Tile((char) (i + 'A'));
      int x = (solution[i] / 4) % 3;
      int y = (solution[i] / 4) / 3;
      int rotation = solution[i] % 4;

      tile.setLayoutX(BOARD_X + (x * SQUARE_SIZE));
      tile.setLayoutY(BOARD_Y + (y * SQUARE_SIZE));
      tile.setRotate(90 * rotation);

      this.solution.getChildren().add(tile);
    }
    this.solution.setOpacity(0);
  }


  /**
   * Set up the group that represents the places that make the board
   */
  private void makeBoard() {
    board.getChildren().clear();

    ImageView baseboard = new ImageView();
    baseboard.setImage(new Image(BASEBOARD_URI));
    baseboard.setFitWidth(MAIN_PANEL_SIZE);
    baseboard.setFitHeight(MAIN_PANEL_SIZE);
    baseboard.setLayoutX(MAIN_PANEL_X);
    board.getChildren().add(baseboard);

    board.toBack();
  }


  /**
   * Set up each of the nine tiles
   */
  private void makeTiles() {
    tiles.getChildren().clear();
    for (char m = 'A'; m <= 'I'; m++) {
      tiles.getChildren().add(new DraggableTile(m));
    }
  }


  /**
   * Add the objective to the board
   */
  private void addObjectiveToBoard() {
    objective.getChildren().clear();
    objective.getChildren().add(new Tile(magicForest.getObjectiveNumber(), MARGIN_X + SQUARE_SIZE, MARGIN_Y / 4));
  }


  /**
   * Check game completion and update status
   */
  private void updateAndCheck() {
    boolean finished = magicForest.updateAndCheck(tileState);
    if (finished == false) return;
    else {
      showCompletion();
    }
  }


  /**
   * Put all of the tiles back in their home position
   */
  private void resetPieces() {
    tiles.toFront();
    for (Node n : tiles.getChildren()) {
      ((DraggableTile) n).snapToHome();
    }
  }


  /**
   * Create the controls that allow the game to be restarted and the difficulty
   * level set.
   */
  private void makeControls() {
    Button button = new Button("Restart");
    button.setLayoutX(GAME_WIDTH / 4 + 30);
    button.setLayoutY(GAME_HEIGHT - 45);
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        newGame();
      }
    });
    controls.getChildren().add(button);

    difficulty.setMin(1);
    difficulty.setMax(4);
    difficulty.setValue(0);
    difficulty.setShowTickLabels(true);
    difficulty.setShowTickMarks(true);
    difficulty.setMajorTickUnit(1);
    difficulty.setMinorTickCount(1);
    difficulty.setSnapToTicks(true);

    difficulty.setLayoutX(GAME_WIDTH / 4 - 140);
    difficulty.setLayoutY(GAME_HEIGHT - 40);
    controls.getChildren().add(difficulty);

    final Label difficultyCaption = new Label("Difficulty:");
    difficultyCaption.setTextFill(Color.GREY);
    difficultyCaption.setLayoutX(GAME_WIDTH / 4 - 210);
    difficultyCaption.setLayoutY(GAME_HEIGHT - 40);
    controls.getChildren().add(difficultyCaption);
  }


  /**
   * Create the message to be displayed when the player completes the puzzle.
   */
  private void makeCompletion() {
    completionText.setFill(Color.BLACK);
    completionText.setEffect(dropShadow);
    completionText.setCache(true);
    completionText.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 80));
    completionText.setLayoutX(20);
    completionText.setLayoutY(375);
    completionText.setTextAlignment(TextAlignment.CENTER);
    root.getChildren().add(completionText);
  }


  /**
   * Show the completion message
   */
  private void showCompletion() {
    completionText.toFront();
    completionText.setOpacity(1);
  }


  /**
   * Hide the completion message
   */
  private void hideCompletion() {
    completionText.toBack();
    completionText.setOpacity(0);
  }


  /**
   * Start a new game, resetting everything as necessary
   */
  private void newGame() {
    try {
      hideCompletion();
      magicForest = new MagicForest((int) difficulty.getValue() - 1);
      char[] sol = magicForest.getSolution();
      makeSolution(sol);
      makeTiles();
      addObjectiveToBoard();
    } catch (IllegalArgumentException e) {
      System.err.println("Uh oh. " + e);
      e.printStackTrace();
      Platform.exit();
    }
    resetPieces();
  }


  @Override
  public void start(Stage primaryStage) {

    primaryStage.setTitle("Magic Forest");
    Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT);
    root.getChildren().add(tiles);
    root.getChildren().add(board);
    root.getChildren().add(solution);
    root.getChildren().add(controls);
    root.getChildren().add(exposed);
    root.getChildren().add(objective);

    setUpHandlers(scene);
    setUpSoundLoop();
    makeBoard();
    makeControls();
    makeCompletion();

    newGame();

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
package algo.src;

import com.google.protobuf.ByteString;
import dab.DotsAndBoxes;
import netcode.Netcode;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

// This class does not (and should not) handle server communication (sending moves, etc..)
public class Playfield {

    private int width = 0;
    private int height = 0;

    private int playerAPoints = 0;
    private int playerBPoints = 0;

    private int fixedLines = 0;
    private int maxLines = 0;

    private BoardValue boardValue = null;   // TODO: NOTE: EXPERIMENTAL! Do not rely on this for algorithm

    private CurrentPlayer currentPlayer; // Player who is allowed to play next half move

    boolean[][] horizontalGaps;
    boolean[][] verticalGaps;

    List<HalfMove> movesPlayed = null; // list of all half moves played starting at 0 (first halfmove)

    /*
    e.g.: 3x2 (width x height) boxes field

    horizontalGaps.length == 3 == height + 1
    horizontalGaps[i].length == 3 == width

    verticalGaps.length == 4 == width + 1
    verticalGaps[0].length == 2 == height

    //TODO: Figure out how to address the gaps before making changes

                    verticalRow
                        v
   horizontalRow ->     +  +--+  +      horizontalGaps[0][1] == true
                                 |      horizontalGaps[1][0] == true
                        +--+  +  +      horizontalGaps[2][2] == true
                        |               verticalGaps[0][1] == true
                        +  +  +--+      verticalGaps[3][0] == true

                    All other false
     */


    public enum CurrentPlayer {
        PLAYER_A,
        PLAYER_B
    }

    public enum GameResult {
        WIN_PLAYER_A,
        WIN_PLAYER_B,
        DRAW
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public CurrentPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public int getPlayerAPoints() {
        return playerAPoints;
    }

    public int getPlayerBPoints() {
        return playerBPoints;
    }

    public BoardValue getBoardValue() {
        return boardValue;
    }

    public List<HalfMove> getMovesPlayed() {
        return movesPlayed;
    }

    private Playfield(int width_, int height_, CurrentPlayer startingPlayer) {
        width = width_;
        height = height_;
        currentPlayer = startingPlayer;
        horizontalGaps = new boolean[height_ + 1][width_];
        verticalGaps = new boolean[width_ + 1][height_];

        maxLines = ((width_ + 1) * height_) + (width_ * (height_ + 1));

        // Initialize gaps with false
        for (boolean[] horizontalGap : horizontalGaps) {
            Arrays.fill(horizontalGap, false);
        }

        for (boolean[] verticalGap : verticalGaps) {
            Arrays.fill(verticalGap, false);
        }

        movesPlayed = new Vector<>();

        calculateBoardValue(); // all values will be 0
    }

    // Copy constructor
    private Playfield(Playfield toCopyFrom) {
        if(toCopyFrom == null) {
            return;
        }

        width = toCopyFrom.width;
        height = toCopyFrom.height;
        currentPlayer = toCopyFrom.currentPlayer;
        playerAPoints = toCopyFrom.playerAPoints;
        playerBPoints = toCopyFrom.playerBPoints;
        fixedLines = toCopyFrom.fixedLines;
        maxLines = toCopyFrom.maxLines;
        boardValue = toCopyFrom.boardValue;

        horizontalGaps = new boolean[height + 1][width];
        verticalGaps = new boolean[width + 1][height];

        for(int i = 0; i < horizontalGaps.length; i++) {
            for(int j = 0; j < horizontalGaps[i].length; j++) {
                horizontalGaps[i][j] = toCopyFrom.horizontalGaps[i][j];
            }
        }
        for(int i = 0; i < verticalGaps.length; i++) {
            for(int j = 0; j < verticalGaps[i].length; j++) {
                verticalGaps[i][j] = toCopyFrom.verticalGaps[i][j];
            }
        }

        movesPlayed = new Vector<>();
        movesPlayed.addAll(toCopyFrom.movesPlayed);
    }

    // Used to initialize a new playfield.
    // Returns a playfield if everything went well otherwise returns null
    public static Playfield initPlayfield(int width_, int height_,CurrentPlayer startingPlayer) {
        if(width_ < 1) {
            System.out.println("Width must be larger than 0");
            return null;
        }
        else if(width_ > 10) {
            System.out.println("Width must be smaller than 11");
            return null;
        }
        else if(height_ < 1) {
            System.out.println("Height must be larger than 0");
            return null;
        }
        else if(height_ > 10) {
            System.out.println("Height must be smaller than 11");
            return null;
        }
        else if(startingPlayer == null) {
            System.out.println("Starting player must be defined");
            return null;
        }

        return new Playfield(width_, height_, startingPlayer);
    }

    // Prints the whole playfield into the console
    public void printPlayfield() {
        for(int i = 0; i <= height; i++) {
            for(int j = 0; j < width; j++) {
                System.out.print("+");
                if(horizontalGaps[i][j]) {
                    System.out.print("--");
                }
                else {
                    System.out.print("  ");
                }
            }
            System.out.print("+\n");

            if(i >= height) {
                break;
            }
            for(int j = 0; j <= width; j++) {
                if(verticalGaps[j][i]) {
                    System.out.print("|");
                }
                else {
                    System.out.print(" ");
                }
                if(j == width) {
                    System.out.print("\n");
                }
                else {
                    System.out.print("  ");
                }
            }
        }
        System.out.println();
    }

    // Checks if a half move is legal and bypasses the player check
    public boolean isHalfMoveValid(HalfMove move) {
        return isHalfMoveValid(move, false);
    }

    public boolean isHalfMoveValid(HalfMove move, boolean checkCurrentPlayerMatch) {
        if(move == null) {
            return false;
        }

        if(checkCurrentPlayerMatch) {
            if(currentPlayer != move.getPlayer()){
                System.out.println("Move invalid: player mismatch");
                return false;
            }
        }

        if(fixedLines >= maxLines) {
            // Game is over
            return false;
        }

        // Check if line is already in place
        if(move.isVertical()) {
            if(verticalGaps[move.getColumnIndex()][move.getGapIndex()]) {
                return false;
            }
        }
        else {
            if(horizontalGaps[move.getColumnIndex()][move.getGapIndex()]) {
                return false;
            }
        }
        return true;
    }

    // Plays a half move if legal and returns the player playing the next half move
    // You should check if the game is over before sending moves
    // Returns null if illegal move was tried
    public CurrentPlayer playHalfMove(HalfMove move) {
        if(!isHalfMoveValid(move, true)) {
            System.out.println("Tried to place invalid line");
            return null;
        }

        fixedLines++;

        if(move.isVertical()) {
            verticalGaps[move.getColumnIndex()][move.getGapIndex()] = true;
        }
        else {
            horizontalGaps[move.getColumnIndex()][move.getGapIndex()] = true;
        }

        int points = doesMoveCloseABox(move);

        if(currentPlayer == CurrentPlayer.PLAYER_A) {
            playerAPoints += points;
        }
        else {
            playerBPoints += points;
        }

        movesPlayed.add(move);

        if(points == 0) {
            // player did not manage to close a box so the other player is next
            changePlayer();
        }

        calculateBoardValue();

        System.out.println(move.getPlayer() + " player move: " + move.getOrientation()
                + " on column: " + move.getColumnIndex() + " and gap: " + move.getGapIndex());

        printPlayfield();

        return currentPlayer;
    }

    // Checks if a move will close a box and returns the number of points that move would generate
    // 0 == does not close box, 1 == closes 1 box, 2 == closes 2 boxes
    public int doesMoveCloseABox(HalfMove move) {
        if(move.isVertical()) {
            int verticalColumnIndex = move.getColumnIndex();
            int verticalGapIndex = move.getGapIndex();

            if(verticalColumnIndex == 0) {
                // on far left column so we only check box to the right
                if(verticalGaps[verticalColumnIndex + 1][verticalGapIndex]
                        && horizontalGaps[verticalGapIndex][verticalColumnIndex]
                        && horizontalGaps[verticalGapIndex + 1][verticalColumnIndex]) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
            else if(verticalColumnIndex == width) {
                // on far right column so we only check box to the left
                if(verticalGaps[verticalColumnIndex - 1][verticalGapIndex]
                        && horizontalGaps[verticalGapIndex][verticalColumnIndex - 1]
                        && horizontalGaps[verticalGapIndex + 1][verticalColumnIndex - 1]) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
            else {
                // somewhere in the middle so we check both boxes
                int boxesClosed = 0;

                // check box to the left of line
                if(verticalGaps[verticalColumnIndex - 1][verticalGapIndex]
                        && horizontalGaps[verticalGapIndex][verticalColumnIndex - 1]
                        && horizontalGaps[verticalGapIndex + 1][verticalColumnIndex - 1]) {
                    boxesClosed++;
                }

                // check box to the right of line
                if(verticalGaps[verticalColumnIndex + 1][verticalGapIndex]
                        && horizontalGaps[verticalGapIndex][verticalColumnIndex]
                        && horizontalGaps[verticalGapIndex + 1][verticalColumnIndex]) {
                    boxesClosed++;
                }

                return boxesClosed;
            }
        }
        else {
            // horizontal

            int horizontalColumnIndex = move.getColumnIndex();
            int horizontalGapIndex = move.getGapIndex();

            if(horizontalColumnIndex == 0) {
                // on top column so we only check box below
                if(horizontalGaps[horizontalColumnIndex + 1][horizontalGapIndex]
                        && verticalGaps[horizontalGapIndex][horizontalColumnIndex]
                        && verticalGaps[horizontalGapIndex + 1][horizontalColumnIndex]) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
            else if(horizontalColumnIndex == height) {
                // on bottom column so we only check box above
                if(horizontalGaps[horizontalColumnIndex - 1][horizontalGapIndex]
                        && verticalGaps[horizontalGapIndex][horizontalColumnIndex - 1]
                        && verticalGaps[horizontalGapIndex + 1][horizontalColumnIndex - 1]) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
            else {
                // somewhere in middle so we check both boxes
                int boxesClosed = 0;

                // check box above
                if(horizontalGaps[horizontalColumnIndex - 1][horizontalGapIndex]
                        && verticalGaps[horizontalGapIndex][horizontalColumnIndex - 1]
                        && verticalGaps[horizontalGapIndex + 1][horizontalColumnIndex - 1]) {
                    boxesClosed++;
                }

                // check box below
                if(horizontalGaps[horizontalColumnIndex + 1][horizontalGapIndex]
                        && verticalGaps[horizontalGapIndex][horizontalColumnIndex]
                        && verticalGaps[horizontalGapIndex + 1][horizontalColumnIndex]) {
                    boxesClosed++;
                }

                return boxesClosed;
            }
        }
    }

    // Changes the currently playing player to the other
    private void changePlayer() {
        if(currentPlayer == CurrentPlayer.PLAYER_A) {
            currentPlayer = CurrentPlayer.PLAYER_B;
        }
        else {
            currentPlayer = CurrentPlayer.PLAYER_A;
        }
    }

    public boolean isGameOver() {
        return fixedLines >= maxLines;
    }

    public void printStatus() {
        System.out.println("Width: " + width + ", Height: " + height);
        System.out.println("Player A points: " + playerAPoints);
        System.out.println("Player B points: " + playerBPoints);
        System.out.println("Current player: " + currentPlayer.toString());
        System.out.println("Total number of lines(half moves): " + maxLines);
        System.out.println("Current number of lines(half moves): " + fixedLines);
        System.out.println("Half moves remaining: " + (maxLines - fixedLines));
        System.out.println("Possible total closing half moves: " + boardValue.closingMoves);
        System.out.println("Of those that can double close: " + boardValue.doubleCloseMoves);
    }

    private void calculateBoardValue() {
        BoardValue currentBoardValue = new BoardValue();

        // check all horizontal lines
        for(int i = 0; i <= height; i++) {
            for(int j = 0; j < width; j++) {
                HalfMove move = HalfMove.newHalfMove(i, j, HalfMove.LineOrientation.HORIZONTAL);
                if(move == null) {
                    continue;
                }
                if(!horizontalGaps[i][j]) {
                    int points = doesMoveCloseABox(move);
                    if(points == 1) {
                        currentBoardValue.closingMoves++;
                        currentBoardValue.singleCloseMoves++;
                    }
                    else if(points == 2) {
                        currentBoardValue.closingMoves++;
                        currentBoardValue.doubleCloseMoves++;
                    }
                }
            }
        }

        for(int i = 0; i <= width; i++) {
            for(int j = 0; j < height; j++) {
                HalfMove move = HalfMove.newHalfMove(i, j, HalfMove.LineOrientation.VERTICAL);
                if(move == null) {
                    continue;
                }
                if(!verticalGaps[i][j]) {
                    int points = doesMoveCloseABox(move);
                    if(points == 1) {
                        currentBoardValue.closingMoves++;
                        currentBoardValue.singleCloseMoves++;
                    }
                    else if(points == 2) {
                        currentBoardValue.closingMoves++;
                        currentBoardValue.doubleCloseMoves++;
                    }
                }
            }
        }

        boardValue = currentBoardValue;
    }

    // Returns an index for a flattened 1d array that is equivalent to the 2d board array
    public int TwoDimIndexToOneDimIndex(int columnIndex, int gapIndex, HalfMove.LineOrientation orientation) {
        int index = -1;
        if(orientation == HalfMove.LineOrientation.VERTICAL) {
            index = columnIndex * height + gapIndex;
        }
        else {
            index = columnIndex * width + gapIndex;
        }
        return index;
    }

    // Returns an array of 2 indices {column, gap} that is equivalent an index of a flattened 1d array
    public int[] OneDimIndexToTwoDimIndex(int index, HalfMove.LineOrientation orientation) {
        int columnIndex = -1;
        int gapIndex = -1;
        if(orientation == HalfMove.LineOrientation.VERTICAL) {
            gapIndex = index % height;
            columnIndex = (index - gapIndex) / height;
        }
        else {
            gapIndex = index % width;
            columnIndex = (index - gapIndex) / width;
        }
        int[] retvalue = new int[2];
        retvalue[0] = columnIndex;
        retvalue[1] = gapIndex;
        return retvalue;
    }

    // Plays the turns of the opponent since the last update (possibly no moves if nothing happened)
    // If it is your turn afterwards returns true otherwise false
    public boolean playOpponentMoves(DotsAndBoxes.GameState dabGameState) {

        byte[] horizontalLines = dabGameState.getHorizontalLines().toByteArray();
        byte[] verticalLines = dabGameState.getVerticalLines().toByteArray();

        // Stores indices of new moves
        Vector<Integer> newHorizontalLines = new Vector<>();
        Vector<Integer> newVerticalLines = new Vector<>();

        // Find differences
        for(int i = 0; i < horizontalLines.length; i++) {
            if(!horizontalGaps[i / width][i % width] && horizontalLines[i] == 1) {
                newHorizontalLines.add(i);
            }
        }
        for(int i = 0; i < verticalLines.length; i++) {
            if(!verticalGaps[i / height][i % height] && verticalLines[i] == 1) {
                newVerticalLines.add(i);
            }
        }

        // Create half moves for all new moves
        Vector<HalfMove> halfMoves = new Vector<>();

        for(Integer integer : newHorizontalLines) {
            halfMoves.add(HalfMove.newHalfMove(integer / width, integer % width, HalfMove.LineOrientation.HORIZONTAL, currentPlayer));
        }
        for(Integer integer : newVerticalLines) {
            halfMoves.add(HalfMove.newHalfMove(integer / height, integer % height, HalfMove.LineOrientation.VERTICAL, currentPlayer));
        }

        CurrentPlayer startPlayer = currentPlayer;

        // Plays the half moves
        // Order of moves LIKELY!!! does not matter
        // TODO: Check the likely part
        while(!halfMoves.isEmpty()) {
            for(HalfMove halfMove : halfMoves) {
                if(isHalfMoveValid(halfMove)) {
                    playHalfMove(halfMove);
                    halfMoves.remove(halfMove);
                    break;
                }
            }
        }

        if(startPlayer == currentPlayer) {
            return false;
        }
        else {
            return true;
        }
    }

    // Returns the winner of the game if it is over otherwise null
    public GameResult getWinner() {
        if(!isGameOver()){
            return null;
        }

        if(playerAPoints > playerBPoints) {
            return GameResult.WIN_PLAYER_A;
        }
        else if(playerBPoints > playerAPoints) {
            return GameResult.WIN_PLAYER_B;
        }
        else {
            return GameResult.DRAW;
        }
    }

    // Returns a deep copy of the given playfield
    public Playfield getCopyOfPlayfield(Playfield toCopyFrom) {
        if(toCopyFrom == null) {
            return null;
        }
        else {
            return new Playfield(toCopyFrom);
        }
    }

}

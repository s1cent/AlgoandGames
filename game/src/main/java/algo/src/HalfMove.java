package algo.src;

public class HalfMove {

    private Playfield.CurrentPlayer player = null; // Player who plays this move
    private int columnIndex = -1;
    private int gapIndex = -1;
    private LineOrientation orientation = null;

    public enum LineOrientation {
        VERTICAL,
        HORIZONTAL
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getGapIndex() {
        return gapIndex;
    }

    public LineOrientation getOrientation() {
        return orientation;
    }

    public Playfield.CurrentPlayer getPlayer() {
        return player;
    }

    public void setPlayer(Playfield.CurrentPlayer player_ ) {
        player = player_;
    }
    public void setOrientation(LineOrientation orientation_) {
        orientation = orientation_;
    }

    public boolean isVertical() {
        return orientation == LineOrientation.VERTICAL;
    }

    private HalfMove(int columnIndex_, int gapIndex_, LineOrientation orientation_) {
        columnIndex = columnIndex_;
        gapIndex = gapIndex_;
        orientation = orientation_;
        player = Playfield.CurrentPlayer.PLAYER_A;
    }

    private HalfMove(int columnIndex_, int gapIndex_, LineOrientation orientation_, Playfield.CurrentPlayer player_) {
        columnIndex = columnIndex_;
        gapIndex = gapIndex_;
        orientation = orientation_;
        player = player_;
    }

    private static boolean checkMove(int columnIndex_, int gapIndex_, LineOrientation orientation_) {
        if(columnIndex_ < 0 || gapIndex_ < 0 || orientation_ == null) {
            return false;
        }
        else {
            return true;
        }
    }

    // Creates and returns a new half move if successful
    // Returns null otherwise
    public static HalfMove newHalfMove(int columnIndex_, int gapIndex_, LineOrientation orientation_) {
        if(!checkMove(columnIndex_, gapIndex_, orientation_)) {
            System.out.println("Tried to create illegal HalfMove!");
            return null;
        }

        return new HalfMove(columnIndex_, gapIndex_, orientation_);
    }

    public static HalfMove newHalfMove(int columnIndex_, int gapIndex_, LineOrientation orientation_, Playfield.CurrentPlayer player_) {
        if(!checkMove(columnIndex_, gapIndex_, orientation_)) {
            System.out.println("Tried to create illegal HalfMove! (indices)");
            return null;
        }
        else if(player_ == null) {
            System.out.println("Tried to create illegal HalfMove! (player)");
            return null;
        }

        return new HalfMove(columnIndex_, gapIndex_, orientation_, player_);
    }

    // Checks if the move are the same ignoring player playing it
    public static boolean areMovesEquivalent(HalfMove move1, HalfMove move2) {
        if(move1 == move2) {
            return true;
        }
        if(move1.getGapIndex() != move2.getGapIndex()) {
            return false;
        }
        if(move1.getColumnIndex() != move2.getColumnIndex()) {
            return false;
        }
        if(move1.getOrientation() != move2.getOrientation()) {
            return false;
        }
        return true;
    }

    public void print() {
        System.out.println("Move: " + player + ", " + orientation + ", on column: " + columnIndex + " and gap: " + gapIndex);
    }

    public static HalfMove copyHalfMove(HalfMove move) {
        return new HalfMove(move.columnIndex, move.gapIndex, move.orientation, move.player);
    }

}

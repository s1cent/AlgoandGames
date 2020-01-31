package algo.src;

public class FutureMove {
    private HalfMove halfMove;
    private int value;

    public int getValue() {
        return value;
    }

    public HalfMove getHalfMove() {
        return halfMove;
    }

    public FutureMove(HalfMove move, int value_) {
        halfMove = HalfMove.newHalfMove(move.getColumnIndex(), move.getGapIndex(), move.getOrientation(), move.getPlayer());
        value = value_;
    }

    public FutureMove(int value_) {
        halfMove = null;
        value = value_;
    }
}

package algo.src;

import java.util.Comparator;

public class FutureMove{
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

class FutureMoveComparator implements Comparator<FutureMove> {
    @Override
    public int compare(FutureMove firstMove, FutureMove secondMove) {
        return (firstMove.getValue() - secondMove.getValue());
    }
}

package algo.src;

import java.util.ArrayList;
import java.util.List;



public class FutureMoveList {

    public static FutureMoveList root = new FutureMoveList(-1, null);
    private static int exploredDepth = -1;

    private int depth;
    private FutureMove move;
    private List<FutureMoveList> nextMoves;
    private FutureMove bestOfNextMoves;

    public void setBestOfNextMoves(FutureMove bestOfNextMoves) {
        this.bestOfNextMoves = bestOfNextMoves;
    }

    public FutureMove getBestOfNextMoves() {
        return bestOfNextMoves;
    }

    public int getDepth() {
        return depth;
    }

    public List<FutureMoveList> getNextMoves() {
        return nextMoves;
    }

    public FutureMove getMove() {
        return move;
    }

    public synchronized static int getExploredDepth() {
        return exploredDepth;
    }

    public synchronized static void setExploredDepth(int exploredDepth) {
        FutureMoveList.exploredDepth = exploredDepth;
    }

    public FutureMoveList(int depth_, FutureMove move_) {
        depth = depth_;
        move = move_;
        nextMoves = new ArrayList<>();
        bestOfNextMoves = null;
    }

    public void addToList(FutureMove move_) {
        nextMoves.add(new FutureMoveList(depth + 1, move_));
    }

    // Finds a move one level deep
    public FutureMoveList findMove(FutureMove move) {
        for(FutureMoveList list : nextMoves) {
            if(list.move == move) {
                return list;
            }
        }
        return null;
    }

    public static FutureMoveList setNewRoot(FutureMoveList moveList) {
        root = moveList;
        return root;
    }

}

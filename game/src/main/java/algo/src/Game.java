package algo.src;

import com.google.protobuf.ByteString;
import dab.DotsAndBoxes;
import netcode.Netcode;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Game {

    private Playfield playfield;
    private Playfield.CurrentPlayer player; // Which player we are

    private int wins = 0;
    private int losses = 0;
    private int draws = 0;

    private NetworkManager networkManager;

    private boolean networkGameInProgress = false;

    boolean soloGame = false;

    enum Algorithm {
        GREEDY,
        DEPTH
    }

    private static final String logFile = "moveCalculationSpeed.txt";

    public Game() {
        networkManager = new NetworkManager();

        System.out.println("Do you want the bot to auto play against network players?");
        System.out.println("Doing so will prevent you from interacting with the game until you exit it.");
        System.out.println("Write 1 for true, 0 for false");
        Scanner scanner = new Scanner(System.in);
        int option = -1;
        if(scanner.hasNextInt()) {
            option = scanner.nextInt();
        }

        boolean continousPlay = false;

        if(option == 1) {
            continousPlay = true;
        }
        else {
            continousPlay = false;
        }

        playGames(continousPlay);
    }

    private void playGames(boolean continousNetworkPlaying) {
        if(continousNetworkPlaying) {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                }
                catch(Exception ex) {}

                System.out.println("Searching for an online game...");
                resetGame();
                startNetworkGame();

                printStatistics();

                // Should abort network game if it is not finished
                if (networkGameInProgress) {
                    abortNetworkGame(true);
                }
            }
        }
        else {
            System.out.println("Remove this line and the comments below if you know what you are doing");
            return;
        }
        /*
        while(true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter which type of game you want to play:");
            System.out.println("'network': A network game bot against other bot (might fail if no connection can be established)");
            System.out.println("'offline': An offline game against our bot");
            System.out.println("'exit': Exits the program cleanly");
            String option = "";
            if(scanner.hasNext()) {
                option = scanner.next();
            }

            // This will block until a game is finished or something fails
            if(option.equals("network")) {
                System.out.println("Searching for an online game...");
                resetGame();
                startNetworkGame();
            }
            else if(option.equals("offline")) {
                System.out.println("Starting offline game against our bot...");
                System.out.println("Set a width and height!");

                resetGame();
                soloGame = true;
                startGame(scanner.nextInt(), scanner.nextInt(), scanner);
                continue;
            }
            else if(option.equals("exit")) {
                System.out.println("Exiting program....");
                printStatistics();
                break;
            }
            else {
                System.out.println("Command not understood please try again...");
                continue;
            }
        }

        // Should abort network game if it is not finished
        if(networkGameInProgress) {
            abortNetworkGame(true);
        }
         */
    }

    // Resets the member variables so we dont leave anything behind
    private void resetGame() {
        if(networkGameInProgress) {
            abortNetworkGame(true);
        }

        playfield = null;
        player = null;
    }

    // Aborts the currently active network game
    private void abortNetworkGame(boolean resetPlayfield) {
        System.out.println("Aborting match with token: " + networkManager.getMatchToken());
        if(!networkManager.abortMatch()) {
            System.out.println("Aborting match failed....");
        }

        if(resetPlayfield) {
            playfield = null;
            player = null;
        }

        networkManager.closeChannel();

        networkManager = new NetworkManager();
        networkGameInProgress = false;
    }

    // Starts a new network game with a random board size
    private void startNetworkGame() {
        if(UserToken.userToken.equals("")){
            System.out.println("No user token found....");
            return;
        }

        int prefWidth = ThreadLocalRandom.current().nextInt(2, 8 + 1); // in columns
        int prefHeight = ThreadLocalRandom.current().nextInt(2, 7 + 1);
        System.out.println("Trying to start a game with size: width: " + prefWidth + "(columns) and height: " + prefHeight + "(columns)");
        Netcode.MatchResponse response = networkManager.newMatch(prefWidth, prefHeight);
        if(response == null) {
            System.out.println("Starting a new network game failed....");
            return;
        }

        networkGameInProgress = true;

        System.out.println("Started match with token: " + response.getMatchToken());

        Netcode.GameStateResponse state = networkManager.getGameState();
        if(state == null) {
            System.out.println("Starting a new network game failed....");
            networkManager.abortMatch();
            return;
        }
        if(state.getGameStatus().getNumber() == Netcode.GameStatus.MATCH_NOT_STARTED_VALUE) {
            System.out.println("No player/bot could be found. Try again later");
            networkManager.abortMatch();
            return;
        }

        int width = state.getDabGameState().getVerticalColumns() - 1; // in boxes
        int height = state.getDabGameState().getHorizontalColumns() - 1;
        boolean startingPlayer = state.getBeginningPlayer();
        if(startingPlayer) {
            player = Playfield.CurrentPlayer.PLAYER_A;
        }
        else {
            player = Playfield.CurrentPlayer.PLAYER_B;
        }
        System.out.println("Starting network game with width: " + (width + 1) + "(columns), height: " + (height + 1)+ "(columns) and us being " + player.toString());

        playfield = Playfield.initPlayfield(width, height, Playfield.CurrentPlayer.PLAYER_A);

        // Plays the game until end or
        playNetworkGame();
    }

    // Returns 1 if it is our turn to play
    // Returns 0 if it is opponents turn to play
    // Returns -1 if game is over
    private int isOurTurn() {
        try {
            TimeUnit.SECONDS.sleep(5);

            Netcode.GameStateResponse state = networkManager.getGameState();
            if(state == null) {
                System.out.println("isOurTurn getGameState failed");
                return 0;
            }
            System.out.println("Game status: " + state.getGameStatus());

            if(state.getGameStatus().getNumber() == Netcode.GameStatus.MATCH_ABORTED_VALUE) {
                System.out.println("Match was aborted");
                return -1;
            }
            else if(state.getGameStatus().getNumber() == Netcode.GameStatus.MATCH_LOST_VALUE) {
                System.out.println("Match was lost");
                playfield.printStatus();
                losses++;
                return -1;
            }
            else if(state.getGameStatus().getNumber() == Netcode.GameStatus.MATCH_WON_VALUE) {
                System.out.println("Match was won");
                playfield.printStatus();
                wins++;
                return -1;
            }
            else if(state.getGameStatus().getNumber() == Netcode.GameStatus.DRAW_VALUE) {
                System.out.println("Match was a draw");
                playfield.printStatus();
                draws++;
                return -1;
            }
            else if(state.getGameStatus().getNumber() == Netcode.GameStatus.YOUR_TURN_VALUE) {
                playfield.playOpponentMoves(state.getDabGameState(), true);
                return 1;
            }
            else if(state.getGameStatus().getNumber() == Netcode.GameStatus.OPPONENTS_TURN_VALUE) {
                playfield.playOpponentMoves(state.getDabGameState(), true);
                return 0;
            }

            return 0;
        }
        catch (Exception ex) {
            System.out.println("isOurTurn exception: " + ex);
            ex.printStackTrace();
            return 0;
        }
    }

    // Loop until no more moves are possible
    private void playNetworkGame() {
        while (true) {

            long startWaitTime  = System.currentTimeMillis();
            // Waits for opponent to play
            while (true)
            {
                int retvalue = isOurTurn();
                if(retvalue == 1) {
                    // our turn
                    break;
                }
                else if(retvalue == -1) {
                    // game is over
                    return;
                }

                if(System.currentTimeMillis() >= startWaitTime + (6 * 60 * 1000)) {
                    // Wait for 6 minutes for next move
                    System.out.println("Opponent did not play fast enough. Aborting....");
                    abortNetworkGame(true);
                    return;
                }
                // Opponent playing....
            }

            // Blocks until our move has been played
            if(!playMove()) {
                return;
            }
        }
    }


    // Generates a good random move if possible otherwise gets a random one
    // TODO: Algorithm goes here and only here. Unless you ABSOLUTELY know what you are doing dont touch anything else
    private HalfMove generateMove() {
        List<HalfMove> remainingValidMoves = playfield.getAllRemainingValidMoves();
        if(remainingValidMoves.isEmpty()) {
            assert false;
            return null;
        }

        // TODO: Change this for algorithm selection
        Algorithm algorithm = Algorithm.DEPTH;

        if(algorithm == Algorithm.GREEDY) {
            // Confirmed to be working
            return getGreedyMove(playfield, true);
        }
        else if(algorithm == Algorithm.DEPTH) {
            int maxDepth;
            int remainingMoves = playfield.getAllRemainingValidMoves().size();

            // Calculate how deep we can search in 1 minute or less
            long calculationSpeed; // moves per second
            boolean usingWeakMachine = false; // TODO: SET TO TRUE IF YOU ARE RUNNING ON LAPTOP OR WEAK HARDWARE
            if(usingWeakMachine) {
                calculationSpeed = 150000;
            }
            else {
                calculationSpeed = 300000; // (Around a half of what an i5 4670k OCd to 4.3GHz can calculate at least)
            }
            long maximumMoveChecks = calculationSpeed * 90; // How many moves we can calculate in 90 seconds

            int possibleDepth;
            long possibleChecks = remainingMoves;
            for(possibleDepth = 1; possibleChecks < maximumMoveChecks; possibleDepth++) {
                if(remainingMoves - possibleDepth == 0) {
                    break;
                }
                possibleChecks *= (remainingMoves - possibleDepth);
            }
            maxDepth = possibleDepth - 1;

            // Performance debugging
            if(remainingMoves < maxDepth) {
                maxDepth = remainingMoves - 1;
            }

            long totalChecks = remainingMoves;
            for(int i = 1; i < maxDepth; i++) {
                totalChecks *= (remainingMoves - i);
            }
            if(maxDepth == 0) {
                totalChecks = 1;
            }
            System.out.println("Depth: " + maxDepth + ", expected moves to check: " + totalChecks + " expected time: "
                    + (totalChecks / calculationSpeed) + " seconds");
            long startTime = System.nanoTime();

            System.out.println("Getting best move with depth " + maxDepth);
            HalfMove move = getBestFutureMove(playfield, true, 0, maxDepth, 0).getHalfMove();

            long endTime = System.nanoTime();
            long differenceInMillis = (endTime - startTime) / 1000000;
            System.out.println("Algorithm took " + differenceInMillis + "ms for " + totalChecks + " checked moves");

            try {
                FileWriter fileWriter = new FileWriter(logFile, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.println(totalChecks + " in " + differenceInMillis + " ms");
                printWriter.close();
            }
            catch(Exception ex) {
                System.out.println("Could not write to file: " + ex);
            }

            if(!playfield.isHalfMoveValid(move, true)) {
                // Fallback to greedy
                System.out.println("Depth algorithm failed");
                move = getGreedyMove(playfield, true);
            }
            return move;
        }
        else {
            System.out.println("Algorithm not set");
            System.exit(-100);
        }

        return null;
    }

    public HalfMove getGreedyMove(final Playfield playfield_, boolean ourMove) {
        List<HalfMove> remainingValidMoves = playfield.getAllRemainingValidMoves();

        Playfield.CurrentPlayer currentPlayer;

        if(ourMove) {
            currentPlayer = player;
        }
        else {
            if(player == Playfield.CurrentPlayer.PLAYER_A) {
                currentPlayer = Playfield.CurrentPlayer.PLAYER_B;
            }
            else {
                currentPlayer = Playfield.CurrentPlayer.PLAYER_A;
            }
        }

        // See if any move closes 2 boxes
        for(HalfMove move : remainingValidMoves) {
            if(playfield_.doesMoveCloseABox(move) == 2) {
                move.setPlayer(currentPlayer);
                return move;
            }
        }

        // See if any move closes 1 box
        for(HalfMove move : remainingValidMoves) {
            if(playfield_.doesMoveCloseABox(move) == 1) {
                move.setPlayer(currentPlayer);
                return move;
            }
        }

        // Just get any move then
        return playfield_.getValidRandomHalfMove(currentPlayer);
    }

    /*
    I dont know what is even going on here
    Dont bother trying to fix it
    public FutureMove getBestFutureMove(final Playfield playfield_, boolean ourMove, int depth, int maxDepth) {
        // Do not change the playfield parameter that is passed

        if(depth == maxDepth) {
            Playfield playfieldCopy = Playfield.getCopyOfPlayfield(playfield_);
            int value = playfieldCopy.getBoardValue().doubleCloseMoves * 2 + playfieldCopy.getBoardValue().singleCloseMoves;
            HalfMove move = getGreedyMove(playfieldCopy, ourMove); //Basically does nothing expect when maxDepth = 0
            return new FutureMove(move, value);
        }

        Map<HalfMove, Integer> moveResults = new HashMap<>();
        List<HalfMove> moves = new Vector<>(playfield_.getAllRemainingValidMoves());
        for(HalfMove move : moves) {
            Playfield playfieldCopy = Playfield.getCopyOfPlayfield(playfield_);
            boolean isNextTurnOurs = !ourMove;
            if(playfieldCopy.doesMoveCloseABox(move) > 0) {
                isNextTurnOurs = ourMove;
            }
            playfieldCopy.playHalfMove(move, ourMove, false);



            int futureValue = getBestFutureMove(playfieldCopy, isNextTurnOurs, depth++, maxDepth).getBoardValue();

            //BoardValue boardValue = playfieldCopy.getBoardValue();
            //int value = boardValue.doubleCloseMoves * 2 + boardValue.singleCloseMoves;
            moveResults.put(move, futureValue);
        }

        Map<HalfMove, Integer> sortedResults = null;

        // if it is our turn we want best board value
        // if it is opponent we want lowest board value
        if(ourMove) {
            sortedResults = moveResults.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }
        else {
            sortedResults = moveResults.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }

        Map.Entry<HalfMove, Integer> result = sortedResults.entrySet().iterator().next();

        FutureMove futureMove = new FutureMove(result.getKey(), result.getValue());

        return futureMove;
    }
    */

    // Gets the best future move that can close as many boxes as possible
    public FutureMove getBestFutureMove(final Playfield playfield_, boolean ourMove, int depth, int maxDepth, int boxesClosed) {
        // Do not change the playfield parameter that is passed

        if(depth == maxDepth) {
            return new FutureMove(boxesClosed);
        }

        //int index = 0;

        Map<HalfMove, Integer> moveResults = new HashMap<>();
        List<HalfMove> moves = new ArrayList<>(playfield_.getAllRemainingValidMoves());
        for(HalfMove move : moves) {
            move = HalfMove.copyHalfMove(move);
            Playfield playfieldCopy = Playfield.getCopyOfPlayfield(playfield_);
            boolean isNextTurnOurs = !ourMove;
            int closes = playfieldCopy.doesMoveCloseABox(move);
            if(closes > 0) {
                isNextTurnOurs = ourMove;
            }
            if(ourMove) {
                move.setPlayer(player);
            }
            else {
                if(player == Playfield.CurrentPlayer.PLAYER_A) {
                    move.setPlayer(Playfield.CurrentPlayer.PLAYER_B);
                }
                else {
                    move.setPlayer(Playfield.CurrentPlayer.PLAYER_A);
                }
            }

            //System.out.print(index);
            //move.print();

            playfieldCopy.playHalfMove(move, ourMove, false);

            int resultingValue;

            if(ourMove) {
                resultingValue = getBestFutureMove(playfieldCopy, isNextTurnOurs, depth + 1, maxDepth, boxesClosed + closes).getValue();
            }
            else {
                resultingValue = getBestFutureMove(playfieldCopy, isNextTurnOurs, depth + 1, maxDepth, boxesClosed - closes).getValue();
            }

            //System.out.print(index);
            //move.print();
            //index++;

            moveResults.put(move, resultingValue);
        }

        /*
        index = 0;
        for(HalfMove move : moveResults.keySet()) {
            System.out.print(index);
            move.print();
            index++;
        }
         */

        Map<HalfMove, Integer> sortedResults = null;

        // if it is our turn we want highest value
        // if it is opponent we want lowest value
        if(ourMove) {
            sortedResults = moveResults.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }
        else {
            sortedResults = moveResults.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }

        Map.Entry<HalfMove, Integer> result = sortedResults.entrySet().iterator().next();

        //System.out.print("depth: " + depth + ", value: " + result.getValue() + " ");
        //result.getKey().print();

        return new FutureMove(result.getKey(), result.getValue());
    }

    // Plays our own move
    // Returns true if played and false if we aborted
    private boolean playMove() {
        System.out.println("Playing move number: " + (playfield.movesPlayed.size() + 1));

        HalfMove move = generateMove();

        if(playfield.playHalfMove(move, true, true) == -1) {
            System.out.println("Illegal move was tried");
            playfield.printPlayfield();;
            playfield.printStatus();
            if(!soloGame) {
                abortNetworkGame(false);
            }
            return false;
        }
        if(!soloGame)
        {
            long startWaitTime  = System.currentTimeMillis();
            while(true) {
                if(networkManager.submitTurn(move) != null) {
                    break;
                }

                if(System.currentTimeMillis() >= startWaitTime + (30 * 1000)) {
                    // Repeat move for 30 seconds and abort if still doesnt work
                    System.out.println("Own move timeout. Server did not accept move. THIS SHOULD NEVER HAPPEN!");
                    abortNetworkGame(true);
                    return false;
                }
            }

        }
        System.out.println("We are " + player);
        playfield.printStatus();
        return true;

    }

    private void startGame(int width, int height, Scanner scanner) {
        try {
            // Example of a simple game situation
            player = Playfield.CurrentPlayer.PLAYER_A;

            playfield = Playfield.initPlayfield(width, height, player);
            playfield.printPlayfield();
            System.out.println("First column then gab and than if horizontal or vertical!");

            while (true)
            {
                if(playfield.isGameOver())
                    break;
                if(player != Playfield.CurrentPlayer.PLAYER_B)
                {
                    System.out.println("Your Turn!");
                    offlineMove(scanner);
                }

                if(playfield.isGameOver())
                    break;

                if(player != Playfield.CurrentPlayer.PLAYER_A)
                    playMove();
            }
            System.out.println("The winner is: " + playfield.getWinner());

            if(Playfield.GameResult.WIN_PLAYER_A == playfield.getWinner())
                wins++;
            else if(Playfield.GameResult.WIN_PLAYER_B == playfield.getWinner())
                losses++;
            else
                draws++;
        }

        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void offlineMove(Scanner scanner) {
        int column = scanner.nextInt();
        int gap = scanner.nextInt();
        int vertical = scanner.nextInt();

        HalfMove move;
        if(vertical == 0)
            move = HalfMove.newHalfMove(column, gap, HalfMove.LineOrientation.VERTICAL, player);
        else
            move = HalfMove.newHalfMove(column, gap, HalfMove.LineOrientation.HORIZONTAL, player);

        //TODO: Needs fixing but dont bother
        //player = playfield.playHalfMove(move, true);
        playfield.printStatus();
    }

    public boolean checkIfmove (HalfMove move)
    {
        for(HalfMove old_move : playfield.movesPlayed)
        {
            if(old_move.getGapIndex() == move.getGapIndex()
                    && old_move.getColumnIndex() == move.getColumnIndex()
            && old_move.getOrientation() == move.getOrientation())
                return true;
        }
        return false;
    }

    private void printStatistics() {
        System.out.println("Total games: " + (wins + losses + draws));
        System.out.println("Wins: \t" + wins);
        System.out.println("Losses:\t" + losses);
        System.out.println("Draws: \t" + draws);
    }
}

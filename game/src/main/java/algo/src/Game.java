package algo.src;

import com.google.protobuf.ByteString;
import dab.DotsAndBoxes;
import netcode.Netcode;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Game {

    private Playfield playfield;
    private Playfield.CurrentPlayer player; // Which player we are

    private int wins = 0;
    private int losses = 0;
    private int draws = 0;

    private NetworkManager networkManager;

    private boolean networkGameInProgress = false;


    boolean soloGame = false;

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
        int prefHeight = ThreadLocalRandom.current().nextInt(2, 8 + 1);
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
            TimeUnit.SECONDS.sleep(3);

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
                playfield.playOpponentMoves(state.getDabGameState());
                return 1;
            }
            else if(state.getGameStatus().getNumber() == Netcode.GameStatus.OPPONENTS_TURN_VALUE) {
                playfield.playOpponentMoves(state.getDabGameState());
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
    // TODO: Algorithm goes here and only here. Unless you ABSOLUTELYX know what you are doing dont touch anything else
    private HalfMove generateMove() {
        List<HalfMove> remainingValidMoves = playfield.getAllRemainingValidMoves();
        if(remainingValidMoves.isEmpty()) {
            assert false;
            return null;
        }

        // See if any move closes 2 boxes
        for(HalfMove move : remainingValidMoves) {
            if(playfield.doesMoveCloseABox(move) == 2) {
                move.setPlayer(player);
                return move;
            }
        }

        // See if any move closes 1 box
        for(HalfMove move : remainingValidMoves) {
            if(playfield.doesMoveCloseABox(move) == 1) {
                move.setPlayer(player);
                return move;
            }
        }

        // Just get any move then
        return playfield.getValidRandomHalfMove(player);
    }

    // Plays our own move
    // Returns true if played and false if we aborted
    private boolean playMove() {
        System.out.println("Playing move number: " + (playfield.movesPlayed.size() + 1));

        HalfMove move = generateMove();

        if(playfield.playHalfMove(move, true) == -1) {
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

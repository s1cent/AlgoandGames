package algo.src;

import com.google.protobuf.ByteString;
import dab.DotsAndBoxes;
import netcode.Netcode;

import java.util.Scanner;
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

    public Game() {
        networkManager = new NetworkManager();

        playGames();

        /*
        player = Playfield.CurrentPlayer.PLAYER_A;
        currentPlayer = player;
        playfield = Playfield.initPlayfield(3, 3, player);
        if(playfield == null) {
            System.out.println("Playfield initialization failed");
            return;
        }

        playfield.printPlayfield();

        startGame();
         */
    }

    private void playGames() {
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
                resetGame();

                System.out.println("Offline games not implemented yet");
                // TODO:
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

        networkManager = new NetworkManager();
        networkGameInProgress = false;
    }

    // Starts a new network game with a random board size
    private void startNetworkGame() {
        if(UserToken.userToken.equals("")){
            System.out.println("No user token found....");
            return;
        }

        int prefWidth = ThreadLocalRandom.current().nextInt(2, 8 + 1);
        int prefHeight = ThreadLocalRandom.current().nextInt(2, 8 + 1);
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

        int width = state.getDabGameState().getVerticalColumns() - 1;
        int height = state.getDabGameState().getHorizontalColumns() - 1;
        boolean startingPlayer = state.getBeginningPlayer();
        if(startingPlayer) {
            player = Playfield.CurrentPlayer.PLAYER_A;
        }
        else {
            player = Playfield.CurrentPlayer.PLAYER_B;
        }

        playfield = Playfield.initPlayfield(width, height, Playfield.CurrentPlayer.PLAYER_A);

        // Plays the game until end or
        playNetworkGame();
    }

    // Returns true if it is our turn to play
    private boolean isOurTurn() {
        if(playfield.getCurrentPlayer() == player) {
            return true;
        }
        try {
            TimeUnit.SECONDS.sleep(1);

            Netcode.GameStateResponse state = networkManager.getGameState();
            if(state == null) {
                System.out.println("isOurTurn getGameState failed");
                return false;
            }

            if(playfield.playOpponentMoves(state.getDabGameState())) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception ex) {
            System.out.println("isOurTurn exception: " + ex);
            return false;
        }
    }

    // Loop until no more moves are possible
    private void playNetworkGame() {
        while (true) {

            // Check if game is over
            if(playfield.isGameOver()) {
                Playfield.GameResult result = playfield.getWinner();
                if(player == Playfield.CurrentPlayer.PLAYER_A) {
                    if(result == Playfield.GameResult.WIN_PLAYER_A) {
                        wins++;
                    }
                    else if(result == Playfield.GameResult.WIN_PLAYER_B) {
                        losses++;
                    }
                    else {
                        draws++;
                    }
                }
                else {
                    if(result == Playfield.GameResult.WIN_PLAYER_A) {
                        losses++;
                    }
                    else if(result == Playfield.GameResult.WIN_PLAYER_B) {
                        wins++;
                    }
                    else {
                        draws++;
                    }
                }
                return;
            }

            long startWaitTime  = System.currentTimeMillis();
            boolean timeout = false;
            // Waits for opponent to play
            while (!isOurTurn())
            {
                if(System.currentTimeMillis() >= startWaitTime + (10 * 60 * 1000)) {
                    // Wait for 10 minutes for next move
                    abortNetworkGame(true);
                    return;
                }
                // Opponent playing....
            }

            // Blocks until our move has been played
            playMove();
        }
    }

    // Plays our own move
    private void playMove() {
        // TODO: Play the game
        HalfMove move = HalfMove.newHalfMove(1, 1, HalfMove.LineOrientation.HORIZONTAL, player);
        if(playfield.playHalfMove(move) == null) {
            System.out.println("Illegal move was tried.... Check the algorithm and playfield");
            playfield.printPlayfield();
            playfield.printStatus();
            abortNetworkGame(false);
        }
    }

    private void startGame() {
        try {
            // Example of a simple game situation
            TimeUnit.SECONDS.sleep(2);

            HalfMove move = HalfMove.newHalfMove(0, 0, HalfMove.LineOrientation.HORIZONTAL, player);
            Playfield.CurrentPlayer currentPlayer = playfield.playHalfMove(move);
            playfield.printPlayfield();
            playfield.printStatus();

            TimeUnit.SECONDS.sleep(2);

            move = HalfMove.newHalfMove(0, 0, HalfMove.LineOrientation.VERTICAL, currentPlayer);
            currentPlayer = playfield.playHalfMove(move);
            playfield.printPlayfield();
            playfield.printStatus();

            TimeUnit.SECONDS.sleep(2);

            move = HalfMove.newHalfMove(1, 0, HalfMove.LineOrientation.VERTICAL, currentPlayer);
            currentPlayer = playfield.playHalfMove(move);
            playfield.printPlayfield();
            playfield.printStatus();

            TimeUnit.SECONDS.sleep(2);

            // this move closes box
            move = HalfMove.newHalfMove(1, 0, HalfMove.LineOrientation.HORIZONTAL, currentPlayer);
            Playfield.CurrentPlayer closePlayer = playfield.playHalfMove(move);
            playfield.printPlayfield();
            playfield.printStatus();

            assert(closePlayer == currentPlayer);

            currentPlayer = closePlayer;

            TimeUnit.SECONDS.sleep(2);

            move = HalfMove.newHalfMove(1, 1, HalfMove.LineOrientation.HORIZONTAL, currentPlayer);
            currentPlayer = playfield.playHalfMove(move);
            playfield.printPlayfield();
            playfield.printStatus();

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void printStatistics() {
        System.out.println("Total games: " + (wins + losses + draws));
        System.out.println("Wins: \t" + wins);
        System.out.println("Losses:\t" + losses);
        System.out.println("Draws: \t" + draws);
    }
}

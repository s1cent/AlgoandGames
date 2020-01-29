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

    private List<HalfMove> moveList = new ArrayList<>();

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
                System.out.println("Set a width and height!");

                resetGame();
                startGame(scanner.nextInt(), scanner.nextInt(), scanner);
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
        if(state.getGameStatus().getNumber() == Netcode.GameStatus.MATCH_NOT_STARTED_VALUE) {
            System.out.println("No player/bot could be found. Try again later");
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
        System.out.println("Starting network game with width: " + width + ", height: " + height + " and us being " + player.toString());

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
                losses++;
                return -1;
            }
            else if(state.getGameStatus().getNumber() == Netcode.GameStatus.MATCH_WON_VALUE) {
                System.out.println("Match was won");
                wins++;
                return -1;
            }
            else if(state.getGameStatus().getNumber() == Netcode.GameStatus.DRAW_VALUE) {
                System.out.println("Match was a draw");
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

            /*
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
             */

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

    // Plays our own move
    // Returns true if played and false if we aborted
    private boolean playMove() {
        System.out.println("Playing move number: " + (playfield.movesPlayed.size() + 1));
        // TODO: Play the game

        HalfMove move;
        int index = 0;
        while (true){
            Random r = new Random();
            System.out.println("Find a move!");
            int vertical = r.nextInt(2);

            if (vertical == 0) {
                System.out.println("vertical");
                move = HalfMove.newHalfMove(r.nextInt(playfield.getHeight() - 1),
                        r.nextInt(playfield.getHeight() - 2), HalfMove.LineOrientation.VERTICAL, player);

                if(checkIfmove(move))
                {
                    move.setOrientation(HalfMove.LineOrientation.HORIZONTAL);
                }
            }
            else
            {
                System.out.println("horizontal");

                move = HalfMove.newHalfMove(r.nextInt(playfield.getWidth() - 1),
                        r.nextInt(playfield.getWidth() - 2), HalfMove.LineOrientation.HORIZONTAL, player);

                if(checkIfmove(move))
                {
                    move.setOrientation(HalfMove.LineOrientation.VERTICAL);
                }
            }

            if(index == 10)
            {
                move = findValidmove();
            }
            index++;

            if(!checkIfmove(move))
                break;
        }

        moveList.add(move);
        if((player = playfield.playHalfMove(move)) == null) {
            System.out.println(move.getColumnIndex() + " " + move.getGapIndex());
        }

        //long startWaitTime  = System.currentTimeMillis();
//        while(true) {
//            if(networkManager.submitTurn(move) != null) {
//                break;
//            }
//
//            if(System.currentTimeMillis() >= startWaitTime + (30 * 1000)) {
//                // Repeat move for 30 seconds and abort if still doesnt work
//                System.out.println("Own move timeout. Server did not accept move. THIS SHOULD NEVER HAPPEN!");
//                abortNetworkGame(true);
//                return false;
//            }
//        }
        playfield.printStatus();
        return true;

    }

    private HalfMove findValidmove() {

        for(int i = 0; i < playfield.getWidth(); i++)
        {
            for(int j = 0; j < playfield.getWidth()- 1; j++)
            {
                HalfMove move = HalfMove.newHalfMove(i,j, HalfMove.LineOrientation.HORIZONTAL, player);
                System.out.println("Search in Horizontal!");
                if(!checkIfmove(move))
                    return move;
            }
        }

        for (int i = 0; i < playfield.getHeight(); i++)
        {
            for(int j = 0; j < playfield.getHeight() - 1; j++)
            {
                HalfMove move = HalfMove.newHalfMove(i,j, HalfMove.LineOrientation.VERTICAL, player);
                System.out.println("Search in Vertical!");

                if(!checkIfmove(move))
                    return move;
            }
        }

        System.out.println("Nothing found!");
        return null;
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


        moveList.add(move);
        player = playfield.playHalfMove(move);
        playfield.printStatus();
    }

    public boolean checkIfmove (HalfMove move)
    {
        for(HalfMove old_move : moveList)
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

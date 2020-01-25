package algo.src;

import java.util.concurrent.TimeUnit;

public class Game {

    private Playfield playfield;
    private Playfield.CurrentPlayer player; // Which player we are
    private Playfield.CurrentPlayer currentPlayer; // Player that is currently playing

    public Game() {
        player = Playfield.CurrentPlayer.PLAYER_A;
        currentPlayer = player;
        playfield = Playfield.initPlayfield(3, 3, player);
        if(playfield == null) {
            System.out.println("Playfield initialization failed");
            return;
        }

        playfield.printPlayfield();

        startGame();
    }

    private void startGame() {
        try {
            // Example of a simple game situation
            TimeUnit.SECONDS.sleep(2);

            HalfMove move = HalfMove.newHalfMove(0, 0, HalfMove.LineOrientation.HORIZONTAL, player);
            currentPlayer = playfield.playHalfMove(move);
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
}

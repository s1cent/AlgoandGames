package algo.src;

import dab.DotsAndBoxes;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import netcode.GameComGrpc;
import netcode.Netcode;
import netcode.GameComGrpc.GameComStub;

import java.util.concurrent.CountDownLatch;

// TODO: If you see missing UserToken errors read the README setup again
public class NetworkManager {

    private static String ip = "129.27.202.46";
    private static int port = 80;

    private ManagedChannel channel;
    private GameComStub stub;

    private String matchToken = null;

    private boolean ready = false;

    public enum Orientation{
        VERTICAL,
        HORIZONTAL
    }

    // TODO: Remember to call abortMatch when closing game
    public NetworkManager() {
        channel = ManagedChannelBuilder.forAddress(ip, port).usePlaintext().build();
        stub = GameComGrpc.newStub(channel);
        matchToken = null;
        ready = true;
    }

    public String getMatchToken() {
        return matchToken;
    }

    public boolean isReady() {
        return ready;
    }


    // Returns MatchResponse if rpc call was valid, null otherwise
    // Blocks until finished
    // You can get the information by using .get... on the Response
    public Netcode.MatchResponse newMatch(int width, int height) {
        if(!ready) {
            System.out.println("Something went wrong during initialization");
            return null;
        }
        if(UserToken.userToken == null) {
            System.out.println("No user token found. Did you do the setup?");
            return null;
        }
        if(width <= 0) {
            System.out.println("Width must be larger than 0");
            return null;
        }
        if(height <= 0) {
            System.out.println("Height must be larger than 0");
            return null;
        }
        if(width > 10) {
            System.out.println("Maximum width is 10");
            return null;
        }
        if(height > 10) {
            System.out.println("Maximum height is 10");
            return null;
        }
        if(matchToken != null) {
            System.out.println("Aborting last match...");

            if(!abortMatch()) {
                System.out.println("Aborting last match failed...");
                return null;
            }
        }

        CountDownLatch latch = new CountDownLatch(1);

        DotsAndBoxes.GameParameter parameter = DotsAndBoxes.GameParameter.newBuilder()
                .setNumberOfHorizontalColumns(height).setNumberOfVerticalColumns(width).build();
        Netcode.MatchRequest request = Netcode.MatchRequest.newBuilder()
                .setDabGameParameters(parameter).setUserToken(UserToken.userToken).setGameToken("dab").build();

        final Netcode.MatchResponse[] responses = {null};

        StreamObserver<Netcode.MatchResponse> observer = new StreamObserver<Netcode.MatchResponse>() {
            @Override
            public void onNext(Netcode.MatchResponse matchResponse) {
                System.out.println("matchResponse: " + matchResponse);
                responses[0] = matchResponse;
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("matchResponseError: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("matchRequest rpc Complete");
                latch.countDown();
            }
        };

        try {
            stub.newMatch(request, observer);
            latch.await();
            if(responses[0] == null) {
                return null;
            }
            matchToken = responses[0].getMatchToken();
        }
        catch (Exception e) {
            System.out.println("newMatch GRPC Failed: " + e);
            e.printStackTrace();
            return null;
        }

        return responses[0];
    }


    // Returns true if successfully aborted, false otherwise
    // Blocks until finished
    public boolean abortMatch() {
        if(!ready) {
            System.out.println("Something went wrong during initialization");
            return false;
        }
        if(matchToken == null) {
            System.out.println("No match token found. Did you start the game?");
            return false;
        }
        if(UserToken.userToken == null) {
            System.out.println("No user token found. Did you do the setup?");
            return false;
        }

        CountDownLatch latch = new CountDownLatch(1);

        Netcode.AbortMatchRequest request = Netcode.AbortMatchRequest.newBuilder()
                .setMatchToken(matchToken).setUserToken(UserToken.userToken).build();

        StreamObserver<Netcode.Nothing> observer = new StreamObserver<Netcode.Nothing>() {
            @Override
            public void onNext(Netcode.Nothing nothing) {
                System.out.println("abortResponse: " + nothing);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("abortResponseError: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("abortMatch rpc Complete");
                latch.countDown();
            }
        };

        try {
            stub.abortMatch(request, observer);
            latch.await();
            matchToken = null;
        }
        catch (Exception e) {
            System.out.println("abortMatch GRPC Failed: " + e);
            e.printStackTrace();
            return false;
        }

        return true;
    }


    // Returns GameStateResponse if rpc call was valid, null otherwise
    // Blocks until finished
    // You can get the information by using .get... on the Response
    public Netcode.GameStateResponse getGameState() {
        if(!ready) {
            System.out.println("Something went wrong during initialization");
            return null;
        }
        if(matchToken == null) {
            System.out.println("No match token found. Did you start the game?");
            return null;
        }
        if(UserToken.userToken == null) {
            System.out.println("No user token found. Did you do the setup?");
            return null;
        }

        CountDownLatch latch = new CountDownLatch(1);

        Netcode.GameStateRequest request = Netcode.GameStateRequest.newBuilder()
                .setMatchToken(matchToken).setUserToken(UserToken.userToken).build();

        final Netcode.GameStateResponse[] responses = {null};

        StreamObserver<Netcode.GameStateResponse> observer = new StreamObserver<Netcode.GameStateResponse>() {
            @Override
            public void onNext(Netcode.GameStateResponse gameStateResponse) {
                //System.out.println("gameStateResponse: " + gameStateResponse);
                responses[0] = gameStateResponse;
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("gameStateResponseError: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("getGameState rpc Complete");
                latch.countDown();
            }
        };

        try {
            stub.getGameState(request, observer);
            latch.await();
        }
        catch (Exception e) {
            System.out.println("getGameState GRPC Failed: " + e);
            e.printStackTrace();
            return null;
        }

        return responses[0];
    }


    // Returns TurnResponse if rpc call was valid, null otherwise
    // Blocks until finished
    // You can get the information by using .get... on the Response
    // TODO: NOTE: NEVER EVER!!! SUBMIT A TURN BEFORE PLAYING IT IN THE PLAYFIELD OR CHECKING IT FOR VALIDTY FIRST!!!
    public Netcode.TurnResponse submitTurn(int column, int gap, Orientation orientation) {
        if(!ready) {
            System.out.println("Something went wrong during initialization");
            return null;
        }
        if(matchToken == null) {
            System.out.println("No match token found. Did you start the game?");
            return null;
        }
        if(UserToken.userToken == null) {
            System.out.println("No user token found. Did you do the setup?");
            return null;
        }
        if(column < 0) {
            System.out.println("Column cannot be smaller than 0");
            return null;
        }
        if(column > 9) {
            System.out.println("Max field size is 10 so column cannot be larger than 9");
            return null;
        }
        if(gap < 0) {
            System.out.println("Gap cannot be smaller than 0");
            return null;
        }
        if(gap > 8) {
            System.out.println("Max field size is 10 so gap cannot be larger than 8");
            return null;
        }
        if(orientation == null) {
            System.out.println("Orientation was not specified");
            return null;
        }

        CountDownLatch latch = new CountDownLatch(1);

        boolean vertical = false;
        if(orientation == Orientation.VERTICAL) {
            vertical = true;
        }

        DotsAndBoxes.GameTurn turn = DotsAndBoxes.GameTurn.newBuilder().setTargetColumn(column).setTargetGap(gap).setVertical(vertical).build();
        Netcode.TurnRequest request = Netcode.TurnRequest.newBuilder()
                .setDabGameTurn(turn).setMatchToken(matchToken).setUserToken(UserToken.userToken).build();

        final Netcode.TurnResponse[] responses = {null};

        StreamObserver<Netcode.TurnResponse> observer = new StreamObserver<Netcode.TurnResponse>() {
            @Override
            public void onNext(Netcode.TurnResponse turnResponse) {
                //System.out.println("turnResponse: " + turnResponse);
                responses[0] = turnResponse;
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("turnResponseError: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("submitTurn rpc Complete");
                latch.countDown();
            }
        };

        try {
            stub.submitTurn(request, observer);
            latch.await();
        }
        catch (Exception e) {
            System.out.println("submitTurn GRPC Failed: " + e);
            e.printStackTrace();
            return null;
        }

        return responses[0];
    }

    public Netcode.TurnResponse submitTurn(HalfMove halfMove) {
        if(halfMove.getOrientation() == HalfMove.LineOrientation.VERTICAL) {
            return submitTurn(halfMove.getColumnIndex(), halfMove.getGapIndex(), Orientation.VERTICAL);
        }
        else {
            return submitTurn(halfMove.getColumnIndex(), halfMove.getGapIndex(), Orientation.HORIZONTAL);
        }
    }

    public void closeChannel() {
        channel.shutdownNow();
    }
}


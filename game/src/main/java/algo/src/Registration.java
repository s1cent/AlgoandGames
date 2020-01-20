package algo.src;

import io.grpc.*;
import io.grpc.stub.StreamObserver;
import netcode.GameComGrpc;
import netcode.Netcode;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

// Only userRegistration and getUserToken are important for you
public class Registration {

    public static void main(String[] args) {
        userRegistration();
        getUserToken();
    }

    //
    public static void userRegistration() {
        System.out.println("userRegistration called!");

        String url = "129.27.202.46";
        int port = 80;

        if(UserRegistrationSettings.email.isEmpty()) {
            System.out.println("You fucked up. Read the readme again.");
            return;
        }

        CountDownLatch latch = new CountDownLatch(1);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(url, port).usePlaintext().build();
        GameComGrpc.GameComStub stub = GameComGrpc.newStub(channel);
        Netcode.UserRegistrationRequest request = Netcode.UserRegistrationRequest.newBuilder()
                .setEmail(UserRegistrationSettings.email).setFullname(UserRegistrationSettings.full_name)
                .setMatrNumber(UserRegistrationSettings.matrNr).setSecret(UserRegistrationSettings.secret).build();

        StreamObserver<Netcode.UserRegistrationResponse> observer = new StreamObserver<Netcode.UserRegistrationResponse>() {
            @Override
            public void onNext(Netcode.UserRegistrationResponse userRegistrationResponse) {
                System.out.println("userRegistrationResponse: " + userRegistrationResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("userRegistrationError: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("userRegistration rpc Complete");
                latch.countDown();
            }
        };


        try {
            stub.userRegistration(request, observer);
            latch.await();
        }
        catch (Exception e) {
            System.out.println("userRegistration GRPC Failed: " + e);
            e.printStackTrace();
        }

        System.out.println("Successful user registration!");
    }


    public static void getUserToken() {
        System.out.println("getUserToken called!");

        String url = "129.27.202.46";
        int port = 80;

        String matrNr = UserRegistrationSettings.matrNr;
        String secret = UserRegistrationSettings.secret;

        CountDownLatch latch = new CountDownLatch(1);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(url, port).usePlaintext().build();
        GameComGrpc.GameComStub stub = GameComGrpc.newStub(channel);
        Netcode.AuthPacket authPacket = Netcode.AuthPacket.newBuilder().setMatrNumber(matrNr).setSecret(secret).build();

        StreamObserver<Netcode.GetUserTokenResponse> response = new StreamObserver<Netcode.GetUserTokenResponse>() {
            @Override
            public void onNext(Netcode.GetUserTokenResponse getUserTokenResponse) {
                System.out.println("getUserTokenResponse: " + getUserTokenResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("getUserTokenError: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("getUserToken rpc complete");
                latch.countDown();
            }
        };

        try {
            stub.getUserToken(authPacket, response);
            latch.await();
        }
        catch (Exception e) {
            System.out.println("getUserToken GRPC Failed: " + e);
            e.printStackTrace();
        }

        System.out.println("Successful get user token!");
    }

    // DO NOT RUN THIS. I WILL DO IT
    public static void groupRegistration() {
        System.out.println("groupRegistration called!");

        String url = "129.27.202.46";
        int port = 80;

        String matrNr = "01635074";
        String secret = UserRegistrationSettings.secret;
        List<String> groupMatrNrs = new Vector<>();
        groupMatrNrs.add("01635074");
        groupMatrNrs.add("01611738");
        groupMatrNrs.add("01616164");
        groupMatrNrs.add("01532012");
        groupMatrNrs.add("01606562");

        CountDownLatch latch = new CountDownLatch(1);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(url, port).usePlaintext().build();
        GameComGrpc.GameComStub stub = GameComGrpc.newStub(channel);
        Netcode.AuthPacket authPacket = Netcode.AuthPacket.newBuilder().setMatrNumber(matrNr).setSecret(secret).build();
        Netcode.GroupRegistrationRequest request = Netcode.GroupRegistrationRequest.newBuilder()
                .addAllMatrNumber(groupMatrNrs).setAuth(authPacket).build();

        StreamObserver<Netcode.GroupRegistrationResponse> response = new StreamObserver<Netcode.GroupRegistrationResponse>() {
            @Override
            public void onNext(Netcode.GroupRegistrationResponse groupRegistrationResponse) {
                System.out.println("groupRegistrationResponse: " + groupRegistrationResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("groupRegistrationError: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("groupRegistration rpc complete");
                latch.countDown();
            }
        };

        try {
            stub.groupRegistration(request, response);
            latch.await();
        }
        catch (Exception e) {
            System.out.println("GroupRegistration GRPC Failed: " + e);
            e.printStackTrace();
        }

        System.out.println("Successful group registration!");
    }

    // DO NOT RUN THIS. I WILL DO IT
    public static void setGroupPseudonym() {
        System.out.println("setGroupPseudonym called!");

        String url = "129.27.202.46";
        int port = 80;

        String matrNr = "01635074";
        String secret = UserRegistrationSettings.secret;
        String pseudonym = "Pseudo_Gang";

        CountDownLatch latch = new CountDownLatch(1);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(url, port).usePlaintext().build();
        GameComGrpc.GameComStub stub = GameComGrpc.newStub(channel);
        Netcode.AuthPacket authPacket = Netcode.AuthPacket.newBuilder().setMatrNumber(matrNr).setSecret(secret).build();
        Netcode.SetPseudonymRequest request = Netcode.SetPseudonymRequest.newBuilder().setAuth(authPacket).setPseudonym(pseudonym).build();

        StreamObserver<Netcode.SetPseudonymResponse> response = new StreamObserver<Netcode.SetPseudonymResponse>() {
            @Override
            public void onNext(Netcode.SetPseudonymResponse setPseudonymResponse) {
                System.out.println("setPseudonymResponse: " + setPseudonymResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("setPseudonymError: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("setGroupPseudonym rpc complete");
                latch.countDown();
            }
        };

        try {
            stub.setGroupPseudonym(request, response);
            latch.await();
        }
        catch (Exception e) {
            System.out.println("setGroupPseudonym GRPC Failed: " + e);
        }

        System.out.println("Successful set group pseudonym!");
    }




}

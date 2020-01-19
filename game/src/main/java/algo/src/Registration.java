package algo.src;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import netcode.GameComGrpc;
import netcode.Netcode;

import java.util.List;
import java.util.Vector;

// Only userRegistration and getUserToken are important for you
public class Registration {

    //
    public void userRegistration() {
        System.out.println("userRegistration called!");

        String url = "129.27.202.46";
        int port = 80;

        if(UserRegistrationSettings.email.isEmpty()) {
            System.out.println("You fucked up. Read the readme again.");
            return;
        }

        ManagedChannel channel = ManagedChannelBuilder.forAddress(url, port).build();

        GameComGrpc.GameComBlockingStub stub = GameComGrpc.newBlockingStub(channel);

        Netcode.UserRegistrationRequest request = Netcode.UserRegistrationRequest.newBuilder()
                .setEmail(UserRegistrationSettings.email).setFullname(UserRegistrationSettings.full_name)
                .setMatrNumber(UserRegistrationSettings.matrNr).setSecret(UserRegistrationSettings.secret).build();

        StreamObserver<Netcode.UserRegistrationResponse> observer = new StreamObserver<Netcode.UserRegistrationResponse>() {
            @Override
            public void onNext(Netcode.UserRegistrationResponse userRegistrationResponse) {
                System.out.println(userRegistrationResponse.getErrorCode().getDescriptorForType());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {

            }
        };


        try {
            Netcode.UserRegistrationResponse response = stub.userRegistration(request);
            System.out.println(response.getErrorCode());
        }
        catch (Exception e) {
            System.out.println("GRPC Failed: " + e);
        }

        System.out.println("Successful user registration!");
    }


    public void getUserToken() {
        System.out.println("getUserToken called!");

        String url = "129.27.202.46";
        int port = 80;

        String matrNr = UserRegistrationSettings.matrNr;
        String secret = UserRegistrationSettings.secret;

        ManagedChannel channel = ManagedChannelBuilder.forAddress(url, port).build();

        GameComGrpc.GameComStub stub = GameComGrpc.newStub(channel);

        Netcode.AuthPacket authPacket = Netcode.AuthPacket.newBuilder().setMatrNumber(matrNr).setSecret(secret).build();

        StreamObserver<Netcode.GetUserTokenResponse> response = new StreamObserver<Netcode.GetUserTokenResponse>() {
            @Override
            public void onNext(Netcode.GetUserTokenResponse getUserTokenResponse) {
                System.out.println(getUserTokenResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Complete");
            }
        };

        try {
            stub.getUserToken(authPacket, response);
        }
        catch (Exception e) {
            System.out.println("GRPC Failed: " + e);
        }

        System.out.println("Successful get user token!");
    }

    // DO NOT RUN THIS. I ALREADY DID IT
    public void groupRegistration() {
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


        ManagedChannel channel = ManagedChannelBuilder.forAddress(url, port).build();

        GameComGrpc.GameComStub stub = GameComGrpc.newStub(channel);

        Netcode.AuthPacket authPacket = Netcode.AuthPacket.newBuilder().setMatrNumber(matrNr).setSecret(secret).build();

        Netcode.GroupRegistrationRequest request = Netcode.GroupRegistrationRequest.newBuilder()
                .addAllMatrNumber(groupMatrNrs).setAuth(authPacket).build();

        StreamObserver<Netcode.GroupRegistrationResponse> response = new StreamObserver<Netcode.GroupRegistrationResponse>() {
            @Override
            public void onNext(Netcode.GroupRegistrationResponse groupRegistrationResponse) {
                System.out.println(groupRegistrationResponse.getErrorCode().getDescriptorForType());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Complete");
            }
        };

        try {
            stub.groupRegistration(request, response);
        }
        catch (Exception e) {
            System.out.println("GRPC Failed: " + e);
        }

        System.out.println("Successful group registration!");
    }

    // DO NOT RUN THIS. I ALREADY DID IT
    public void setGroupPseudonym() {
        System.out.println("setGroupPseudonym called!");

        String url = "129.27.202.46";
        int port = 80;

        String matrNr = "01635074";
        String secret = UserRegistrationSettings.secret;
        String pseudonym = "Pseudo_Gang";

        ManagedChannel channel = ManagedChannelBuilder.forAddress(url, port).build();

        GameComGrpc.GameComStub stub = GameComGrpc.newStub(channel);

        Netcode.AuthPacket authPacket = Netcode.AuthPacket.newBuilder().setMatrNumber(matrNr).setSecret(secret).build();

        Netcode.SetPseudonymRequest request = Netcode.SetPseudonymRequest.newBuilder().setAuth(authPacket).setPseudonym(pseudonym).build();

        StreamObserver<Netcode.SetPseudonymResponse> response = new StreamObserver<Netcode.SetPseudonymResponse>() {
            @Override
            public void onNext(Netcode.SetPseudonymResponse setPseudonymResponse) {
                System.out.println(setPseudonymResponse.getErrorCode().getDescriptorForType());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Complete");
            }
        };

        try {
            stub.setGroupPseudonym(request, response);
        }
        catch (Exception e) {
            System.out.println("GRPC Failed: " + e);
        }

        System.out.println("Successful set group pseudonym!");
    }




}

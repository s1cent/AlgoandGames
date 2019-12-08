package algo.src;

import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;



public class Game extends Application{

        public static void main(String[] args) {

            launch(args);
        }

        @Override
        public void start(Stage stage) throws Exception {

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
            stage.setTitle("Registration Form FXML Application");
            stage.setScene(new Scene(root));
            stage.show();

        }

}

package algo.src;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class menuController {

        @FXML
        private Button submitButton;

        @FXML
        private ChoiceBox widthBox;

        @FXML
        private ChoiceBox heightBox;

        @FXML
        protected void getsClicked(ActionEvent event) throws IOException {

            System.out.println("Width: " + widthBox.getValue() + " height:" + heightBox.getValue());
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("inGame.fxml"));
            Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stageTheEventSourceNodeBelongs.setScene(new Scene(root, 600 ,400));
        }
}


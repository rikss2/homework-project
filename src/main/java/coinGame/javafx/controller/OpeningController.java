package coinGame.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import org.tinylog.Logger;

/**
 * FXML Controller for {@code opening.fxml}.
 */
public class OpeningController {
    @FXML
    private Button start;
    @FXML
    private Label title;
    @FXML
    private Label namePromt;
    @FXML
    private TextField playerName;
    @FXML
    private Label errorLabel;

    /**
     * Changes the stage to display the game board.
     * @param actionEvent this is an on Button click event
     * @throws IOException
     */
    public void startAction(ActionEvent actionEvent) throws IOException {
        if (playerName.getText().isEmpty()) {
            errorLabel.setText("Please enter your name!");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/game.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<GameController>getController().setPlayerName(playerName.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            Logger.info("The user's name is set to {}, loading game scene", playerName.getText()); // TODO
        }
    }

    @FXML
    private Button scoreboard;

    /**
     * Changes the stage to the scoreboard.
     * @param actionEvent this is an on Button click event
     * @throws IOException
     */
    public void toScoreboard(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/scoreBoard.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }
}

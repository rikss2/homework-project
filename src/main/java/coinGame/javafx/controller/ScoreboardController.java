package coinGame.javafx.controller;

import coinGame.jdbi.DataBaseManager;
import coinGame.jdbi.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * FXML Controller for {@code scoreBoard.fxml}.
 */
public class ScoreboardController {
    @FXML
    private Label LeaderBoardTitle;
    @FXML
    private TextFlow scoreSpace;

    @FXML
    private void initialize() {
        DataBaseManager dbm = new DataBaseManager();
        dbm.createTable();
        ArrayList<Player> scores = dbm.getScores();
        int rank = 0;
        for (Player player : scores) {
            rank++;
            scoreSpace.getChildren().add(new Text(rank + ". " + player.getName() + (player.isWon()?" won":" lost") +" with " + player.getSteps() + " points\n"));
        }
    }

    @FXML
    private Button backButton;

    /**
     * Changes the stage back to the menu.
     * @param actionEvent this is an on Button click event
     * @throws IOException
     */
    public void backAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/opening.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

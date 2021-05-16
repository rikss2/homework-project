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
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.util.ArrayList;

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
            scoreSpace.getChildren().add(new Text(rank + ". " + player.getName() + " with " + player.getSteps() + " points\n"));
        }
    }

    @FXML
    private Button backButton;

    public void backAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/opening.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

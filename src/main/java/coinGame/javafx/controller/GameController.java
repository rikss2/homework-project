package coinGame.javafx.controller;

import coinGame.jdbi.DataBaseManager;
import coinGame.model.Position;
import coinGame.model.GameModel;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        public SelectionPhase alter() {
            return switch (this) {
                case SELECT_FROM -> SELECT_TO;
                case SELECT_TO -> SELECT_FROM;
            };
        }
    }

    private SelectionPhase selectionPhase = SelectionPhase.SELECT_FROM;
    private final List<Position> selectablePositions = new ArrayList<>();
    private Position selected;
    private String playerName;

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    private final GameModel model = new GameModel();

    @FXML
    private Label StepsLabel;
    @FXML
    private Label stepCounterLabel;

    @FXML
    private GridPane board;

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

    @FXML
    private void initialize() {
        createBoard();
        createPieces();
        setSelectablePositions();
        showSelectablePositions();
        stepCounterLabel.textProperty().bind(model.gameState.steps.asString());
    }

    private void createBoard() {
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare();
                board.add(square, j, i);
            }
        }
        model.gameState.ended.addListener(this::handleGameEnded);

    }

    private void handleGameEnded(ObservableValue<? extends Boolean> observableValue, boolean oldValue, boolean newValue) {
        if (newValue) {
            Logger.info("Player {} has ended the game in {} steps", playerName, model.gameState.steps.get());
            Logger.info("The game was {}", Won() ? "won" : "Lost");
            DataBaseManager dbm = new DataBaseManager();
            dbm.createTable();
            dbm.addPlayer(playerName, model.gameState.steps.get());

        }
    }

    public boolean Won() {
        for (Position position : GameModel.GOAL_POSITIONS) {
            if (model.getCoinNumber(position).isEmpty()) return false;
        }
        return true;
    }

    private StackPane createSquare() {
        var square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    private void createPieces() {
        for (int i = 0; i < GameModel.COIN_QUANTITY; i++) {
            model.positionProperty(i).addListener(this::piecePositionChange);
            var coin = new Circle(25);
            coin.setFill(Color.BLACK);
            getSquare(model.gameState.getCoins().get(i).positionProperty().get()).getChildren().add(coin);
        }
    }

    private void piecePositionChange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition) {
        Logger.debug("Move: {} -> {}", oldPosition, newPosition);
        StackPane oldSquare = getSquare(oldPosition);
        StackPane newSquare = getSquare(newPosition);
        newSquare.getChildren().addAll(oldSquare.getChildren());
        oldSquare.getChildren().clear();
        model.gameState.steps.set(model.gameState.steps.get() + 1);
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        if (model.gameState.ended.get()) return;
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        var position = new Position(row, col);
        Logger.debug("Click on square {}", position);
        handleClickOnSquare(position);
    }

    private void handleClickOnSquare(Position position) {
        switch (selectionPhase) {
            case SELECT_FROM -> {
                if (selectablePositions.contains(position)) {
                    selectPosition(position);
                    alterSelectionPhase();
                }
            }
            case SELECT_TO -> {
                if (selectablePositions.contains(position)) {
                    var coinNumber = model.getCoinNumber(selected).getAsInt();
                    Logger.debug("Moving piece {} {}", coinNumber, selected.toString());
                    model.gameState.getCoins().get(coinNumber).moveTo(position);
                    deselectSelectedPosition();
                    alterSelectionPhase();

                }
            }
        }
    }

    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        setSelectablePositions();
        showSelectablePositions();
    }

    private void setSelectablePositions() {
        selectablePositions.clear();
        switch (selectionPhase) {
            case SELECT_FROM -> {
                for (int i = 0; i < GameModel.COIN_QUANTITY; i++) {
                    if (model.moveable(i)) selectablePositions.add(model.positionProperty(i).get());
                }
            }
            case SELECT_TO -> {
                var pieceNumber = model.getCoinNumber(selected).getAsInt();
                selectablePositions.addAll(model.getValidMoves(pieceNumber));
            }
        }
        if (selectablePositions.isEmpty()) model.gameState.ended.set(true);
    }

    private void deselectSelectedPosition() {
        hideSelectedPosition();
        selected = null;
    }

    private void showSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().add("selectable");
        }
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().remove("selectable");
        }
    }

    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

    private void selectPosition(Position position) {
        selected = position;
        showSelectedPosition();
    }

    private void showSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().add("selected");
    }

    private void hideSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().remove("selected");
    }
}

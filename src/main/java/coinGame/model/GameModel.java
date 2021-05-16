package coinGame.model;

import javafx.beans.property.ObjectProperty;

import java.util.*;

public class GameModel {
    public static int BOARD_SIZE = 4;
    public static int COIN_QUANTITY = 4;

    public State gameState;

    public GameModel() {
        gameState = new State(BOARD_SIZE);
    }


    public OptionalInt getCoinNumber(Position position) {
        for (int i = 0; i < COIN_QUANTITY; i++) {
            if (gameState.getCoins().get(i).positionProperty().get().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (var coin : gameState.getCoins()) {
            joiner.add(coin.toString());
        }
        return joiner.toString();
    }

    public ObjectProperty<Position> positionProperty(int coinNumber) {
        return gameState.getCoins().get(coinNumber).positionProperty();
    }

    public boolean moveable(int coinNumber){
        boolean result = false;
        Position position = gameState.getCoins().get(coinNumber).positionProperty().get();
        Position nextPosition = position;
        for (Direction direction : Direction.values())
        {
            nextPosition = position.moveTowards(direction);
            if (!getCoinNumber(nextPosition).isEmpty()) result = true;
        }
        return result;
    }
    public ArrayList<Position> getValidMoves(int coinNumber) {
        Position position = gameState.getCoins().get(coinNumber).positionProperty().get();
        Position nextPosition = position;
        ArrayList<Position> validMoves = new ArrayList<>(BOARD_SIZE * BOARD_SIZE);
        if (!moveable(coinNumber)) return validMoves;
        for (Direction direction : Direction.values()) {
            nextPosition = position.moveTowards(direction);
            while (Position.isOnBoard(nextPosition) && getCoinNumber(nextPosition).isEmpty()) {
                validMoves.add(nextPosition);
                nextPosition = nextPosition.moveTowards(direction);
            }
        }

        return validMoves;
    }


    public static void main(String[] args) {
        GameModel gameModel = new GameModel();
        System.out.println(gameModel.getValidMoves(0));
    }
}

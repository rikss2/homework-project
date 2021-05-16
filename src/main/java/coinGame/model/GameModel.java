package coinGame.model;

import javafx.beans.property.ObjectProperty;

import java.util.*;

/**
 * Represents the rules by possible operators.
 */
public class GameModel {

    /**
     * The size of the Board determined by the rules.
     */
    public static int BOARD_SIZE = 4;
    /**
     * The number of coins on the board.
     */
    public static int COIN_QUANTITY = 4;
    /**
     * These are the places where the coins should be at the end in order to win.
     */
    public static Position[] GOAL_POSITIONS = {new Position(0, 0), new Position(0, BOARD_SIZE), new Position(BOARD_SIZE, 0), new Position(BOARD_SIZE, BOARD_SIZE)};

    /**
     * The state of the game at the time.
     */
    public State gameState;

    /**
     * The creation of the board and the coins.
     */
    public GameModel() {
        gameState = new State(BOARD_SIZE);
    }


    /**
     * The index of the coin at that position.
     * @param position Position
     * @return {@code int index}
     */
    public OptionalInt getCoinNumber(Position position) {
        for (int i = 0; i < COIN_QUANTITY; i++) {
            if (gameState.getCoins().get(i).positionProperty().get().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    /**
     * @return String representation of the game state.
     */
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (var coin : gameState.getCoins()) {
            joiner.add(coin.toString());
        }
        return joiner.toString();
    }

    /**
     * Returns the position of the coin with the {@code index}.
     * @param coinNumber the index of the coin
     * @return Position
     */
    public ObjectProperty<Position> positionProperty(int coinNumber) {
        return gameState.getCoins().get(coinNumber).positionProperty();
    }

    /**
     * Returns whether the coin is moveable by the rules.
     * @param coinNumber the index of the coin
     * @return boolean
     */
    public boolean moveable(int coinNumber) {
        boolean result = false;
        Position position = gameState.getCoins().get(coinNumber).positionProperty().get();
        Position nextPosition = position;
        for (Direction direction : Direction.values()) {
            nextPosition = position.moveTowards(direction);
            if (!getCoinNumber(nextPosition).isEmpty()) result = true;
        }
        return result;
    }

    /**
     * Returns all possible Position where the coin can be placed.
     * @param coinNumber the index of the coin
     * @return {@code Position}
     */
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
}

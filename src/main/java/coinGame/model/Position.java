package coinGame.model;

import java.util.Objects;

import static java.util.Objects.hash;

/**
 * Represents a place on the 2D plane.
 * @param row X coordinate
 * @param col Y coordinate
 */
public record Position(int row, int col) {

    /**
     * Determines if the place is on the board.
     * @param position
     * @return boolean
     */
    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < GameModel.BOARD_SIZE
                && 0 <= position.col() && position.col() < GameModel.BOARD_SIZE;
    }

    /**
     * @return the String representation of the place.
     */
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

    /**
     * Returns a place in the direction 1 step away.
     * @param direction from {@code enum direction}
     * @return position
     */
    public Position moveTowards(Direction direction){
        return switch (direction){
            case up -> new Position(row-1, col);
            case down -> new Position(row+1, col);
            case left -> new Position(row, col-1);
            case right ->new Position(row, col+1);
        };
    }
    @Override
    public boolean equals(Object o){
        if (o == this)return true;
        return (o instanceof Position position) && position.col==this.col && position.row == this.row;
    }

    @Override
    public int hashCode() {
        return hash(row, col);
    }
}

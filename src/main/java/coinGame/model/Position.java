package coinGame.model;

import java.util.Objects;

import static java.util.Objects.hash;

public record Position(int row, int col) {

    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < GameModel.BOARD_SIZE
                && 0 <= position.col() && position.col() < GameModel.BOARD_SIZE;
    }

    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

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

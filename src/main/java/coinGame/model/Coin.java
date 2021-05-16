package coinGame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a coin on the game board.
 */
public class Coin {
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    /**
     * A new coin with th following position.
     * @param position
     */
    public Coin(Position position) {
        this.position.set(position);
    }

    /**
     * @return position of this coin
     */
    public Position getPosition() {
        return position.get();
    }

    /**
     * Displaces the coin.
     * @param nextPosition to place the coin.
     */
    public void moveTo(Position nextPosition) {
        position.set(nextPosition);

    }

    /**
     * Printable representation of the coin.
     * @return String
     */
    public String toString() {
        return position.get().toString();
    }

    /**
     * Access to the property.
     * @return Position
     */
    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        return (o instanceof Coin coin) && coin.positionProperty().get().equals(this.positionProperty().get());

    }
}

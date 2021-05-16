package coinGame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Coin {
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    public Coin(Position position) {
        this.position.set(position);
    }

    public Position getPosition() {
        return position.get();
    }

    public void moveTo(Position nextPosition) {
        position.set(nextPosition);

    }

    public String toString() {
        return position.get().toString();
    }

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

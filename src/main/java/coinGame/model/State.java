package coinGame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of the current state of the game.
 */
public class State {
    private final ArrayList<Coin> coins;
    /**
     * Property of the current game: whether it has ended.
     */
    public final ObjectProperty<Boolean> ended = new SimpleObjectProperty<>(false);
    /**
     * Property of the current game: how many steps are taken.
     */
    public final ObjectProperty<Integer> steps = new SimpleObjectProperty<>(0);

    /**
     * Sets up the Initial state.
     * @param size
     */
    State(int size)
    {
        this.coins = new ArrayList<>(size);
        CreateInitialState();
    }

    private void CreateInitialState(){
        coins.add(new Coin(new Position(1, 1)));
        coins.add(new Coin(new Position(1, 2)));
        coins.add(new Coin(new Position(2, 1)));
        coins.add(new Coin(new Position(2, 2)));
    }

    /**
     * @return the list of the 4 coins.
     */
    public ArrayList<Coin> getCoins(){
        return coins;
    }

    /**
     * @return the position of the 4 coins.
     */
    public List<Position> getCoinsPosition() {
        List<Position> positions = new ArrayList<>(GameModel.COIN_QUANTITY);
        for (var coin : getCoins()) {
            positions.add(coin.getPosition());
        }
        return positions;
    }
}

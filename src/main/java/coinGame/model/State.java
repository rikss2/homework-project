package coinGame.model;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class State {
    private final ArrayList<Coin> coins;
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
    public ArrayList<Coin> getCoins(){
        return coins;
    }

    public List<Position> getCoinsPosition() {
        List<Position> positions = new ArrayList<>(GameModel.COIN_QUANTITY);
        for (var coin : getCoins()) {
            positions.add(coin.getPosition());
        }
        return positions;
    }
}

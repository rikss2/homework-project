package coinGame.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    @Test
    void testGetCoinsPosition() {
        State gameState = new State(GameModel.BOARD_SIZE);
        Position[] positionArray = {
                new Position(1,1),
                new Position(1, 2),
                new Position(2, 1),
                new Position(2, 2)};

        ArrayList<Position> positionList = new ArrayList(Arrays.asList(positionArray));
        assertEquals(positionList, gameState.getCoinsPosition());
        gameState.getCoins().get(0).moveTo(new Position(0, 0));
        positionList.set(0, new Position(0, 0));
        assertEquals(positionList, gameState.getCoinsPosition());
    }
}
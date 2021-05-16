package coinGame.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    @Test
    void testGetCoinNumber() {
        GameModel gameModel = new GameModel();
        assertEquals(OptionalInt.of(0), gameModel.getCoinNumber(new Position(1, 1)));
        assertEquals(OptionalInt.empty(), gameModel.getCoinNumber(new Position(0, 1)));
        gameModel.gameState.getCoins().get(0).moveTo(new Position(0, 0));
        assertEquals(OptionalInt.of(0), gameModel.getCoinNumber(new Position(0, 0)));
        assertEquals(OptionalInt.empty(), gameModel.getCoinNumber(new Position(1, 1)));
        assertEquals(OptionalInt.empty(), gameModel.getCoinNumber(null));
        assertEquals(OptionalInt.empty(), gameModel.getCoinNumber(new Position(-1, 1)));
    }

    @Test
    void testPositionProperty() {
        GameModel gameModel = new GameModel();
        assertEquals(new Position(1, 1), gameModel.positionProperty(0).get());
        gameModel.gameState.getCoins().get(0).moveTo(new Position(0, 0));
        assertEquals(new Position(0, 0), gameModel.positionProperty(0).get());
        assertThrows(IndexOutOfBoundsException.class ,()-> gameModel.positionProperty(-1));


    }

    @Test
    void testGetValidMoves() {
        GameModel gameModel = new GameModel();

        HashSet<Position> positionHashSet = new HashSet<>();
        positionHashSet.add(new Position(1, 0));
        positionHashSet.add(new Position(0, 1));
        assertEquals(positionHashSet, new HashSet<Position>(gameModel.getValidMoves(0)));

        gameModel.gameState.getCoins().get(0).moveTo(new Position(0, 0));
        assertEquals( new HashSet<Position>(), new HashSet<Position>(gameModel.getValidMoves(0)));
        positionHashSet.clear();
        positionHashSet.add(new Position(2, 0));
        positionHashSet.add(new Position(3, 0));
        positionHashSet.add(new Position(1, 1));
        positionHashSet.add(new Position(1, 2));
        positionHashSet.add(new Position(1, 3));
        gameModel.gameState.getCoins().get(1).moveTo(new Position(1, 0));
        assertEquals( positionHashSet, new HashSet<Position>(gameModel.getValidMoves(1)));

    }


    @Test
    void testToString() {
        GameModel gameModel = new GameModel();
        String expectedInitial = "[(1,1),(1,2),(2,1),(2,2)]";
        assertEquals(expectedInitial, gameModel.toString());
        gameModel.gameState.getCoins().get(0).moveTo(new Position(0, 0));
        String expected = "[(0,0),(1,2),(2,1),(2,2)]";
        assertEquals(expected, gameModel.toString());
    }
}
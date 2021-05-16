package coinGame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinTest {

    @Test
    void moveTo() {
        Coin coin = new Coin(new Position(2, 1));
        coin.moveTo(new Position(0,2));
        assertEquals(new Coin(new Position(0, 2)), coin);
    }

    @Test
    void testEquals() {
        Coin coin = new Coin(new Position(0, 0));
        assertTrue(coin.equals(coin));
        Coin other = new Coin(new Position(0, 0));
        assertTrue(coin.equals(other));
        assertFalse(coin.equals(null));
        assertFalse(coin.equals("foo"));
        Coin fake = new Coin(new Position(5, 6));
        assertFalse(coin.equals(fake));

    }
}
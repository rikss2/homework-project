package coinGame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testEquals() {
        Position position = new Position(0, 0);
        assertTrue(position.equals(position));
        assertTrue(position.equals(new Position(0,0)));
        assertFalse(position.equals(null));
        assertFalse(position.equals("foo"));
        assertFalse(position.equals(new Position(1, 0)));
    }

    @Test
    void moveTowards() {
        Position position = new Position(0, 0);
        assertEquals(new Position(-1, 0), position.moveTowards(Direction.up));
        assertEquals(new Position(1, 0), position.moveTowards(Direction.down));
        assertEquals(new Position(0, -1), position.moveTowards(Direction.left));
        assertEquals(new Position(0, 1), position.moveTowards(Direction.right));
    }

    @Test
    void testHashCode() {
        Position position = new Position(0,0);
        Position other = new Position(0,0);
        assertTrue(position.hashCode() == other.hashCode());
        other = new Position(1, 1);
        assertFalse(position.hashCode() == other.hashCode());
    }
    @Test
    void testIsOnBoard() {
        assertFalse(Position.isOnBoard(new Position(5, 1)));
        assertFalse(Position.isOnBoard(new Position(-1, 1)));
        assertFalse(Position.isOnBoard(new Position(3, -1)));
        assertFalse(Position.isOnBoard(new Position(3, 5)));
        assertTrue(Position.isOnBoard(new Position(3, 3)));
        assertThrows(NullPointerException.class, () -> Position.isOnBoard(null));
    }
}
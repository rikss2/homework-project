package coinGame.jdbi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Each player who finishes.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Player {
    private String name;
    private int steps;
    private boolean won;
}
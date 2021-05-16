package coinGame.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.util.ArrayList;

/**
 * Defines a disposable accessor for the DAO.
 */
public class DataBaseManager {
    private static Jdbi jdbi;

    /**
     * Sets up database access.
     */
    public DataBaseManager() {
        jdbi = Jdbi.create("jdbc:h2:file:~/.coinGame/leaderboard", "leaderboard", "");
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.installPlugin(new H2DatabasePlugin());
    }

    /**
     * Creates a table for storing player data.
     */
    public static void createTable() {
        try {
            jdbi.withExtension(ScoreBoardDAO.class, dao -> {
                dao.createTable();
                return true;
            });
        } catch (Exception e) {
            Logger.info("table exists");
        }
    }

    /**
     * Adds the following player data to the table.
     * @param playerName
     * @param steps
     * @param won
     */
    public void addPlayer(String playerName, int steps, boolean won) {
        jdbi.withExtension(ScoreBoardDAO.class, dao -> {
            dao.addPlayer(playerName, steps, won);
            return true;
        });

    }

    /**
     * @return List of played games of all players
     */
    public ArrayList<Player> getScores() {
        return (ArrayList<Player>) jdbi.withExtension(ScoreBoardDAO.class, DAO -> DAO.getPlayers());
    }
}

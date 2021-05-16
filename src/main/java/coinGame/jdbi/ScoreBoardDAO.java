package coinGame.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * Interface to the H2 SQL database.
 */
@RegisterBeanMapper(Player.class)
public interface ScoreBoardDAO
{
    /**
     * Creates the {@code table(id, name, steps, won)} if it doesnt exist.
     */
    @SqlUpdate("CREATE TABLE leaderboard (id IDENTITY NOT NULL PRIMARY KEY, name VARCHAR, steps INTEGER, won BOOLEAN)")
    void createTable();


    /**
     * Adds a player to the table.
     * @param name
     * @param steps
     * @param won
     */
    @SqlUpdate("INSERT INTO leaderboard (name,steps,won) VALUES (:name, :steps, :won)")
    void addPlayer(@Bind("name") String name, @Bind("steps") int steps, @Bind("won") boolean won);


    /**
     * @return The List of players from the table.
     */
    @SqlQuery("SELECT * FROM leaderboard ORDER BY steps DESC")
    List<Player> getPlayers();

}
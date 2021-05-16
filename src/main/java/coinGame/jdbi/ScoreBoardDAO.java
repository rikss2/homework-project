package coinGame.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterBeanMapper(Player.class)
public interface ScoreBoardDAO
{
    @SqlUpdate("CREATE TABLE leaderboard (id IDENTITY NOT NULL PRIMARY KEY, name VARCHAR, steps INTEGER)")
    void createTable();

    @SqlUpdate("INSERT INTO leaderboard (name,steps) VALUES (:name, :steps)")
    void addPlayer(@Bind("name") String name, @Bind("steps") int steps);

    @SqlQuery("SELECT * FROM leaderboard ORDER BY steps DESC")
    List<Player> getPlayers();

}
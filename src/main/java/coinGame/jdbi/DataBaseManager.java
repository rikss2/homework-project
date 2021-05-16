package coinGame.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.util.ArrayList;

public class DataBaseManager {
    private static Jdbi jdbi;
    public DataBaseManager() {
        jdbi = Jdbi.create("jdbc:h2:file:~/.coinGame/leaderboard", "leaderboard", "");
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.installPlugin(new H2DatabasePlugin());
    }
    public static void createTable(){
        try {
            jdbi.withExtension(ScoreBoardDAO.class, dao -> {
                dao.createTable(); return true;
            });
        } catch (Exception e) {
            Logger.info("table exists");
        }
    }

    public void addPlayer(String playerName, int steps) {
        jdbi.withExtension(ScoreBoardDAO.class, dao -> {
            dao.addPlayer(playerName, steps);
            return true;
        });

    }

    public ArrayList<Player> getScores(){
        return (ArrayList<Player>) jdbi.withExtension(ScoreBoardDAO.class, DAO->DAO.getPlayers());
    }
}

package dataAccess;

import model.GameData;
import java.util.Collection;

public interface GameDAO {
    Collection<GameData> listGames();
    int createGame(String gameName) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    void updateGame(GameData game) throws DataAccessException;
    boolean gameExists(int gameID);
    void clear();
}
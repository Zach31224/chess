package dataAccess;

import model.GameData;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {
    private final Map<Integer, GameData> gameDatabase = new HashMap<>(); // key: gameID
    private int nextGameID = 1;

    public Collection<GameData> listGames() {
        return gameDatabase.values();
    }

    public int createGame(String gameName) throws DataAccessException {
        if (gameName == null || gameName.isEmpty()) {
            throw new DataAccessException("Error: bad request");
        }
        int gameID = nextGameID++;
        gameDatabase.put(gameID, new GameData(gameID, null, null, gameName, null));
        return gameID;
    }

    public GameData getGame(int gameID) throws DataAccessException {
        GameData game = gameDatabase.get(gameID);
        if (game == null) {
            throw new DataAccessException("Error: bad request");
        }
        return game;
    }

    public void updateGame(GameData game) throws DataAccessException {
        if (!gameDatabase.containsKey(game.gameID())) {
            throw new DataAccessException("Error: bad request");
        }
        gameDatabase.put(game.gameID(), game);
    }

    public boolean gameExists(int gameID) {
        return gameDatabase.containsKey(gameID);
    }

    public void clear() {
        gameDatabase.clear();
        nextGameID = 1;
    }
}
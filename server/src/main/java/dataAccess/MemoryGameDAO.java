package dataAccess;

import model.GameData;

import java.util.HashSet;

public class MemoryGameDAO implements GameDAO {

    private HashSet<GameData> games;

    public MemoryGameDAO() {
        games = HashSet.newHashSet(16);
    }

    @Override
    public HashSet<GameData> listGames() {
        return games;
    }

    @Override
    public void createGame(GameData game) {
        games.add(game);
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        for (GameData g : games) {
            if (g.gameID() == gameID) {
                return g;
            }
        }
        throw new DataAccessException("Game with ID " + gameID + " not found.");
    }

    @Override
    public boolean gameExists(int gameID) {
        for (GameData g : games) {
            if (g.gameID() == gameID) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateGame(GameData game) {
        try {
            GameData existing = getGame(game.gameID());
            games.remove(existing);
        } catch (DataAccessException e) {
            // no existing game, so just add new
        }
        games.add(game);
    }

    @Override
    public void clear() {
        games = HashSet.newHashSet(16);
    }
}

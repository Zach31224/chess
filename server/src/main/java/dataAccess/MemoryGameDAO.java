package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.HashSet;
import java.util.Set;

public class MemoryGameDAO implements GameDAO {

    HashSet<GameData> db;

    public MemoryGameDAO() {
        db = HashSet.newHashSet(16);
    }

    @Override
    public HashSet<GameData> listGames(String username) {
        HashSet<GameData> games = HashSet.newHashSet(16);
        for (GameData game : db) {
            if (game.whiteUsername().equals(username) ||
                    game.blackUsername().equals(username)) {
                games.add(game);
            }
        }
        return games;
    }

    @Override
    public void createGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        db.add(new GameData(gameID, whiteUsername, blackUsername, gameName, game));
    }

    @Override
    public void createGame(GameData game) {
        db.add(game);
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        for (GameData game : db) {
            if (game.gameID() == gameID) {
                return game;
            }
        }
        throw new DataAccessException("Game not found, id: " +gameID);
    }

    @Override
    public void clear() {
        db = HashSet.newHashSet(16);
    }

    @Override
    public Set<GameData> getGamesByUser(String username) {
        return Set.of();
    }

    @Override
    public void insertGame(int id, String whitePlayer, String blackPlayer, String name, ChessGame match) {

    }

    @Override
    public void insertGame(GameData game) {

    }

    @Override
    public GameData fetchGame(int id) throws DataAccessException {
        return null;
    }

    @Override
    public void reset() {

    }
}
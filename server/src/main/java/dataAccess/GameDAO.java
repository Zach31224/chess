package dataAccess;

import model.GameData;
import chess.ChessGame;

import java.util.HashSet;
import java.util.Set;

public interface GameDAO {

    HashSet<GameData> listGames(String username);

    void createGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game);

    void createGame(GameData game);

    GameData getGame(int gameID) throws DataAccessException;

    void clear();

    Set<GameData> getGamesByUser(String username);

    void insertGame(int id, String whitePlayer, String blackPlayer, String name, ChessGame match);

    void insertGame(GameData game);

    GameData fetchGame(int id) throws DataAccessException;

    void reset();
}

package service;

import dataaccess.DataAccessException;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import java.util.Collection;

public class GameService {
    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public GameService(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public Collection<GameData> listGames(String authToken) throws ServiceException {
        try {
            authDAO.getAuth(authToken);
            return gameDAO.listGames();
        } catch (DataAccessException e) {
            throw new ServiceException("HTTP 401 Error: unauthorized");
        }
    }

    public int createGame(String authToken, String gameName) throws ServiceException {
        try {
            authDAO.getAuth(authToken);
            if (gameName == null || gameName.isEmpty()) {
                throw new ServiceException("HTTP 400 Error: bad request");
            }
            return gameDAO.createGame(gameName);
        } catch (DataAccessException e) {
            throw new ServiceException("HTTP 401 Error: unauthorized");
        }
    }

    public void joinGame(String authToken, int gameID, String playerColor) throws ServiceException {
        try {
            AuthData auth = authDAO.getAuth(authToken);
            GameData game = gameDAO.getGame(gameID);

            if (playerColor != null) {
                String color = playerColor.toUpperCase();
                if (("WHITE".equals(color) && game.whiteUsername() != null) ||
                        ("BLACK".equals(color) && game.blackUsername() != null)) {
                    throw new ServiceException("HTTP 403 Error: already taken");
                }

                GameData updatedGame = new GameData(
                        game.gameID(),
                        "WHITE".equals(color) ? auth.username() : game.whiteUsername(),
                        "BLACK".equals(color) ? auth.username() : game.blackUsername(),
                        game.gameName(),
                        game.game()
                );
                gameDAO.updateGame(updatedGame);
            }
        } catch (DataAccessException e) {
            if (e.getMessage().contains("bad request")) {
                throw new ServiceException("HTTP 400 Error: bad request");
            }
            throw new ServiceException("HTTP 401 Error: unauthorized");
        }
    }
}
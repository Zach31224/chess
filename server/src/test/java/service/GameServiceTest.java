package service;

import dataAccess.*;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;

import java.util.Collection;

public class GameServiceTest {
    static GameService gameService;
    static GameDAO gameDAO;
    static AuthDAO authDAO;
    static AuthData authData;

    @BeforeAll
    static void init() throws DataAccessException {
        gameDAO = new MemoryGameDAO();
        authDAO = new MemoryAuthDAO();
        gameService = new GameService(gameDAO, authDAO);
        authData = new AuthData("Username", "authToken");
        authDAO.addAuth(authData);
    }

    @BeforeEach
    void setup() {
        gameDAO.clear();
    }

    @Test
    @DisplayName("Create Valid Game")
    void createGameTestPositive() throws Exception {
        int gameID1 = gameService.createGame(authData.authToken(), "game1");
        Assertions.assertTrue(gameDAO.gameExists(gameID1));

        int gameID2 = gameService.createGame(authData.authToken(), "game2");
        Assertions.assertNotEquals(gameID1, gameID2);
    }

    @Test
    @DisplayName("Create Invalid Game")
    void createGameTestNegative() {
        Assertions.assertThrows(ServiceException.class, () -> gameService.createGame("badToken", "game"));
    }

    @Test
    @DisplayName("Proper List Games")
    void listGamesTestPositive() throws Exception {
        int gameID1 = gameService.createGame(authData.authToken(), "game1");
        int gameID2 = gameService.createGame(authData.authToken(), "game2");

        Collection<GameData> games = gameService.listGames(authData.authToken());
        Assertions.assertEquals(2, games.size());
    }

    @Test
    @DisplayName("Improper List Games")
    void listGamesTestNegative() {
        Assertions.assertThrows(ServiceException.class, () -> gameService.listGames("badToken"));
    }

    @Test
    @DisplayName("Proper Join Game")
    void joinGameTestPositive() throws Exception {
        int gameID = gameService.createGame(authData.authToken(), "game");
        gameService.joinGame(authData.authToken(), gameID, "WHITE");
        GameData game = gameDAO.getGame(gameID);
        Assertions.assertEquals(authData.username(), game.whiteUsername());
    }

    @Test
    @DisplayName("Improper Join Game")
    void joinGameTestNegative() {
        Assertions.assertThrows(ServiceException.class, () -> gameService.joinGame("badToken", 1, "WHITE"));
        Assertions.assertThrows(ServiceException.class, () -> gameService.joinGame(authData.authToken(), 9999, "WHITE"));
        Assertions.assertThrows(ServiceException.class, () -> gameService.joinGame(authData.authToken(), 1, "INVALID"));
    }
}
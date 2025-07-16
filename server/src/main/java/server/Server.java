package server;

import com.google.gson.Gson;
import dataAccess.*;
import service.GameService;
import service.UserAuthService;
import spark.*;

public class Server {

    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    private final UserAuthService authService;
    private final GameService gameService;

    private final UserAuthHandler authHandler;
    private final GameHandler gameHandler;

    public Server() {
        // Initialize DAOs
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();

        // Initialize services with DAOs
        authService = new UserAuthService(userDAO, authDAO);
        gameService = new GameService(gameDAO);

        // Create route handlers with services
        authHandler = new UserAuthHandler(authService);
        gameHandler = new GameHandler(gameService);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");

        // Register endpoints
        Spark.post("/user", authHandler::handleRegister);
        Spark.delete("/db", this::handleClear);

        // TODO: Add more routes for login, games, etc.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object handleClear(Request req, Response res) {
        try {
            authService.clear();
            gameService.clear();

            res.status(200);
            return "{}";
        } catch (Exception ex) {
            res.status(500);
            return new Gson().toJson(new ErrorMessage("Error: " + ex.getMessage()));
        }
    }

    private record ErrorMessage(String message) {}
}

package server;

import com.google.gson.Gson;
import dataAccess.*;
import service.GameService;
import service.UserService;
import spark.*;

public class Server {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    private final UserService userService;
    private final GameService gameService;

    private final UserHandler userHandler;
    private final GameHandler gameHandler;

    public Server() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        userService = new UserService(userDAO, authDAO);
        gameService = new GameService(gameDAO, authDAO);
        userHandler = new UserHandler(userService);
        gameHandler = new GameHandler(gameService);
    }

    public int run(int port) {
        Spark.port(port);
        Spark.staticFiles.location("web");

        // Register endpoints
        Spark.delete("/db", this::clear);
        Spark.post("/user", userHandler::register);
        Spark.post("/session", userHandler::login);
        Spark.delete("/session", userHandler::logout);
        Spark.get("/game", gameHandler::listGames);
        Spark.post("/game", gameHandler::createGame);
        Spark.put("/game", gameHandler::joinGame);
        Spark.after((request, response) -> {
            response.type("application/json");
        });

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clear(Request req, Response resp) {
        try {
            userDAO.clear();
            authDAO.clear();
            gameDAO.clear();
            resp.status(200);
            return "{}";
        } catch (Exception e) {
            resp.status(500);
            return new Gson().toJson(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    private record ErrorResponse(String message) {}
}
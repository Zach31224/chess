package server;

import com.google.gson.Gson;
import dataAccess.*;
import service.GameService;
import service.UserService;
import spark.*;

public class Server {

    private UserDAO userDAO;
    private AuthDAO authDAO;
    private GameDAO gameDAO;

    private UserService userService;
    private GameService gameService;

    private UserHandler userHandler;
    private GameHandler gameHandler;

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

        Spark.delete("/db", this::clear);
        Spark.post("/user", userHandler::register);
        Spark.post("/session", userHandler::login);
        Spark.delete("/session", userHandler::logout);

        Spark.get("/game", gameHandler::listGames);
        Spark.post("/game", gameHandler::createGame);
        Spark.put("/game", gameHandler::joinGame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clear(Request req, Response resp) {
        try {
            userService.clear();
            gameService.clear();

            resp.status(200);
            return "{}";
        } catch (Exception e) {
            resp.status(500);
            String errMsg = new Gson().toJson(e.getMessage());
            return String.format("{ \"message\": \"Error: %s\"}", errMsg);
        }
    }
}

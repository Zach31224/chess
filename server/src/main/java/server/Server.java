package server;

import com.google.gson.Gson;
import dataaccess.*;
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

        // Register endpoints using lambda wrappers to match expected Route signature
        Spark.delete("/db", (req, res) -> clear(req, res));
        Spark.post("/user", (req, res) -> userHandler.register(req, res));
        Spark.post("/session", (req, res) -> userHandler.login(req, res));
        Spark.delete("/session", (req, res) -> userHandler.logout(req, res));
        Spark.get("/game", (req, res) -> gameHandler.listGames(req, res));
        Spark.post("/game", (req, res) -> gameHandler.createGame(req, res));
        Spark.put("/game", (req, res) -> gameHandler.joinGame(req, res));

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

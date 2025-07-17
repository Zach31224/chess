package server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import model.GameData;
import service.GameService;
import service.ServiceException;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class GameHandler {
    private final GameService gameService;
    private final Gson gson = new Gson();

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public Object listGames(Request req, Response resp) {
        try {
            String authToken = req.headers("authorization");
            if (authToken == null) {
                resp.status(401);
                return gson.toJson(new ErrorResponse("Error: unauthorized"));
            }

            Collection<GameData> games = gameService.listGames(authToken);
            resp.status(200);
            return gson.toJson(new GameListResponse(games));
        } catch (ServiceException e) {
            resp.status(401);
            return gson.toJson(new ErrorResponse("Error: unauthorized"));
        } catch (Exception e) {
            resp.status(500);
            return gson.toJson(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    public Object createGame(Request req, Response resp) {
        try {
            String authToken = req.headers("authorization");
            if (authToken == null) {
                resp.status(401);
                return gson.toJson(new ErrorResponse("Error: unauthorized"));
            }

            CreateGameRequest request = gson.fromJson(req.body(), CreateGameRequest.class);
            if (request == null || request.gameName() == null || request.gameName().isEmpty()) {
                resp.status(400);
                return gson.toJson(new ErrorResponse("Error: bad request"));
            }

            int gameID = gameService.createGame(authToken, request.gameName());
            resp.status(200);
            return gson.toJson(new CreateGameResponse(gameID));
        } catch (JsonSyntaxException e) {
            resp.status(400);
            return gson.toJson(new ErrorResponse("Error: bad request"));
        } catch (ServiceException e) {
            resp.status(401);
            return gson.toJson(new ErrorResponse("Error: unauthorized"));
        } catch (Exception e) {
            resp.status(500);
            return gson.toJson(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    public Object joinGame(Request req, Response resp) {
        try {
            String authToken = req.headers("authorization");
            if (authToken == null) {
                resp.status(401);
                return gson.toJson(new ErrorResponse("Error: unauthorized"));
            }

            JoinGameRequest request = gson.fromJson(req.body(), JoinGameRequest.class);
            if (request == null || request.gameID() <= 0) {
                resp.status(400);
                return gson.toJson(new ErrorResponse("Error: bad request"));
            }

            gameService.joinGame(authToken, request.gameID(), request.playerColor());
            resp.status(200);
            return "{}";
        } catch (JsonSyntaxException e) {
            resp.status(400);
            return gson.toJson(new ErrorResponse("Error: bad request"));
        } catch (ServiceException e) {
            if (e.getMessage().contains("403")) {
                resp.status(403);
                return gson.toJson(new ErrorResponse("Error: already taken"));
            }
            resp.status(401);
            return gson.toJson(new ErrorResponse("Error: unauthorized"));
        } catch (Exception e) {
            resp.status(500);
            return gson.toJson(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    // Response and Request record classes
    private record GameListResponse(Collection<GameData> games) {}
    private record CreateGameResponse(int gameID) {}
    private record CreateGameRequest(String gameName) {}
    private record JoinGameRequest(String playerColor, int gameID) {}
    private record ErrorResponse(String message) {}
}
package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.UnauthorizedException;
import model.GameData;
import service.GameService;
import spark.Request;
import spark.Response;

import java.util.HashSet;

public class GameHandler {

    private final GameService gameService;

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public Object listGames(Request req, Response resp) {
        try {
            String authToken = req.headers("authorization");
            HashSet<GameData> games = gameService.listGames(authToken);
            resp.status(200);
            String gamesJson = new Gson().toJson(games);
            return String.format("{ \"games\": %s }", gamesJson);
        } catch (DataAccessException e) {
            resp.status(401);
            return "{ \"message\": \"Error: unauthorized\" }";
        } catch (Exception e) {
            resp.status(500);
            return String.format("{ \"message\": \"Error: %s\" }", e.getMessage());
        }
    }

    public Object createGame(Request req, Response resp) {
        if (!req.body().contains("\"gameName\":")) {
            resp.status(400);
            return "{ \"message\": \"Error: bad request\" }";
        }

        try {
            String authToken = req.headers("authorization");
            int gameID = gameService.createGame(authToken);
            resp.status(200);
            return String.format("{ \"gameID\": %d }", gameID);
        } catch (DataAccessException e) {
            resp.status(401);
            return "{ \"message\": \"Error: unauthorized\" }";
        } catch (Exception e) {
            resp.status(500);
            return String.format("{ \"message\": \"Error: %s\" }", e.getMessage());
        }
    }

    public Object joinGame(Request req, Response resp) {
        if (!req.body().contains("\"gameID\":")) {
            resp.status(400);
            return "{ \"message\": \"Error: bad request\" }";
        }

        try {
            String authToken = req.headers("authorization");

            // Local record class for deserialization
            record JoinGameData(String playerColor, int gameID) {}

            JoinGameData joinData = new Gson().fromJson(req.body(), JoinGameData.class);
            int joinStatus = gameService.joinGame(authToken, joinData.gameID(), joinData.playerColor());

            if (joinStatus == 0) {
                resp.status(200);
                return "{}";
            } else if (joinStatus == 1) {
                resp.status(400);
                return "{ \"message\": \"Error: bad request\" }";
            } else if (joinStatus == 2) {
                resp.status(403);
                return "{ \"message\": \"Error: already taken\" }";
            }

            resp.status(200);
            return "{}";

        } catch (DataAccessException e) {
            resp.status(400);
            return "{ \"message\": \"Error: bad request\" }";
        } catch (UnauthorizedException e) {
            resp.status(401);
            return "{ \"message\": \"Error: unauthorized\" }";
        } catch (Exception e) {
            resp.status(500);
            return String.format("{ \"message\": \"Error: %s\" }", e.getMessage());
        }
    }
}

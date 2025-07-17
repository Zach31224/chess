package server;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import service.UserService;
import service.ServiceException;
import spark.Request;
import spark.Response;

public class UserHandler {
    private final UserService userService;
    private final Gson gson = new Gson();

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public Object register(Request req, Response resp) {
        try {
            UserData user = gson.fromJson(req.body(), UserData.class);
            if (user.username() == null || user.password() == null || user.email() == null) {
                resp.status(400);
                return gson.toJson(new ErrorResponse("Error: bad request"));
            }

            AuthData authData = userService.register(user);
            resp.status(200);
            return gson.toJson(authData);
        } catch (ServiceException e) {
            if (e.getMessage().contains("403")) {
                resp.status(403);
                return gson.toJson(new ErrorResponse("Error: already taken"));
            }
            resp.status(400);
            return gson.toJson(new ErrorResponse("Error: bad request"));
        } catch (Exception e) {
            resp.status(500);
            return gson.toJson(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    public Object login(Request req, Response resp) {
        try {
            UserData user = gson.fromJson(req.body(), UserData.class);
            if (user.username() == null || user.password() == null) {
                resp.status(400);
                return gson.toJson(new ErrorResponse("Error: bad request"));
            }

            AuthData authData = userService.login(user.username(), user.password());
            resp.status(200);
            return gson.toJson(authData);
        } catch (ServiceException e) {
            resp.status(401);
            return gson.toJson(new ErrorResponse("Error: unauthorized"));
        } catch (Exception e) {
            resp.status(500);
            return gson.toJson(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    public Object logout(Request req, Response resp) {
        try {
            String authToken = req.headers("authorization");
            if (authToken == null) {
                resp.status(401);
                return gson.toJson(new ErrorResponse("Error: unauthorized"));
            }

            userService.logout(authToken);
            resp.status(200);
            return "{}";
        } catch (ServiceException e) {
            resp.status(401);
            return gson.toJson(new ErrorResponse("Error: unauthorized"));
        } catch (Exception e) {
            resp.status(500);
            return gson.toJson(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    private record ErrorResponse(String message) {}
}
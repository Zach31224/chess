package server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.UserAuthService;
import spark.Request;
import spark.Response;

public class UserAuthHandler {

    private final UserAuthService authService;
    private final Gson gson = new Gson();

    public UserAuthHandler(UserAuthService authService) {
        this.authService = authService;
    }

    public Object handleRegister(Request req, Response res) throws DataAccessException {
        try {
            UserData user = gson.fromJson(req.body(), UserData.class);
            AuthData auth = authService.createUser(user);

            res.status(200);
            return gson.toJson(auth);

        } catch (JsonSyntaxException e) {
            res.status(400);
            return gson.toJson(new ErrorMessage("Error: bad request"));
        } catch (Exception e) {
            res.status(500);
            return gson.toJson(new ErrorMessage("Error: " + e.getMessage()));
        }
    }

    public Object handleLogin(Request req, Response res) {
        res.status(501); // Not implemented
        return gson.toJson(new ErrorMessage("Login functionality not yet implemented"));
    }

    private record ErrorMessage(String message) {}
}

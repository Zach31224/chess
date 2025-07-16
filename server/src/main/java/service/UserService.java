package service;

import dataAccess.*;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class UserService {

    UserDAO userDAO;
    AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public AuthData createUser(UserData userData) throws DataAccessException, BadRequestException {
        if (userData.username() == null || userData.password() == null) {
            throw new BadRequestException("Missing username or password");
        }

        try {
            userDAO.getUser(userData.username());
            // If we get here, the user already exists
            throw new BadRequestException("User already exists");
        } catch (DataAccessException e) {
            // This is expected — user not found, good to proceed
        }

        userDAO.createUser(userData);
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(userData.username(), authToken);
        authDAO.addAuth(authData);

        return authData;
    }

    public AuthData loginUser(UserData userData) throws DataAccessException {
        boolean userAuthenticated = userDAO.authenticateUser(userData.username(), userData.password());

        if (userAuthenticated) {
            String authToken = UUID.randomUUID().toString();
            AuthData authData = new AuthData(userData.username(), authToken);
            authDAO.addAuth(authData);
            return authData;
        } else {
            throw new DataAccessException("Password is incorrect");
        }
    }

    public void logoutUser(String authToken) throws DataAccessException {
        authDAO.getAuth(authToken); // Will throw if not found
        authDAO.deleteAuth(authToken);
    }

    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }
}

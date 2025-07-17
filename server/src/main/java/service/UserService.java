package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;

public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public AuthData register(UserData user) throws ServiceException {
        try {
            userDAO.createUser(user);
            return authDAO.createAuth(user.username());
        } catch (DataAccessException e) {
            if (e.getMessage().contains("already taken")) {
                throw new ServiceException("HTTP 403 " + e.getMessage());
            }
            throw new ServiceException("HTTP 400 " + e.getMessage());
        }
    }

    public AuthData login(String username, String password) throws ServiceException {
        try {
            if (!userDAO.authenticateUser(username, password)) {
                throw new ServiceException("HTTP 401 Error: unauthorized");
            }
            return authDAO.createAuth(username);
        } catch (DataAccessException e) {
            throw new ServiceException("HTTP 401 Error: unauthorized");
        }
    }

    public void logout(String authToken) throws ServiceException {
        try {
            authDAO.deleteAuth(authToken);
        } catch (DataAccessException e) {
            throw new ServiceException("HTTP 401 Error: unauthorized");
        }
    }

    public void clear() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
    }
}
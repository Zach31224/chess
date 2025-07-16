package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class UserAuthService {

    private final UserDAO userStore;
    private final AuthDAO authStore;

    public UserAuthService(UserDAO userStore, AuthDAO authStore) {
        this.userStore = userStore;
        this.authStore = authStore;
    }

    public AuthData registerUser(UserData user) throws DataAccessException {
        String token = UUID.randomUUID().toString();
        AuthData auth = new AuthData(user.username(), token);

        userStore.createUser(user);
        authStore.addAuth(auth);

        return auth;
    }

    public void reset() {
        userStore.clear();
        authStore.clear();
    }

    public AuthData createUser(UserData user) {
        return null;
    }

    public void clear() {
    }
}

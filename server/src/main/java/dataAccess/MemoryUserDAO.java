package dataAccess;

import model.UserData;
import java.util.HashSet;
import java.util.Set;

public class MemoryUserDAO implements UserDAO {

    private Set<UserData> users;

    public MemoryUserDAO() {
        users = new HashSet<>(16);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        for (UserData user : users) {
            if (user.username().equals(username)) {
                return user;
            }
        }
        throw new DataAccessException("User not found: " + username);
    }

    @Override
    public void createUser(String username, String password, String email) throws DataAccessException {
        if (userExists(username)) {
            throw new DataAccessException("User already exists: " + username);
        }
        users.add(new UserData(username, password, email));
    }

    public void createUser(UserData user) throws DataAccessException {
        if (userExists(user.username())) {
            throw new DataAccessException("User already exists: " + user.username());
        }
        users.add(user);
    }

    @Override
    public boolean authenticateUser(String username, String password) throws DataAccessException {
        UserData user = getUser(username);
        return user.password().equals(password);
    }

    @Override
    public void clear() {
        users.clear();
    }

    @Override
    public UserData findUserByUsername(String username) {
        return null;
    }

    @Override
    public void addUser(String username, String password, String email) {

    }

    @Override
    public boolean validateCredentials(String username, String password) {
        return false;
    }

    @Override
    public void reset() {

    }

    private boolean userExists(String username) {
        for (UserData user : users) {
            if (user.username().equals(username)) {
                return true;
            }
        }
        return false;
    }
}

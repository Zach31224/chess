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
    public UserData getUser(String username) {
        return users.stream()
                .filter(user -> user.username().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void createUser(String username, String password, String email) {
        users.add(new UserData(username, password, email));
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        return users.stream()
                .anyMatch(user -> user.username().equals(username) &&
                        user.password().equals(password));
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
}

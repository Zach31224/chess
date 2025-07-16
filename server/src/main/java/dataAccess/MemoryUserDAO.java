package dataAccess;

import model.UserData;

import java.util.HashSet;
import java.util.Set;

public class MemoryUserDAO implements UserDAO {

    private Set<UserData> users;

    public MemoryUserDAO() {
        users = new HashSet<>(16);
    }


}


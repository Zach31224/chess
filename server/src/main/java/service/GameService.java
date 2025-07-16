package service;

import dataAccess.GameDAO;

public class GameService {

    private final GameDAO gameStorage;

    public GameService(GameDAO gameStorage) {
        this.gameStorage = gameStorage;
    }

    public void reset() {
        gameStorage.clear();
    }

    public void clear() {
    }
}

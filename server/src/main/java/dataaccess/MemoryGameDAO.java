package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO {

    private int nextId = 1;
    final private HashMap<Integer, GameData> games = new HashMap<>();

    GameData createGame(String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        GameData newGame = new GameData(nextId++, whiteUsername, blackUsername, gameName, game);

        games.put(nextId, newGame);
        return newGame;
    }

    GameData getGame(int gameID) {
        return games.get(gameID);
    }

    Collection<GameData> listGames() {
        return games.values();
    }

    void updateGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        games.put(gameID, new GameData(gameID, whiteUsername, blackUsername, gameName, game));
    }

    void clear() {
        games.clear();
    }

}

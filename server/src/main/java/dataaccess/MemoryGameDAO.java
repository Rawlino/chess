package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {

    private int nextId = 1;
    final private HashMap<Integer, GameData> games = new HashMap<>();

    public int createGame(String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        GameData newGame = new GameData(nextId++, whiteUsername, blackUsername, gameName, game);

        games.put(nextId, newGame);
        return nextId;
    }

    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    public Collection<GameData> listGames() {
        return games.values();
    }

    public void updateGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        games.put(gameID, new GameData(gameID, whiteUsername, blackUsername, gameName, game));
    }

    public void clear() {
        games.clear();
    }

}

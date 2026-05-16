package dataaccess;

import chess.ChessGame;
import model.GameData;

public interface GameDAO {

    GameData createGame(String whiteUsername, String blackUsername, String gameName, ChessGame game) throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    void listGames() throws DataAccessException;

    void updateGame(int gameID) throws DataAccessException;

    void clear() throws DataAccessException;

}

package dataaccess;

import model.GameData;

public interface GameDAO {

    GameData createGame() throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    void listGames() throws DataAccessException;

    void updateGame(int gameID) throws DataAccessException;

    void clear() throws DataAccessException;
}

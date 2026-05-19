package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import dataaccess.DataAccessException;

import java.util.Collection;

public class GameService {

    private final GameDAO gameDAO;
    private final AuthDAO authDAO;

    public GameService(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }

    public Collection<GameData> listGames(String authToken) throws DataAccessException {
        AuthData auth = authDAO.getAuth(authToken);
        if (auth == null) {
            throw new DataAccessException("Error: unauthorized");
        } else {
            return gameDAO.listGames();
        }
    }

    public int createGame(String authToken, String gameName) throws DataAccessException {
        AuthData auth = authDAO.getAuth(authToken);
        if (auth == null) {
            throw new DataAccessException("Error: unauthorized");
        } else {
            ChessGame game = new ChessGame();
            return gameDAO.createGame(null, null, gameName, game);
        }
    }

    public boolean joinGame(String authToken, String playerColor, int gameID) throws DataAccessException {
        AuthData auth = authDAO.getAuth(authToken);
        GameData game = gameDAO.getGame(gameID);
        if (auth == null) {
            throw new DataAccessException("Error: unauthorized");
        } else if (game == null) {
            throw new DataAccessException("Error: bad request");
        } else if ((playerColor.equals("WHITE") && game.whiteUsername() != null) || (playerColor.equals("BLACK") && game.blackUsername() != null)) {
            throw new DataAccessException("Error: already taken");
        } else if (playerColor.equals("WHITE")) {
            gameDAO.updateGame(gameID, auth.username(), game.blackUsername(), game.gameName(), game.game());
            return true;
        } else if (playerColor.equals("BLACK")) {
            gameDAO.updateGame(gameID, game.whiteUsername(), auth.username(), game.gameName(), game.game());
            return true;
        } else {
            throw new DataAccessException("Error: bad request");
        }
    }

}

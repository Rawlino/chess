package handler;

import com.google.gson.Gson;
import io.javalin.http.Context;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import service.GameService;

public class GameHandler {

    private final GameService gameService;

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public void listGames(Context ctx) throws DataAccessException {
        AuthData auth = new Gson().fromJson(ctx.body(), AuthData.class);
        gameService.listGames(auth.authToken());
    }

    public void createGame(Context ctx) throws DataAccessException {
        GameData game = new Gson().fromJson(ctx.body(), GameData.class);
        AuthData auth = new Gson().fromJson(ctx.body(), AuthData.class);
        gameService.createGame(auth.authToken(), game.gameName());
        ctx.result(new Gson().toJson(gameService));
    }

    public void joinGame(Context ctx) throws DataAccessException {
        GameData game = new Gson().fromJson(ctx.body(), GameData.class);
        AuthData auth = new Gson().fromJson(ctx.body(), AuthData.class);
        gameService.joinGame(auth.authToken(), auth.username(), game.gameID());
        ctx.result(new Gson().toJson(gameService));
    }

}

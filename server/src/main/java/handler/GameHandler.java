package handler;

import com.google.gson.Gson;
import io.javalin.http.Context;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import service.GameService;

import java.util.Collection;

public class GameHandler {

    private final GameService gameService;

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public void listGames(Context ctx) throws DataAccessException {
        Collection<GameData> games = gameService.listGames(ctx.header("Authorization"));
        ctx.result(new Gson().toJson(games));
    }

    public void createGame(Context ctx) throws DataAccessException {
        GameData game = new Gson().fromJson(ctx.body(), GameData.class);
        AuthData auth = new Gson().fromJson(ctx.body(), AuthData.class);
        gameService.createGame(auth.authToken(), game.gameName());
        ctx.result(new Gson().toJson(game.gameID()));
    }

    public void joinGame(Context ctx) throws DataAccessException {
        GameData game = new Gson().fromJson(ctx.body(), GameData.class);
        AuthData auth = new Gson().fromJson(ctx.body(), AuthData.class);
        gameService.joinGame(auth.authToken(), auth.username(), game.gameID());
        ctx.result("{}");
    }

}

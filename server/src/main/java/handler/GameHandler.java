package handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
        String response = String.format("{\"games\": %s}", new Gson().toJson(games));
        ctx.result(response);
    }

    public void createGame(Context ctx) throws DataAccessException {
        GameData game = new Gson().fromJson(ctx.body(), GameData.class);
        int gameID = gameService.createGame(ctx.header("Authorization"), game.gameName());
        String response = String.format("{\"gameID\": %d}", gameID);
        ctx.result(response);
    }

    public void joinGame(Context ctx) throws DataAccessException {
        JsonObject obj = JsonParser.parseString(ctx.body()).getAsJsonObject();
        if (obj.get("playerColor") == null) {
            throw new DataAccessException("Error: bad request");
        } else if (obj.get("gameID") == null) {
            throw new DataAccessException("Error: bad request");
        } else {
            gameService.joinGame(ctx.header("Authorization"), obj.get("playerColor").getAsString(), obj.get("gameID").getAsInt());
            ctx.result("{}");
        }

    }

}

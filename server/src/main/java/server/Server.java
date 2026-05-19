package server;

import com.google.gson.Gson;
import io.javalin.*;
import model.*;
import handler.*;
import dataaccess.*;
import service.*;
import io.javalin.http.Context;


public class Server {

    private final Javalin javalin;
    private final ClearHandler clearHandler;
    private final UserHandler userHandler;
    private final GameHandler gameHandler;
    private final MemoryAuthDAO sharedAuthDAO;
    private final MemoryGameDAO sharedGameDAO;
    private final MemoryUserDAO sharedUserDAO;

    public Server() {

        sharedAuthDAO = new MemoryAuthDAO();
        sharedGameDAO = new MemoryGameDAO();
        sharedUserDAO = new MemoryUserDAO();

        clearHandler = new ClearHandler(new ClearService(sharedUserDAO, sharedGameDAO, sharedAuthDAO));
        userHandler = new UserHandler(new UserService(sharedAuthDAO, sharedUserDAO));
        gameHandler = new GameHandler(new GameService(sharedGameDAO, sharedAuthDAO));

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
        // Register your endpoints and exception handlers here.
                .post("/user", this.userHandler::register)
                .post("/session", this.userHandler::login)
                .delete("/session", this.userHandler::logout)
                .get("/game", this.gameHandler::listGames)
                .post("/game", this.gameHandler::createGame)
                .put("/game", this.gameHandler::joinGame)
                .delete("/db", this.clearHandler::clearDB)
                .exception(DataAccessException.class, this::exceptionHandler);

    }

    private void exceptionHandler(DataAccessException ex, Context ctx) {
        if (ex.getMessage().contains("unauthorized")) {
            ctx.status(401);
            ctx.result(new Gson().toJson(new ErrorResponse(ex.getMessage())));
        } else if (ex.getMessage().contains("bad response")) {
            ctx.status(400);
            ctx.result(new Gson().toJson(new ErrorResponse(ex.getMessage())));
        } else if (ex.getMessage().contains("already taken")) {
            ctx.status(403);
            ctx.result(new Gson().toJson(new ErrorResponse(ex.getMessage())));
        }
    }

    private record ErrorResponse(String message) {}

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}

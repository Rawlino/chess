package server;

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

    public Server() {

        clearHandler = new ClearHandler(new ClearService(new MemoryUserDAO(), new MemoryGameDAO(), new MemoryAuthDAO()));
        userHandler = new UserHandler(new UserService(new MemoryAuthDAO(), new MemoryUserDAO()));
        gameHandler = new GameHandler(new GameService(new MemoryGameDAO(), new MemoryAuthDAO()));

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
        // Register your endpoints and exception handlers here.
                .post("/user", this.userHandler::register)
                .post("/session", this.userHandler::login)
                .delete("/session", this.userHandler::logout)
                .delete("/db", this.clearHandler::clearDB)
                .get("/game", this.gameHandler::listGames)
                .post("/game", this.gameHandler::createGame)
                .put("/game", this.gameHandler::joinGame)
                .exception(DataAccessException.class, this::exceptionHandler);

    }

    private void exceptionHandler(DataAccessException ex, Context ctx) {
        if (ex.toString().contains("unauthorized")) {
            ctx.status(401);
        } else if (ex.toString().contains("bad response")) {
            ctx.status(400);
        } else if (ex.toString().contains("already taken")) {
            ctx.status(403);
        }
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}

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

    public Server() {

        clearHandler = new ClearHandler(new ClearService(new MemoryUserDAO(), new MemoryGameDAO(), new MemoryAuthDAO()));

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
        // Register your endpoints and exception handlers here.
//                .post("/")
                .delete("/db", this.clearHandler::clearDB);

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}

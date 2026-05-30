package client;

import chess.*;
import repl.LoggedOutREPL;
import server.ServerFacade;

public class ClientMain {
    public static void main(String[] args) {
        String serverUrl = "http://localhost:8080";
        ServerFacade sharedServerFacade = new ServerFacade(serverUrl);
        if (args.length == 1) {
            serverUrl = args[0];
        }

        try {
            new LoggedOutREPL(sharedServerFacade).run();

        } catch (Throwable ex) {
            System.out.printf("Unable to start server: %s%n", ex.getMessage());
        }
    }
}

package client;

import chess.*;
import repl.LoggedInREPL;
import repl.LoggedOutREPL;
import server.ServerFacade;

public class ClientMain {
    public static void main(String[] args) {
        String serverUrl = "http://localhost:8080";
        ServerFacade sharedServerFacade = new ServerFacade(serverUrl);
        String result = "LOGGED_OUT";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        try {
            while (!result.equals("QUIT")) {
                if (result.equals("LOGGED_OUT")) {
                    result = new LoggedOutREPL(sharedServerFacade).run();
                } else if (result.equals("LOGGED_IN")) {
                    result = new LoggedInREPL(sharedServerFacade).run();
                } else if (result.equals("IN_GAME")) {
                    result = new LoggedInREPL(sharedServerFacade).run();
                }
            }
        } catch (Throwable ex) {
            System.out.printf("Unable to start server: %s%n", ex.getMessage());
        }
    }
}

package client;

import repl.InGameREPL;
import repl.LoggedInREPL;
import repl.LoggedOutREPL;
import server.ServerFacade;

public class ClientMain {
    public static void main(String[] args) {
        String serverUrl = "http://localhost:8080";
        ServerFacade sharedServerFacade = new ServerFacade(serverUrl);
        LoggedOutREPL loggedOutREPL = new LoggedOutREPL(sharedServerFacade);
        LoggedInREPL loggedInREPL = new LoggedInREPL(sharedServerFacade);
        InGameREPL inGameREPL = new InGameREPL(sharedServerFacade);
        String result = "LOGGED_OUT";
        if (args.length == 1) {
            serverUrl = args[0];
        }
        try {
            while (!result.equals("QUIT")) {
                if (result.equals("LOGGED_OUT")) {
                    result = loggedOutREPL.run();
                } else if (result.equals("LOGGED_IN")) {
                    result = loggedInREPL.run();
                } else if (result.equals("IN_GAME")) {
                    result = inGameREPL.run();
                }
            }
        } catch (Throwable ex) {
            System.out.printf("Unable to start server: %s%n", ex.getMessage());
        }
    }
}

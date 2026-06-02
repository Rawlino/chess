package repl;

import dataaccess.DataAccessException;
import server.ServerFacade;

import java.util.Arrays;
import java.util.Scanner;

public class LoggedInREPL {
    private String user = null;
    private ServerFacade serverFacade;

    public LoggedInREPL(ServerFacade sharedServerFacade) {
        serverFacade = sharedServerFacade;
    }

    public String run() {
        System.out.println("♕ 240 Chess Client:");
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = eval(line);
                if (result.equals("LOGGED_OUT")) {
                    return "LOGGED_OUT";
                }
                if (result.equals("IN_GAME")) {
                    return "IN_GAME";
                }
                System.out.print(result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
        return "";
    }

    private void printPrompt() {
        System.out.print("\n" + ">>> ");
    }

    public String eval(String input) throws DataAccessException {
        String[] tokens = input.toLowerCase().split(" ");
        String cmd = (tokens.length > 0) ? tokens[0] : "help";
        String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "help" -> help();
            case "logout" -> logOut(params);
            case "create" -> "create selected";
            case "list" -> "list selected";
            case "play" -> play(params);
            case "observe" -> "observe selected";
            default -> "Unknown command. To list available commands, type 'help'";
        };
    }

    public String play(String... params) throws DataAccessException {
        if (params.length == 2) {
            return "IN_GAME";
        }
        throw new DataAccessException("Expected: play <gameID> <teamColor>");
    }

    public String logOut(String... params) throws DataAccessException {
        if (params.length == 0) {
            serverFacade.logout(LoggedOutREPL.authToken);
            LoggedOutREPL.authToken = null;
            System.out.print("Thank you. Have a great day\n");
            return "LOGGED_OUT";
        }
        throw new DataAccessException("Expected: logout");
    }

    public String help() {
        return """
                - help: list useful commands
                - logout: logout of your account
                - create <gameName>: create a new game
                - list: list available games
                - play <gameID> <teamColor>: enter a chessgame and play
                - observe <gameID>: watch a chessgame
                """;
    }
}

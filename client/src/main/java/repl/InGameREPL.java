package repl;

import dataaccess.DataAccessException;
import server.ServerFacade;

import java.util.Arrays;
import java.util.Scanner;

public class InGameREPL {
    private String user = null;
    private ServerFacade serverFacade;

    public InGameREPL(ServerFacade sharedServerFacade) {
        serverFacade = sharedServerFacade;
    }

    public String run() {
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = eval(line);
                if (result.equals("LOGGED_IN")) {
                    return "LOGGED_IN";
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
            case "leave" -> leave(params);
            default -> "Unknown command. To list available commands, type 'help'";
        };
    }

    public String leave(String... params) throws DataAccessException {
        if (params.length == 0) {
            System.out.print("Thank you for watching!");
            return "LOGGED_IN";
        }
        throw new DataAccessException("Expected: leave");
    }

    public String help() {
        return """
                - help: list useful commands
                - leave: leave game
                """;
    }
}

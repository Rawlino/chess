package repl;

import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.*;
import server.ServerFacade;

import javax.xml.crypto.Data;

import static ui.EscapeSequences.*;

public class LoggedOutREPL {
    private String user = null;
    private ServerFacade serverFacade;

    public LoggedOutREPL(ServerFacade sharedServerFacade) {
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
            case "login" -> logIn(params);
            case "register" -> "register selected";
            case "quit" -> "quit selected";
            default -> "Unknown command. To list available commands, type 'help'";
        };
    }

    public String logIn(String... params) throws DataAccessException {
        if (params.length >= 1) {
            user = String.join("-", params);
            System.out.print(String.format("You signed in as %s.\n", user));
            return "LOGGED_IN";
        }
        throw new DataAccessException("Expected: login <username>");
    }

    public String help() {
        return """
                - help: list useful commands
                - login <username> <password>: login to account
                - register <username> <password> <email>: register new account
                - quit: close program
                """;
    }
}

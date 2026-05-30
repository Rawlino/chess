package repl;

import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.*;
import server.ServerFacade;

import static ui.EscapeSequences.*;

public class LoggedOutREPL {
    private String user = null;
    private ServerFacade serverFacade;

    public LoggedOutREPL(ServerFacade sharedServerFacade) {
        serverFacade = sharedServerFacade;
    }

    public void run() {
        System.out.println("♕ 240 Chess Client:");
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = eval(line);
                System.out.print(result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
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
            case "login" -> "login selected";
            case "register" -> "register selected";
            case "quit" -> "quit selected";
            default -> "Unknown command. To list available commands, type 'help'";
        };
    }

    public String help() {
        return """
                - help: list useful commands
                - login: login to account
                - register: register new account
                - quit: close program
                """;
    }
}

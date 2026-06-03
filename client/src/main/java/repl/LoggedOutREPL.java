package repl;

import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.DataAccessException;
import model.*;
import server.ServerFacade;

public class LoggedOutREPL {
    private String user = null;
    private ServerFacade serverFacade;
    public static String authToken = null;

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
                } else if (result.equals("QUIT")) {
                    return "QUIT";
                }
                System.out.print(result);
            } catch (Throwable e) {
                Gson gson = new Gson();
                JsonObject object = gson.fromJson(e.getMessage(), JsonObject.class);
                String message = object.get("message").getAsString();
                System.out.print(message);
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
            case "register" -> register(params);
            case "quit" -> quit(params);
            default -> "Unknown command. To list available commands, type 'help'";
        };
    }

    public String logIn(String... params) throws DataAccessException {
        if (params.length == 2) {
            user = params[0];
            String password = params[1];
            UserData userData = new UserData(user, password, null);
            AuthData auth = serverFacade.login(userData);
            authToken = auth.authToken();
            System.out.print(String.format("You signed in as %s.\n", user));
            return "LOGGED_IN";
        }
        throw new DataAccessException("Expected: login <username> <password>");
    }

    public String register(String... params) throws DataAccessException {
        if (params.length == 3) {
            user = params[0];
            String password = params[1];
            String email = params[2];
            UserData userData = new UserData(user, password, email);
            AuthData auth = serverFacade.register(userData);
            authToken = auth.authToken();
            System.out.print(String.format("Thank you for registering %s.\n", user));
            return "LOGGED_IN";
        }
        throw new DataAccessException("Expected: register <username> <password> <email>");
    }

    public String quit(String... params) throws DataAccessException {
        if (params.length == 0) {
            System.out.print("Thank you for joining us.");
            return "QUIT";
        }
        throw new DataAccessException("Expected: quit");
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

package repl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.DataAccessException;
import model.GameData;
import server.ServerFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static repl.LoggedOutREPL.extracted;
import static ui.RenderBoard.renderBlackBoard;
import static ui.RenderBoard.renderWhiteBoard;

public class LoggedInREPL {
    private ServerFacade serverFacade;
    private ArrayList<GameData> allGames;
    public static String playerColor;

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
                extracted(e);
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
            case "create" -> createGame(params);
            case "list" -> listGames(params);
            case "join" -> joinGame(params);
            case "observe" -> observe(params);
            default -> "Unknown command. To list available commands, type 'help'";
        };
    }

    public String joinGame(String... params) throws DataAccessException {
        try {
            if (params.length == 2) {
                try {
                    int gameID = Integer.parseInt(params[0]);
                    GameData gameData = allGames.get(gameID - 1);
                } catch (Exception e) {
                    return "Please enter valid int in list, not string or char";
                }
                int gameID = Integer.parseInt(params[0]);
                String teamColor = params[1];
                if (!teamColor.equals("white") && !teamColor.equals("black")) {
                    throw new DataAccessException("Not a valid teamColor, please choose \"white\" or \"black\"");
                } else if (teamColor.equals("white")) {
                    GameData gameData = allGames.get(gameID - 1);
                    JsonObject joinData = new JsonObject();
                    joinData.addProperty("playerColor", "WHITE");
                    joinData.addProperty("gameID", gameData.gameID());
                    playerColor = "WHITE";
                    serverFacade.joinGame(LoggedOutREPL.authToken, joinData);
                    System.out.print(String.format("Successfully joined game: %s\n", gameData.gameName()));
                    //DRAW WHITE BOARD HERE
                    renderWhiteBoard();
                } else {
                    GameData gameData = allGames.get(gameID - 1);
                    JsonObject joinData = new JsonObject();
                    joinData.addProperty("playerColor", "BLACK");
                    joinData.addProperty("gameID", gameData.gameID());
                    playerColor = "BLACK";
                    serverFacade.joinGame(LoggedOutREPL.authToken, joinData);
                    System.out.print(String.format("Successfully joined game: %s\n", gameData.gameName()));
                    //DRAW BLACK BOARD HERE
                    renderBlackBoard();
                }
                return "IN_GAME";
            } else {
                throw new DataAccessException("Expected: join <gameID> <teamColor>");
            }
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public String observe(String... params) throws DataAccessException {
        if (params.length == 1) {
            try {
                int gameID = Integer.parseInt(params[0]);
                GameData gameData = allGames.get(gameID - 1);
            } catch (Exception e) {
                return "Please enter valid int in list, not string or char";
            }
            int gameID = Integer.parseInt(params[0]);
            GameData gameData = allGames.get(gameID - 1);
            System.out.print(String.format("Now observing game: %s\n", gameData.gameName()));
            //DRAW WHITE BOARD HERE
            renderWhiteBoard();
            return "IN_GAME";
        }
        throw new DataAccessException("Expected: observe <gameID>");
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

    public String listGames(String... params) throws DataAccessException {
        try {
            if (params.length == 0) {
                ArrayList<GameData> games = serverFacade.listGames(LoggedOutREPL.authToken);
                allGames = games;
                int i = 1;
                for (GameData game : games) {
                    System.out.print(String.format("%d. %s (Black: %s | White: %s)\n", i++, game.gameName(),
                            game.blackUsername(), game.whiteUsername()));
                }
                return "";
            } else {
                throw new DataAccessException("Expected: list");
            }
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public String createGame(String... params) throws DataAccessException {
        try {
            if (params.length == 1) {
                String gameName = params[0];
                GameData gameData =
                        new GameData(0, null, null, gameName, null);
                //Declared here in case we want to return the gameID as part of the response.
                GameData gameID = serverFacade.createGame(gameData, LoggedOutREPL.authToken);
                return String.format("The game \"%s\" was created successfully\n", gameName);
            } else {
                throw new DataAccessException("Expected: create <gameName>");
            }
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public String help() {
        return """
                - help: list useful commands
                - logout: logout of your account
                - create <gameName>: create a new game
                - list: list all existing games
                - join <gameID> <teamColor>: enter a chessgame and play
                - observe <gameID>: watch a chessgame
                """;
    }
}

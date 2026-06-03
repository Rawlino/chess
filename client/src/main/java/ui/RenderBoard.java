package ui;

import java.util.ArrayList;
import java.util.List;

import static ui.EscapeSequences.*;

public class RenderBoard {
    public static String[] whiteLetters = {"a", "b", "c", "d", "e", "f", "g", "h"};
    public static String[] blackLetters = {"h", "g", "f", "e", "d", "c", "b", "a"};
    public static Integer[] whiteNumbers = {8, 7, 6, 5, 4, 3, 2, 1};
    public static Integer[] blackNumbers = {1, 2, 3, 4, 5, 6, 7, 8};


    public static void renderWhiteBoard() {
        System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + "  " + RESET_BG_COLOR + RESET_TEXT_COLOR);
        for (String letter : whiteLetters) {
            System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + "  " + letter + RESET_BG_COLOR + RESET_TEXT_COLOR);
        }
        System.out.print(SET_BG_COLOR_BLACK + "    " + RESET_BG_COLOR + "\n");
        for (int i = 0; i<8;i++) {
            System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + " " + whiteNumbers[i] + " " + RESET_BG_COLOR + RESET_TEXT_COLOR);
            for (int j = 0; j<8;j++) {
                if ((i+j) % 2 == 0) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY + EMPTY + RESET_BG_COLOR);
                } else {
                    System.out.print(SET_BG_COLOR_DARK_GREY + EMPTY + RESET_BG_COLOR);
                }
            }
            System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + " " + whiteNumbers[i] + " " + RESET_BG_COLOR + RESET_TEXT_COLOR + "\n");
        }
        System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + "  " + RESET_BG_COLOR + RESET_TEXT_COLOR);
        for (String letter : whiteLetters) {
            System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + "  " + letter + RESET_BG_COLOR + RESET_TEXT_COLOR);
        }
        System.out.print(SET_BG_COLOR_BLACK + "    " + RESET_BG_COLOR + "\n");
    }

    public static void renderBlackBoard() {
        System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + "  " + RESET_BG_COLOR + RESET_TEXT_COLOR);
        for (String letter : blackLetters) {
            System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + "  " + letter + RESET_BG_COLOR + RESET_TEXT_COLOR);
        }
        System.out.print(SET_BG_COLOR_BLACK + "    " + RESET_BG_COLOR + "\n");
        for (int i = 0; i<8;i++) {
            System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + " " + blackNumbers[i] + " " + RESET_BG_COLOR + RESET_TEXT_COLOR);
            for (int j = 0; j<8;j++) {
                if ((i+j) % 2 == 0) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY + EMPTY + RESET_BG_COLOR);
                } else {
                    System.out.print(SET_BG_COLOR_DARK_GREY + EMPTY + RESET_BG_COLOR);
                }
            }
            System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + " " + blackNumbers[i] + " " + RESET_BG_COLOR + RESET_TEXT_COLOR + "\n");
        }
        System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + "  " + RESET_BG_COLOR + RESET_TEXT_COLOR);
        for (String letter : blackLetters) {
            System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + "  " + letter + RESET_BG_COLOR + RESET_TEXT_COLOR);
        }
        System.out.print(SET_BG_COLOR_BLACK + "    " + RESET_BG_COLOR + "\n");
    }
}

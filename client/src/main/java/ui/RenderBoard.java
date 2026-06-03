package ui;

import java.util.ArrayList;
import java.util.List;

import static ui.EscapeSequences.*;

public class RenderBoard {
    public static String[] whiteLetters = {"a", "b", "c", "d", "e", "f", "g", "h"};
    public static String[] blackLetters = {"h", "g", "f", "e", "d", "c", "b", "a"};
    public static Integer[] whiteNumbers = {8, 7, 6, 5, 4, 3, 2, 1};
    public static Integer[] blackNumbers = {1, 2, 3, 4, 5, 6, 7, 8};
    public static String[] bWhiteSpecialPieces = {WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_KING, WHITE_QUEEN,
            WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK};
    public static String[] bBlackSpecialPieces = {BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_KING, BLACK_QUEEN,
            BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK};
    public static String[] whiteSpecialPieces = {WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING,
            WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK};
    public static String[] blackSpecialPieces = {BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING,
            BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK};



    public static void renderWhiteBoard() {
        System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + "  " + RESET_BG_COLOR + RESET_TEXT_COLOR);
        for (String letter : whiteLetters) {
            System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + "  " + letter + RESET_BG_COLOR + RESET_TEXT_COLOR);
        }
        System.out.print(SET_BG_COLOR_BLACK + "    " + RESET_BG_COLOR + "\n");
        for (int i = 0; i<8;i++) {
            System.out.print(SET_TEXT_COLOR_WHITE + SET_BG_COLOR_BLACK + " " + whiteNumbers[i] + " " + RESET_BG_COLOR + RESET_TEXT_COLOR);
            for (int j = 0; j<8;j++) {
                if (i == 2 || i == 3 || i == 4 || i == 5) {
                    if ((i+j) % 2 == 0) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY + EMPTY + RESET_BG_COLOR);
                    } else {
                        System.out.print(SET_BG_COLOR_DARK_GREY + EMPTY + RESET_BG_COLOR);
                    }
                } else if (i == 0) {
                    if ((i+j) % 2 == 0) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY + blackSpecialPieces[j] + RESET_BG_COLOR);
                    } else {
                        System.out.print(SET_BG_COLOR_DARK_GREY + blackSpecialPieces[j] + RESET_BG_COLOR);
                    }
                } else if (i == 1) {
                    if ((i+j) % 2 == 0) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY + BLACK_PAWN + RESET_BG_COLOR);
                    } else {
                        System.out.print(SET_BG_COLOR_DARK_GREY + BLACK_PAWN + RESET_BG_COLOR);
                    }
                } else if (i == 6) {
                    if ((i+j) % 2 == 0) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY + WHITE_PAWN + RESET_BG_COLOR);
                    } else {
                        System.out.print(SET_BG_COLOR_DARK_GREY + WHITE_PAWN + RESET_BG_COLOR);
                    }
                } else {
                    if ((i+j) % 2 == 0) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY + whiteSpecialPieces[j] + RESET_BG_COLOR);
                    } else {
                        System.out.print(SET_BG_COLOR_DARK_GREY + whiteSpecialPieces[j] + RESET_BG_COLOR);
                    }
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
                if (i == 2 || i == 3 || i == 4 || i == 5) {
                    if ((i+j) % 2 == 0) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY + EMPTY + RESET_BG_COLOR);
                    } else {
                        System.out.print(SET_BG_COLOR_DARK_GREY + EMPTY + RESET_BG_COLOR);
                    }
                } else if (i == 0) {
                    if ((i+j) % 2 == 0) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY + bWhiteSpecialPieces[j] + RESET_BG_COLOR);
                    } else {
                        System.out.print(SET_BG_COLOR_DARK_GREY + bWhiteSpecialPieces[j] + RESET_BG_COLOR);
                    }
                } else if (i == 1) {
                    if ((i+j) % 2 == 0) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY + WHITE_PAWN + RESET_BG_COLOR);
                    } else {
                        System.out.print(SET_BG_COLOR_DARK_GREY + WHITE_PAWN + RESET_BG_COLOR);
                    }
                } else if (i == 6) {
                    if ((i+j) % 2 == 0) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY + BLACK_PAWN + RESET_BG_COLOR);
                    } else {
                        System.out.print(SET_BG_COLOR_DARK_GREY + BLACK_PAWN + RESET_BG_COLOR);
                    }
                } else {
                    if ((i+j) % 2 == 0) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY + bBlackSpecialPieces[j] + RESET_BG_COLOR);
                    } else {
                        System.out.print(SET_BG_COLOR_DARK_GREY + bBlackSpecialPieces[j] + RESET_BG_COLOR);
                    }
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

package de.encoway.gewinnt.utils;

import de.encoway.gewinnt.exceptions.WinException;
import de.encoway.gewinnt.model.Board;
import de.encoway.gewinnt.model.Game;

/**
 * @author Aleksandar Pantelic
 */
public class WinChecker {

    // The margin which prevents the win check from going out of bounds
    private final static int MARGIN = 3;

    private static char[][] boardArr;

    private static char currentPlayerToken;

    /**
     * Checks for the given game if a win has occurred
     *
     * @param game the game to check
     */
    public static void checkForWin(Game game) throws WinException {
        // algorithm based on: https://stackoverflow.com/questions/32770321/connect-4-check-for-a-win-algorithm
        boardArr = game.getBoard().getBoardArr();
        currentPlayerToken = game.getCurrentPlayer().getToken();
        if (checkHorizontalWin()) {
            throw new WinException();
        } else if (checkVerticalWin()) {
            throw new WinException();
        } else if (checkDiagonalAscendingWin()) {
            throw new WinException();
        } else if (checkDiagonalDescendingWin()) {
            throw new WinException();
        }
    }

    private static boolean checkHorizontalWin() {
        for (int j = 0; j < (Board.NUM_OF_COLS - MARGIN); j++) {
            for (int i = 0; i < Board.NUM_OF_ROWS; i++) {
                if (boardArr[i][j] == currentPlayerToken && boardArr[i][j + 1] == currentPlayerToken && boardArr[i][j + 2] == currentPlayerToken
                        && boardArr[i][j + 3] == currentPlayerToken) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkVerticalWin() {
        for (int i = 0; i < (Board.NUM_OF_ROWS - MARGIN); i++) {
            for (int j = 0; j < Board.NUM_OF_COLS; j++) {
                if (boardArr[i][j] == currentPlayerToken && boardArr[i + 1][j] == currentPlayerToken && boardArr[i + 2][j] == currentPlayerToken
                        && boardArr[i + 3][j] == currentPlayerToken) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkDiagonalAscendingWin() {
        for (int i = MARGIN; i < Board.NUM_OF_ROWS; i++) {
            for (int j = 0; j < (Board.NUM_OF_COLS - MARGIN); j++) {
                if (boardArr[i][j] == currentPlayerToken && boardArr[i - 1][j + 1] == currentPlayerToken && boardArr[i - 2][j + 2] == currentPlayerToken
                        && boardArr[i - 3][j + 3] == currentPlayerToken) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkDiagonalDescendingWin() {
        for (int i = MARGIN; i < Board.NUM_OF_ROWS; i++) {
            for (int j = MARGIN; j < Board.NUM_OF_COLS; j++) {
                if (boardArr[i][j] == currentPlayerToken && boardArr[i - 1][j - 1] == currentPlayerToken && boardArr[i - 2][j - 2] == currentPlayerToken
                        && boardArr[i - 3][j - 3] == currentPlayerToken) {
                    return true;
                }
            }
        }
        return false;
    }
}

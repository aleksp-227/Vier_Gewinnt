package de.encoway.gewinnt.model;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.stereotype.Component;

/**
 * @author Aleksandar Pantelic
 */
@Component
public class Board implements Serializable {

    public final static int NUM_OF_ROWS = 6;
    public final static int NUM_OF_COLS = 7;
    private char[][] boardArr;

    public Board() {
        this.boardArr = new char[NUM_OF_ROWS][NUM_OF_COLS];
    }

    public char[][] getBoardArr() {
        return boardArr;
    }

    public void setBoardArr(char[][] boardArr) {
        this.boardArr = boardArr;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(boardArr).replace("],", "],\n");
    }
}

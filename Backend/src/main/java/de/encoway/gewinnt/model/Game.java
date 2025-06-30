package de.encoway.gewinnt.model;

import java.io.Serializable;

/**
 * @author Aleksandar Pantelic
 */

public class Game implements Serializable {

    private final String id;

    private final Player player1;

    private final Player player2;

    private Player currentPlayer;

    private final Board board;

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Game(String id) {
        this.id = id;
        this.player1 = new Player('X', "abc");
        this.player2 = new Player('O', "xyz");
        this.currentPlayer = player1;
        this.board = new Board();
    }

    public String getId() {
        return id;
    }
}

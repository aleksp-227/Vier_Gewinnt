package de.encoway.gewinnt.model;

import java.io.Serializable;

/**
 * @author Aleksandar Pantelic
 */
public class Player implements Serializable {

    private char token;
    private String name;

    public Player(char token, String name) {
        this.token = token;
        this.name = name;
    }

    public Player() {
        this.token = '\u0000';
        this.name = "";
    }

    public char getToken() {
        return token;
    }

    public String getName() {
        return name;
    }
}

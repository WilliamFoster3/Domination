package com.example.domination;

import java.util.ArrayList;

public class Upgrades {
    private int upgType = 0;
    private String name;
    private int digit;
    Upgrades() {
        digit = (int) (Math.random() * 7);
        if (digit == 0) {
            name = "aid";
            upgType = 0;
        } else if (digit == 1) {
            name = "hone";
            upgType = 1;
        } else if (digit == 2) {
            name = "death";
            upgType = 1;
        } else if (digit == 3) {
            name = "birth";
            upgType = 0;
        } else if (digit == 4) {
            name = "weak";
            upgType = 0;
        } else if (digit == 5) {
            name = "tank";
            upgType = 2;
        } else {
            name = "smite";
            upgType = 2;
        }
    }
    public int getUpgType() {
        return upgType;
    }
    public int getDigit() {
        return digit;
    }
    public static int rand() {
        return (int) (Math.random() * 7);
    }
    public String getName() {
        return name;
    }
    public ArrayList<Characters> aid(ArrayList<Characters> deck) {
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).getDefense() > 0) {
                Characters.adjustments(deck.get(i), 1, 1);
            }
        }
        digit = -1;
        return deck;
    }
    public Characters hone(Characters one) {
        digit = -1;
        return Characters.adjustments(one, 3, 3);
    }
    public Characters death(Characters one) {
        digit = -1;
        return Characters.adjustments(one, 0, -100);
    }
    public ArrayList<Characters> birth(ArrayList<Characters> deck) {
        digit = -1;
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).getDefense() <= 0) {
                deck.set(i, new Characters(3, 4, 3));
                return deck;
            }
        }
        return deck;
    }
    public ArrayList<Characters> weak(ArrayList<Characters> deck) {
        digit = -1;
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).getDefense() > 0) {
                Characters.adjustments(deck.get(i), -1, -1);
            }
        }
        return deck;
    }
    public Characters smite(Characters one) {
        digit = -1;
        return Characters.adjustments(one, 0, -3);
    }
    public Player smite(Player one) {
        digit = -1;
        one.healthChange(-3);
        return one;
    }
    public Characters tank(Characters one) {
        digit = -1;
        return Characters.adjustments(one, 0, 5);
    }
    public Player tank(Player one) {
        digit = -1;
        one.healthChange(5);
        return one;
    }
}

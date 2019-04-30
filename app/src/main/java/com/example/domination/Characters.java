package com.example.domination;

import java.util.ArrayList;

public class Characters extends Upgrades {
    private int attack;
    public boolean sleep = true;
    private int defense;
    public int charNumb = 0;
    Characters(int attac, int defens, int charNumb1) {
        attack = attac;
        defense = defens;
        charNumb = charNumb1;
    }
    public void setAttack(int new1) {
        attack = new1;
    }
    public int getAttack() {
        return attack;
    }
    public void setDefense(int new1) {
        defense = new1;
    }
    public int getDefense() {
        return defense;
    }
    public static Characters adjustments(Characters one, int attac, int defens) {
        one.attack += attac;
        one.defense += defens;
        return one;
    }
    public void attacks(Characters other) {
        sleep = true;
        defense -= other.getAttack();
        other.defense -= attack;
    }


}

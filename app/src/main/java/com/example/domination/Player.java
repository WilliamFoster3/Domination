package com.example.domination;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


public class Player extends Upgrades{
    public Upgrades[] upgArr = new Upgrades[2];
    private boolean hasShaked = false;
    private static int charInt = 0;
    private static String charString = "";
    private boolean isLost = false;
    private int health;
    public ArrayList<Characters> deck;
    Player (int healt) {
        charInt++;
        charString = "Player" + charInt;
        health = healt;
        ArrayList<Characters> deckOg = new ArrayList<Characters>();
        for (int i = 0; i < 5; i++) {
            Characters weak = new Characters(0, 0, 3);
            deckOg.add(weak);
        }
        newUpgrade();
        deck = deckOg;
    }
    public boolean getHasShaked() {
        return hasShaked;
    }
    public Upgrades[] getUpgArr() {
        return upgArr;
    }
    public void setDeck(ArrayList<Characters> new1) {
        deck = new1;
    }
    public ArrayList<Characters> getDeck() {
        return deck;
    }
    public int getHealth() { return health; }
    public void setHealth(int new1) {
        health = new1;
    }
    public String returnHealth(int index) {
        return Integer.toString(deck.get(index).getDefense());
    }
    public void setHasShaked(boolean yep) {
        hasShaked = yep;
    }

    public void changeTurns(Characters choice) {
        newUpgrade();
        newChar(choice);
        if (deck.size() >= 5) {
            return;
        } else {
            newChar(choice);
        }
    }
    public void nullCheck() {
        if (upgArr[0] != null && upgArr[0].getDigit() == -1) {
            upgArr[0] = null;
        } if (upgArr[1] != null && upgArr[1].getDigit() == -1) {
            upgArr[1] = null;
        }
    }
    public void newUpgrade() {
        Upgrades add = new Upgrades();
        if (upgArr[0] == null) {
            upgArr[0] = add;
        } else if (upgArr[1] == null) {
            upgArr[1] = add;
        }
    }
    public void newChar(Characters choice) {
        for (int i = 0; i < 5; i++) {
            if (deck.get(i).getDefense() == 0) {
                Characters new1 = choice;
                deck.set(i, new1);
                return;
            }
        }
    }

    public void healthChange(int change) {
        health = health + change;
        if (health <= 0) {
            isLost = true;
        }
    }


}

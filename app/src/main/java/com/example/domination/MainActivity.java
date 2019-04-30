package com.example.domination;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ImageButton[] choices = new ImageButton[4];
    public Player current;
    public Player attacked;
    public Upgrades yourUpg;
    public Characters yours;
    public Characters theirs;
    public TextView[] botCharStats = new TextView[12];
    public TextView[] topCharStats = new TextView[12];
    public ImageButton[] topChars = new ImageButton[5];
    public ImageButton[] botChars = new ImageButton[5];
    public ImageButton botHead;
    public TextView botHealth;
    public ImageButton topHead;
    private Button endturn;
    public Button returnToMain;
    public TextView textBox;
    public TextView choiceSta;
    public TextView topHealth;
    public TextView endScreen;
    public SensorManager sm;
    private float acelVal;
    private float acelLast;
    private float shake;
    Player botPlayer;
    Player topPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
        rulesBtn();
        botPlayer = new Player(20);
        topPlayer = new Player(20);
        botPlayer.newChar(new Characters(2, 2, 3));
        endturn = findViewById(R.id.endTurn);

        returnToMain = findViewById(R.id.btnMainMenu);

        botHead = findViewById(R.id.botCharacter);
        topHead = findViewById(R.id.topCharacter);
        choiceSta = findViewById(R.id.choiceStats);

        // characters of bot
        botChars[0] = findViewById(R.id.bottomPlayer1);
        botChars[1] = findViewById(R.id.bottomPlayer2);
        botChars[2] = findViewById(R.id.bottomPlayer3);
        botChars[3] = findViewById(R.id.bottomPlayer4);
        botChars[4] = findViewById(R.id.bottomPlayer5);

        //  characters of top
        topChars[0] = findViewById(R.id.topPlayer1);
        topChars[1] = findViewById(R.id.topPlayer2);
        topChars[2] = findViewById(R.id.topPlayer3);
        topChars[3] = findViewById(R.id.topPlayer4);
        topChars[4] = findViewById(R.id.topPlayer5);
        endScreen = findViewById(R.id.endScreen);


        // defense of bot
        botCharStats[0] = findViewById(R.id.defensebot1);
        botCharStats[2] = findViewById(R.id.defensebot2);
        botCharStats[4] = findViewById(R.id.defensebot3);
        botCharStats[6] = findViewById(R.id.defensebot4);
        botCharStats[8] = findViewById(R.id.defensebot5);
        //attack of bot
        botCharStats[1] = findViewById(R.id.attackbot1);
        botCharStats[3] = findViewById(R.id.attackbot2);
        botCharStats[5] = findViewById(R.id.attackbot3);
        botCharStats[7] = findViewById(R.id.attackbot4);
        botCharStats[9] = findViewById(R.id.attackbot5);
        //upgrade of bot
        botCharStats[10] = findViewById(R.id.botUpgrade1);
        botCharStats[11] = findViewById(R.id.botUpgrade2);


        // defense of top
        topCharStats[0] = findViewById(R.id.defensetop1);
        topCharStats[2] = findViewById(R.id.defensetop2);
        topCharStats[4] = findViewById(R.id.defensetop3);
        topCharStats[6] = findViewById(R.id.defensetop4);
        topCharStats[8] = findViewById(R.id.defensetop5);
        //attack of top
        topCharStats[1] = findViewById(R.id.attacktop1);
        topCharStats[3] = findViewById(R.id.attacktop2);
        topCharStats[5] = findViewById(R.id.attacktop3);
        topCharStats[7] = findViewById(R.id.attacktop4);
        topCharStats[9] = findViewById(R.id.attacktop5);
        textBox = findViewById(R.id.textBox);
        //upgrade of top
        topCharStats[10] = findViewById(R.id.topUpgrade1);
        topCharStats[11] = findViewById(R.id.topUpgrade2);

        choices[0] = findViewById(R.id.charChoice1);
        choices[1] = findViewById(R.id.charChoice2);
        choices[2] = findViewById(R.id.charChoice3);
        choices[3] = findViewById(R.id.charChoice4);

        //health of heros
        botHealth = findViewById(R.id.botKingHealth);
        topHealth = findViewById(R.id.topKingHealth);
        adjust(topPlayer, botPlayer);
        current = botPlayer;
        botHead.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
    }
    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;
            System.out.println(shake);
            if (shake > 12) {
                if (current.getHasShaked() == false) {
                    if (current == botPlayer) {
                        for (int i = 0; i < 2; i++) {
                            if (botPlayer.getUpgArr()[i] != null) {
                                Upgrades add = new Upgrades();
                                botPlayer.upgArr[i] = add;
                            }
                        }
                        botPlayer.setHasShaked(true);
                    } else if (current == topPlayer) {
                        for (int i = 0; i < 2; i++) {
                            if (topPlayer.getUpgArr()[i] != null) {
                                Upgrades add = new Upgrades();
                                topPlayer.upgArr[i] = add;
                            }
                        }
                        topPlayer.setHasShaked(true);
                    }
                    adjust(topPlayer, botPlayer);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    public void rulesBtn() {
        Button nextButton = (Button) findViewById(R.id.rules);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, rules.class));
            }
        });
    }

    public void endTurn(View v) {
        resetClicks();
        yours = null;
        theirs = null;
        attacked = null;
        yourUpg = null;
        for (int i = 0; i < 5; i++) {
            topPlayer.deck.get(i).sleep = false;
            botPlayer.deck.get(i).sleep = false;
        }
        if (current == botPlayer) {
            current = topPlayer;
            botHead.setBackgroundColor(getResources().getColor(android.R.color.black));
            topHead.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        } else {
            current = botPlayer;
            topHead.setBackgroundColor(getResources().getColor(android.R.color.black));
            botHead.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        }
        for (int i = 0; i < 5; i++) {
            if (current.deck.get(i).getDefense() <= 0) {
                break;
            }
            if (i == 4) {
                return;
            }
        }
        for (int i = 0; i < 4; i++) {
            choices[i].setVisibility(View.VISIBLE);
        }
        endturn.setVisibility(View.INVISIBLE);
        textBox.setVisibility(View.VISIBLE);
        choiceSta.setVisibility(View.VISIBLE);
        endScreen.setVisibility(View.VISIBLE);
    }
    public void clickCharacter(View v) {
        if (yourUpg != null) {
            if (yourUpg.getUpgType() == 1) {
                for (int i = 0; i < 5; i++) {
                    if (botChars[i].equals(v)) {
                        botPlayer.deck.set(i, executeUpg(botPlayer.getDeck().get(i)));
                        yourUpg = null;
                        return;
                    } else if (topChars[i].equals(v)) {
                        topPlayer.deck.set(i, executeUpg(topPlayer.getDeck().get(i)));
                        yourUpg = null;
                        return;
                    }
                }
            } else if (yourUpg.getUpgType() == 2) {
                for (int i = 0; i < 5; i++) {
                    if (botChars[i].equals(v)) {
                        botPlayer.deck.set(i, executeUpg(botPlayer.getDeck().get(i)));
                        yourUpg = null;
                        return;
                    } else if (topChars[i].equals(v)) {
                        topPlayer.deck.set(i, executeUpg(topPlayer.getDeck().get(i)));
                        yourUpg = null;
                        return;
                    }
                }
                if (v.equals(botHead)) {
                    botPlayer = executeUpg(botPlayer);
                    yourUpg = null;
                } else if (v.equals(topHead)) {
                    topPlayer = executeUpg(topPlayer);
                    yourUpg = null;
                }
            }
        }
        if (v.equals(topHead) && current == botPlayer) {
            attacked = topPlayer;
            clickAttack(v);
        } else if (v.equals(botHead) && current == topPlayer) {
            attacked = botPlayer;
            clickAttack(v);
        }
        for (int i = 0; i < 5; i++) {
            if (v.equals(topChars[i]) && current == botPlayer) {
                clickAttack(v);
                return;
            } else if (v.equals(botChars[i]) &&  current == topPlayer) {
                clickAttack(v);
                return;
            } else if (v.equals(topChars[i]) &&  current == topPlayer) {
                if (topPlayer.getDeck().get(i).sleep == false) {
                    yours = topPlayer.getDeck().get(convertNumbers(i));
                }
            } else if (v.equals(botChars[i]) && current == botPlayer) {
                if (botPlayer.getDeck().get(i).sleep == false) {
                    yours = botPlayer.getDeck().get(convertNumbers(i));
                }
            }
        }
        resetClicks();
        if (v.equals(botHead) || v.equals(topHead)) {
            return;
        }
        v.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
    }
    public void defense (View v) {
        newTurn(new Characters(1, 7, 1));
    }
    public void midDef (View v) {
        newTurn(new Characters(2, 4, 2));
    }
    public void mid (View v) {
        newTurn(new Characters(3, 3, 3));
    }
    public void highAt (View v) {
        newTurn(new Characters(5, 1, 4));
    }
    public void newTurn (Characters choice) {
        if (current == botPlayer) {
            botPlayer.changeTurns(choice);
        } else if (current == topPlayer) {
            topPlayer.changeTurns(choice);
        }
        for (int i = 0; i < 4; i++) {
            choices[i].setVisibility(View.INVISIBLE);
        }
        endturn.setVisibility(View.VISIBLE);
        textBox.setVisibility(View.INVISIBLE);
        choiceSta.setVisibility(View.INVISIBLE);
        endScreen.setVisibility(View.INVISIBLE);
        adjust(topPlayer, botPlayer);
    }
    public void clickAttack(View v) {
        if (yours == null) {
            return;
        }
        for (int i = 0; i < 5; i++) {
            if (v.equals(topChars[i]) && current == botPlayer) {
                theirs = topPlayer.getDeck().get(convertNumbers(i));
            } else if (v.equals(botChars[i]) && current == topPlayer) {
                theirs = botPlayer.getDeck().get(convertNumbers(i));
            }
        }
        if (theirs != null) {
            attacking(yours, theirs);
            yours = null;
            theirs = null;
            resetClicks();
            return;
        }
        if (attacked == topPlayer) {
            for (int i = 0; i < 5; i++) {
                if (yours.equals(topPlayer.deck.get(i))) {
                    topPlayer.deck.get(i).sleep = true;
                } else if (yours.equals(botPlayer.deck.get(i))) {
                    botPlayer.deck.get(i).sleep = true;
                }
            }
            topPlayer.setHealth(topPlayer.getHealth() - yours.getAttack());
            topHead.setBackgroundColor(getResources().getColor(android.R.color.black));
            yours = null;
            attacked = null;
        } else if (attacked == botPlayer) {
            for (int i = 0; i < 5; i++) {
                if (yours.equals(topPlayer.deck.get(i))) {
                    topPlayer.deck.get(i).sleep = true;
                } else if (yours.equals(botPlayer.deck.get(i))) {
                    botPlayer.deck.get(i).sleep = true;
                }
            }
            botPlayer.setHealth(botPlayer.getHealth() - yours.getAttack());
            topHead.setBackgroundColor(getResources().getColor(android.R.color.black));
            yours = null;
            attacked = null;
        }
        if (botPlayer.getHealth() <= 0 || topPlayer.getHealth() <= 0) {
            endGame();
        }
        adjust(topPlayer, botPlayer);
        resetClicks();
    }
    public void attacking (Characters yours1, Characters theirs1) {
        yours1.attacks(theirs1);
        adjust(topPlayer, botPlayer);
    }
    public int convertNumbers(int i) {
        return i;
    }
    public void resetClicks() {
        for (int i = 0; i < 5; i++) {
            topChars[i].setBackgroundColor(getResources().getColor(android.R.color.background_dark));
            botChars[i].setBackgroundColor(getResources().getColor(android.R.color.background_dark));
        }
    }
    public void upgradeClick(View v) {
        yours = null;
        theirs = null;
        attacked = null;
        resetClicks();
        for (int i = 10; i < 12; i++) {
            if ((v.equals(botCharStats[i]) && current == topPlayer)
                    || (v.equals(topCharStats[i]) && current == botPlayer)) {
                yourUpg = null;
                return;
            }
            if (v.equals(botCharStats[i]) && current == botPlayer) {
                yourUpg = botPlayer.getUpgArr()[i - 10];
                v.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            } else if (v.equals(topCharStats[i]) && current == topPlayer) {
                yourUpg = topPlayer.getUpgArr()[i - 10];
                v.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            }
        }
        if (yourUpg.getUpgType() == 0) {
            executeUpg();
        }
    }
    public void executeUpg() {
        if (yourUpg.getDigit() == 0) {
            if (current == topPlayer) {
                topPlayer.setDeck(yourUpg.aid(topPlayer.getDeck()));
            } else {
                botPlayer.setDeck(yourUpg.aid(botPlayer.getDeck()));
            }
        } else if (yourUpg.getDigit() == 3) {
            if (current == topPlayer) {
                topPlayer.setDeck(yourUpg.birth(topPlayer.getDeck()));
            } else {
                botPlayer.setDeck(yourUpg.birth(botPlayer.getDeck()));
            }

        } else if (yourUpg.getDigit() == 4) {
            if (current == botPlayer) {
                topPlayer.setDeck(yourUpg.weak(topPlayer.getDeck()));
            } else {
                botPlayer.setDeck(yourUpg.weak(botPlayer.getDeck()));
            }
        }
        botPlayer.nullCheck();
        topPlayer.nullCheck();
        adjust(topPlayer, botPlayer);
    }
    public Characters executeUpg(Characters atta) {
        if (yourUpg.getUpgType() == 1) {
            if (yourUpg.getDigit() == 1) {
                atta = yourUpg.hone(atta);
            } else if (yourUpg.getDigit() == 2) {
                atta = yourUpg.death(atta);
            }
        } else if (yourUpg.getUpgType() == 2) {
            if (yourUpg.getDigit() == 5) {
                atta = yourUpg.tank(atta);
            } else if (yourUpg.getDigit() == 6) {
                atta = yourUpg.smite(atta);
            }
        }
        botPlayer.nullCheck();
        topPlayer.nullCheck();
        adjust(topPlayer, botPlayer);
        return atta;
    }
    public Player executeUpg(Player atta) {
        if (yourUpg.getUpgType() == 2) {
            if (yourUpg.getDigit() == 5) {
                atta = yourUpg.tank(atta);
                if (botPlayer.getHealth() <= 0 || topPlayer.getHealth() <= 0) {
                    endGame();
                }
            } else if (yourUpg.getDigit() == 6) {
                atta = yourUpg.smite(atta);
            }
        }
        botPlayer.nullCheck();
        topPlayer.nullCheck();
        adjust(topPlayer, botPlayer);
        return atta;
    }

        public void endGame() {
        TextView endScreen = findViewById(R.id.endScreen);
        endturn.setVisibility(View.INVISIBLE);
        endScreen.setVisibility(View.VISIBLE);
        if (botPlayer.getHealth() <= 0) {
            endScreen.setText("Game Over    Congratulations Top Player!");
        } else {
            endScreen.setText("Game Over    Congratulations Bottom Player!");
        }
        returnToMain.setVisibility(View.VISIBLE);
    }
    public void adjust (Player topPlayer, Player botPlayer) {
        theirs = null;
        yours = null;

        botHealth.setText(String.valueOf(botPlayer.getHealth()));
        topHealth.setText(String.valueOf(topPlayer.getHealth()));
        for (int i = 0; i < 10; i++) {
            if (botPlayer.getDeck().get(i / 2).getDefense() <= 0) {
                botPlayer.getDeck().get(i / 2).setDefense(0);
                botCharStats[i].setVisibility(View.INVISIBLE);
            } else  {
                botCharStats[i].setVisibility(View.VISIBLE);
            }
            if (i % 2 == 0) {
                botCharStats[i].setText(String.valueOf(botPlayer.getDeck().get(i / 2).getDefense()));
            } else {
                botCharStats[i].setText(String.valueOf(botPlayer.getDeck().get(i / 2).getAttack()));
            }
        }
        for (int i = 0; i < 10; i++) {
            if (topPlayer.getDeck().get(i / 2).getDefense() <= 0) {
                topPlayer.getDeck().get(i / 2).setDefense(0);
                topCharStats[i].setVisibility(View.INVISIBLE);
            } else  {
                topCharStats[i].setVisibility(View.VISIBLE);
            }
            if (i % 2 == 0) {
                topCharStats[i].setText(String.valueOf(topPlayer.getDeck().get(i / 2).getDefense()));
            } else {
                topCharStats[i].setText(String.valueOf(topPlayer.getDeck().get(i / 2).getAttack()));
            }
        }

        for (int i = 10; i < 12; i++) {
            if (topPlayer.getUpgArr()[i - 10] != null) {
                topCharStats[i].setText(topPlayer.getUpgArr()[i - 10].getName());
                topCharStats[i].setVisibility(View.VISIBLE);
                topCharStats[i].setBackgroundResource(R.drawable.black_white_background);

            } else {
                topCharStats[i].setVisibility(View.INVISIBLE);
            }
            if (botPlayer.getUpgArr()[i - 10] != null) {
                botCharStats[i].setText(botPlayer.getUpgArr()[i - 10].getName());
                botCharStats[i].setVisibility(View.VISIBLE);
                botCharStats[i].setBackgroundResource(R.drawable.black_white_background);


            } else {
                botCharStats[i].setVisibility(View.INVISIBLE);
            }
        }
        for (int i = 0; i < 5; i++) {
            if (topPlayer.getDeck().get(i).getDefense() <= 0) {
                topChars[i].setVisibility(View.INVISIBLE);
            } else {
                matchPic(i, 1);
                topChars[i].setVisibility(View.VISIBLE);
            }
            if (botPlayer.getDeck().get(i).getDefense() <= 0) {
                botChars[i].setVisibility(View.INVISIBLE);
            } else {
                matchPic(i, 2);
                botChars[i].setVisibility(View.VISIBLE);
            }
        }
    }
    public void matchPic(int ind, int player) {
        if (player == 1) {
            int check = topPlayer.deck.get(ind).charNumb;
            if (check == 1) {
                topChars[ind].setImageResource(R.drawable.defensere);
            } else if (check == 2) {
                topChars[ind].setImageResource(R.drawable.midre);
            } else if (check == 3) {
                topChars[ind].setImageResource(R.drawable.enemy);
            } else {
                topChars[ind].setImageResource(R.drawable.attackre);
            }
        } else {
            int check = botPlayer.deck.get(ind).charNumb;
            if (check == 1) {
                botChars[ind].setImageResource(R.drawable.defensere);
            } else if (check == 2) {
                botChars[ind].setImageResource(R.drawable.midre);
            } else if (check == 3) {
                botChars[ind].setImageResource(R.drawable.enemy);
            } else {
                botChars[ind].setImageResource(R.drawable.attackre);
            }
        }
    }
}

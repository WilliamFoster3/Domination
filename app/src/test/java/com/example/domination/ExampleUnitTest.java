package com.example.domination;

import android.media.Image;
import android.widget.Button;
import android.widget.ImageButton;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static Player yo;
    private static Player yo2;
    private Characters one;
    private Upgrades up;
    private Characters two;
    int i = 0;
    @Before
    public void setUp() throws Exception {
        yo = new Player(20);
        yo2 = new Player(20);
        one = new Characters(69, 4);
        two = new Characters(2, 5);
    }
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void returnHealth() { assertEquals(null, yo.returnHealth(2));
    }
    @Test
    public void random() {assertEquals(2, up.rand());}
}
package com.example.domination;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;

public class titlescreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);
        configureNextButton();
        configureNextButton2();
    }
    private void configureNextButton() {
        Button nextButton = (Button) findViewById(R.id.buttonplay);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(titlescreen.this, MainActivity.class));
            }
        });
    }
    private void configureNextButton2() {
        Button nextButton = (Button) findViewById(R.id.buttonrules);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(titlescreen.this, rules.class));
            }
        });
    }


}

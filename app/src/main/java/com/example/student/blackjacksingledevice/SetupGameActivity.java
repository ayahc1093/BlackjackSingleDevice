package com.example.student.blackjacksingledevice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SetupGameActivity extends Activity {

    Intent intent; //So we can move info to GamePlayActivity.java
    int numberOfPlayersFromIntent = 0; //Parsed from string value of "numofplayers" retrieved from spinner on OpeningActivity.java
    EditText names; //for programmatically adding EditText views
    ArrayList<String> allNames = new ArrayList<String>(); //To store values of EditTexts (names)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_game);

        String text = getIntent().getStringExtra("numofplayers");
        Toast.makeText(SetupGameActivity.this, text, Toast.LENGTH_SHORT).show();

        numberOfPlayersFromIntent = Integer.parseInt(text);
        LinearLayout myLayout = (LinearLayout) findViewById(R.id.llayout);

        intent = new Intent(this, GamePlayActivity.class);

        for (int i = 0; i < numberOfPlayersFromIntent; i++) {
            names = new EditText(SetupGameActivity.this);
            names.setId(i);
            names.setHint("Enter Player's Name Here....");
            //intent.putExtra("name" + i, i); Originally thought to put seperate values, but will be hard to track
            names.setLines(1);
            allNames.add(names.getText().toString());
            myLayout.addView(names);

        }
        intent.putStringArrayListExtra("all", allNames);
    }
    public void openGamePlayActivity(View view) {
        startActivity(this.intent);
    }
}
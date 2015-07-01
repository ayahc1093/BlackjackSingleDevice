package com.example.student.blackjacksingledevice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class SetupGameActivity extends Activity {

    Intent intent; //So we can move info to GamePlayActivity.java
    int numberOfPlayersFromIntent = 0; //Parsed from string value of "numofplayers" retrieved from spinner on OpeningActivity.java
    EditText names; //for programmatically adding EditText views, instead of hardcoding in xml
    List<EditText> allEds = new ArrayList<EditText>();  //To store EditTexts (names)
    LinearLayout myLayout; //To hold EditTexts

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_game);

        String text = getIntent().getStringExtra("numofplayers");

        //To test test that the number of players successfully transferred over with the Intent:
        Toast.makeText(SetupGameActivity.this, text, Toast.LENGTH_SHORT).show();

        numberOfPlayersFromIntent = Integer.parseInt(text);
        myLayout = (LinearLayout) findViewById(R.id.llayout);

        intent = new Intent(this, GamePlayActivity.class);

        for (int i = 0; i < numberOfPlayersFromIntent; i++) {
            names = new EditText(SetupGameActivity.this);
            allEds.add(names);
            names.setId(i);
            names.setHint("Enter Player's Name Here....");
            names.setLines(1);
            myLayout.addView(names);
        }
    }

    public void openGamePlayActivity(View view) {
    /*  To test that all names are captured:
        for (int i = 0; i < allEds.size(); i++) {
            Toast.makeText(this, allEds.get(i).getText().toString(), Toast.LENGTH_SHORT).show();
        }*/
        ArrayList<String> allNames = new ArrayList<>();
        for(int i=0; i < allEds.size(); i++){
            allNames.add(allEds.get(i).getText().toString());
        }
        intent.putStringArrayListExtra("all", allNames);
        startActivity(this.intent);
    }
}
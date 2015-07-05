package com.example.student.blackjacksingledevice;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;

public class GamePlayActivity extends Activity {

    ArrayList<String> allNames;  //To store values of EditTexts (names)
    String player = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        allNames = new ArrayList<>();
        allNames = getIntent().getStringArrayListExtra("all");

        // To test that the ArrayList successfully transferred over with the Intent:
        for (int i = 0; i < allNames.size(); i++) {
            player = allNames.get(i);
            Toast.makeText(GamePlayActivity.this, "Player " + (i + 1) + ": " + player, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

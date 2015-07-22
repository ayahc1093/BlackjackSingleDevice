package com.candeapps.blackjacksingledevice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/*import com.candeapps.blackjacksingledevice.blackjacksingledevice.R;*/


public class OpeningActivity extends Activity {

    private String[] numPlayers = {"1", "2", "3", "4", "5"};

    String splode = "";
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        Spinner spinPlay = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, numPlayers);

        spinPlay.setAdapter(adapter);
        intent = new Intent(this, SetupGameActivity.class);
        spinPlay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                splode=numPlayers[position];
                //Toast.makeText(OpeningActivity.this, "You selected: " + numPlayers[position], Toast.LENGTH_SHORT).show();
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void startGame(View view) {
        intent.putExtra("numofplayers", splode);
        //setResult(RESULT_OK, intent);
        startActivity(this.intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

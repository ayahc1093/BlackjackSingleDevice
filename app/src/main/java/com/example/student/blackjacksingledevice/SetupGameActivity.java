package com.example.student.blackjacksingledevice;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class SetupGameActivity extends Activity {

    static final int SAMPLE_CODE = 0;
    //Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_game);

        String text = getIntent().getStringExtra("numofplayers");
        Toast.makeText(SetupGameActivity.this, text, Toast.LENGTH_SHORT).show();

    }
}
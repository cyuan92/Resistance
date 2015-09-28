package com.steveandconnie.projects.resistance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class VoteForMission extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_for_mission);

        // Get objects passed from previous activity
        Intent intent = getIntent();
        Resistance resistanceGame = (Resistance) intent.getParcelableExtra("resistanceGame");
        ArrayList<Player> playerList = intent.getParcelableArrayListExtra("playerList");
        ArrayList<String> selectedPlayers = intent.getStringArrayListExtra("selectedPlayers");

        // test test
        TextView someText = (TextView) findViewById(R.id.someText);
        someText.setText(selectedPlayers.toString());
        Log.d("selectedPlayers", selectedPlayers.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vote_for_mission, menu);
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

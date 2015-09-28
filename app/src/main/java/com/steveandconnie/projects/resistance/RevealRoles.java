package com.steveandconnie.projects.resistance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;

public class RevealRoles extends AppCompatActivity {

    private ArrayList<Player> playerList;
    private final int NUM_BTNS_PER_ROW = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal_roles);
        Intent intent = getIntent();
        playerList = intent.getParcelableArrayListExtra("playerList");
        createPlayerRoleBtns();
    }

    private void createPlayerRoleBtns() {
        // shuffle to make sure identity not guessable
        Collections.shuffle(playerList);

        // create a grid view with player role toggle buttons
        GridView grid = (GridView) findViewById(R.id.playerRoleBtnGroup);
        grid.setNumColumns(NUM_BTNS_PER_ROW);
        grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        grid.setAdapter(new PlayerRoleButtonAdapter(this, playerList));
    }

    public void onClickStartMissionsBtn(View view) {
        // start screen 3 intent
        Intent currentMissionIntent = new Intent(RevealRoles.this, CurrentMission.class);

        // pass Resistance object to next activity
        Resistance resistanceGame = new Resistance(1, new boolean[5]);
        currentMissionIntent.putExtra("resistanceGame", resistanceGame);
        currentMissionIntent.putParcelableArrayListExtra("playerList", playerList);
        RevealRoles.this.startActivity(currentMissionIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reveal_roles, menu);
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

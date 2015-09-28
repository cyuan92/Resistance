package com.steveandconnie.projects.resistance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class CurrentMission extends AppCompatActivity {

    private ArrayList<Player> playerList;
    private Resistance resistanceGame;
    private final int NUM_BTNS_PER_ROW = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_mission);

        // Get objects passed from previous activity
        Intent intent = getIntent();
        resistanceGame = (Resistance) intent.getParcelableExtra("resistanceGame");
        playerList = intent.getParcelableArrayListExtra("playerList");

        // Display text instructions for how many players to select on this mission
        int numPlayersToSelect = GameLogic.getNumPlayersToSelect(resistanceGame.getCurrentMissionNum(), playerList.size());
        TextView currentMissionInstructions = (TextView) findViewById(R.id.currentMissionInstructions);
        currentMissionInstructions.setText(this.getString(R.string.current_mission_instructions, numPlayersToSelect));

        createPlayerBtns();
    }

    private void createPlayerBtns() {
        // create a grid view with player role toggle buttons
        GridView grid = (GridView) findViewById(R.id.playerBtnGroup);
        grid.setNumColumns(NUM_BTNS_PER_ROW);
        grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        grid.setAdapter(new PlayerSelectButtonAdapter(this, playerList));
    }

    public void onClickVoteMissionsBtn(View view) {
        ViewGroup playerGroup = (ViewGroup)findViewById(R.id.playerBtnGroup);
        ArrayList<String> selectedPlayers = new ArrayList<String>();
        for(int i = 0; i < playerGroup.getChildCount(); i++ ) {
            // children in id.playerBtnGroup are toggle buttons
            ToggleButton player = (ToggleButton)playerGroup.getChildAt(i);

            if (player.isChecked()) {
                selectedPlayers.add(player.getText().toString());
            }
        }

        if (GameLogic.checkNumPlayersSelected(selectedPlayers.size(), resistanceGame.getCurrentMissionNum(), playerList.size())) {
            // go vote
            Intent voteForMissionIntent = new Intent(CurrentMission.this, VoteForMission.class);
            voteForMissionIntent.putExtra("resistanceGame", resistanceGame);
            voteForMissionIntent.putParcelableArrayListExtra("playerList", playerList);
            voteForMissionIntent.putStringArrayListExtra("selectedPlayers", selectedPlayers); //eventually will pass player objects
            CurrentMission.this.startActivity(voteForMissionIntent);
        } else {
            // display error message and try again
            String title = this.getString(R.string.alert_title);
            String message = this.getString(R.string.num_players_selected_alert_message);
            String positiveBtnMessage = this.getString(R.string.alert_okay);
            GameUtils.displayAlertMessage(this, title, message, positiveBtnMessage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_current_mission, menu);
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

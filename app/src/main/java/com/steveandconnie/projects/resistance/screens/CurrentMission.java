package com.steveandconnie.projects.resistance.screens;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import com.steveandconnie.projects.resistance.R;
import com.steveandconnie.projects.resistance.common.GameRules;
import com.steveandconnie.projects.resistance.common.GameUtils;
import com.steveandconnie.projects.resistance.common.Player;
import com.steveandconnie.projects.resistance.common.PlayerSelectButtonAdapter;
import com.steveandconnie.projects.resistance.common.Resistance;

import java.util.ArrayList;
import java.util.HashMap;

public class CurrentMission extends AppCompatActivity {

    private ArrayList<Player> playerList;
    private ArrayList<Player> selectedPlayerList;
    private HashMap<String, Player> nameToPlayerMap;

    private Resistance resistanceGame;
    private final int NUM_BTNS_PER_ROW = 3;
    int numPlayersToSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_mission);

        // Get objects passed from previous activity
        Intent intent = getIntent();
        resistanceGame = (Resistance) intent.getParcelableExtra("resistanceGame");
        playerList = intent.getParcelableArrayListExtra("playerList");
        nameToPlayerMap = new HashMap<String, Player>();
        for(Player player : playerList) {
            nameToPlayerMap.put(player.getPlayerName(), player);
        }

        // Display text instructions for how many players to select on this mission
        int numPlayers = resistanceGame.getNumPlayers();
        numPlayersToSelect = GameRules.getNumPlayersToSelect(resistanceGame.getCurrentMissionNum(), numPlayers);
        boolean isFourthMission = resistanceGame.getCurrentMissionNum() == 4;
        int numPlayersToFail = GameRules.MISSION_FOUR_FAILS_NEEDED.get(numPlayers);
        TextView currentMissionInstructions = (TextView) findViewById(R.id.currentMissionInstructions);
        String playerSelectInstructions = this.getString(R.string.current_mission_instructions, numPlayersToSelect);
        String message = playerSelectInstructions;

        if (isFourthMission && numPlayersToFail > 1) {
            String playerFailInstructions = this.getString(R.string.fourth_mission_instructions, numPlayersToFail);
            message += playerFailInstructions;
        }

        currentMissionInstructions.setText(message);
        createPlayerBtns();

        // add mission history fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MissionHistoryFragment fragment = MissionHistoryFragment.newInstance(resistanceGame.getCurrentMissionNum(), resistanceGame.getMissionHistory());
        fragmentTransaction.add(R.id.missionHistoryFragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    private void createPlayerBtns() {
        // create a grid view with player role toggle buttons
        GridView grid = (GridView) findViewById(R.id.playerBtnGroup);
        grid.setNumColumns(NUM_BTNS_PER_ROW);
        grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        grid.setAdapter(new PlayerSelectButtonAdapter(this, playerList, null));
    }

    public void onClickVoteMissionsBtn(View view) {
        // create a new list of the selected players
        ViewGroup playerGroup = (ViewGroup)findViewById(R.id.playerBtnGroup);
        selectedPlayerList = new ArrayList<Player>();
        for(int i = 0; i < playerGroup.getChildCount(); i++ ) {
            // children in id.playerBtnGroup are toggle buttons
            ToggleButton playerBtn = (ToggleButton)playerGroup.getChildAt(i);

            if (playerBtn.isChecked()) {
                String playerName = playerBtn.getText().toString();
                Player player = nameToPlayerMap.get(playerName);
                selectedPlayerList.add(player);
            }
        }

        if (GameRules.checkNumPlayersSelected(selectedPlayerList.size(), resistanceGame.getCurrentMissionNum(), playerList.size())) {

            // go vote
            Intent voteForMissionIntent = new Intent(CurrentMission.this, LaunchMission.class);
            voteForMissionIntent.putExtra("resistanceGame", resistanceGame);
            voteForMissionIntent.putParcelableArrayListExtra("playerList", playerList);
            voteForMissionIntent.putParcelableArrayListExtra("selectedPlayerList", selectedPlayerList);
            CurrentMission.this.startActivity(voteForMissionIntent);
        } else {
            // display error message and try again
            String title = this.getString(R.string.alert_title);
            String message = this.getString(R.string.num_players_selected_alert_message, numPlayersToSelect);
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

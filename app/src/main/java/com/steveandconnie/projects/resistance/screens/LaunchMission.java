package com.steveandconnie.projects.resistance.screens;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;

import com.steveandconnie.projects.resistance.R;
import com.steveandconnie.projects.resistance.common.GameRules;
import com.steveandconnie.projects.resistance.common.Player;
import com.steveandconnie.projects.resistance.common.PlayerSelectButtonAdapter;
import com.steveandconnie.projects.resistance.common.Resistance;
import com.steveandconnie.projects.resistance.common.Role;

import java.util.ArrayList;
import java.util.HashMap;

public class LaunchMission extends AppCompatActivity {

    private ArrayList<Player> playerList;
    private ArrayList<Player> selectedPlayerList;
    private HashMap<Player, Boolean> playerToVoteMap;
    private HashMap<String, Player> nameToPlayerMap;

    private Resistance resistanceGame;
    private final int NUM_BTNS_PER_ROW = 3;
    private final int VOTE_RESULT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mission);

        playerToVoteMap = new HashMap<Player, Boolean>();

        // Get objects passed from previous activity
        Intent intent = getIntent();
        resistanceGame = (Resistance) intent.getParcelableExtra("resistanceGame");
        selectedPlayerList = intent.getParcelableArrayListExtra("selectedPlayerList");

        // TODO: put nameToPlayerMap in Resistance object
        // create hashmap of player name to Player
        nameToPlayerMap = new HashMap<String, Player>();
        for (Player p : selectedPlayerList) {
            nameToPlayerMap.put(p.getPlayerName(), p);
        }

        // display voting instructions
        TextView votingInstructions = (TextView) findViewById(R.id.votingInstructions);
        boolean isFourthMission = resistanceGame.getCurrentMissionNum() == 4;
        int numPlayersToFail = GameRules.MISSION_FOUR_FAILS_NEEDED.get(resistanceGame.getNumPlayers());

        String playerVoteInstructions = this.getString(R.string.vote_for_mission_instructions);
        String message = playerVoteInstructions;

        if (isFourthMission && numPlayersToFail > 1) {
            String fourthMissionMessage = this.getString(R.string.fourth_mission_instructions, numPlayersToFail);
            message += fourthMissionMessage;
        }

        votingInstructions.setText(message);

        // create player buttons
        createPlayerBtns();

        // add mission history fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MissionHistoryFragment fragment = MissionHistoryFragment.newInstance(resistanceGame.getCurrentMissionNum(), resistanceGame.getMissionHistory());
        fragmentTransaction.add(R.id.missionHistoryFragmentContainer, fragment);
        fragmentTransaction.commit();
    }


    private void createPlayerBtns() {
        // create listener
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // go to next activity for voting
            String votingPlayerName = (String) buttonView.getText();
            Intent voteIntent = new Intent(LaunchMission.this, MissionVote.class);
            voteIntent.putExtra("votingPlayerName", votingPlayerName);
            LaunchMission.this.startActivityForResult(voteIntent, VOTE_RESULT_REQUEST);
            }
        };
        // create a grid view with player role toggle buttons
        GridView grid = (GridView) findViewById(R.id.playerBtnGroup);
        grid.setNumColumns(NUM_BTNS_PER_ROW);
        grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        grid.setAdapter(new PlayerSelectButtonAdapter(this, selectedPlayerList, listener));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent backToMission) {
        if (requestCode == VOTE_RESULT_REQUEST && resultCode == RESULT_OK) {
            boolean voteResult = backToMission.getBooleanExtra("voteResult", true);
            String votingPlayerName = backToMission.getStringExtra("votingPlayerName");
            Log.d("check result", "onActivityResult returned result: " + voteResult + " votingPlayerName: " + votingPlayerName);
            // update playerToVoteMap
            Player votingPlayer = nameToPlayerMap.get(votingPlayerName);
            playerToVoteMap.put(votingPlayer, voteResult);
        }
    }

    public void onClickRevealMissionResultBtn(View view) {
        // TODO: check that all players have voted
        int numFails = countNumFails();
        boolean missionFailed = false;
        if (resistanceGame.getCurrentMissionNum() == 4) {
            // find out number of fails needed
            int numFailsNeeded = GameRules.getMissionFourNumFailsNeeded(resistanceGame.getNumPlayers());
            if (numFails >= numFailsNeeded) {
                missionFailed = true;
            }
        } else if (numFails >= 1) {
            missionFailed = true;
        }
        resistanceGame.setMissionResult(!missionFailed);
        //
        Intent showMissionResult = new Intent(LaunchMission.this, MissionResult.class);
        showMissionResult.putExtra("resistanceGame", resistanceGame);
        showMissionResult.putExtra("missionFailed", missionFailed);
        LaunchMission.this.startActivity(showMissionResult);
    }

    public int countNumFails() {
        int numFails = 0;
        for (Player p : selectedPlayerList) {
            boolean vote = playerToVoteMap.get(p);
            // REBELs will always vote pass
            if (p.getPlayerRole() == Role.REBEL) {
                vote = true;
                playerToVoteMap.put(p, vote);
            }
            numFails += (vote ? 0 : 1);
        }
        return numFails;
    }

    // ---------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launch_mission, menu);
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

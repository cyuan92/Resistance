package com.steveandconnie.projects.resistance.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.steveandconnie.projects.resistance.R;
import com.steveandconnie.projects.resistance.common.GameRules;
import com.steveandconnie.projects.resistance.common.GameUtils;
import com.steveandconnie.projects.resistance.common.Player;
import com.steveandconnie.projects.resistance.common.Resistance;
import com.steveandconnie.projects.resistance.common.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateGame extends AppCompatActivity {

    public static final int MIN_NUM_PLAYERS = 5;
    public static final int MAX_NUM_PLAYERS = 10;
    private static int numPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        Intent intent = getIntent();
        ArrayList<Player> playerList = intent.getParcelableArrayListExtra("playerList");
        numPlayers = 0;

        final LinearLayout playerNamesGroup = (LinearLayout) findViewById(R.id.playerNamesGroup);
        if (playerList == null) {
            // dynamically create min new players
            for (int i = 0; i < MIN_NUM_PLAYERS; i++) {
                PlayerItem item = new PlayerItem(this, null);
                // add player item to overall players list
                playerNamesGroup.addView(item);
            }
        } else {
            for (Player player : playerList) {
                PlayerItem item = new PlayerItem(this, player.getPlayerName());
                playerNamesGroup.addView(item);
            }

        }
    }

    protected static void incrementNumPlayers() {
        numPlayers++;
    }

    protected static void decrementNumPlayers() {
        numPlayers--;
    }

    public void onClickAddPlayerBtn(View view) {
        LinearLayout playerNamesGroup = (LinearLayout) findViewById(R.id.playerNamesGroup);

        // do not want more than 10 players
        if (playerNamesGroup.getChildCount() >= 10) {
            return;
        }

        // add a player
        PlayerItem item = new PlayerItem(this, null);

        // add player item to overall players list
        playerNamesGroup.addView(item);

        // scroll to added player
        item.getParent().requestChildFocus(item, item);
    }

    public void onClickStartBtn(View view) {
        //start game logic. grab game name
        EditText gameNameText = (EditText)findViewById(R.id.gameName);
        String gameName = gameNameText.getText().toString();

        // grab player names and put them in a list
        ViewGroup playerGroup = (ViewGroup)findViewById(R.id.playerNamesGroup);
        List<String> playerNames = new ArrayList<String>();
        for(int i = 0; i < playerGroup.getChildCount(); i++ ) {
            // children in id.playerNamesGroup are PlayerItems (linear layouts)
            PlayerItem player = (PlayerItem) playerGroup.getChildAt(i);

            // TODO: remove this testing code
            EditText playerName = (EditText) player.getChildAt(0);
            playerName.setText("Player"+i);

            playerNames.add(player.getPlayerName());
        }

        // run start game logic
        startGame(gameName, playerNames);
    }

    private void startGame(String gameName, List<String> playerNames) {
        if (numPlayers < MIN_NUM_PLAYERS || numPlayers > MAX_NUM_PLAYERS) {
            // check valid number of players
            String title = this.getString(R.string.alert_title);
            String message = this.getString(R.string.num_player_alert_message, MIN_NUM_PLAYERS, MAX_NUM_PLAYERS);
            String positiveBtnMessage = this.getString(R.string.alert_okay);
            GameUtils.displayAlertMessage(this, title, message, positiveBtnMessage);
        } else if (!GameUtils.checkStringListValid(playerNames)) {
            // check if players have names
            String title = this.getString(R.string.alert_title);
            String message = this.getString(R.string.player_name_alert_message);
            String positiveBtnMessage = this.getString(R.string.alert_okay);
            GameUtils.displayAlertMessage(this, title, message, positiveBtnMessage);
        } else {
            // run assign role logic
            ArrayList<Player> playerList = assignRoles(playerNames);

            // start screen 2 intent
            Intent revealRolesIntent = new Intent(CreateGame.this, RevealRoles.class);

            // create instance of Resistance object
            // TODO: remove this test code
            boolean[] missionHistory = new boolean[]{true, true, false};
            Resistance resistanceGame = new Resistance(4, missionHistory, playerList);      // for debugging
//        Resistance resistanceGame = new Resistance(playerList);

            // pass playerList to next activity
            revealRolesIntent.putExtra("resistanceGame", resistanceGame);
            CreateGame.this.startActivity(revealRolesIntent);
        }
    }

    private ArrayList<Player> assignRoles(List<String> playerNames) {
        // determine number of spies in game
        int numSpies = GameRules.NUM_PLAYERS_TO_SPIES.get(numPlayers);

        // create Players and store them in a list
        Collections.shuffle(playerNames);
        ArrayList<Player> playerList = new ArrayList<Player>();
        for (int i=0; i<numPlayers; i++) {
            Player player;
            if (i<numSpies) {
                // assign first N people as spies
                player = new Player(playerNames.get(i), Role.SPY);
            } else {
                // all other people are rebels
                player = new Player(playerNames.get(i), Role.REBEL);
            }
            playerList.add(player);
        }
        return playerList;
    }
    
    // ---------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_game, menu);
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

    // ---------------------------------------------------------------------------------------------

    private class PlayerItem extends LinearLayout {

        private String playerName = null;
        private EditText playerNameEditText;

        private PlayerItem(Context context, String playerName) {
            super(context);
            this.setOrientation(LinearLayout.HORIZONTAL);

            this.playerNameEditText = new EditText(context);

            this.playerNameEditText.setSingleLine(true);
            this.playerNameEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);

            if (playerName == null || playerName.isEmpty()) {
                // add player hint to each input
                String playerHint = context.getString(R.string.player_hint);
                this.playerNameEditText.setHint(playerHint);
            } else {
                this.playerNameEditText.setText(playerName);
            }

            // give the edit text a width_weight
            LinearLayout.LayoutParams playerParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0.7f);
            this.playerNameEditText.setLayoutParams(playerParams);

            // create remove button for player
            final Button removePlayerBtn = new Button(context);
            String removeBtnName = context.getString(R.string.remove_btn_name);
            removePlayerBtn.setText(removeBtnName);
            removePlayerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // grab the parent vertical layout and remove this PlayerItem
                    LinearLayout playerNamesGroup = (LinearLayout) PlayerItem.this.getParent();
                    playerNamesGroup.removeView(PlayerItem.this);
                    CreateGame.decrementNumPlayers();
                }
            });
            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0.3f);
            this.playerNameEditText.setLayoutParams(btnParams);

            // add player name and remove button to player item
            this.addView(this.playerNameEditText);
            this.addView(removePlayerBtn);

            CreateGame.incrementNumPlayers();
        }

        private String getPlayerName() {
            if (this.playerName == null || this.playerName.isEmpty()) {
                this.playerName = this.playerNameEditText.getText().toString();
            }
            return this.playerName;
        }
    }
}

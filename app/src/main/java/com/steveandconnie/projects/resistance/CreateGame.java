package com.steveandconnie.projects.resistance;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CreateGame extends AppCompatActivity {

    public static final int MIN_NUM_PLAYERS = 5;
    public static final int MAX_NUM_PLAYERS = 10;

    private static int numPlayers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        // dynamically create min new players
        final LinearLayout playerNamesGroup = (LinearLayout) findViewById(R.id.playerNamesGroup);
        for (int i = 0; i < MIN_NUM_PLAYERS; i++) {
            PlayerItem item = new PlayerItem(this);
            // add player item to overall players list
            playerNamesGroup.addView(item);
        }
    }

    protected static void incrementNumPlayers() {
        numPlayers++;
    }

    protected static void decrementNumPlayers() {
        numPlayers--;
    }

    public void onClickAddPlayerBtn(View view) {
        // add a player
        PlayerItem item = new PlayerItem(this);

        // add player item to overall players list
        final LinearLayout playerNamesGroup = (LinearLayout) findViewById(R.id.playerNamesGroup);
        playerNamesGroup.addView(item);

    }

    public void onClickStartBtn(View view) {
        //start game logic. grab game name
        EditText gameNameText = (EditText)findViewById(R.id.gameName);
        String gameName = gameNameText.getText().toString();
        // grab player names and put them in a list
        ViewGroup playerGroup = (ViewGroup)findViewById(R.id.playerNamesGroup);
        List<String> playerNames = new ArrayList<String>();
        for(int i = 0; i < playerGroup.getChildCount(); i++ ) {
            // children in playerNamesGroup are PlayerItem linear layouts
            PlayerItem player = (PlayerItem)playerGroup.getChildAt(i);
            playerNames.add(player.getPlayerName());
        }

        startGame(gameName, playerNames);
        //pass to screen 2
    }



    private void startGame(String gameName, List<String> playerNames) {
        if (numPlayers < MIN_NUM_PLAYERS || numPlayers > MAX_NUM_PLAYERS) {
            String title = this.getString(R.string.alert_title);
            String message = this.getString(R.string.num_player_alert_message, MIN_NUM_PLAYERS, MAX_NUM_PLAYERS);
            String positiveBtnMessage = this.getString(R.string.alert_okay);
            GameUtils.displayAlertMessage(this, title, message, positiveBtnMessage);
        } else if (!GameUtils.checkStringListValid(playerNames)) {
            String title = this.getString(R.string.alert_title);
            String message = this.getString(R.string.player_name_alert_message);
            String positiveBtnMessage = this.getString(R.string.alert_okay);
            GameUtils.displayAlertMessage(this, title, message, positiveBtnMessage);
        } else {
            Intent revealRolesIntent = new Intent(CreateGame.this, RevealRoles.class);
            //revealRolesIntent.putExtra("key", value); //optional parameters
            CreateGame.this.startActivity(revealRolesIntent);
        }


        // update text view for visual feedback
        TextView testOutput = (TextView)findViewById(R.id.testOutput);
        testOutput.setText("Game name: " + gameName + "\nPlayer names: " + playerNames);
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
}

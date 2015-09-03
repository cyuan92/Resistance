package com.steveandconnie.projects.resistance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CreateGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
    }

    public void onClickStartBtn(View view) {
        //start game logic
        EditText gameNameText = (EditText)findViewById(R.id.gameName);
        String gameName = gameNameText.getText().toString();

        ViewGroup playerGroup = (ViewGroup)findViewById(R.id.playerNamesGroup);
        List<String> playerNames = new ArrayList<String>();
        for(int i = 0; i < playerGroup.getChildCount(); i++ ) {
            EditText playerText = (EditText)playerGroup.getChildAt(i);
            playerNames.add(playerText.getText().toString());
        }
        startGame(gameName, playerNames);
        //pass to screen 2
    }

    private void startGame(String gameName, List<String> playerNames) {
        TextView testOutput = (TextView)findViewById(R.id.testOutput);

        testOutput.setText("Game name: " + gameName + " Player names: " + playerNames);
    }

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

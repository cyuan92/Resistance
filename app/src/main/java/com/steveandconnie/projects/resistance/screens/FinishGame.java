package com.steveandconnie.projects.resistance.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.steveandconnie.projects.resistance.R;
import com.steveandconnie.projects.resistance.common.Resistance;
import com.steveandconnie.projects.resistance.common.Role;

public class FinishGame extends AppCompatActivity {

    private Resistance resistanceGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_game);
        Intent intent = getIntent();
        resistanceGame = (Resistance) intent.getParcelableExtra("resistanceGame");
        TextView view = (TextView) findViewById(R.id.finishGameResult);
        view.setText(getWinnerAnnouncement());
    }

    public String getWinnerAnnouncement() {
        Role winner = resistanceGame.getWinner();
        String winnerAnnouncement = null;
        if (winner == Role.REBEL) {
            winnerAnnouncement = getString(R.string.rebel_win_announcement);
        } else if (winner == Role.SPY) {
            winnerAnnouncement = getString(R.string.spy_win_announcement);
        }

        return winnerAnnouncement;
    }

    public void onClickStartNewGameBtn(View view) {
        Intent intent = new Intent(FinishGame.this, CreateGame.class);
        intent.putParcelableArrayListExtra("playerList", resistanceGame.getPlayerList());
        FinishGame.this.startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_finish_game, menu);
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

package com.steveandconnie.projects.resistance.screens;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.steveandconnie.projects.resistance.R;
import com.steveandconnie.projects.resistance.common.Resistance;

public class MissionResult extends AppCompatActivity {

    Resistance resistanceGame;
    boolean missionFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_result);

        //
        Intent intent = getIntent();
        resistanceGame = (Resistance) intent.getParcelableExtra("resistanceGame");

        // TODO: implement a countdown

        missionFailed = intent.getBooleanExtra("missionFailed", false);
        TextView text = (TextView) findViewById(R.id.missionResultText);
        if (missionFailed) {
            text.setText("FAILED!");
        } else {
            text.setText("PASSED!");
        }
        boolean isGameOver = resistanceGame.checkIfGameOver();
        if (isGameOver) {
            Button btn = (Button) findViewById(R.id.goToNextMissionBtn);
            btn.setText("Finish game");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MissionResult.this, FinishGame.class);
                    intent.putExtra("resistanceGame", resistanceGame);
                    MissionResult.this.startActivity(intent);
                }
            });
        }

        // add mission history fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MissionHistoryFragment fragment = MissionHistoryFragment.newInstance(resistanceGame.getCurrentMissionNum(), resistanceGame.getMissionHistory());
        fragmentTransaction.add(R.id.missionHistoryFragmentContainer, fragment);
        fragmentTransaction.commit();
    }



    public void onClickGoToNextMissionBtn(View view) {
        resistanceGame.incrementMissionNum();
        Intent intent = new Intent(MissionResult.this, CurrentMission.class);
        intent.putExtra("resistanceGame", resistanceGame);
        MissionResult.this.startActivity(intent);
    }

    // ---------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mission_result, menu);
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

package com.steveandconnie.projects.resistance.screens;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.steveandconnie.projects.resistance.R;

import java.util.Random;

public class MissionVote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_vote);

        // get voting player name
        Intent intent = getIntent();
        final String votingPlayerName = intent.getStringExtra("votingPlayerName");

        // programmatically add pass and fail buttons
        LinearLayout voteBtnWrapper = (LinearLayout) findViewById(R.id.voteBtnWrapper);

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 300);
        Button passBtn = new Button(this);
        passBtn.setText("Pass");
        passBtn.setLayoutParams(btnParams);

        Button failBtn = new Button(this);
        failBtn.setText("Fail");
        failBtn.setHeight(100);
        failBtn.setLayoutParams(btnParams);

        Random num = new Random();
        boolean order = num.nextBoolean();
        if (order) {
            voteBtnWrapper.addView(passBtn);
            voteBtnWrapper.addView(failBtn);
        } else {
            voteBtnWrapper.addView(failBtn);
            voteBtnWrapper.addView(passBtn);
        }

        passBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToMission = new Intent();
                backToMission.putExtra("voteResult", true);
                backToMission.putExtra("votingPlayerName", votingPlayerName);
                setResult(RESULT_OK, backToMission);
                finish();
            }
        });

        failBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToMission = new Intent();
                backToMission.putExtra("voteResult", false);
                backToMission.putExtra("votingPlayerName", votingPlayerName);
                setResult(RESULT_OK, backToMission);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mission_vote, menu);
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

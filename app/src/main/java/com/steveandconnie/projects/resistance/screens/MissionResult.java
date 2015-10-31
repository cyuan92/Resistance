package com.steveandconnie.projects.resistance.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.steveandconnie.projects.resistance.R;

public class MissionResult extends AppCompatActivity {

    boolean missionFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_result);

        //
        Intent intent = getIntent();
        missionFailed = intent.getBooleanExtra("missionFailed", false);
        TextView text = (TextView) findViewById(R.id.yo);
        if (missionFailed) {
            text.setText("FAIL BITCHES");
        } else {
            text.setText("PASSSSSSS");
        }
    }

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

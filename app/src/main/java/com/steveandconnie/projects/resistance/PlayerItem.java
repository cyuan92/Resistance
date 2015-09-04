package com.steveandconnie.projects.resistance;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by connieyuan on 9/3/15.
 */
public class PlayerItem extends LinearLayout {

    private String playerName;

    protected PlayerItem(Context context, String playerName) {
        super(context);
        this.setOrientation(LinearLayout.HORIZONTAL);

        // create player name field
        this.playerName = playerName;
        final EditText playerNameEditText = new EditText(context);
        playerNameEditText.setText(playerName);
        // give the edit text a width_weight
        LinearLayout.LayoutParams playerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                0.7f);
        playerNameEditText.setLayoutParams(playerParams);

        // create remove button for player
        final Button removePlayerBtn = new Button(context);
        removePlayerBtn.setText("Remove");
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
        playerNameEditText.setLayoutParams(btnParams);

        // add player name and remove button to player item
        this.addView(playerNameEditText);
        this.addView(removePlayerBtn);
        CreateGame.incrementNumPlayers();
    }

    public String getPlayerName() {
        return this.playerName;
    }
}

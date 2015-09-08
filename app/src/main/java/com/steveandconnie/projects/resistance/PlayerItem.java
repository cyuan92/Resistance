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

    private String playerName = null;
    private EditText playerNameEditText;

    public PlayerItem(Context context) {
        super(context);
        this.setOrientation(LinearLayout.HORIZONTAL);

        this.playerNameEditText = new EditText(context);

        // add player hint to each input
        String playerHint = context.getString(R.string.player_hint);
        this.playerNameEditText.setHint(playerHint);

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

    public String getPlayerName() {
        if (this.playerName == null || this.playerName.isEmpty()) {
            this.playerName = this.playerNameEditText.getText().toString();
        }
        return this.playerName;
    }
}

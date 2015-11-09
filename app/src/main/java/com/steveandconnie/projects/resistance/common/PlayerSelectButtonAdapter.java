package com.steveandconnie.projects.resistance.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.steveandconnie.projects.resistance.common.Player;

import java.util.List;

/**
 * Created by connieyuan on 9/27/15.
 */
public class PlayerSelectButtonAdapter extends BaseAdapter {

    private Context context;
    private List<Player> playerList;
    private CompoundButton.OnCheckedChangeListener listener;

    public PlayerSelectButtonAdapter(Context context, List<Player> playerList, CompoundButton.OnCheckedChangeListener listener) {
        this.context = context;
        this.playerList = playerList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return playerList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ToggleButton playerRoleBtn;
        if (convertView == null) {
            // create player toggle button from playerList
            Player player = playerList.get(position);
            playerRoleBtn = new ToggleButton(context);
            playerRoleBtn.setText(player.getPlayerName());
            playerRoleBtn.setTextOn(player.getPlayerName());
            playerRoleBtn.setTextOff(player.getPlayerName());

            // set the listener, if not null
            if (this.listener != null) {
                playerRoleBtn.setOnCheckedChangeListener(listener);
            }

            // size the toggle button
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int width = metrics.widthPixels / 4;
            playerRoleBtn.setWidth(width);
            playerRoleBtn.setHeight(width);
        } else {
            playerRoleBtn = (ToggleButton) convertView;
        }
        return playerRoleBtn;
    }
}

package com.steveandconnie.projects.resistance;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ToggleButton;

import java.util.List;

/**
 * Created by connieyuan on 9/27/15.
 */
public class PlayerSelectButtonAdapter extends BaseAdapter {
    private Context context;
    private List<Player> playerList;

    public PlayerSelectButtonAdapter(Context context, List<Player> playerList) {
        this.context = context;
        this.playerList = playerList;
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

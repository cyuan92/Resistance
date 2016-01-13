package com.steveandconnie.projects.resistance.common;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ToggleButton;

import java.util.List;

/**
 * Created by connieyuan on 9/12/15.
 */
public class PlayerRoleButtonAdapter extends BaseAdapter {

    private Context context;
    private List<Player> playerList;
    private MediaPlayer onMp;
    private MediaPlayer offMp;

    public PlayerRoleButtonAdapter(Context context, List<Player> playerList, int onResId, int offResId) {
        this.context = context;
        this.playerList = playerList;
        this.onMp = MediaPlayer.create(context, onResId);
        this.offMp = MediaPlayer.create(context, offResId);
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
        final ToggleButton playerRoleBtn;
        if (convertView == null) {
            // create player toggle button from playerList
            Player player = playerList.get(position);
            playerRoleBtn = new ToggleButton(context);
            playerRoleBtn.setText(player.getPlayerName());
            playerRoleBtn.setTextOff(player.getPlayerName());
            playerRoleBtn.setTextOn(player.getPlayerRole().toString());

//            playerRoleBtn.setBackgroundTintList(context.getResources().getColorStateList(R.color.reveal_roles_colorstatelist, null));

            playerRoleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playerRoleBtn.isChecked()) {
                        while (onMp.isPlaying() || offMp.isPlaying()) {
                            // wait to complete
                        }
                        onMp.start();
                    } else {
                        while (onMp.isPlaying() || offMp.isPlaying()) {
                            // wait to complete
                        }
                        offMp.start();
                    }

                }
            });

            // size the toggle button
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int width = metrics.widthPixels / 4;
            playerRoleBtn.setWidth(width);
            playerRoleBtn.setHeight((int) (1.3 * width));
        } else {
            playerRoleBtn = (ToggleButton) convertView;
        }
        return playerRoleBtn;
    }
}
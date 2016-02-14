package com.example.brianmote.teammanager.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brianmote.teammanager.R;

import java.util.ArrayList;

/**
 * Created by Brian Mote on 2/12/2016.
 */
public class UserTeamsAdapter extends RecyclerView.Adapter<UserTeamsAdapter.UserTeamsHolder>{

    private ArrayList<String> teamNameList;
    private int itemLayout;

    public UserTeamsAdapter(ArrayList<String> teamNameList, int itemLayout) {
        this.teamNameList = teamNameList;
        this.itemLayout = itemLayout;
    }

    @Override
    public UserTeamsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new UserTeamsHolder(view);
    }

    @Override
    public void onBindViewHolder(UserTeamsHolder holder, int position) {
        holder.nameView.setText(teamNameList.get(position));

    }

    @Override
    public int getItemCount() {
        return teamNameList.size();
    }

    public static class UserTeamsHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        public UserTeamsHolder(View itemView) {
            super(itemView);
            nameView = (TextView)itemView.findViewById(R.id.userTeamsView);
        }
    }
}

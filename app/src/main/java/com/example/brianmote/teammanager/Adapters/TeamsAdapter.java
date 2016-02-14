package com.example.brianmote.teammanager.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brianmote.teammanager.Pojos.Team;
import com.example.brianmote.teammanager.R;

import java.util.ArrayList;

/**
 * Created by Brian Mote on 2/14/2016.
 */
public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.TeamsHolder>{
    private ArrayList<Team> teams;
    private int itemLayout;

    public TeamsAdapter(ArrayList<Team> teams, int itemLayout) {
        this.teams = teams;
        this.itemLayout = itemLayout;
    }

    @Override
    public TeamsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new TeamsHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamsHolder holder, int position) {
        holder.teamNameHolder.setText(teams.get(position).getName());
        holder.teamRankHolder.setText(teams.get(position).getRank());
        holder.teamGameHolder.setText(teams.get(position).getGame());
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public static class TeamsHolder extends RecyclerView.ViewHolder {
        TextView teamNameHolder;
        TextView teamRankHolder;
        TextView teamGameHolder;

        public TeamsHolder(View itemView) {
            super(itemView);
            teamNameHolder = (TextView) itemView.findViewById(R.id.teamNameHolder);
            teamRankHolder = (TextView) itemView.findViewById(R.id.teamRankHolder);
            teamGameHolder = (TextView) itemView.findViewById(R.id.teamGameHolder);
        }
    }
}

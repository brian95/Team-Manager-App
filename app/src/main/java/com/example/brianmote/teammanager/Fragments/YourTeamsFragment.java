package com.example.brianmote.teammanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brianmote.teammanager.Firebase.TeamHandler;
import com.example.brianmote.teammanager.Models.Team;
import com.example.brianmote.teammanager.R;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class YourTeamsFragment extends Fragment {
    private static final String PAGE_NUM = "page_num";
    private int posArg;
    private OnFragmentInteractionListener mListener;
    private TeamHandler handler;
    private Team team;
    private FirebaseRecyclerAdapter mAdapter;
    private ArrayList<Team> teamsList;
    private RecyclerView teamsRView;

    public YourTeamsFragment() {
        // Required empty public constructor
    }

    public static YourTeamsFragment newInstance(int position) {
        YourTeamsFragment fragment = new YourTeamsFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE_NUM, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            posArg = getArguments().getInt(PAGE_NUM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_your_teams, container, false);
        teamsRView = (RecyclerView) mView.findViewById(R.id.yourTeamsRView);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        teamsRView.setLayoutManager(llm);
        fetchData();

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void fetchData() {
        if (handler == null) {
            handler = new TeamHandler();
        }

        mAdapter = new FirebaseRecyclerAdapter<Team, TeamViewHolder>(Team.class, R.layout
                .your_team_holders, TeamViewHolder.class, handler.getAllTeams()) {
            @Override
            protected void populateViewHolder(TeamViewHolder viewHolder, Team team, int position) {
                super.populateViewHolder(viewHolder, team, position);

                viewHolder.teamNameHolder.setText(team.getName());
                viewHolder.teamRankHolder.setText(team.getRank());
                viewHolder.teamGameHolder.setText(team.getGame());
            }

        };
        teamsRView.setAdapter(mAdapter);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        TextView teamNameHolder;
        TextView teamRankHolder;
        TextView teamGameHolder;

        public TeamViewHolder(View itemView) {
            super(itemView);
            teamNameHolder = (TextView) itemView.findViewById(R.id.teamNameHolder);
            teamRankHolder = (TextView) itemView.findViewById(R.id.teamRankHolder);
            teamGameHolder = (TextView) itemView.findViewById(R.id.teamGameHolder);

            teamNameHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("clicked " + getAdapterPosition());
                }
            });

            teamRankHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("clicked rank");
                }
            });

            teamGameHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("clicked game");
                }
            });
        }
    }

}

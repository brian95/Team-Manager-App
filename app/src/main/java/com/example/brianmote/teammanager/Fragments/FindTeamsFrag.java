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

import com.example.brianmote.teammanager.Adapters.TeamsAdapter;
import com.example.brianmote.teammanager.Firebase.FirebaseInit;
import com.example.brianmote.teammanager.Handlers.TeamHandler;
import com.example.brianmote.teammanager.Listeners.FBItemChangeListener;
import com.example.brianmote.teammanager.Pojos.Team;
import com.example.brianmote.teammanager.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class FindTeamsFrag extends Fragment {
    private static final String TAG = "Find Teams Fragment";
    private static final String PAGE_NUM = "page_num";
    private Firebase ref = new Firebase(FirebaseInit.BASE_REF);
    private int posArg;
    private OnFragmentInteractionListener mListener;
    private TeamHandler handler;
    private Team team;
//    private FirebaseRecyclerAdapter mAdapter;
    private TeamsAdapter mAdapter;
    private ArrayList<Team> teamsList;
    private RecyclerView teamsRView;

    public FindTeamsFrag() {
        // Required empty public constructor
    }

    public static FindTeamsFrag newInstance(int position) {
        FindTeamsFrag fragment = new FindTeamsFrag();
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void fetchData() {
        Firebase teamsRef = ref.child("Teams");
        if (handler == null) {
            handler = new TeamHandler();
        }

        if (teamsList == null) {
            teamsList = new ArrayList<>();
        }

        mAdapter = new TeamsAdapter(teamsList, R.layout.your_team_holders);
        teamsRView.setAdapter(mAdapter);

        handler.populateList(teamsRef, new FBItemChangeListener() {
            @Override
            public void onItemChanged(DataSnapshot dataSnapshot) {
                Team team = dataSnapshot.getValue(Team.class);
                if (!teamsList.contains(team)) {
                    teamsList.add(team);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemDeleted(DataSnapshot dataSnapshot) {
                Team team = dataSnapshot.getValue(Team.class);
                if (teamsList.contains(team)) {
                    teamsList.remove(team);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel(FirebaseError error) {
                System.out.println(error.getMessage());
            }
        });
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
}

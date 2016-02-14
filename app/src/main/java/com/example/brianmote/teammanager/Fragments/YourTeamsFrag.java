package com.example.brianmote.teammanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.brianmote.teammanager.Adapters.UserTeamsAdapter;
import com.example.brianmote.teammanager.Firebase.FirebaseInit;
import com.example.brianmote.teammanager.Handlers.UserHandler;
import com.example.brianmote.teammanager.Listeners.FBItemChangeListener;
import com.example.brianmote.teammanager.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class YourTeamsFrag extends Fragment {
    private static final String TAG = "Your Team Fragment";
    private static final String PAGE_NUM = "page_num";
    private int numArg;
    private OnFragmentInteractionListener mListener;
    private UserTeamsAdapter mAdapter;
    private RecyclerView userTeamsRecycler;
    private ArrayList<String> teamNames;
    private UserHandler userHandler;
    private Firebase ref = new Firebase(FirebaseInit.BASE_REF);

    public YourTeamsFrag() {
        // Required empty public constructor
    }


    public static YourTeamsFrag newInstance(int position) {
        YourTeamsFrag fragment = new YourTeamsFrag();
        Bundle args = new Bundle();
        args.putInt(PAGE_NUM, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            numArg = getArguments().getInt(PAGE_NUM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_team, container, false);
        userTeamsRecycler = (RecyclerView) view.findViewById(R.id.userTeamsRecycler);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        userTeamsRecycler.setLayoutManager(llm);
        populateList();
        return view;
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

    @Override
    public void onResume() {
        super.onResume();
    }

    private void populateList() {
        Firebase userTeamsRef = ref.child("Users").child(ref.getAuth().getUid()).child("Teams");
        if (userHandler == null) {
            userHandler = new UserHandler();
        }

        if (teamNames == null) {
            teamNames = new ArrayList<>();
        }

        mAdapter = new UserTeamsAdapter(teamNames, R.layout.user_teams_holder_view);
        userTeamsRecycler.setAdapter(mAdapter);

        userHandler.populateList(userTeamsRef, new FBItemChangeListener() {
            @Override
            public void onItemChanged(DataSnapshot dataSnapshot) {
                Log.d(TAG, dataSnapshot.getKey());
                if (!teamNames.contains(dataSnapshot.getKey())) {
                    teamNames.add(dataSnapshot.getKey());
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemDeleted(DataSnapshot dataSnapshot) {
                if (teamNames.contains(dataSnapshot.getKey())) {
                    teamNames.remove(dataSnapshot.getKey());
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

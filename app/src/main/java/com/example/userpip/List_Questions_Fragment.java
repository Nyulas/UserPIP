package com.example.userpip;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List_Questions_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "user";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private User user;
    RecyclerView recyclerView;
    private ArrayList<User> userlist;
    private com.google.firebase.database.DatabaseReference mDatabase;
    RecyclerViewAdaptorVotes recyclerViewAdaptorVotes;

    public List_Questions_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment List_Questions_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static List_Questions_Fragment newInstance(User user) {
        List_Questions_Fragment fragment = new List_Questions_Fragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list__questions_, container, false);

        recyclerView = view.findViewById(R.id.Votes_RV);

        userlist = new ArrayList<User>();

        Bundle bundle = this.getArguments();

        user = (User) bundle.getSerializable("user");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("admin").child("result");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userlist.clear();
                for(DataSnapshot iterator : dataSnapshot.getChildren())
                {

                    User new_user = iterator.getValue(User.class);

                    Log.d("uzenet", user.getName());
                    Log.d("uzenet", new_user.getName());

                    if(user.getName().equals(new_user.getName())){
                        Log.d("uzenet", "bejott");
                        userlist.add(new_user);
                    }

                }
                recyclerViewAdaptorVotes = new RecyclerViewAdaptorVotes(getContext(),userlist);
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                recyclerView.setAdapter(recyclerViewAdaptorVotes);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}

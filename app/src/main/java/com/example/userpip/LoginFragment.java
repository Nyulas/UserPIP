package com.example.userpip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private com.google.firebase.database.DatabaseReference mDatabase;
    private com.google.firebase.database.DatabaseReference groupReff;
    EditText username_et,userID_et;
    private int userID;
    private String username;
    private User user;
    String userId;
    private int helper;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);


        helper = 0;
        username_et = view.findViewById(R.id.Username_ET);
        userID_et = view.findViewById(R.id.UserId_ET);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        groupReff = FirebaseDatabase.getInstance().getReference().child("admin").child("groups");

        Button next_fragment = view.findViewById(R.id.NextPage_BT);

        next_fragment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                username = username_et.getText().toString();

                userId =userID_et.getText().toString();

                if (!userId.isEmpty() ) {
                    userID = Integer.parseInt(userId);
                    idCheck();
                }else {
                    Toast.makeText(view.getContext(),"You thought",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public void idCheck(){

        groupReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot iterator : dataSnapshot.getChildren())
                {

                    Group g = iterator.getValue(Group.class);

                    if(g.getID() == userID)
                    {
                        if(helper == 0){
                            helper++;
                            user = new User(username,userID,"0");
                            mDatabase.child("admin").child("groups").child(g.getName()).child(username).setValue(user);

                            VoteFragment voteFragment = VoteFragment.newInstance(user,g);

                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fg_placeholder,voteFragment,"GAQ_fragment");
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            return;

                        }

                    }

                }
                Toast.makeText(getContext(),"nincsen ilyen csoport",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


}

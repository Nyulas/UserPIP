package com.example.userpip;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class VoteFragment extends Fragment
{

    private static final String ARG_PARAM1 = "user";
    private static final String ARG_PARAM2 = "group";

    private static int counter = 0;
    private int pressed_button_id;
    static String title;
    private com.google.firebase.database.DatabaseReference groupReff;
    int user_id;
    TextView tv_title;
    private User user;
    private Group group;

    public static VoteFragment newInstance(User user,Group group) {
        VoteFragment fragment = new VoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, user);
        args.putSerializable(ARG_PARAM2, group);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        //inflate the layout for this fragment (don't forget to set the last param to false !!!)
        View retView = inflater.inflate(R.layout.vote_fragment,container,false);

        //text of buttons in arraylist
        final ArrayList<String> buttonText = new ArrayList<>(Arrays.asList("0","1/2","1","2","3","5","8","13","20","40","100","?","coffee"));

        GridLayout myGridLayout =retView.findViewById(R.id.ly_grid);

        //fill grid with buttons
        for (int i=0; i<12; ++i)
        {
            Button myButton = new Button(getContext());

            myButton.setId(i);
            String btn_text = buttonText.get(i);
            myButton.setText(btn_text);
            myGridLayout.addView(myButton);
        }

        //the last button contains an image, has to be managed differently form other buttons
        ImageButton btn_coffe = new ImageButton(getContext());
        btn_coffe.setLayoutParams(new GridLayout.LayoutParams());
        int btn_id=12;
        //it will be placed in the 4th row, and 1st column (5,2)
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(4),GridLayout.spec(1));
        btn_coffe.setLayoutParams(params);
        btn_coffe.setId(btn_id);
        Drawable dr = getResources().getDrawable(R.drawable.coffe,null);
        Bitmap bitmap = ((BitmapDrawable)dr).getBitmap();
        Drawable d = new BitmapDrawable(getResources(),Bitmap.createScaledBitmap(bitmap,50,50,true));
        btn_coffe.setImageDrawable(d);
        myGridLayout.addView(btn_coffe);


        //set title for textview

        groupReff = FirebaseDatabase.getInstance().getReference().child("admin").child("groups");

        tv_title = retView.findViewById(R.id.tv_voteFor);

        Bundle bundle = this.getArguments();

        user = (User) bundle.getSerializable("user");
        group = (Group) bundle.getSerializable("group");

        user_id = user.getID();

        groupReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot iterator : dataSnapshot.getChildren())
                {
                    Group g = iterator.getValue(Group.class);


                    if(g.getID() == user_id ){
                        tv_title.setText(g.getQuestion().getQuestion());

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        //whichever button was pressed last, its id will be stored in pressed_button_id
        btn_coffe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pressed_button_id = 12;

            }
        });

        //set onClickListener for each button
        for(int i = 0; i < 12; ++i)
        {
            final Button button = retView.findViewById(i);
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    v.setSelected(true);
                    pressed_button_id=button.getId();
                }
            });
        }

        Button btn_vote = retView.findViewById(R.id.btn_Vote);
        btn_vote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                Log.d("uzenet", "cucc: " + pressed_button_id);
                user.setVote_value(buttonText.get(pressed_button_id));
                groupReff.child(group.getName()).child(user.getName()).setValue(user);

            }
        });


        return retView;
    }


}

package com.example.parassehgal.qdown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class previous_purchases extends AppCompatActivity {

    FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    ArrayList<String> mItems=new ArrayList<String>();
    ArrayAdapter mAdapter;
    ListView mlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_purchases);
        mlistView=findViewById(R.id.prev_list);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("cart").child("users").child("user1");
        mAuth= FirebaseAuth.getInstance();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getValue(String.class).substring(0, 2).equals("!!"))
                { mItems.add(dataSnapshot.getValue(String.class));
                    mAdapter.notifyDataSetChanged();
                }}

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mItems);
        mlistView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


    }
}

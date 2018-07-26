package com.example.parassehgal.qdown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class queue_length extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int n;
    TextView textView;
    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_length);
        textView=findViewById(R.id.queue_length);
        textView1=findViewById(R.id.textView5);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("queue");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               long l= dataSnapshot.getValue(Long.class)*10;
                textView.setText(dataSnapshot.getValue(Long.class).toString());
                textView1.setText(l+"minutes");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                long l= dataSnapshot.getValue(Long.class)*10;
                textView.setText(dataSnapshot.getValue(Long.class).toString());
                textView1.setText(l+"minutes");
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
    }
}

package com.example.parassehgal.qdown;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class NewUpdateActivity extends AppCompatActivity {


    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    ArrayList<String> mItems=new ArrayList<String>();

    ArrayAdapter mAdapter;
    ListView mlistView;
    DataSnapshot dataSnapshot1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        Intent intent=getIntent();
        final String choice=intent.getExtras().getString("itemname");

        mlistView=(ListView)findViewById(R.id.listView);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("items").child(choice);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mItems.add(dataSnapshot.getValue(String.class).substring(0,dataSnapshot.getValue(String.class).indexOf('@')));
                mAdapter.notifyDataSetChanged();
            }

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

        mAdapter=new ArrayAdapter<String>(NewUpdateActivity.this,android.R.layout.simple_list_item_1,mItems);
        mlistView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog.Builder dialog=new AlertDialog.Builder(NewUpdateActivity.this);
                View dialogView=getLayoutInflater().inflate(R.layout.custom_dialog,null);
                final EditText number=(EditText)dialogView.findViewById(R.id.editText);
                final EditText quant=dialogView.findViewById(R.id.priceText);
                Button updateButton=(Button)dialogView.findViewById(R.id.updateButton);
                Button cancelButton=(Button)dialogView.findViewById(R.id.cancelButton);
                dialog.setView(dialogView);
                dialog.setCancelable(false);

                final Dialog customDialog=dialog.create();
                customDialog.show();

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!number.getText().toString().isEmpty() && !quant.getText().toString().isEmpty()) {
                            String n = number.getText().toString();
                            if (Integer.parseInt(n) > 0)
                            {

                               /*String q= dataSnapshot1.getValue(String.class).substring(dataSnapshot1.getValue(String.class).indexOf('@'),dataSnapshot1.getValue(String.class).indexOf('$'));
                                Toast.makeText(NewUpdateActivity.this,q,Toast.LENGTH_LONG).show();*/
                                String quantity=((mAdapter.getItem(i).toString()).toLowerCase())+"@"+(number.getText())+"$"+quant.getText();
                                databaseReference.child((mAdapter.getItem(i).toString()).toLowerCase()).setValue(quantity);
                                //String q= databaseReference.child((mAdapter.getItem(i).toString()).toLowerCase()).gtValue(quantity);
                                customDialog.dismiss();
                            }

                        }
                        else
                        {
                            Toast.makeText(NewUpdateActivity.this,"Enter both the fields",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

            }});

    }
}

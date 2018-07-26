package com.example.parassehgal.qdown;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class items_user extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    public String[] cars = {"Pulsar","Bikes","ll"};
    ArrayList<String> mItems=new ArrayList<String>();
    ArrayList<String> fulldetails = new ArrayList<String>();

    ArrayList<String> fulldetails21 = new ArrayList<String>();
    ArrayAdapter mAdapter;
    ListView mlistView;
    ProgressBar pg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_user);
        Intent i=getIntent();
        final Bundle b=i.getExtras();
        String s=b.getString("item");
        pg = (ProgressBar) findViewById(R.id.progressBar1);
        mlistView=findViewById(R.id.itemlist);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("items").child(s);
        long count=0;
        pg.setVisibility(View.VISIBLE);

        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                long i=  dataSnapshot.getChildrenCount();

                fulldetails21.add(dataSnapshot.getValue(String.class));
                fulldetails.add(dataSnapshot.getValue(String.class).substring(0,
                        dataSnapshot.getValue(String.class).indexOf('$')));
                mItems.add(dataSnapshot.getValue(String.class).substring(0,dataSnapshot.getValue(String.class).indexOf('@')));
                mAdapter.notifyDataSetChanged();
                if(i==0){
                    pg.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mItems.add(dataSnapshot.getValue(String.class).substring(0,dataSnapshot.getValue(String.class).indexOf('@')));
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


        /*Cart cart=new Cart();
        if(cart.getA().size()==2) {
            String sttr=cart.getA().get(1).toString();
            Toast.makeText(items_user.this, sttr,Toast.LENGTH_SHORT).show();}
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("item");


        ListAdapter adapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1,cars);
        ListView listView = findViewById(R.id.itemlist);
        listView.setAdapter(adapter);*/
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int j = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(items_user.this);
                builder.setTitle("QUANTITY LEFT :");
                View view1 = getLayoutInflater().inflate(R.layout.popquants,null);

                final EditText editText = view1.findViewById(R.id.buyingquants);
                Button button = view1.findViewById(R.id.gocart);
                TextView quantsleft = view1.findViewById(R.id.price12);
                String range = fulldetails21.get(i);
               final int len = range.length();
               int indu = range.indexOf('@');
                int indes = range.indexOf('$');

                final String range1 = range.substring(indu +1 , indes);
                String rem = range.substring(indes+1 , len);
               // int finalrange = Integer.parseInt(range);
                quantsleft.setText(range1 + " pieces \n" + " Price: Rs "+rem+"/piece");

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int n1 = Integer.parseInt(range1);

                        if(!editText.getText().toString().isEmpty()) {
                            final int totalbuy = Integer.parseInt(editText.getText().toString());
                            if (totalbuy < n1) {

                                String product = mAdapter.getItem(j).toString();
                               // final int totalbuy = Integer.parseInt(editText.getText().toString());
                                Cart cart = new Cart();
                                cart.setA("Product :" + product + " " + "Quantity :" + totalbuy);
                                Intent i = new Intent(items_user.this, show_cart.class);
                                startActivity(i);
                            }
                        }
                    }
                });

                builder.setView(view1);
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setLayout(800,900);

             /*   Intent intent = new Intent(items_user.this , popup.class);
                Bundle bundle1=new Bundle();
                bundle1.putString("Items",mAdapter.getItem(i).toString());
                intent.putExtras(bundle1);
                startActivity(intent);*/

            }
        });



    }
}
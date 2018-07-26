package com.example.parassehgal.qdown;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

import java.util.ArrayList;

public class welcome_shopkeeper extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    int size;
    Cart c=new Cart();

    ArrayList<String> mItems=new ArrayList<String>();
    ArrayAdapter mAdapter;
    ListView mlistView;
    NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        OneSignal.sendTag("User_ID","shopkeeper@gg.com");
        setContentView(R.layout.activity_welcome_shopkeeper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        nv = (NavigationView) findViewById(R.id.nav_view);
        //initialising the variables defined above
        mlistView=(ListView)findViewById(R.id.rl3);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("users").child("customer");


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            /*    c.setNoodles(dataSnapshot.getValue(String.class));
                String message=c.getNoodles();*/
                mItems.add(dataSnapshot.getValue(String.class));
                size=mItems.size();
                //Toast.makeText(getApplicationContext(),String.valueOf(size),Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext() ,dataSnapshot.getValue(String.class),Toast.LENGTH_LONG).show();
                DatabaseReference dr1=database.getReference("queue");
                dr1.child("size").setValue(size);


                //list.setText(string);
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
        //getting intent from shopkeeper_token_generator




        mAdapter=new ArrayAdapter<String>(welcome_shopkeeper.this,android.R.layout.simple_list_item_1,mItems);
        mlistView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                size=mItems.size();
                Toast.makeText(getApplicationContext(),String.valueOf(size),Toast.LENGTH_LONG).show();
                DatabaseReference dr1=database.getReference("queue");
                dr1.child("size").setValue(size-1);

                String item= mAdapter.getItem(position).toString();
               Intent i=new Intent(getApplicationContext(),Shopkeeper_Token_Generator.class);
               Bundle b=new Bundle();
              //  Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
               b.putString("name",item);
               b.putInt("position",position);
               i.putExtras(b);

               startActivityForResult(i,SECOND_ACTIVITY_REQUEST_CODE);
            }
        });

        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



         nv.setNavigationItemSelectedListener(this);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                Bundle b = data.getExtras();
                int pos = b.getInt("returnposition");
                Toast.makeText(getApplicationContext(), String.valueOf(pos), Toast.LENGTH_LONG).show();
                // set text view with string
                mItems.remove(pos);

                mAdapter.notifyDataSetChanged();

            }
        }
    }



        protected void onStart() {

        super.onStart();
        };



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome_shopkeeper, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(this,Add.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this,AddItemCategory.class));
        }else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

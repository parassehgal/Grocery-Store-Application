package com.example.parassehgal.qdown;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.onesignal.OneSignal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class welcome_user extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton mButton01;
    private ImageButton mButton02;
    private ImageButton mButton03;
    private ImageButton mButton04;
    private ImageButton mButton05;
    private ImageButton mButton06;
    private ImageButton mButton07;
    private ImageButton mButton08;
    private DrawerLayout mdrawerLayout1;
    ImageView img;
    TextView name;
    TextView user_id;
    private ActionBarDrawerToggle mToggle;
    private Intent intent;
    String personName;
    String personEmail;
    String personPhoto;
    NavigationView navigationView;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_user);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
             personEmail = acct.getEmail();
            String personId = acct.getId();
            navigationView=findViewById(R.id.nav_view_user);

            View hView =  navigationView.getHeaderView(0);


            name=hView.findViewById(R.id.Name_user);
            user_id=hView.findViewById(R.id.Id_user);
            img=hView.findViewById(R.id.imageView_user);
            personPhoto = acct.getPhotoUrl().toString();
            Glide.with(this).load(personPhoto).into(img);
            name.setText(personName);
            user_id.setText(personEmail);
           // img.setImageURI(personPhoto);
        }

        mButton01 =  findViewById(R.id.image01);
        mButton02 =  findViewById(R.id.image02);
        mButton03 =  findViewById(R.id.image03);
        mButton04 =  findViewById(R.id.image04);
        mButton05 =  findViewById(R.id.image05);
        mButton06 =  findViewById(R.id.image06);
        mButton07 =  findViewById(R.id.image07);
        mButton08 =  findViewById(R.id.image08);

        mdrawerLayout1 =findViewById(R.id.drawer_user);
        mAuth=FirebaseAuth.getInstance();
        Toast.makeText(getApplicationContext(),personName+personEmail+personPhoto.toString(),Toast.LENGTH_SHORT).show();

        mButton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // noodles(new noodless());

                String s = "lotion";
                intent = new Intent(welcome_user.this , items_user.class);

                Bundle bundle = new Bundle();
                bundle.putString("item",s);
                intent.putExtras(bundle);
                startActivity(intent);
                Log.d("naviga","Click noodless");

            }
        });




        //onclick listener for Chips
        mButton02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chips(new Chips());
                String s = "perfume";
                Intent intent = new Intent(welcome_user.this,items_user.class);
                Bundle bundle = new Bundle();
                bundle.putString("item",s);
                intent.putExtras(bundle);
                startActivity(intent);
                Log.d("naviga","Click Chips");
            }
        });


        mButton03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(User.this , Noods.class);
                //  biscuits(new Biscuits());
                String s = "juices";
                Intent intent = new Intent(welcome_user.this, items_user.class);
                Bundle bundle = new Bundle();
                bundle.putString("item",s);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        mButton04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // dairy(new Dairy());
                String s = "Chocolates";
                Intent intent = new Intent(welcome_user.this,items_user.class);
                Bundle bundle = new Bundle();
                bundle.putString("item",s);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        mButton05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = "snacks";
                Intent intent = new Intent(welcome_user.this , items_user.class);
                Bundle bundle = new Bundle();
                bundle.putString("item",s);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mButton06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = "Dairy";
                Intent intent = new Intent(welcome_user.this , items_user.class);
                Bundle bundle = new Bundle();
                bundle.putString("item",s);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mButton07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = "biscuit";
                Intent intent = new Intent(welcome_user.this , items_user.class);
                Bundle bundle = new Bundle();
                bundle.putString("item",s);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        mButton08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = "Kitchen";
                Intent intent = new Intent(welcome_user.this , items_user.class);
                Bundle bundle = new Bundle();
                bundle.putString("item",s);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


     /*   mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(getApplicationContext(),"Signed out Successfully",Toast.LENGTH_LONG).show();
                startActivity(new Intent(welcome_user.this,LoginActivity.class));
                finish();

            }
        });*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_user);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_user);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void sendNotification() {
        AsyncTask.execute(new Runnable() {
                              @Override
                              public void run() {
                                  int SDK_INT = android.os.Build.VERSION.SDK_INT;
                                  if (SDK_INT > 8) {
                                      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                              .permitAll().build();
                                      StrictMode.setThreadPolicy(policy);
                                      String send_email;

                                      //This is a Simple Logic to Send Notification different Device Programmatically....
                                      if (LoginActivity.email.equals("user1@gmail.com")) {
                                          send_email = "user2@gmail.com";
                                      } else {
                                          send_email = "user1@gmail.com";
                                      }

                                      try {
                                          String jsonResponse;

                                          URL url = new URL("https://onesignal.com/api/v1/notifications");
                                          HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                          con.setUseCaches(false);
                                          con.setDoOutput(true);
                                          con.setDoInput(true);

                                          con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                                          con.setRequestProperty("Authorization", "Basic OWIxODM3NTAtYjg4Yi00YjliLTk3ODItNzhhZTQxNGJjN2Q0");
                                          con.setRequestMethod("POST");

                                          String strJsonBody = "{"
                                                  + "\"app_id\": \"d9657ba9-8040-46b7-8afd-c1ad09e3f46d\","

                                                  + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                                  + "\"data\": {\"foo\": \"bar\"},"
                                                  + "\"contents\": {\"en\": \"English Message\"}"
                                                  + "}";


                                          System.out.println("strJsonBody:\n" + strJsonBody);

                                          byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                                          con.setFixedLengthStreamingMode(sendBytes.length);

                                          OutputStream outputStream = con.getOutputStream();
                                          outputStream.write(sendBytes);

                                          int httpResponse = con.getResponseCode();
                                          System.out.println("httpResponse: " + httpResponse);

                                          if (httpResponse >= HttpURLConnection.HTTP_OK
                                                  && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                                              Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                                              jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                              scanner.close();
                                          } else {
                                              Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                                              jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                              scanner.close();
                                          }
                                          System.out.println("jsonResponse:\n" + jsonResponse);

                                      } catch (Throwable t) {
                                          t.printStackTrace();
                                      }
                                  }
                              }
                          }
        );
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_user);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.user_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            startActivity(new Intent(welcome_user.this,show_cart.class));
        } else if (id == R.id.nav_recom) {
            sendNotification();
        }else if (id == R.id.prev_buys) {
            startActivity(new Intent(welcome_user.this,previous_purchases.class));
        }
        else if (id == R.id.nav_queue) {
            startActivity(new Intent(welcome_user.this,queue_length.class));
        } else if (id == R.id.nav_logout)
        {
            mAuth.signOut();
            Toast.makeText(getApplicationContext(),"Signed out Successfully",Toast.LENGTH_LONG).show();
            OneSignal.sendTag("User_ID","");
            startActivity(new Intent(welcome_user.this,LoginActivity.class));
            finish();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_user);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
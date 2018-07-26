package com.example.parassehgal.qdown;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class User extends AppCompatActivity {

    private ImageButton mButton1;
    private ImageButton mButton2;
    private ImageButton mButton3;
    private ImageButton mButton4;
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private FirebaseAuth mAuth;



  /*  protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);
        mButton1 = (ImageButton) findViewById(R.id.image1);
       mButton4=findViewById(R.id.image4);
        mButton3=findViewById(R.id.image3);
        mdrawerLayout =(DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mdrawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mAuth=FirebaseAuth.getInstance();
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noodles(new noodless());
                Log.d("naviga","Click noodless");

            }
        });
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(getApplicationContext(),"Signed out Successfully",Toast.LENGTH_LONG).show();
                startActivity(new Intent(User.this,LoginActivity.class));
                finish();

            }
        });


        mdrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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
    private void noodles(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction =  fm.beginTransaction();
        fragmentTransaction.addToBackStack(backStateName);
        // adding to stack the fragments one by one. So that presseing the back button doesn't mean that your screen gets closed.
        boolean fragmentPopped = fm.popBackStackImmediate (backStateName, 0);
        //Here grid is the name of my GridLayout.
        fragmentTransaction.replace(R.id.grid,fragment);
        fragmentTransaction.commit();*/





    }



package com.example.parassehgal.qdown;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class show_cart extends AppCompatActivity {

    ArrayList<String> mItems=new ArrayList<String>();
    ArrayAdapter mAdapter;
    ListView mlistView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    String personEmail;
    Button pack;
    TextView orderno;
    String order;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cart);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {

            personEmail = acct.getEmail();
        }
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        orderno=findViewById(R.id.orderno);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("users").child("order");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                order=dataSnapshot.getValue(String.class);
                Toast.makeText(getApplicationContext(),order,Toast.LENGTH_LONG).show();
                if(mItems.size()!=0){
                    int in=Integer.parseInt(order)+1;
                    orderno.setText("Expected Token :"+in);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                order=dataSnapshot.getValue(String.class);
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


            databaseReference=database.getReference("users").child("customer");
        pack=findViewById(R.id.pack);
        mlistView=findViewById(R.id.show_cart_list);
        mItems=Cart.a;
        mAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mItems);
        mlistView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        pack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mItems.size()!=0){

                    OneSignal.sendTag("User_ID",personEmail);
                    int in=Integer.parseInt(order)+1;
                    database.getReference("users").child("order").child("ordernumber").setValue(String.valueOf(in));
                databaseReference.child(personEmail.substring(0,personEmail.indexOf('@'))).setValue(personEmail);
                databaseReference=database.getReference("cart").child("users");
                for(int i=0;i<mItems.size();i++) {
                    databaseReference.child(personEmail.substring(0,personEmail.indexOf('@'))).child(mItems.get(i).replaceAll("[0-9]","")).setValue(mItems.get(i));

                }
                    databaseReference.child(personEmail.substring(0,personEmail.indexOf('@'))).child("ordernumber").setValue("ordernumber"+String.valueOf(in));
                sendNotification();
                }
                else{

                 Toast.makeText(getApplicationContext(),"AtLeast add 1 item in the cart",Toast.LENGTH_LONG).show();
                    }
                }
        });


       /* Toast.makeText(show_cart.this, "hello",Toast.LENGTH_SHORT).show();
        Cart cart=new Cart();
        if(cart.getA().size()==2)
        {
            String sttr=cart.getA().get(1).toString();
            Toast.makeText(show_cart.this, sttr+"hii",Toast.LENGTH_SHORT).show();
        }*/
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
                                      if (personEmail.isEmpty()) {
                                          send_email = "shopkeeper@gg.com";
                                      } else {
                                          send_email = "shopkeeper@gg.com";
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
                                                  + "\"contents\": {\"en\": \"New Order from:"+ personEmail + "\"}"
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
}

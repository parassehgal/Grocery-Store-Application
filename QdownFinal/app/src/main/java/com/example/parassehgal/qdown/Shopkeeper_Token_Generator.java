package com.example.parassehgal.qdown;

import android.content.Intent;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import static java.sql.Types.NULL;

public class Shopkeeper_Token_Generator extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    ArrayList<String> mItems=new ArrayList<String>();
    ArrayAdapter mAdapter;
    ListView mlistView;
    Button orderpck;
    String username;
    TextView shop_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper__token__generator);
        orderpck=findViewById(R.id.orderpck);
        mlistView=findViewById(R.id.token);
        database=FirebaseDatabase.getInstance();
        shop_token=findViewById(R.id.shop_token);
        Intent i=getIntent();
        Bundle b=i.getExtras();
        username=b.getString("name");
        databaseReference=database.getReference("cart").child("users").child(username.substring(0,username.indexOf('@')));
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(!dataSnapshot.getValue(String.class).substring(0, 2).equals("!!")&&!dataSnapshot.getValue(String.class).substring(0,4).equals("orde"))
                { mItems.add(dataSnapshot.getValue(String.class));

                mAdapter.notifyDataSetChanged();

            }if(dataSnapshot.getValue(String.class).substring(0,4).equals("orde"))
            shop_token.setText("Order Token is :"+dataSnapshot.getValue(String.class).substring(11,12));
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
        mAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mItems);
        mlistView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

       // Intent i=getIntent();
        //Bundle b=i.getExtras();
        //final String s=b.getString("name");
        final int pos=b.getInt("position");
        Toast.makeText(getApplication(),String.valueOf(pos),Toast.LENGTH_LONG).show();
        orderpck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),welcome_shopkeeper.class);
                Bundle b=new Bundle();
                b.putString("returnname",username);
                b.putInt("returnposition",pos);
                intent.putExtras(b);
                setResult(RESULT_OK, intent);
                //databaseReference=database.getReference("cart").child("users");
                for(int i=0;i<mItems.size();i++) {
                    databaseReference.child(mItems.get(i)).setValue("!!"+mItems.get(i));

                }
                databaseReference=database.getReference("users").child("customer");
                databaseReference.child(username.substring(0,username.indexOf('@'))).removeValue();

                sendNotification();
                finish();
            }
        });
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
                                      if (username.isEmpty()) {
                                          send_email = username;
                                      } else {
                                          send_email = username;
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
                                                  + "\"contents\": {\"en\": \"Your Order is ready\"}"
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

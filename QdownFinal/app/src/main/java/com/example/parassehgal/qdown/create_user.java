package com.example.parassehgal.qdown;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class create_user extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private EditText name;
    private EditText email;
    private EditText password;
    private Button button;
    TextView login;
    RadioButton radioShopkeeper;
    RadioButton radioUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__user);

        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        button=(Button)findViewById(R.id.registerButton);
        login=(TextView)findViewById(R.id.login);
        radioShopkeeper=(RadioButton)findViewById(R.id.shopkeeper);
        radioUser=(RadioButton)findViewById(R.id.user);

        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("users");

        mAuthListner= new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){

                }
                else{

                }}
        };
        button.setOnClickListener(this);

        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value=dataSnapshot.getValue(String.class);
                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListner!=null){
            mAuth.removeAuthStateListener(mAuthListner);

        }
    }

    @Override
    public void onClick(View v) {
        final String emailString=email.getText().toString();
        String pass=password.getText().toString();
        if(!emailString.equals("")&&!pass.equals("")){

          if(radioShopkeeper.isChecked()){
            mAuth.createUserWithEmailAndPassword(emailString, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(create_user.this,name.toString(),Toast.LENGTH_LONG).show();
                                databaseReference.child("shopkeeper").child(name.getText().toString()).setValue(emailString);

                                Toast.makeText(create_user.this, "Authentication Successful",
                                        Toast.LENGTH_SHORT).show();

                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(create_user.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });}
       else {
              mAuth.createUserWithEmailAndPassword(emailString, pass)
                  .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()) {
                              // Sign in success, update UI with the signed-in user's information
                              Log.d("TAG", "createUserWithEmail:success");
                              FirebaseUser user = mAuth.getCurrentUser();
                              String email=emailString.substring(0,emailString.indexOf('@'));
                              databaseReference.child("customer").child(name.getText().toString()).setValue(email);
                             FirebaseDatabase database1=FirebaseDatabase.getInstance();

                              DatabaseReference databaseReference1=database1.getReference("cart");
                              databaseReference1.child("users").child(email).child("1").setValue("maggi");
                              databaseReference1=database1.getReference("recommendations");
                              databaseReference1.child(email).child("1").setValue("maggi");
                              Toast.makeText(create_user.this, "Authentication Successful",
                                      Toast.LENGTH_SHORT).show();

                              //updateUI(user);
                          } else {
                              // If sign in fails, display a message to the user.
                              Log.w("TAG", "createUserWithEmail:failure", task.getException());
                              Toast.makeText(create_user.this, "Authentication failed.",
                                      Toast.LENGTH_SHORT).show();
                              //updateUI(null);
                          }

                          // ...
                      }
                  });




          }

        }
    }
}

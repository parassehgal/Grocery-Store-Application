package com.example.parassehgal.qdown

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

import com.google.firebase.auth.FirebaseUser

import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import android.R.attr.password
import android.content.Context
import android.content.Intent
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.onesignal.OneSignal


class LoginActivity : AppCompatActivity() {

    val mAuth=FirebaseAuth.getInstance()
    companion object {
        @JvmStatic lateinit var email:String
    }


    val RC_SIGN_IN:Int=1

    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference:DatabaseReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();



        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        firebaseDatabase= FirebaseDatabase.getInstance()
        databaseReference=firebaseDatabase.getReference("users")



        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        button2.setOnClickListener {
            linearlayout.visibility=View.VISIBLE
        }


        loginButton.setOnClickListener {

            email=emailEditText.text.toString()
            var password=passwordEditText.text.toString()

            if(!email.isBlank() && !password.isBlank()) {

                if (password.equals("shopkeeper")) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(FragmentActivity.TAG, "signInWithEmail:success")

                                    startActivity(Intent(this, welcome_shopkeeper::class.java))
                                    Toast.makeText(this@LoginActivity, "Login Successful",
                                            Toast.LENGTH_SHORT).show()

                                    val editor=getSharedPreferences("shopkeeper",Context.MODE_PRIVATE).edit()
                                    editor.putBoolean("accountCreated",true)
                                    editor.apply()
                                    //updateUI(user)
                                } else {
                                    // If sign in fails, display a message to the user.
                                    // Log.w(FragmentActivity.TAG, "signInWithEmail:failure", task.exception)
                                    Toast.makeText(this@LoginActivity, "Authentication failed",
                                            Toast.LENGTH_SHORT).show()
                                    // updateUI(null)
                                }

                                // ...
                            }
                } else {

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(FragmentActivity.TAG, "signInWithEmail:success")
                                    OneSignal.sendTag("User_ID", email);

                                    startActivity(Intent(this,welcome_user ::class.java))
                                    Toast.makeText(this@LoginActivity, "Login Successful",
                                            Toast.LENGTH_SHORT).show()
                                    //updateUI(user)
                                } else {
                                    // If sign in fails, display a message to the user.
                                    // Log.w(FragmentActivity.TAG, "signInWithEmail:failure", task.exception)
                                    Toast.makeText(this@LoginActivity, "Authentication failed",
                                            Toast.LENGTH_SHORT).show()
                                    // updateUI(null)
                                }

                                // ...
                            }

                }
            }
            else{
                Toast.makeText(this,"Please enter the credentials",Toast.LENGTH_LONG).show()
            }

        }

        signinButton.setOnClickListener {
            signIn()
        }

    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase

                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
                Toast.makeText(this,"Login jej succesfull",Toast.LENGTH_LONG).show()
            } catch (e: ApiException) {
                Toast.makeText(this,"catch ke andar",Toast.LENGTH_LONG).show()
                // Google Sign In failed, update UI appropriately
                //Log.w(FragmentActivity.TAG, "Google sign in failed", e)
                // ...
            }

        }
        /*else{
            // Pass the activity result back to the Facebook SDK
            mCallbackManager.onActivityResult(requestCode, resultCode, data)
        }*/
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        //Log.d(FragmentActivity.TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        Toast.makeText(this,"aa gyaa",Toast.LENGTH_LONG).show()
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(FragmentActivity.TAG, "signInWithCredential:success")
                        val user = mAuth.currentUser
                        Toast.makeText(this,"Login succesfull",Toast.LENGTH_LONG).show()
                        var email:String= user!!.email.toString()
                        Toast.makeText(this,email,Toast.LENGTH_LONG).show()

                        var index=email.indexOf('@')
                        var username=email.substring(0,index)
                        Toast.makeText(this,username,Toast.LENGTH_LONG).show()

                        databaseReference.child("customer1").child(username).setValue(username)
                        startActivity(Intent(this,welcome_user::class.java))

                        //displayText.text=email
                        val editor=getSharedPreferences("userdata", Context.MODE_PRIVATE).edit()
                        editor.putBoolean("accountCreated",true)
                        editor.apply()

                        /*val editor=getSharedPreferences("userdata", Context.MODE_PRIVATE).edit()
                        editor.putBoolean("accountCreated",true)
                        editor.apply()*/
                    } else {
                        Toast.makeText(this,"failed",Toast.LENGTH_LONG).show()
                        // If sign in fails, display a message to the user.
                        // Log.w(FragmentActivity.TAG, "signInWithCredential:failure", task.exception)
                        //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                        //updateUI(null)
                    }

                    // ...
                }
    }



}

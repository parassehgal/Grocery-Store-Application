package com.example.parassehgal.qdown


import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

    var accountCreated:Boolean = false
    var delayRun=2000L
    private var activityStarted: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (activityStarted
                && intent != null
                && intent.flags and Intent.FLAG_ACTIVITY_REORDER_TO_FRONT != 0) {
            finish()
            return
        }

        activityStarted = true
        val sharedPreferences=getSharedPreferences("userdata", Context.MODE_PRIVATE)
        accountCreated=sharedPreferences.getBoolean("accountCreated",false)

        var handler = Handler()
        var run=Runnable{
            if(!accountCreated){
                startActivity(Intent(this,LoginActivity :: class.java))
                finish()

                val sharedPreferences2=getSharedPreferences("shopkeeper", Context.MODE_PRIVATE)
                accountCreated=sharedPreferences2.getBoolean("accountCreated1",false)
                if(!accountCreated)
                {
                    startActivity(Intent(this,LoginActivity :: class.java))
                    finish()
                }
                else
                {
                    startActivity(Intent(this,welcome_shopkeeper::class.java))
                    finish()
                }
            }
            else
            {
                startActivity(Intent(this,welcome_user::class.java))
                finish()
            }
        }
        handler.postDelayed(run,delayRun)





    }
}


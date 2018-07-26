package com.example.parassehgal.qdown

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.custom_dialog.*

class AddItemActivity : AppCompatActivity() {

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        firebaseDatabase= FirebaseDatabase.getInstance()
        databaseReference=firebaseDatabase.getReference("items")

        val ss:String = intent.getStringExtra("itemname")
        Toast.makeText(this,"Welcome",Toast.LENGTH_LONG).show()
        addButton.setOnClickListener {
            Toast.makeText(this,"added",Toast.LENGTH_LONG).show()
            var quantity:String=(itemName.text.toString().toLowerCase())+"@0"+"$0"
            databaseReference.child(ss).child(itemName.text.toString().toLowerCase()).setValue(quantity)
            itemName.text.clear()
        }
    }
}
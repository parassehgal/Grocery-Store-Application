package com.example.parassehgal.qdown

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_update_item.*
import kotlinx.android.synthetic.main.custom_dialog.*

class UpdateItemActivity : AppCompatActivity() {

    var stringData = arrayOf("Lays", "Uncle Chips", "Haldirams", "Cream and Onion",
            "Tomato", "Plain Salted", "Data 6", "Data 7",
            "Data 8", "Data 9","Data 10", "Data 11",
            "Data 12","Data 13","Data 13","Data 15")

    lateinit var data:MutableList<String>
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    lateinit var firebaseDatabase1: FirebaseDatabase
    lateinit var databaseReference1: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_item)

        val ss:String = intent.getStringExtra("itemname")

        firebaseDatabase1= FirebaseDatabase.getInstance()
        databaseReference1=firebaseDatabase1.getReference("items").child(ss)

        firebaseDatabase= FirebaseDatabase.getInstance()
        databaseReference=firebaseDatabase.getReference("items")

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){

                }
            }

        })
        var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stringData)

        listView.adapter = arrayAdapter

        listView.setOnItemClickListener {adapterView, view, index, id ->

            Toast.makeText(this, "You clicked on ${stringData[index]}", Toast.LENGTH_LONG).show()

            val dialog=AlertDialog.Builder(this)
            val dialogView=layoutInflater.inflate(R.layout.custom_dialog,null)
            val number=dialogView.findViewById<EditText>(R.id.editText)
            val quant=dialogView.findViewById<EditText>(R.id.priceText)
            dialog.setView(dialogView)
            dialog.setTitle("Update items")
            dialog.setCancelable(false)
            dialog.setPositiveButton("Update",{dialogInterface:DialogInterface, i ->  })

            dialog.setNegativeButton("Cancel",{dialogInterface:DialogInterface, i ->  })
            val customDialog=dialog.create()
            customDialog.show()
            customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener({
                var n=number.text.toString()
                if(n.toInt() >0){
                    var quantity:String=((stringData[index]).toLowerCase())+"@"+(number.text)+"$"+quant.text
                    databaseReference.child("shopkeeper").child((stringData[index]).toLowerCase()).setValue(quantity)
                    customDialog.dismiss()
                }
                else
                    Toast.makeText(baseContext,"not valid",Toast.LENGTH_LONG).show()
            })

            customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener({
                customDialog.dismiss()
            })


        }
    }
}

package com.example.parassehgal.qdown

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.content_welcome_user.*

class AddItemCategory : AppCompatActivity(),View.OnClickListener {

    override fun onClick(view: View?) {
        Toast.makeText(this, "Add some items", Toast.LENGTH_LONG).show()
        when (view!!.id) {
            R.id.image01->{
                val intent=Intent(this,AddItemActivity::class.java)
                intent.putExtra("itemname", "lotion")
                startActivity(intent)
            }
            R.id.image02->{
                val intent=Intent(this,AddItemActivity::class.java)
                intent.putExtra("itemname", "perfume")
                startActivity(intent)
            }
            R.id.image03->{
                val intent=Intent(this,AddItemActivity::class.java)
                intent.putExtra("itemname", "juices")
                startActivity(intent)
            }
            R.id.image04->{
                val intent=Intent(this,AddItemActivity::class.java)
                intent.putExtra("itemname", "Chocolates")
                startActivity(intent)
            }
            R.id.image05->{
                val intent=Intent(this,AddItemActivity::class.java)
                intent.putExtra("itemname", "snacks")
                startActivity(intent)
            }
            R.id.image06->{
                val intent=Intent(this,AddItemActivity::class.java)
                intent.putExtra("itemname", "Dairy")
                startActivity(intent)
            }
            R.id.image07->{
                val intent=Intent(this,AddItemActivity::class.java)
                intent.putExtra("itemname", "biscuit")
                startActivity(intent)
            }
            R.id.image08->{
                val intent=Intent(this,AddItemActivity::class.java)
                intent.putExtra("itemname", "Kitchen")
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_welcome_user)

        image01.setOnClickListener(this);
        image02.setOnClickListener(this);
        image03.setOnClickListener(this);
        image04.setOnClickListener(this);
        image05.setOnClickListener(this);
        image06.setOnClickListener(this);
        image07.setOnClickListener(this);
        image08.setOnClickListener(this);

    }
}

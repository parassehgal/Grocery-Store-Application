package com.example.parassehgal.qdown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class popup extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        DisplayMetrics dm = new DisplayMetrics();
        Button button = findViewById(R.id.addcart);
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        final EditText editText= findViewById(R.id.quantity);


        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        final String name = bundle.getString("Items");

        getWindow().setLayout((int) (width*.8),(int) (height*.6));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int n = Integer.parseInt( editText.getText().toString());
                Cart cart=new Cart();
                cart.setA(name+n);

                Intent i=new Intent(popup.this,show_cart.class);
                startActivity(i);
            }
        });
    }
}

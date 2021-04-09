package com.example.ppe_code;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainPage extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        View v = findViewById(R.id.button1);
        v.setOnClickListener(this);

        View v2 = findViewById(R.id.button6);
        v2.setOnClickListener(this);

        View v3 = findViewById(R.id.button);
        v3.setOnClickListener(this);


    }

    @Override
    public void onClick(View arg0){
        if(arg0.getId()== R.id.button1){

            Intent intent = new Intent(this, Menu1.class);

            this.startActivity(intent);


        }
        if(arg0.getId()==R.id.button6){

            Intent intent = new Intent(this, Menu2.class);

            this.startActivity(intent);

        }
        if(arg0.getId()==R.id.button){
            Intent intent = new Intent(this,Cupboard.class);
            this.startActivity(intent);
        }
    }


}
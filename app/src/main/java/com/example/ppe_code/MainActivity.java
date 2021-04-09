package com.example.ppe_code;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View v = findViewById(R.id.button8);
        v.setOnClickListener(this);
    }
    public void onClick(View arg0) {
        if (arg0.getId() == R.id.button8) {

            Intent intent = new Intent(this, MainPage.class);

            this.startActivity(intent);


        }
    }
}

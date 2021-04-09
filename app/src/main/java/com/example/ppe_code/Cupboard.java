package com.example.ppe_code;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Cupboard extends AppCompatActivity implements View.OnClickListener  {

    EditText code, brand, price, site, material, color, category, size;
    Button insert, update, delete, view;
    DBHelper2 DB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cupboard);

        code = findViewById(R.id.code);
        brand= findViewById(R.id.brand);
        price= findViewById(R.id.price);
        site = findViewById(R.id.site);
        material = findViewById(R.id.material);
        color = findViewById(R.id.color);
        category = findViewById(R.id.category);
        size = findViewById(R.id.size);

        insert = findViewById(R.id.insert);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        view = findViewById(R.id.view);

        View v = findViewById(R.id.back);
        v.setOnClickListener(this);

        DB = new DBHelper2(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long codeTXT= Long.parseLong(String.valueOf(code.getText()));
                String brandTXT = brand.getText().toString();
                int priceTXT =Integer.parseInt(String.valueOf(price.getText()));
                String siteTXT = site.getText().toString();
                String materialTXT = material.getText().toString();
                String colorTXT = color.getText().toString();
                String categoryTXT = category.getText().toString();
                String sizeTXT = size.getText().toString();

                Boolean check = DB.insertUserDate( codeTXT, brandTXT, categoryTXT, colorTXT, sizeTXT, priceTXT,
                        siteTXT, materialTXT);
                if(check==true){
                    Toast.makeText(Cupboard.this,"New Entry Inserted",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Cupboard.this,"New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long codeTXT= Long.parseLong(String.valueOf(code.getText()));
                String brandTXT = brand.getText().toString();
                int priceTXT =Integer.parseInt(String.valueOf(price.getText()));
                String siteTXT = site.getText().toString();
                String materialTXT = material.getText().toString();
                String colorTXT = color.getText().toString();
                String categoryTXT = category.getText().toString();
                String sizeTXT = size.getText().toString();

                Boolean check2 = DB.updateuserdata( codeTXT, brandTXT, categoryTXT, colorTXT, sizeTXT, priceTXT, siteTXT, materialTXT);
                if(check2==true){
                    Toast.makeText(Cupboard.this,"Entry Updated",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Cupboard.this,"Entry Not Updated", Toast.LENGTH_SHORT).show();
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long codeTXT= Long.parseLong(String.valueOf(code.getText()));


                Boolean check3 = DB.delete( codeTXT);
                if(check3==true){
                    Toast.makeText(Cupboard.this,"Entry Deletd",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Cupboard.this,"Entry Not Deleted", Toast.LENGTH_SHORT).show();
                }

            }
        });

        view.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //long codeTXT= Long.parseLong(String.valueOf(code.getText()));
                Cursor res=DB.getData();
                if(res.getCount()==0){
                    Toast.makeText(Cupboard.this,"No Entry Exists",Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Code : "+res.getLong(0)+"\n");
                    buffer.append("Brand : "+res.getString(1)+"\n");
                    buffer.append("Category : "+res.getString(2)+"\n");
                    buffer.append("Size : "+res.getString(3)+"\n");
                    buffer.append("Color : "+res.getString(4)+"\n");
                    buffer.append("Price : "+res.getInt(5)+"\n");
                    buffer.append("Place of Manufacturing : "+res.getString(6)+"\n");
                    buffer.append("Material : "+res.getString(7)+"\n"+"\n");


                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Cupboard.this);
                builder.setCancelable(true);
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });




    }

    public void onClick(View arg0){
        if(arg0.getId()== R.id.back){

            Intent intent = new Intent(this, MainPage.class);

            this.startActivity(intent);


        }
    }


}


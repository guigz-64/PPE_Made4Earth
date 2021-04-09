package com.example.ppe_code;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Menu1 extends AppCompatActivity implements View.OnClickListener {

    EditText code;
    Button select;

    private DBHelper DB;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu1);

        View v = findViewById(R.id.button2);
        v.setOnClickListener(this);

        code = findViewById(R.id.code);

        select=findViewById(R.id.select);
        DB=new DBHelper(this);


        DB.insertUserDate(1,"Rosaé Paris",195,"Baby Doll Dress","\n- Raw Material : Netherlands/France"+"\n"+"- Clothing : Romania","\n- 100% Certified Oeko Cotton"+"\n- Metal Buttons","Small","Denim Anthracite","\n- Main labels made from recycled polyester \n"+"- Composition label printed on pure cotton\n"+"Denim certified Oeko","Good working conditions","No risk for user health","No animal material",10);
        DB.insertUserDate(9782849028193L, "Rosaé Paris", 135, "L’Esterel Top", "\n- Raw Material : Spain/France/Netherlands"+"\n"+"- Clothing : Romania", "\n- Cotton Bobble Bio White \n - Fabric: 100% GOTS Certified Organic Cotton \n - Buttons: Mother of pearl ","Small","Washed Denim","\n- Main labels made from recycled polyester \n"+"- Composition label printed on pure cotton\n"+"Cotton Bobble Bio", "Good Working condition","No risk for user health","No animal material",10);
        DB.insertUserDate(1000010087111180L,"Rosaé Paris",195,"Baby Doll Dress","\n- Raw Material : Netherlands/France"+"\n"+"- Clothing : Romania","\n- 100% Certified Oeko Cotton"+"\n- Metal Buttons","Small","Denim Anthracite","\n- Main labels made from recycled polyester \n"+"- Composition label printed on pure cotton\n"+"Denim certified Oeko","Good working conditions","No risk for user health","No animal material",10);
        //DB.insertUserDate(1000010087111180L,"Levi's",88,"Jeans 501"," Mexico ","93% coton, 4% polyester, 2% polyethylene, 1% elasthanne","Large","Blue","Bad waste management \n"+"High use of fossil energy","Correct working conditions","Limited publication on substances used","Use of leather, fluff,silk",3);

        select.setOnClickListener(new View.OnClickListener(){
           public void onClick(View view){
               long codeTXT= Long.parseLong(String.valueOf(code.getText()));
               Cursor res=DB.getData(codeTXT);
               if(res.getCount()==0){
                   Toast.makeText(Menu1.this,"No Entry Exists",Toast.LENGTH_SHORT).show();
                   return;
               }
               StringBuffer buffer = new StringBuffer();
               while(res.moveToNext()){
                   buffer.append("Code : "+res.getLong(0)+"\n");
                   buffer.append("Brand : "+res.getString(1)+"\n");
                   buffer.append("Price : "+res.getInt(2)+"\n");
                   buffer.append("Place of Manufacturing : "+res.getString(3)+"\n");
                   buffer.append("Material : "+res.getString(5)+"\n");
                   buffer.append("Category : "+res.getString(4)+"\n");
                   buffer.append("Color : "+res.getString(11)+"\n");
                   buffer.append("Size : "+res.getString(10)+"\n");
                   buffer.append("Environment : "+res.getString(6)+"\n");
                   buffer.append("Human : "+res.getString(7)+"\n");
                   buffer.append("Health : "+res.getString(8)+"\n");
                   buffer.append("Animal : "+res.getString(9)+"\n");
                   buffer.append("Sustainability Mark : "+res.getInt(12)+"\n");

               }
               AlertDialog.Builder builder = new AlertDialog.Builder(Menu1.this);
               builder.setCancelable(true);
               builder.setMessage(buffer.toString());
               builder.show();

           }
        });
    }



    public void onClick(View arg0){
        if(arg0.getId()== R.id.button2){

            Intent intent = new Intent(this, MainPage.class);

            this.startActivity(intent);


        }
    }
    public DBHelper getDB(){
        return this.DB;
    }
}

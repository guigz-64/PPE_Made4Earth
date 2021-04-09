package com.example.ppe_code;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Menu2 extends AppCompatActivity implements View.OnClickListener  {


    Button scan_btn;
    DBHelper DB1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu2);

        View v = findViewById(R.id.button3);
        v.setOnClickListener(this);


        scan_btn = findViewById(R.id.button4);
        scan_btn.setOnClickListener(this);

        DB1=new DBHelper(this);


    }


        private void scanCode(){
            IntentIntegrator integrator= new IntentIntegrator(this);
            integrator.setCaptureActivity(ScanHelper.class);
            integrator.setOrientationLocked(false);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setPrompt("Scanning Code");
            integrator.initiateScan();
            

        }

        protected void onActivityResult(int requestCode,int resultCode,Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result !=null){
            if(result.getContents() != null){
                long codeTXT= Long.parseLong(result.getContents());
                System.out.println("C'est ici woula"+codeTXT);
                Cursor res=DB1.getData(codeTXT);
                if(res.getCount()==0){
                    Toast.makeText(Menu2.this,"No Entry Exists",Toast.LENGTH_SHORT).show();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(buffer.toString());
                builder.setTitle("Scanning Result");
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanCode();
                    }
                }).setNegativeButton("finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dialog= builder.create();
                dialog.show();
            }
            else{
                Toast.makeText(this,"No Results",Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);

        }
        }



    public void onClick(View arg0){
        if(arg0.getId()== R.id.button3){

            Intent intent = new Intent(this, MainPage.class);

            this.startActivity(intent);


        }
        if(arg0.getId()==R.id.button4){
            scanCode();
        }
    }
}

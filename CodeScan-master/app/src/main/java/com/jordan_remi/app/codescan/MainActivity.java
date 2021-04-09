package com.jordan_remi.app.codescan;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jordan_remi.app.codescan.database.CodeDbHelper;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.Calendar;

import static com.jordan_remi.app.codescan.Ressource.db;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Code> listeDeCodes;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainpage, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sync:
                new Thread(new RUN_FirebaseSynchronize(mHandler)).start();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    final private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // L'avancement se situe dans msg.arg1
            listeDeCodes.clear();
            listeDeCodes.addAll(db.getAllCodes());
            mAdapter.notifyDataSetChanged();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("notifications");

        db = new CodeDbHelper(this);
        listeDeCodes = db.getAllCodes();

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode("hello", BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap image = barcodeEncoder.createBitmap(bitMatrix);

        mRecyclerView = findViewById(R.id.liste_code);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int position = viewHolder.getAdapterPosition();
                listeDeCodes.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MonAdapteur(listeDeCodes,getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        FloatingActionButton bouton_ajouter = findViewById(R.id.bouton_ajouter);
        bouton_ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanBarecode();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == CommonStatusCodes.SUCCESS){
                if(data != null){
                    Barcode barcode = data.getParcelableExtra("barcode");
                    Code newCode = new Code(barcode.format, Calendar.getInstance().getTime(), barcode.displayValue, "", Code.ValueStatus.CREATE_BY_USER);
                    listeDeCodes.add(newCode);
                    mAdapter.notifyDataSetChanged();

                    //INSERER DANS LA BDD

                    db.addCode(newCode);

                    Toast.makeText(this, "La valeur du barcode est : " + barcode.displayValue, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Pas de rÃ©sultat sur la capture de la camÃ©ra", Toast.LENGTH_SHORT).show();
                }
            }
        }
            super.onActivityResult(requestCode, resultCode, data);


    }


    public void scanBarecode(){
        Intent intent = new Intent(this, ACT_Scan_Barcode.class);
        startActivityForResult(intent, 0);
    }

    private void changeStatusNewCodeFromFirastore(){
        boolean modif = false;
        for(Code item : listeDeCodes) {
            if(item.getStatus() == Code.ValueStatus.CREATE_BY_FIRESTORE) {
                item.setStatus(Code.ValueStatus.NORMAL);
                db.updateCode(item);
                if(!modif)
                    modif = true;
            }
        }
        if(modif)
            mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        changeStatusNewCodeFromFirastore();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        changeStatusNewCodeFromFirastore();
        super.onDestroy();
    }
}

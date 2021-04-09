package com.jordan_remi.app.codescan;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.jordan_remi.app.codescan.database.CodeContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.jordan_remi.app.codescan.Ressource.db;

/**
 * Created by jorda on 17/02/2018.
 */

public class RUN_FirebaseSynchronize implements Runnable {

    private FirebaseFirestore ff;
    private static final String NAMME_COLLECTION_CODES = "codes";
    private Handler mHandler;

    private ArrayList<Code> liste_codes;

    public RUN_FirebaseSynchronize(Handler mHandler){
        this.mHandler = mHandler;
    }

    @Override
    public void run() {
        ff = FirebaseFirestore.getInstance();
        liste_codes = db.getAllCodes();
        getFromFireStoreDataBase();
        setInFireStoreDataBase();
        Message msg = mHandler.obtainMessage(0, 0, 0);
        mHandler.sendMessage(msg);
    }

    private void getFromFireStoreDataBase(){
        ff.collection(NAMME_COLLECTION_CODES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String code = document.getString(CodeContract.FeedEntry.COLUMN_NAME_TITLE_CODE);
                                int type = document.getLong(CodeContract.FeedEntry.COLUMN_NAME_TITLE_TYPE).intValue();
                                String informations = document.getString(CodeContract.FeedEntry.COLUMN_NAME_TITLE_INFOS);
                                Date date = document.getDate(CodeContract.FeedEntry.COLUMN_NAME_TITLE_DATE);
                                String id = document.getString(CodeContract.FeedEntry._ID);
                                Code mcode = new Code(type, date, code, informations, Code.ValueStatus.CREATE_BY_FIRESTORE);
                                mcode.setId(id);
                                if (!liste_codes.contains(mcode))
                                    db.addCode(mcode);
                            }
                        } else {
                            System.out.println("fail get");
                        }
                    }
                });
    }

    private void setInFireStoreDataBase(){
        ArrayList<Map<String, Object>> liste_codes_ff = new ArrayList<>();

        for (Code item: liste_codes) {

            if(item.getStatus() != Code.ValueStatus.NORMAL && item.getStatus() != Code.ValueStatus.CREATE_BY_FIRESTORE ) {

                Map<String, Object> codes = new HashMap<>();

                codes.put(CodeContract.FeedEntry._ID, item.getId());
                codes.put(CodeContract.FeedEntry.COLUMN_NAME_TITLE_CODE, item.getCode());
                codes.put(CodeContract.FeedEntry.COLUMN_NAME_TITLE_INFOS, item.getInformations());
                codes.put(CodeContract.FeedEntry.COLUMN_NAME_TITLE_TYPE, item.getType());
                codes.put(CodeContract.FeedEntry.COLUMN_NAME_TITLE_DATE, item.getDate());

                liste_codes_ff.add(codes);

            }

        }

        for (Map<String, Object> map: liste_codes_ff ) {
            ff.collection(NAMME_COLLECTION_CODES)
                    .add(map)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            for (Code item: liste_codes) {
                                if(item.getStatus() == Code.ValueStatus.CREATE_BY_USER) {
                                    item.setStatus(Code.ValueStatus.NORMAL);
                                    db.updateCode(item);
                                }
                            }
                            System.out.println("success set");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("fail set");
                        }
                    });
        }
    }

}

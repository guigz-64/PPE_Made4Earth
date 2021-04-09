package com.jordan_remi.app.codescan;


import android.graphics.Bitmap;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by joplagne on 29/09/17.
 */

public class Code implements Serializable {

    private String _id;
    private int type;
    private Date date;
    private String code;
    private String informations ;

    static class ValueStatus{

        static final int NORMAL = 0;
        static final int CREATE_BY_USER = 1;
        static final int CREATE_BY_FIRESTORE = 2;
        static final int NOT_IN_FIRESTORE = 3;

    }

    private int status; // 0 : Normal | 1 : Creer par l'utilisateur | 2 : A été ajouté dans la base de données en ligne | 3 : N'est plus dans la base de données

    public Code(int type, Date date, String code, String informations, int status) {
        this.type = type;
        this.date = date;
        this.code = code;
        this.informations = informations;
        this.status = status;
    }

    public Code(){

    }

    public void setId(String id){
        this._id = id;
    }

    Bitmap getImage(int size) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            if(!code.isEmpty()) {
                BitMatrix bitMatrix = multiFormatWriter.encode(code, getBarcodeFormat(), size, size);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                return barcodeEncoder.createBitmap(bitMatrix);
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInformations() {
        return informations;
    }

    public void setInformations(String informations) {
        this.informations = informations;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public String getId(){
        return _id;
    }

    public String getColor(){
        switch (status){
            case 0:
                return "#B5B5B5";
            case 1:
                return "#5D82B2";
            case 2:
                return "#07B252";
            case 3:
                return "#8A0B09";
            default:
                return "#8A0B09";
        }
    }

    public String getTypeString(){
        switch (type){
            case Barcode.QR_CODE:
                return "QR Code";
            case Barcode.DATA_MATRIX:
                return "Data Matrix";
            default:
                return "EAN 13";
        }
    }

    private BarcodeFormat getBarcodeFormat(){
        switch (type){
            case Barcode.QR_CODE:
                return BarcodeFormat.QR_CODE;
            case Barcode.DATA_MATRIX:
                return BarcodeFormat.DATA_MATRIX;
            default:
                return BarcodeFormat.EAN_13;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj!= null && obj instanceof Code){
            if( this._id.equals(((Code) obj)._id) ){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

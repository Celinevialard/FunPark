package com.example.funpark.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.inputmethod.InputMethodManager;

import com.example.funpark.database.entity.SalesTicketEntity;
import com.google.gson.Gson;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * Helper qui permet de générer un Qr à partir d'une entité
 */
public class QrHelper {

    public static Bitmap generate(SalesTicketEntity salesTicket){
        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {
            Gson gson = new Gson();
            String json = gson.toJson(salesTicket);
            //BitMatrix class to encode entered text and set Width & Height
            BitMatrix mMatrix = mWriter.encode(json, BarcodeFormat.QR_CODE, 400,400);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);
            return mBitmap;

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}

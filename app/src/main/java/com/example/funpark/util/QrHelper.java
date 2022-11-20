package com.example.funpark.util;
import android.util.Log;
import com.example.funpark.database.entity.SalesTicketEntity;
//import androidmads.library.qrgenearator.QRGContents;
//import androidmads.library.qrgenearator.QRGEncoder;
//import androidmads.library.qrgenearator.QRGSaver;

public class QrHelper {
   /* public static void generate(SalesTicketEntity salesTicket){
        int dimen = 100;
        QRGEncoder qrgEncoder = new QRGEncoder(salesTicket.toString(), null, QRGContents.Type.TEXT, dimen);
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            qrCodeIV.setImageBitmap(bitmap);
        } catch (WriterException e) {
            // this method is called for
            // exception handling.
            Log.e("Tag", e.toString());
        }
    }*/
}

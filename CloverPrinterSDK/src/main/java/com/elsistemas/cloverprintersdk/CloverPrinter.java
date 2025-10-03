package com.elsistemas.cloverprintersdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.printer.job.ImagePrintJob2;
import com.clover.sdk.v1.printer.job.TextPrintJob;

public class CloverPrinter {
    private final Context context;

    private static final String CLOVER_ACCOUNT_TYPE = "clover";

    public CloverPrinter(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }

        this.context = context;
    }

    public void printText(String text) {
        if (text == null ) {
            throw new IllegalArgumentException("Text cannot be null");
        }

        TextPrintJob textPrintJob = new TextPrintJob.Builder()
                .text(text)
                .build();

        textPrintJob.print(context, CloverAccount.getAccount(this.context));
    }

    public void printImage(byte[] bytes) {
        if (bytes == null ) {
            throw new IllegalArgumentException("Bytes cannot be null");
        }

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        if (bitmap == null) {
            throw new IllegalArgumentException("Failed to decode bitmap from bytes");
        }

        // Validate bitmap height (Clover SDK maximum is 2048 pixels)
        if (bitmap.getHeight() > ImagePrintJob2.MAX_HEIGHT) {
            bitmap.recycle();
            throw new IllegalArgumentException("Bitmap height cannot be greater than " + ImagePrintJob2.MAX_HEIGHT + " pixels");
        }

        // Convert to ARGB_8888 for better compatibility with Clover printers
        Bitmap compatibleBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false);

        // Recycle original bitmap if it's a different instance
        if (compatibleBitmap != bitmap) {
            bitmap.recycle();
        }

        // Execute bitmap processing and printing on background thread
        // as recommended by Clover SDK (Builder methods perform blocking I/O)
        final Bitmap finalBitmap = compatibleBitmap;
        new Thread(() -> {
            ImagePrintJob2 imagePrintJob = new ImagePrintJob2.Builder(context)
                    .bitmap(finalBitmap)
                    .build();

            imagePrintJob.print(context, CloverAccount.getAccount(context));
        }).start();
    }
}

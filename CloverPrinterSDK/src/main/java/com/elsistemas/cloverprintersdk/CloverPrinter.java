package com.elsistemas.cloverprintersdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.printer.job.ImagePrintJob;
import com.clover.sdk.v1.printer.job.ImagePrintJob2;
import com.clover.sdk.v1.printer.job.PrintJob;
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

        // Convert to ARGB_8888 for better compatibility with Clover printers
        Bitmap compatibleBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false);

        // Recycle original bitmap if it's a different instance
        if (compatibleBitmap != bitmap) {
            bitmap.recycle();
        }

        PrintJob imagePrintJob2 = new ImagePrintJob.Builder()
                .bitmap(compatibleBitmap)
                .build();

        imagePrintJob2.print(context, CloverAccount.getAccount(this.context));

        // Bitmap will be recycled by the print job when done
    }
}

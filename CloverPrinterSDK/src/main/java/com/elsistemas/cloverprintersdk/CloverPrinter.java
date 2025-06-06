package com.elsistemas.cloverprintersdk;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
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

    public void printText(byte[] text) {
        if (text == null ) {
            throw new IllegalArgumentException("Text cannot be null");
        }

        Account cloverAccount = getCloverAccount(context);

        String value = new String(text);

        TextPrintJob printJob = new TextPrintJob.Builder()
                .text(value)
                .build();

        printJob.print(context, cloverAccount);
    }

    private static Account getCloverAccount(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType(CLOVER_ACCOUNT_TYPE);
        return accounts.length > 0 ? accounts[0] : null;
    }
}

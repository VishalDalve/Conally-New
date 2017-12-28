package com.app.coinally.in.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

/**
 * Created by Vishal on 27/12/2017.
 */

public class TransactionDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "transaction.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_WALLET_BALANCE = "wallet_balance";


    public static final String COLUMN_CURRENCY_NAME = "name";
    public static final String COLUMN_CURRENCY_address = "address";
    public static final String COLUMN_CURRENCY__available = "availacle";
    public static final String COLUMN_CURRENCY__pending = "pending";
    public static final String COLUMN_CURRENCY__resedrved = "reserved";
    public static final String COLUMN_CURRENCY__total= "total";


    private static final String TABLE_CREATE_DASH = "create table "
            + TABLE_WALLET_BALANCE + "(" + COLUMN_CURRENCY_NAME
            + " TEXT PRIMARY KEY, "
            + COLUMN_CURRENCY_address + " TEXT, "
            + COLUMN_CURRENCY__available + " REAL, "
            + COLUMN_CURRENCY__pending + " REAL, "
            + COLUMN_CURRENCY__resedrved + " REAL , "
            + COLUMN_CURRENCY__total + " REAL)";



    public TransactionDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE_DASH);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WALLET_BALANCE);
        db.execSQL(TABLE_CREATE_DASH);

        onCreate(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
       // db.execSQL("PRAGMA foreign_keys=ON;");
        super.onOpen(db);
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            db.setForeignKeyConstraintsEnabled(true);
//        }else{ db.execSQL("PRAGMA foreign_keys=ON;");}
    }

}

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

    public static final String TABLE_HISTORY_BALANCE = "history_balance";


    public static final String COLUMN_HISTORY_id = "id";
    public static final String COLUMN_HISTORY_yodlee_bank_accountId = "yodlee_bank_accountId";
    public static final String COLUMN_HISTORY_isAsset = "isAsset";
    public static final String COLUMN_HISTORY_asOfDate = "asOfDate";
    public static final String COLUMN_HISTORY_date = "date";
    public static final String COLUMN_HISTORY_balance_amount = "balance_amount";
    public static final String COLUMN_HISTORY_balance_currency = "balance_currency";
    public static final String COLUMN_HISTORY_dataSourceType = "dataSourceType";
    public static final String COLUMN_HISTORY_account_id = "account_id";
    public static final String COLUMN_HISTORY_created_at = "created_at";
    public static final String COLUMN_HISTORY_intervalz = "intervalz";

    private static final String TABLE_CREATE_DASH = "create table "
            + TABLE_HISTORY_BALANCE + "(" + COLUMN_HISTORY_id
            + " INTEGER PRIMARY KEY, "
            + COLUMN_HISTORY_yodlee_bank_accountId + " TEXT, "
            + COLUMN_HISTORY_isAsset + " INTEGER, "
            + COLUMN_HISTORY_asOfDate + " TEXT , "
            + COLUMN_HISTORY_date + " NUMERIC NOT NULL, "
            + COLUMN_HISTORY_balance_amount + " REAL, "
            + COLUMN_HISTORY_balance_currency + " TEXT, "
            + COLUMN_HISTORY_dataSourceType + " TEXT , "
            + COLUMN_HISTORY_account_id + " INTEGER , "
            + COLUMN_HISTORY_created_at + " NUMERIC NOT NULL, "
            + COLUMN_HISTORY_intervalz + " TEXT)";



    public TransactionDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE_DASH);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY_BALANCE);
        db.execSQL(TABLE_CREATE_DASH);

        onCreate(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
        super.onOpen(db);
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        }else{ db.execSQL("PRAGMA foreign_keys=ON;");}
    }

}

package com.app.coinally.in.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishal on 4/1/2017.
 */
public class TransactionOperations {

    public static final String LOGTAG = "Capitally";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    public TransactionOperations(Context context) {
        dbhandler = new TransactionDBHandler(context);
    }

    public void open() {
        Log.i(LOGTAG, "Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();

    }

    //ADDING TRANSACTION HISTORY(DASHBOARDDATA TO DATABASE)-------------------------------
    public TransactionData addTransactionDash(TransactionData TransactionData) {
        ContentValues values = new ContentValues();
        values.put(TransactionDBHandler.COLUMN_CURRENCY_NAME, TransactionData.getCoin_name());
        values.put(TransactionDBHandler.COLUMN_CURRENCY_address, TransactionData.getCryptoAddress());
        values.put(TransactionDBHandler.COLUMN_CURRENCY__available, TransactionData.getAvailable_qty());
        values.put(TransactionDBHandler.COLUMN_CURRENCY__pending, TransactionData.getPending_qty());
        values.put(TransactionDBHandler.COLUMN_CURRENCY__resedrved, TransactionData.getReserved_qty());
        values.put(TransactionDBHandler.COLUMN_CURRENCY__total, TransactionData.getTotal_qty());

        // long insertid = database.insertWithOnConflict(TransactionDBHandler.TABLE_TRANSACTION, null, values,SQLiteDatabase.CONFLICT_REPLACE);
// working,not tested       db.insertWithOnConflict(TABLE_NAME_CONTACT_EXTRA, null, row, SQLiteDatabase.CONFLICT_IGNORE);
        long insertid = database.insert(TransactionDBHandler.TABLE_WALLET_BALANCE, null, values);
        TransactionData.setId(insertid);
        return TransactionData;

    }

    //GETTING ALL DASHBOARD DATA AVAILABLE ------------------------------------
    public List<TransactionData> getAllDashTransaction() {

        // Cursor cursor = database.query(TransactionDBHandler.TABLE_TRANSACTION, allColumns, null, null, null, null, null);
        database = dbhandler.getReadableDatabase();

        String q = "select * from history_balance";


        Cursor cursor = database.rawQuery(q, null);

        List<TransactionData> transactions = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                TransactionData transact = new TransactionData();
                transact.setCoin_name(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_CURRENCY_NAME)));
                transact.setCryptoAddress(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_CURRENCY_address)));
                transact.setAvailable_qty(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_CURRENCY__available)));
                transact.setPending_qty(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_CURRENCY__pending)));
                transact.setReserved_qty(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_CURRENCY__resedrved)));
                transact.setTotal_qty(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_CURRENCY__total)));
                transactions.add(transact);
            }
        }
        // return All Transactions
        return transactions;
    }



//    //ADDING INDIVIDUAL TRANSACTION DATA TO DB -------------------------------------------
//    public TransactionData addTransaction(TransactionData TransactionData) {
//        ContentValues values = new ContentValues();
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_id, TransactionData.getTran_id());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_transactionDate, TransactionData.getDate());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_amount, TransactionData.getAmount());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_category, TransactionData.getCategory());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_accountId, TransactionData.getTran_accountid());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_amount_currency, TransactionData.getTran_currancy());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_description_simple, TransactionData.getTran_simpledesc());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_description_original, TransactionData.getOrignaldesc());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_icon_url, TransactionData.getTran_icon_url());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_type, TransactionData.getTran_type());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_runningBalance, TransactionData.getTran_running_bal());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_runningBalance_currency, TransactionData.getTran_runbal_currncy());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_categoryId, TransactionData.getTran_category_id());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_merchant_id, TransactionData.getTran_merchentid());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_CREATED_created_at, TransactionData.getTran_createdat());
//        values.put(TransactionDBHandler.COLUMN_TRANSACTION_basetype, TransactionData.getTran_basetype());
//
//
//        // long insertid = database.insertWithOnConflict(TransactionDBHandler.TABLE_TRANSACTION, null, values,SQLiteDatabase.CONFLICT_REPLACE);
//
//        long insertid = database.insert(TransactionDBHandler.TABLE_TRANSACTION, null, values);
//        TransactionData.setId(insertid);
//        return TransactionData;
//
//    }


    //ADDING INDIVIDUAL TRANSACTION DATA TO DB -------------------------------------------
//    public TransactionData addCategoryData(TransactionData TransactionData) {
//        ContentValues values = new ContentValues();
//        values.put(TransactionDBHandler.COLUMN_CATEGORY_id, TransactionData.getCat_id());
//        values.put(TransactionDBHandler.COLUMN_CATEGORY_yodlee_id, TransactionData.getCat_yodlee_id());
//        values.put(TransactionDBHandler.COLUMN_CATEGORY_source, TransactionData.getCat_source());
//        values.put(TransactionDBHandler.COLUMN_CATEGORY_category, TransactionData.getCat_category());
//        values.put(TransactionDBHandler.COLUMN_CATEGORY_type, TransactionData.getCat_type());
//        values.put(TransactionDBHandler.COLUMN_CATEGORY_highLevelCategoryId, TransactionData.getCat_highLevCatId());
//        values.put(TransactionDBHandler.COLUMN_CATEGORY_highLevelCategoryName, TransactionData.getCat_highLevCatName());
//        values.put(TransactionDBHandler.COLUMN_CATEGORY_created_at, TransactionData.getCat_created_at());
//        values.put(TransactionDBHandler.COLUMN_CATEGORY_created_by, TransactionData.getCat_created_by());
//        values.put(TransactionDBHandler.COLUMN_CATEGORY_icon_url, TransactionData.getCat_icon_url());
//        values.put(TransactionDBHandler.COLUMN_CATEGORY_final_icon, TransactionData.getCat_final_icon());
//        values.put(TransactionDBHandler.COLUMN_CATEGORY_rule_id, TransactionData.getCat_rule_id());
//
//        // long insertid = database.insertWithOnConflict(TransactionDBHandler.TABLE_TRANSACTION, null, values,SQLiteDatabase.CONFLICT_REPLACE);
//
//        long insertid = database.insert(TransactionDBHandler.TABLE_CATEGORY, null, values);
//        TransactionData.setId(insertid);
//        return TransactionData;
//
//    }


    //ADDING ACCOUNTS DATA TO DB -------------------------------------------
//    public TransactionData addAccountsData(TransactionData TransactionData) {
//        ContentValues values = new ContentValues();
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_id, TransactionData.getAc_id());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_CONTAINER, TransactionData.getAc_CONTAINER());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_accountName, TransactionData.getAc_AccountName());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_accountStatus, TransactionData.getAc_accountStatus());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_accountNumber, TransactionData.getAc_accountNumber());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_isAsset, TransactionData.getAc_isAsset());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_balance_amount, TransactionData.getAc_balance_amount());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_balance_currency, TransactionData.getAc_balance_currency());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_yodlee_id, TransactionData.getAc_yodlee_id());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_lastUpdated, TransactionData.getAc_lastUpdated());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_includeInNetWorth, TransactionData.getAc_includeInNetWorth());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_providerId, TransactionData.getAc_providerId());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_providerName, TransactionData.getAc_providerName());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_currentBalance_amount, TransactionData.getAc_currentBalance_amount());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_currentBalance_currency, TransactionData.getAc_currentBalance_currency());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_accountType, TransactionData.getAc_accountType());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_refresh_statusMessage, TransactionData.getAc_refresh_statusMessage());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_status, TransactionData.getAc_status());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_icon_url, TransactionData.getAc_icon_url());
//        Log.d("IN Adding", TransactionData.getAc_AccountName());
//        //long insertid = database.insertWithOnConflict(TransactionDBHandler.TABLE_ACCOUNTS, null, values,SQLiteDatabase.CONFLICT_REPLACE);
//
//        long insertid = database.insert(TransactionDBHandler.TABLE_ACCOUNTS, null, values);
//        TransactionData.setId(insertid);
//        return TransactionData;
//
//    }

    //UPDATE ACCOUNTS DATA TO DB -------------------------------------------
//    public int updateAccountsData(TransactionData TransactionData) {
//        ContentValues values = new ContentValues();
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_id, TransactionData.getAc_id());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_CONTAINER, TransactionData.getAc_CONTAINER());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_accountName, TransactionData.getAc_AccountName());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_accountStatus, TransactionData.getAc_accountStatus());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_accountNumber, TransactionData.getAc_accountNumber());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_isAsset, TransactionData.getAc_isAsset());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_balance_amount, TransactionData.getAc_balance_amount());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_balance_currency, TransactionData.getAc_balance_currency());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_yodlee_id, TransactionData.getAc_yodlee_id());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_lastUpdated, TransactionData.getAc_lastUpdated());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_includeInNetWorth, TransactionData.getAc_includeInNetWorth());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_providerId, TransactionData.getAc_providerId());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_providerName, TransactionData.getAc_providerName());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_currentBalance_amount, TransactionData.getAc_currentBalance_amount());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_currentBalance_currency, TransactionData.getAc_currentBalance_currency());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_accountType, TransactionData.getAc_accountType());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_refresh_statusMessage, TransactionData.getAc_refresh_statusMessage());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_status, TransactionData.getAc_status());
//        values.put(TransactionDBHandler.COLUMN_ACCOUNTS_icon_url, TransactionData.getAc_icon_url());
//        Log.d("IN Adding", TransactionData.getAc_AccountName());
//        //long insertid = database.insertWithOnConflict(TransactionDBHandler.TABLE_ACCOUNTS, null, values,SQLiteDatabase.CONFLICT_REPLACE);
//
////        long insertid = database.insert(TransactionDBHandler.TABLE_ACCOUNTS, null, values);
////        TransactionData.setId(insertid);
////        return TransactionData;
//
////        UPDATE accounts SET balance_amount = ‘’, lastUpdated = ‘’, currentBalance_amount = ‘’,
////        refresh_statusMessage = ‘’, device_updated_time = ‘’ WHERE yodlee_id = ‘’
//
//        return database.update(TransactionDBHandler.TABLE_ACCOUNTS, values,
//                TransactionDBHandler.COLUMN_ACCOUNTS_id + "=?", new String[]{String.valueOf(TransactionData.getAc_id())});
//
//    }

//    public List<TransactionData> getAllTransaction() {
//
//        // Cursor cursor = database.query(TransactionDBHandler.TABLE_TRANSACTION, allColumns, null, null, null, null, null);
//        database = dbhandler.getReadableDatabase();
//
//       // String q = "select * from transactions order by transactionDate DESC";
////
////        Cursor cursor = database.rawQuery(q, null);
////
////        List<TransactionData> transactions = new ArrayList<>();
////        if (cursor.getCount() > 0) {
////            while (cursor.moveToNext()) {
//        String q = "select accounts.providerName, accounts.icon_url, transactions.* from transactions, accounts where accounts.yodlee_id = transactions.accountId order by transactions.transactionDate DESC";
//
//
//
//        Cursor cursor = database.rawQuery(q, null);
//
//        List<TransactionData> transactions = new ArrayList<>();
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                TransactionData transact = new TransactionData();
//
//                transact.setTran_icon_url(cursor.getString(22));
//                transact.setAmount(cursor.getString(4));
//                transact.setDate(cursor.getString(12));
//                transact.setCategory(cursor.getString(19));
//                transact.setTran_basetype(cursor.getString(6));
//                transact.setOrignaldesc(cursor.getString(7));
//                transact.setId(cursor.getString(2));
//                transact.setAc_icon_url(cursor.getString(1));
//                transact.setAc_providerName(cursor.getString(0));
//
////                transact.setId(cursor.getLong(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_id)));
////                transact.setTran_simpledesc(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_description_simple)));
////                transact.setDate(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_transactionDate)));
////                transact.setAmount(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_amount)));
////                transact.setCategory(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_category)));
////                transact.setTran_running_bal(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_runningBalance_currency)));
////                transact.setOrignaldesc(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_description_original)));
////                transact.setTran_type(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_type)));
////                transact.setTran_icon_url(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_icon_url)));
////                transact.setTran_createdat(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_CREATED_created_at)));
////                transact.setTran_category_id(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_categoryId)));
////                transact.setTran_basetype(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_basetype)));
//
//                transactions.add(transact);
//            }
//        }
//        // return All Transactions
//        return transactions;
//    }



    // getting each category with count ----------------------------------
//    public List<TransactionData> getCategoryDetailed(String catname, int mnth) {
//
//        database = dbhandler.getReadableDatabase();
//
//        String where = "";
//        int month = mnth;
//
//        //default always getting cat name-----------
//        where += " AND category = '" + catname + "' ";
//
//        //filter by mont
//        if (month != 0 && month != 13) {
//            where += " AND transactionDate >= datetime('now','-" + month + " month') ";
//        }
//
//        String q = "select * from transactions where 1=1 " + where;
//
//        // where 1=1 " + where + order;
//
//        Cursor cursor = database.rawQuery(q, null);
//
//        List<TransactionData> transactions = new ArrayList<>();
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                TransactionData transact = new TransactionData();
//                transact.setId(cursor.getLong(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_id)));
//                transact.setTran_simpledesc(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_description_simple)));
//                transact.setDate(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_transactionDate)));
//                transact.setAmount(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_amount)));
//                transact.setCategory(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_category)));
//                transact.setTran_running_bal(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_runningBalance_currency)));
//                transact.setOrignaldesc(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_description_original)));
//                transact.setTran_type(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_type)));
//                transact.setTran_icon_url(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_icon_url)));
//                transact.setTran_category_id(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_categoryId)));
//                transact.setTran_basetype(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_basetype)));
//
//                transactions.add(transact);
//            }
//        }
//        // return All data
//        return transactions;
//    }


    // getting each category with name and all data ----------------------------------
//    public List<TransactionData> getCategoryCount() {
//
//        database = dbhandler.getReadableDatabase();
//
//        //String q = "Select t.category, Count(t.id), c.final_icon From transactions t, categories c where t.categoryId = yodlee_id Group by t.category";
//
////        for percentage
//        //  Select t.category, Count(t.id), c.final_icon, counti.summm, Count(t.id)*100/counti.summm  From transactions t, categories c, (Select Count(t.id) as summm  From transactions t, categories c where t.categoryId = yodlee_id Group by t.category limit 1) as counti where t.categoryId = yodlee_id Group by t.category
//
//        String q = "SELECT c.category, c.type, Count(t.id) AS category_count," +
//                " ROUND(SUM(t.amount)*100/counti.summm) AS percent, " +
//                "c.final_icon, SUM(t.amount) AS total_sum FROM transactions t," +
//                " categories c,(SELECT SUM(te.amount) AS summm FROM transactions te," +
//                " categories ce WHERE te.categoryId = yodlee_id) AS counti " +
//                "WHERE t.categoryId = yodlee_id GROUP BY t.category ORDER BY percent DESC";
//
//
//        Cursor cursor =
//                database.rawQuery(q, null);
//
//        List<TransactionData> transactions = new ArrayList<>();
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                TransactionData transact = new TransactionData();
//                transact.setCategory(cursor.getString(0));
//                transact.setCat_type(cursor.getString(1));//income,expence
//                transact.setCount(cursor.getString(2));
//                transact.setCat_highLevCatId(cursor.getString(3));//percentage
//                transact.setCat_icon_url(cursor.getString(4));
//                transact.setCat_highLevCatName(cursor.getString(5));//total amount
//                transactions.add(transact);
//            }
//        }
//        // return All data
//        return transactions;
//    }

    // getting each category with name and all data ----------------------------------
//    public List<TransactionData> getCategoryCountFilter(int mnth) {
//
//        database = dbhandler.getReadableDatabase();
//
//        //String q = "Select t.category, Count(t.id), c.final_icon From transactions t, categories c where t.categoryId = yodlee_id Group by t.category";
//
////        for percentage
//        //  Select t.category, Count(t.id), c.final_icon, counti.summm, Count(t.id)*100/counti.summm  From transactions t, categories c, (Select Count(t.id) as summm  From transactions t, categories c where t.categoryId = yodlee_id Group by t.category limit 1) as counti where t.categoryId = yodlee_id Group by t.category
//
//        String where = "";
////        int month = mnth;
//
//        if (mnth != 0) {
//            where += " AND transactionDate >= datetime('now','-" + mnth + " month') ";
//        }
//
//
//        String q = "SELECT c.category, c.type, Count(t.id) AS category_count, ROUND(SUM(t.amount)*100/counti.summm) AS percent,c.final_icon, SUM(t.amount) AS total_sum " +
//                "FROM transactions t,categories c," +
//                "(SELECT SUM(te.amount) AS summm FROM transactions te, categories ce WHERE te.categoryId = yodlee_id" +
//                where + ") AS counti WHERE t.categoryId = yodlee_id" +
//                where + " GROUP BY t.category ORDER BY percent DESC";
//
//        // String q = "select * from transactions where 1=1 " + where
//
//
//        Cursor cursor =
//                database.rawQuery(q, null);
//
//        List<TransactionData> transactions = new ArrayList<>();
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                TransactionData transact = new TransactionData();
//                transact.setCategory(cursor.getString(0));
//                transact.setCat_type(cursor.getString(1));//income,expence
//                transact.setCount(cursor.getString(2));
//                transact.setCat_highLevCatId(cursor.getString(3));//percentage
//                transact.setCat_icon_url(cursor.getString(4));
//                transact.setCat_highLevCatName(cursor.getString(5));//total amount
//                transactions.add(transact);
//            }
//        }
//        // return All data
//        return transactions;
//    }

    // getting top five category data percentage for home ----------------------------------
//    public List<TransactionData> gettopfiveCategory() {
//
//        database = dbhandler.getReadableDatabase();
//
//        String q = "SELECT t.category, \n" +
//                "       c.final_icon, \n" +
//                "       Round(Sum(t.amount) * 100 / counti.summm) AS FINAL, \n" +
//                "       Sum(t.amount) \n" +
//                "FROM   transactions t, \n" +
//                "       categories c, \n" +
//                "       (SELECT Sum(t.amount) AS summm \n" +
//                "        FROM   transactions t, \n" +
//                "               categories c \n" +
//                "        WHERE  t.categoryid = yodlee_id \n" +
//                "               AND c.type != 'INCOME' \n" +
//                "               AND t.transactiondate >= Date('now', 'start of month', '-1 month' \n" +
//                "                                        ) \n" +
//                "               AND t.transactiondate < Date('now', 'start of month')) AS counti \n" +
//                "WHERE  t.categoryid = yodlee_id \n" +
//                "       AND c.type != 'INCOME' \n" +
//                "       AND t.transactiondate >= Date('now', 'start of month', '-1 month') \n" +
//                "       AND t.transactiondate < Date('now', 'start of month') \n" +
//                "GROUP  BY t.category \n" +
//                "ORDER  BY final DESC \n" +
//                "LIMIT  5 ";
//
//
//        Cursor cursor = database.rawQuery(q, null);
//
//        List<TransactionData> transactions = new ArrayList<>();
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                TransactionData transact = new TransactionData();
//                transact.setCategory(cursor.getString(0));
//                transact.setCat_icon_url(cursor.getString(1));
//                transact.setCount(cursor.getString(2));
//                transact.setAmount(cursor.getString(3));
//                transactions.add(transact);
//            }
//        }
//        // return All data
//        return transactions;
//    }

    //get all accounts new ---------------------------------------------
//    public List<TransactionData> findAccount(String yodlee_id) {
//
//        // Cursor cursor = database.query(TransactionDBHandler.TABLE_TRANSACTION, allColumns, null, null, null, null, null);
//        database = dbhandler.getReadableDatabase();
//
//        String q = "select * from accounts where yodlee_id = '" + yodlee_id + "'";
//
//
//        Cursor cursor = database.rawQuery(q, null);
//
//        List<TransactionData> transactions = new ArrayList<>();
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                TransactionData transact = new TransactionData();
////                Log.d("Hitttt", null);
//                transact.setAc_id(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_id)));
//                transact.setAc_CONTAINER(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_CONTAINER)));
//                transact.setAc_AccountName(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_accountName)));
//                transact.setAc_status(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_accountStatus)));
//                transact.setAc_accountNumber(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_accountNumber)));
//                transact.setAc_isAsset(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_isAsset)));
//                transact.setAc_balance_amount(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_balance_amount)));
//                transact.setAc_balance_currency(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_balance_currency)));
//                transact.setAc_yodlee_id(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_yodlee_id)));
//                transact.setAc_lastUpdated(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_lastUpdated)));
//                transact.setAc_includeInNetWorth(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_includeInNetWorth)));
//                transact.setAc_providerId(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_providerId)));
//                transact.setAc_providerName(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_providerName)));
//                transact.setAc_currentBalance_amount(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_currentBalance_amount)));
//                transact.setAc_currentBalance_currency(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_currentBalance_currency)));
//                transact.setAc_accountType(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_accountType)));
//                transact.setAc_refresh_statusMessage(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_refresh_statusMessage)));
//                transact.setAc_status(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_status)));
//                transact.setAc_icon_url(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_icon_url)));
//                Log.d("IN Fetching", cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_accountName)));
//                transactions.add(transact);
//            }
//        }
//        // return All Transactions
//        return transactions;
//    }

    //****************************update cash amount function********************************
//    public String updateCashAmount(String yodlee_id) {
//
//        // Cursor cursor = database.query(TransactionDBHandler.TABLE_TRANSACTION, allColumns, null, null, null, null, null);
//        database = dbhandler.getReadableDatabase();
//
//        String q = "select * from accounts where yodlee_id = '" + yodlee_id + "'";
//
//
//        Cursor cursor = database.rawQuery(q, null);
//
//        String cashAmount = null;
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                TransactionData transact = new TransactionData();
////                Log.d("Hitttt", null);
//                cashAmount = (cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_balance_amount)));
//            }
//        }
//        // return All Transactions
//        return cashAmount;
//    }

    //get all accounts new ---------------------------------------------
//    public List<TransactionData> getAllAccounts() {
//
//        // Cursor cursor = database.query(TransactionDBHandler.TABLE_TRANSACTION, allColumns, null, null, null, null, null);
//        database = dbhandler.getReadableDatabase();
//
//        String q = "select * from accounts ORDER BY accountType DESC";
//
//
//        Cursor cursor = database.rawQuery(q, null);
//
//        List<TransactionData> transactions = new ArrayList<>();
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                TransactionData transact = new TransactionData();
////                Log.d("Hitttt", null);
//                transact.setAc_id(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_id)));
//                transact.setAc_CONTAINER(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_CONTAINER)));
//                transact.setAc_AccountName(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_accountName)));
//                transact.setAc_status(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_accountStatus)));
//                transact.setAc_accountNumber(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_accountNumber)));
//                transact.setAc_isAsset(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_isAsset)));
//                transact.setAc_balance_amount(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_balance_amount)));
//                transact.setAc_balance_currency(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_balance_currency)));
//                transact.setAc_yodlee_id(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_yodlee_id)));
//                transact.setAc_lastUpdated(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_lastUpdated)));
//                transact.setAc_includeInNetWorth(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_includeInNetWorth)));
//                transact.setAc_providerId(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_providerId)));
//                transact.setAc_providerName(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_providerName)));
//                transact.setAc_currentBalance_amount(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_currentBalance_amount)));
//                transact.setAc_currentBalance_currency(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_currentBalance_currency)));
//                transact.setAc_accountType(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_accountType)));
//                transact.setAc_refresh_statusMessage(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_refresh_statusMessage)));
//                transact.setAc_status(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_status)));
//                transact.setAc_icon_url(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_icon_url)));
//                Log.d("IN Fetching", cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_ACCOUNTS_accountName)));
//                transactions.add(transact);
//            }
//        }
//        // return All Transactions
//        return transactions;
//    }


    // Updating TransactionData ----------------------------------------
//    public int updateTransaction(TransactionData transactiondata) {
//
//        ContentValues values = new ContentValues();
//        values.put(TransactionDBHandler.COLUMN_HISTORY_id, transactiondata.getDash_id());
//        values.put(TransactionDBHandler.COLUMN_HISTORY_yodlee_bank_accountId, transactiondata.getDash_yodlee_bank_accountId());
//        values.put(TransactionDBHandler.COLUMN_HISTORY_isAsset, transactiondata.getDash_isAsset());
//        values.put(TransactionDBHandler.COLUMN_HISTORY_date, transactiondata.getDash_date());
//        values.put(TransactionDBHandler.COLUMN_HISTORY_asOfDate, transactiondata.getDash_asOfDate());
//        values.put(TransactionDBHandler.COLUMN_HISTORY_balance_amount, transactiondata.getDash_balance_amount());
//        values.put(TransactionDBHandler.COLUMN_HISTORY_balance_currency, transactiondata.getDash_balance_amount());
//        values.put(TransactionDBHandler.COLUMN_HISTORY_dataSourceType, transactiondata.getDash_dataSourceType());
//        values.put(TransactionDBHandler.COLUMN_HISTORY_account_id, transactiondata.getDash_account_id());
//        values.put(TransactionDBHandler.COLUMN_HISTORY_created_at, transactiondata.getDash_created_at());
//        values.put(TransactionDBHandler.COLUMN_HISTORY_intervalz, transactiondata.getDash_intervalz());
//
//        // updating row
//        return database.update(TransactionDBHandler.TABLE_TRANSACTION, values,
//                TransactionDBHandler.COLUMN_HISTORY_id + "=?", new String[]{String.valueOf(transactiondata.getId())});
//    }

    // Deleting TransactionData
//    public void removeTransaction(TransactionData transaction) {
//
//        database.delete(TransactionDBHandler.TABLE_TRANSACTION, TransactionDBHandler.COLUMN_HISTORY_id + "=" + transaction.getId(), null);
//    }

    //filter data result ------------------------
//    public List<TransactionData> getFilterTransaction(String s, String e, String sortby, int mnth, String actype) {
//
//        // Cursor cursor = database.query(TransactionDBHandler.TABLE_TRANSACTION, allColumns, null, null, null, null, null);
//        database = dbhandler.getReadableDatabase();
//        // String sortby = null;
//
//        //working ------------
//        //String q = "select * from transactions where amount>'" + s + "'and amount <'" + e + "'";
//        String where = "";
//        int month = mnth;
//
//
//        if (!s.isEmpty() && !e.isEmpty()) {
//            where += " AND amount >='" + s + "'and amount <='" + e + "'";
//        }
//        if (month != 0 && month != 13) {
//            where += " AND transactionDate >= datetime('now','-" + month + " month')";
//        }
//
//
//        // sort by account type -------------
//        if (actype.equals("DEBIT") || actype.equals("CREDIT")) {
//
//            where += " AND basetype ='" + actype + "'";
//
//        }
//
//        String order = " ";
//        if (!sortby.isEmpty() || !sortby.equals(null)) {
//            // where += " AND transactionDate >= datetime('now','-" + month + " month')";
//            if (sortby.equals("name")) {
//                order += " order by description_original DESC";
//            }
//            if (sortby.equals("date")) {
//                order += " order by transactionDate DESC";
//            }
//        }
//
//        String q = "select * from transactions where 1=1 " + where + order;
//
//        Log.d("QQQQ", q);
//        // transactionDate >= datetime('now','-6 month')
//        Cursor cursor = database.rawQuery(q, null);
//
//        List<TransactionData> transactions = new ArrayList<>();
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                TransactionData transact = new TransactionData();
//                transact.setId(cursor.getLong(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_id)));
//                transact.setTran_simpledesc(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_description_simple)));
//                transact.setDate(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_transactionDate)));
//                transact.setAmount(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_amount)));
//                transact.setCategory(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_category)));
//                transact.setTran_running_bal(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_runningBalance_currency)));
//                transact.setOrignaldesc(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_description_original)));
//                transact.setTran_type(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_type)));
//                transact.setTran_icon_url(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_icon_url)));
//                transact.setTran_createdat(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_CREATED_created_at)));
//                transact.setTran_category_id(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_categoryId)));
//                transact.setTran_basetype(cursor.getString(cursor.getColumnIndex(TransactionDBHandler.COLUMN_TRANSACTION_basetype)));
//
//                transactions.add(transact);
//            }
//        }
//        // return All Transactions
//        return transactions;
//    }

    //networth assets details  ------------------------
//   public List<TransactionData> getAssetsbymonth() {
//
//        database = dbhandler.getReadableDatabase();
//
//        String q = "SELECT CAST(SUM(balance_amount)AS INT) as assets, date as asset_date from history_balance where intervalz = 'M' AND isAsset = 1 group by date " +
//                " ORDER by asset_date DESC;";
//
//        Cursor cursor = database.rawQuery(q, null);
//
//        List<TransactionData> transactions = new ArrayList<>();
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                TransactionData transact = new TransactionData();
//
//                transact.setAseetsbymonth(cursor.getString(0));
//                transact.setDatebymonth(cursor.getString(1));
//
//                transactions.add(transact);
//            }
//        }
//        // return All Transactions
//        return transactions;
//    }

    //networth liabilities details  ------------------------SELECT SUM(balance_amount) FROM accounts;
//    public List<TransactionData> getLaibilitiesbymonth() {
//
//        database = dbhandler.getReadableDatabase();
//
//        String q = "SELECT CAST(SUM(balance_amount)AS INT) as assets, date as asset_date from history_balance where intervalz = 'M' AND isAsset = 0 group by date ORDER BY asset_date DESC;";
//
//        Cursor cursor = database.rawQuery(q, null);
//
//        List<TransactionData> transactions = new ArrayList<>();
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                TransactionData transact = new TransactionData();
//
//                transact.setLaibilitiesbymonth(cursor.getString(0));
//                transact.setDatebymonth(cursor.getString(1));
//
//                transactions.add(transact);
//            }
//        }
//        // return All Transactions
//        return transactions;
//    }

    //get balance details  ------------------------
//    public String getBalance() {
//
//        database = dbhandler.getReadableDatabase();
//
//        String q = "SELECT SUM(balance_amount) FROM accounts where isAsset = 1";
//        Cursor cursor = database.rawQuery(q, null);
//        String bal = "0";
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                bal = cursor.getString(0);
//            }
//        }
//        return bal;
//    }

    //get income details  ------------------------
//    public String getIncome() {
//
//        database = dbhandler.getReadableDatabase();
//
//        String q = "SELECT SUM(amount) FROM transactions where strftime('%m %Y',transactionDate) ==  strftime('%m %Y',CURRENT_DATE) and basetype = 'CREDIT'";
//        Cursor cursor = database.rawQuery(q, null);
//        String bal = "0";
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                bal = cursor.getString(0);
//            }
//        }
//        return bal;
//    }

    //get income details  ------------------------
//    public String getExpence() {
//
//        database = dbhandler.getReadableDatabase();
//
//        String q = "SELECT SUM(amount) FROM transactions where strftime('%m %Y',transactionDate) ==  strftime('%m %Y',CURRENT_DATE) and basetype = 'DEBIT'";
//        Cursor cursor = database.rawQuery(q, null);
//        String bal = "0";
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                bal = cursor.getString(0);
//            }
//        }
//        return bal;
//    }


    //setting expences by month ---------------------------------------------------
//    public List<TransactionData> getSpendingsbyMonth(int mnth) {
//
//        database = dbhandler.getReadableDatabase();
//
//        String where = "";
////        int month = mnth;
////        if (month != 0 && month != 10) {
////            where += " AND transactionDate >= datetime('now','-" + month + " month')";
////        }
//
//        String q = "SELECT strftime('%m', transactionDate), strftime('%Y', transactionDate), transactionDate, count(*), SUM(amount) \n" +
//                "FROM transactions where basetype = 'DEBIT' " + where +
//                "GROUP BY strftime('%m %Y', transactionDate) ORDER BY transactionDate DESC";
//        Cursor cursor = database.rawQuery(q, null);
//        List<TransactionData> transactions = new ArrayList<>();
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                TransactionData transact = new TransactionData();
//
//                transact.setAseetsbymonth(cursor.getString(2));
//                transact.setDatebymonth(cursor.getString(4));
//
//                transactions.add(transact);
//            }
//        }
//        // return All Transactions
//        return transactions;
//    }



}

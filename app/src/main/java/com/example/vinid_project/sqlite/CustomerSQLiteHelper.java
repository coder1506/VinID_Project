package com.example.vinid_project.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vinid_project.models.Customer;
import com.example.vinid_project.models.Receipt;

import java.util.ArrayList;
import java.util.List;

public class CustomerSQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = "CustomerSQLiteHelper";
    static final String DB_NAME = "Customers.db";
    static final String DB_NAME_TABLE = "Customers";
    static final int DB_VERSION = 5;

    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;

    public CustomerSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreaTable = "CREATE TABLE Customers ( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "codeCustomers Text ," +
                "nameCustomers Text ," +
                "email Text ," +
                "phoneNumberCustomers Text ," +
                "address Text)";

        //Chạy câu lệnh tạo bảng
        db.execSQL(queryCreaTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("drop table if exists " + DB_NAME_TABLE);
            onCreate(db);
        }
    }

    public void insertCustomers(String codeCustomers,String nameCustomers,String email, String phoneNumberCustomers,String address) {
        sqLiteDatabase = getWritableDatabase();

        contentValues = new ContentValues();
        contentValues.put("codeCustomers", codeCustomers);
        contentValues.put("nameCustomers", nameCustomers);
        contentValues.put("email", email);
        contentValues.put("phoneNumberCustomers", phoneNumberCustomers);
        contentValues.put("address", address);

        sqLiteDatabase.insert(DB_NAME_TABLE, null, contentValues);
        closeDB();
    }
    public void insertCustomers(String nameCustomers, String phoneNumberCustomers) {
        sqLiteDatabase = getWritableDatabase();

        contentValues = new ContentValues();
        contentValues.put("nameCustomers", nameCustomers);
        contentValues.put("phoneNumberCustomers", phoneNumberCustomers);

        sqLiteDatabase.insert(DB_NAME_TABLE, null, contentValues);
        closeDB();
    }
    public void updateCustomers(int id, String nameCustomers, String phoneNumberCustomers) {
        sqLiteDatabase = getWritableDatabase();

        contentValues = new ContentValues();
        contentValues.put("nameCustomers", nameCustomers);
        contentValues.put("phoneNumberCustomers", phoneNumberCustomers);

        sqLiteDatabase.update(DB_NAME_TABLE, contentValues, "id=?",
                new String[]{String.valueOf(id)});
        closeDB();
    }

    public int deleteCustomers(int id) {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_NAME_TABLE, " id=?",
                new String[]{String.valueOf(id)});
    }

    public List<Customer> getAllCustomersAdvanced() {
        List<Customer> customerList = new ArrayList<>();

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_NAME_TABLE, null, null, null
                , null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nameCustomers = cursor.getString(cursor.getColumnIndex("nameCustomers"));
            String phoneNumberCustomers = cursor.getString(cursor.getColumnIndex("phoneNumberCustomers"));
            customerList.add(new Customer(id, nameCustomers, phoneNumberCustomers));
        }
        closeDB();
        return customerList;
    }

    // Đếm số dòng trong bản ghi
    public int getCount() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + DB_NAME_TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        int totalRows = cursor.getCount();
        cursor.close();
        db.close();
        return totalRows;
    }

    private void closeDB() {
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (contentValues != null) contentValues.clear();
        if (cursor != null) cursor.close();
    }
}

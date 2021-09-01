package com.example.vinid_project.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vinid_project.models.ShopItem;

import java.util.ArrayList;
import java.util.List;

public class ItemSQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = "ItemSQLHelper";
    static final String DB_NAME = "Items.db";
    static final String DB_NAME_TABLE = "Items";
    static final int DB_VERSION = 5;

    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;

    public ItemSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreaTable = "CREATE TABLE Items ( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "nameItems Text ," +
                "priceItems Text ," +
                "quantity Text)";

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

    public void insertItems(String nameItems, String priceItems, String quantity) {
        sqLiteDatabase = getWritableDatabase();

        contentValues = new ContentValues();
        contentValues.put("nameItems", nameItems);
        contentValues.put("priceItems", priceItems);
        contentValues.put("quantity", quantity);

        sqLiteDatabase.insert(DB_NAME_TABLE, null, contentValues);
        closeDB();
    }

    public void updateItems(int id, String nameItems, String priceItems, String quantity) {
        sqLiteDatabase = getWritableDatabase();

        contentValues = new ContentValues();
        contentValues.put("nameItems", nameItems);
        contentValues.put("priceItems", priceItems);
        contentValues.put("quantity", quantity);

        sqLiteDatabase.update(DB_NAME_TABLE, contentValues, "id=?",
                new String[]{String.valueOf(id)});
        closeDB();
    }

    public int deleteItems(int id) {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_NAME_TABLE, " id=?",
                new String[]{String.valueOf(id)});
    }

    public List<ShopItem> getAllItemsAdvanced() {
        List<ShopItem> shopItemList = new ArrayList<>();

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_NAME_TABLE, null, null, null
                , null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nameItems = cursor.getString(cursor.getColumnIndex("nameItems"));
            String priceItems = cursor.getString(cursor.getColumnIndex("priceItems"));
            String quantity = cursor.getString(cursor.getColumnIndex("quantity"));
            shopItemList.add(new ShopItem(id, nameItems, priceItems, quantity));
        }
        closeDB();
        return shopItemList;
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

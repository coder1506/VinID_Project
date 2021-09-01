package com.example.vinid_project.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vinid_project.models.Receipt;

import java.util.ArrayList;
import java.util.List;

public class OrderSQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = "OrderSQLiteHelper";
    static final String DB_NAME = "Receipt.db";
    static final String DB_NAME_TABLE = "Receipt";
    static final int DB_VERSION = 5;

    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;

    public OrderSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreaTable = "CREATE TABLE Receipt ( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "tenKhachHang Text ," +
                "soDienThoai Text ," +
                "loaiThanhToan Text ," +
                "soTien Text ," +
                "diaChi Text )";

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

    public void insertReceipt(String tenKhachHang, String soDienThoai, String loaiThanhToan, String soTien, String diaChi) {
        sqLiteDatabase = getWritableDatabase();

        contentValues = new ContentValues();
        contentValues.put("tenKhachHang", tenKhachHang);
        contentValues.put("soDienThoai", soDienThoai);
        contentValues.put("loaiThanhToan", loaiThanhToan);
        contentValues.put("soTien", soTien);
        contentValues.put("diaChi", diaChi);

        sqLiteDatabase.insert(DB_NAME_TABLE, null, contentValues);
        closeDB();
    }

    public void updateReceipt(int id, String tenKhachHang, String soDienThoai, String loaiThanhToan, String soTien, String diaChi) {
        sqLiteDatabase = getWritableDatabase();

        contentValues = new ContentValues();
        contentValues.put("tenKhachHang", tenKhachHang);
        contentValues.put("soDienThoai", soDienThoai);
        contentValues.put("loaiThanhToan", loaiThanhToan);
        contentValues.put("soTien", soTien);
        contentValues.put("diaChi", diaChi);

        sqLiteDatabase.update(DB_NAME_TABLE, contentValues, "id=?",
                new String[]{String.valueOf(id)});
        closeDB();
    }

    public int deleteReceipt(int id) {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_NAME_TABLE, " id=?",
                new String[]{String.valueOf(id)});
    }

    public List<Receipt> getAllReceiptAdvanced() {
        List<Receipt> receiptList = new ArrayList<>();

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_NAME_TABLE, null, null, null
                , null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String tenKhachHang = cursor.getString(cursor.getColumnIndex("tenKhachHang"));
            String soDienThoai = cursor.getString(cursor.getColumnIndex("soDienThoai"));
            String loaiThanhToan = cursor.getString(cursor.getColumnIndex("loaiThanhToan"));
            String soTien = cursor.getString(cursor.getColumnIndex("soTien"));
            String diaChi = cursor.getString(cursor.getColumnIndex("diaChi"));
            receiptList.add(new Receipt(id, tenKhachHang, soDienThoai, loaiThanhToan, soTien, diaChi));
        }
        closeDB();
        return receiptList;
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

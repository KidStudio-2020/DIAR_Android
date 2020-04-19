package com.example.diar.Database;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;



public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "letter";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @SuppressLint("SQLiteString")
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LetterDB.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LetterDB.NAME);

        onCreate(db);
    }

    public void insertLetter(Double latitude, Double longitude, String create_date, String content, String picture, String title) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(LetterDB.LAT, latitude);
        values.put(LetterDB.LONG, longitude);
        values.put(LetterDB.CREATEDATE, create_date);
        values.put(LetterDB.CONTENT, content);
        values.put(LetterDB.PICTURE, picture);
        values.put(LetterDB.TITLE, title);
        db.execSQL("INSERT INTO letter VALUES(" + null + ", " + latitude + ", " + longitude + ", '" + create_date + "', '" + content + "', '" + picture + "', '" + title + "')");
        db.close();
    }

    public String getLetter() {
        SQLiteDatabase db = this.getReadableDatabase();

        String result = " ";

        Cursor cursor = db.rawQuery("SELECT * FROM LETTER", null);
        while (cursor.moveToNext()) {
            result += " id: "
                    + cursor.getDouble(0)
                    + " lat: "
                    + cursor.getDouble(1)
                    + " lng: "
                    + cursor.getDouble(2)
                    + " date: "
                    + cursor.getString(3)
                    + " content: "
                    + cursor.getString(4)
                    + " picture: "
                    + cursor.getString(5)
                    + "\n";
        }
        return result;
    }


    public int getLetterCount() {
        String countQuery = "SELECT  * FROM " + LetterDB.NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public ArrayList<String> getDateList() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<String> dateList = new ArrayList<>();
        String result = null;

        Cursor cursor = db.rawQuery("SELECT create_date FROM " + LetterDB.NAME + " ORDER BY " + LetterDB.LETTERID + " DESC", null);

        while (cursor.moveToNext()) {
            result = cursor.getString(0);
            dateList.add(result);
        }
        cursor.close();

        return dateList;
    }


    public ArrayList<LetterDB> getAllLetter() {
        ArrayList<LetterDB> letterList = new ArrayList<>();
        LetterDB letter;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM letter ORDER BY " + LetterDB.LETTERID + " DESC", null);

        while (cursor.moveToNext()) {
            letter = new LetterDB();
            letter.setLetter_id(cursor.getInt(0));
            letter.setLatitude(cursor.getDouble(1));
            letter.setLongitude(cursor.getDouble(2));
            letter.setCreate_date(cursor.getString(3));
            letter.setContent(cursor.getString(4));
            letter.setPicture(cursor.getString(5));
            letter.setTitle(cursor.getString(6));
            letterList.add(letter);
        }
        cursor.close();

        return letterList;
    }

    public void delete(int idx) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM letter WHERE " + LetterDB.LETTERID + " = " + idx);
        db.close();
    }

}
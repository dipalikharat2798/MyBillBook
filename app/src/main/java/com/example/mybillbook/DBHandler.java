package com.example.mybillbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "billdb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "mybills";
    private static final String COL_1="id";
    private static final String COL_2="category";
    private static final String COL_3="billnumber";
    private static final String COL_4="billname";
    private static final String COL_5="date";
    private static final String COL_6="time";
    private static final String COL_7="amountpaid";
    private static final String COL_8="status";
    private static final String COL_9="photo";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2 + " TEXT,"
                + COL_3 + " TEXT,"
                + COL_4 + " TEXT,"
                + COL_5 + " TEXT,"
                + COL_6 + " TEXT,"
                + COL_7 + " INTEGER,"
                + COL_8 + " TEXT,"
                + COL_9 + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new bill to our sqlite database.
    public void addNewBill(String categary,String billnumber,String billname, String date, String time, int amountpaid,String satus, String photo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_2, categary);
        values.put(COL_3, billnumber);
        values.put(COL_4, billname);
        values.put(COL_5, date);
        values.put(COL_6, time);
        values.put(COL_7,amountpaid);
        values.put(COL_8,satus);
        values.put(COL_9,photo);


        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    // we have created a new method for reading all the Bills.
    public ArrayList<BillModal> readBills() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorBills = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<BillModal> BillModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorBills.moveToFirst()) {
            do {
                BillModalArrayList.add(new BillModal(
                        cursorBills.getString(1),
                        cursorBills.getString(2),
                        cursorBills.getString(3),
                        cursorBills.getString(4),
                        cursorBills.getString(5),
                        cursorBills.getInt(6),
                        cursorBills.getString(7),
                        cursorBills.getString(8)));
            } while (cursorBills.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorBills.close();
        return BillModalArrayList;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void deleteTask(int id){
        SQLiteDatabase db;
        db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(id)});
    }
}
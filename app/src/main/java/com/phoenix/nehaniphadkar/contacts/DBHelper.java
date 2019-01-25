package com.phoenix.nehaniphadkar.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shree on 1/31/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static String tablename = "Contacts";
/*
    public static String columnname = "title";
*/


    public DBHelper(Context context) {
        super(context, "ContactsDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableEmp = "create table Contacts(name TEXT,number TEXT,json TEXT)";

/*
        String tableEmp =("create table "+tablename + "("+idcolumn+"integer primary key autoincrement,"+titlecolumn+" text ,"+descriptioncolumn+"text "+")");
*/
        db.execSQL(tableEmp);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //used to drop exising table
/*
        db.execSQL("drop table "+tablename+" if exists",null);
*/

    }

    public boolean insertData(Contact task) {
        try {


            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", task.getName());
            values.put("number",task.getNumber());
            values.put("json",task.getJson());

            long rows = sqLiteDatabase.insert("Contacts", null, values);
            sqLiteDatabase.close();
            return rows > 0;
        } catch (Exception e) {
            return false;

        }

    }

    public List<Contact> showList() {
        try {
            List<Contact> tasks = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tablename, null);
            if (cursor.moveToFirst()) {
                do {
                    Contact task = new Contact();
                    task.setName(cursor.getString(0));
                    task.setNumber(cursor.getString(1));
                    tasks.add(task);
                } while (cursor.moveToNext());
            }
            sqLiteDatabase.close();
            return tasks;
        } catch (Exception e) {
            return null;

        }

    }
    public  boolean deleteTask(List<String> strings)
    {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();




            long r = 0;
            for(int i = 0; i < strings.size(); i++)
            {

               r= sqLiteDatabase.delete(tablename,"name ='"+ strings.get(i) +"'",null);
            }
           sqLiteDatabase.close();
           return r > 0;
        }
        catch (SQLException e)
        {
            Log.v("exc",e.toString());
            return false;

        }
    }
    Contact getContact(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(tablename, new String[] { "name",
                        "number", "json" }, "name" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
Contact contact=new Contact();
contact.setName(cursor.getString(0));
        contact.setNumber(cursor.getString(1));
        contact.setJson(cursor.getString(2));


        // return contact
        return contact;
    }
}

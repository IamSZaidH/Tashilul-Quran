package com.zaid.tashilulquran;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDatabaseHandler extends SQLiteOpenHelper {
    private static final String db_name = "SyedZaidHusain";
    private static final String table_name = "Bookmark";
    private static final String id = "bookmarkID";
    private static final String title= "SyedZaidHusain";
    private static final String para_No= "para_no";
    private static final String page_no= "page_no";


    public MyDatabaseHandler(Context context) {
        super(context, db_name, null, 1);
    }
// create table tablename(id )
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE "+table_name+" (" +
                id + " INTEGER PRIMARY KEY autoincrement," +
                title + " TEXT," +
                para_No + " TEXT," +
                page_no + " INTEGER" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void addBookmark(int page_no,String para_no,String title){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(MyDatabaseHandler.title,title);
        cv.put(MyDatabaseHandler.para_No,para_no);
        cv.put(MyDatabaseHandler.page_no,page_no);
        db.insert(table_name,null,cv);
    }

    public ArrayList<BookmarkModel> getAllBookmark(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor =db.rawQuery("select * from "+table_name,null);
        ArrayList<BookmarkModel> bookmarks=new ArrayList<>();
        while (cursor.moveToNext()){
            BookmarkModel bookmarkModel =new BookmarkModel();
            bookmarkModel.setId(cursor.getInt(0));
            bookmarkModel.setTitle(cursor.getString(1));
            bookmarkModel.setPage_no(cursor.getInt(3));
            bookmarkModel.setPara_no(cursor.getString(2));
            bookmarks.add(bookmarkModel);
        }
        cursor.close();
        return bookmarks;

    }
    public void deleteBookmark(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(table_name, MyDatabaseHandler.id +" = ? ",new String[]{String.valueOf(id)});
    }
}

package com.example.rupali.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RUPALI on 20-02-2018.
 */

public class NotesOpenHelper extends SQLiteOpenHelper {
    private static NotesOpenHelper openHelper;

    public static NotesOpenHelper getOpenHelper(Context context) {
        if (openHelper==null){
            openHelper=new NotesOpenHelper(context.getApplicationContext());
        }
        return openHelper;
    }

    private NotesOpenHelper(Context context) {
        super(context,Contract.DB_NAME, null, Contract.VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String notesql="CREATE TABLE "+Contract.Notes.TABLE_NAME+" ( "+Contract.Notes.ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                Contract.Notes.TITLE+" TEXT , "+Contract.Notes.DESCRIPTION+" TEXT )";
        sqLiteDatabase.execSQL(notesql);
        String commentSql="CREATE TABLE "+Contract.Comments.TABLE_NAME+" ( "+Contract.Comments.COMMENT_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT , "
                +Contract.Comments.COMMENT+" TEXT, "+Contract.Comments.NOTE_ID+ " INTEGER , FOREIGN KEY ( "+Contract.Comments.NOTE_ID+" ) " +
                "REFERENCES "+Contract.Notes.TABLE_NAME+" ( "+Contract.Notes.ID+" ) ON DELETE CASCADE )";
        sqLiteDatabase.execSQL(commentSql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

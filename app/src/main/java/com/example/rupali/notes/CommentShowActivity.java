package com.example.rupali.notes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CommentShowActivity extends AppCompatActivity {
    NotesOpenHelper openHelper;
    ListView commentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_show);
        commentView=findViewById(R.id.commentshowListview);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        ArrayList<String> comments=new ArrayList<>();
        openHelper=NotesOpenHelper.getOpenHelper(this);

        comments=fetchCommentsFromDb(bundle);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,comments);
        commentView.setAdapter(arrayAdapter);

    }
    private ArrayList<String> fetchCommentsFromDb(Bundle bundle){
        ArrayList<String> arrayList=new ArrayList<>();
        int noteId=bundle.getInt(Contract.Notes.ID);
        SQLiteDatabase database=openHelper.getReadableDatabase();
        String []selectionArgs={noteId+""};
        String []selectionCols={Contract.Comments.COMMENT+""};
        Cursor cursor=database.query(Contract.Comments.TABLE_NAME,selectionCols,Contract.Comments.NOTE_ID+"=?",selectionArgs,null,null,null);
        while (cursor.moveToNext()){
            String comment=cursor.getString(cursor.getColumnIndex(Contract.Comments.COMMENT));
            arrayList.add(comment);
        }
        return arrayList;

    }
}

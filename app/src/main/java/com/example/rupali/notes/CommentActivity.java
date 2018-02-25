package com.example.rupali.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {
    Bundle bundle;
    Button saveComment;
    EditText commentEditText;
    NotesOpenHelper openHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        saveComment=findViewById(R.id.commentSave);
        saveComment.setOnClickListener(this);
        commentEditText=findViewById(R.id.commentEditText);
        openHelper=NotesOpenHelper.getOpenHelper(this);
        Intent intent=getIntent();
        bundle=intent.getExtras();
    }

    @Override
    public void onClick(View view) {
        String comment=commentEditText.getText().toString();
        if(comment.isEmpty()){
            Toast.makeText(this,"Comment can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        SQLiteDatabase database=openHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        int noteId=bundle.getInt(Contract.Notes.ID);
        contentValues.put(Contract.Comments.COMMENT,comment);
        contentValues.put(Contract.Comments.NOTE_ID,noteId);
        int commentId= (int) database.insert(Contract.Comments.TABLE_NAME,null,contentValues);
        finish();

    }
}

package com.example.rupali.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity implements View.OnClickListener{
    Button saveButton;
    EditText titleEditText;
    EditText descriptionEditText;
    NotesOpenHelper openHelper;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        saveButton=findViewById(R.id.saveButton);
        titleEditText=findViewById(R.id.titleEditText);
        descriptionEditText=findViewById(R.id.descriptionEditText);
        openHelper=NotesOpenHelper.getOpenHelper(this);
        saveButton.setOnClickListener(this);
        Intent intent=getIntent();
        bundle=intent.getExtras();
        if(bundle!=null){
           populateDataFromBundle(bundle);
        }
        else {
            bundle=new Bundle();
        }

    }

    private void populateDataFromBundle(Bundle bundle) {
        int id=bundle.getInt(Contract.Notes.ID);
        SQLiteDatabase database=openHelper.getReadableDatabase();
        String []selectionArgs={id+""};
        Cursor cursor=database.query(Contract.Notes.TABLE_NAME,null,Contract.Notes.ID+"=?",selectionArgs,null,null,null);
        cursor.moveToFirst();
        String title=cursor.getString(cursor.getColumnIndex(Contract.Notes.TITLE));
        String des=cursor.getString(cursor.getColumnIndex(Contract.Notes.DESCRIPTION));
        titleEditText.setText(title);
        descriptionEditText.setText(des);

    }

    @Override
    public void onClick(View view) {
        String title=titleEditText.getText().toString();
        String description=descriptionEditText.getText().toString();
        if(title.isEmpty()){
            Toast.makeText(this,"Title can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(description.isEmpty()){
            Toast.makeText(this,"Description can't be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        SQLiteDatabase database=openHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Contract.Notes.TITLE,title);
        contentValues.put(Contract.Notes.DESCRIPTION,description);
        if(bundle.isEmpty()) {
            int id = (int) database.insert(Contract.Notes.TABLE_NAME, null, contentValues);
            bundle.putInt(Contract.Notes.ID,id);
        }
        else {
            int id=bundle.getInt(Contract.Notes.ID);
            String []selectionArgs={id+""};
            //id = database.update(Contract.Notes.TABLE_NAME, contentValues, Contract.Notes.ID + "=?",selectionArgs);
            database.update(Contract.Notes.TABLE_NAME, contentValues, Contract.Notes.ID + "=?",selectionArgs);

        }
        Intent intent=new Intent();
        intent.putExtras(bundle);
        setResult(Constants.SAVE_RESULT_CODE,intent);
        finish();
    }
}

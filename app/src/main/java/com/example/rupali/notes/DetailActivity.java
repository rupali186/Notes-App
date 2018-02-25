package com.example.rupali.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    TextView titleTextView;
    TextView descriptionTextView;
    Button commentShowButton;
    NotesOpenHelper openHelper;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        titleTextView=findViewById(R.id.detailActivityTitle);
        descriptionTextView=findViewById(R.id.detailActivityDescription);
        commentShowButton=findViewById(R.id.showCommentButton);
        commentShowButton.setOnClickListener(this);
        openHelper=NotesOpenHelper.getOpenHelper(this);
        Intent intent=getIntent();
        bundle=intent.getExtras();
        if(bundle!=null){
           populateDataFromBundle(bundle);
        }
        else {
            bundle=new Bundle();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.edit){
            Intent intent=new Intent(this,FormActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent,Constants.EDIT_ACTIVITY_REQUEST_CODE);
        }
        else if(item.getItemId()==R.id.add_comment){
            Intent intent=new Intent(this,CommentActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Constants.EDIT_ACTIVITY_REQUEST_CODE&&resultCode==Constants.SAVE_RESULT_CODE) {
            bundle = data.getExtras();
            if (bundle != null) {
                populateDataFromBundle(bundle);
                Intent intent=new Intent();
                intent.putExtras(bundle);
                setResult(Constants.DETAIL_ACTIVITY_RESULT_CODE,intent);
            }
        }
    }

    private void populateDataFromBundle(Bundle bundle) {
        int id=bundle.getInt(Contract.Notes.ID);
        SQLiteDatabase database=openHelper.getReadableDatabase();
        String []strArgs={id+""};
        Cursor cursor=database.query(Contract.Notes.TABLE_NAME,null,Contract.Notes.ID+"=?",strArgs,null,null,null);
        cursor.moveToFirst();
        String title=cursor.getString(cursor.getColumnIndex(Contract.Notes.TITLE));
        String des=cursor.getString(cursor.getColumnIndex(Contract.Notes.DESCRIPTION));
        titleTextView.setText(title);
        descriptionTextView.setText(des);

    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,CommentShowActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

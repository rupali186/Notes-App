package com.example.rupali.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    ListView listView;
    ArrayList<Note> noteArrayList;
    NoteAdapter adapter;
    NotesOpenHelper openHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView=findViewById(R.id.listView);
        openHelper=NotesOpenHelper.getOpenHelper(this);
        noteArrayList=fetchNotesFromDb();
        adapter=new NoteAdapter(this,noteArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    private ArrayList<Note> fetchNotesFromDb() {
        ArrayList<Note> arrayList=new ArrayList<Note>();
        SQLiteDatabase database=openHelper.getReadableDatabase();
        Cursor cursor=database.query(Contract.Notes.TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String title=cursor.getString(cursor.getColumnIndex(Contract.Notes.TITLE));
            String description=cursor.getString(cursor.getColumnIndex(Contract.Notes.DESCRIPTION));
            int id=cursor.getInt(cursor.getColumnIndex(Contract.Notes.ID));
            Note note=new Note(title,description,id);
            arrayList.add(note);
        }
        return arrayList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add){
            Intent intent =new Intent(this,FormActivity.class);
            startActivityForResult(intent,Constants.ADD_ACTIVITY_REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Constants.ADD_ACTIVITY_REQUEST_CODE&&resultCode==Constants.SAVE_RESULT_CODE) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
              fetchDataFromBundle(bundle);
            }
        }
        else if(requestCode==Constants.DETAIL_ACTIVITY_REQUEST_CODE&&resultCode==Constants.DETAIL_ACTIVITY_RESULT_CODE){
            Bundle bundle = data.getExtras();
            if (bundle != null) {
//                noteArrayList.clear();
//                fetchNotesFromDb();
//                adapter.notifyDataSetChanged();
                populateDataFromBundle(bundle);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void populateDataFromBundle(Bundle bundle) {
        int id=bundle.getInt(Contract.Notes.ID);
        SQLiteDatabase database=openHelper.getReadableDatabase();
        String []selectionArgs={id+""};
        Cursor cursor=database.query(Contract.Notes.TABLE_NAME,null,Contract.Notes.ID+"=?",selectionArgs,null,null,null);
        cursor.moveToFirst();
        String title=cursor.getString(cursor.getColumnIndex(Contract.Notes.TITLE));
        String description=cursor.getString(cursor.getColumnIndex(Contract.Notes.DESCRIPTION));
        Note note=new Note(title,description,id);
        int position=findPositionFromArrayList(id);
        noteArrayList.set(position,note);
        adapter.notifyDataSetChanged();

    }

    private int findPositionFromArrayList(int id) {
        int position=-1;
        for(int i=0;i<noteArrayList.size();i++){
            if(noteArrayList.get(i).getId()==id){
                position=i;
                return position;
            }
        }
        return position;
    }

    private void fetchDataFromBundle(Bundle bundle) {
        int id=bundle.getInt(Contract.Notes.ID);
        SQLiteDatabase database=openHelper.getReadableDatabase();
        String []selectionArgs={id+""};
        Cursor cursor=database.query(Contract.Notes.TABLE_NAME,null,Contract.Notes.ID+" =?",selectionArgs,null,null,null);
        cursor.moveToFirst();
        String title=cursor.getString(cursor.getColumnIndex(Contract.Notes.TITLE));
        String description=cursor.getString(cursor.getColumnIndex(Contract.Notes.DESCRIPTION));
        Note note=new Note(title,description,id);
        noteArrayList.add(note);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,DetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt(Contract.Notes.ID,noteArrayList.get(position).getId());
        intent.putExtras(bundle);
        startActivityForResult(intent,Constants.DETAIL_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you really want to delete?");
        builder.setTitle("Confirm Delete");
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int id=noteArrayList.get(pos).getId();
                noteArrayList.remove(pos);
                adapter.notifyDataSetChanged();
                SQLiteDatabase database=openHelper.getWritableDatabase();
                String []selectionArgs={id+""};
                id=database.delete(Contract.Notes.TABLE_NAME,Contract.Notes.ID+"=?",selectionArgs);
                dialogInterface.dismiss();
            }
        });
        builder.show();
        return true;
    }
}

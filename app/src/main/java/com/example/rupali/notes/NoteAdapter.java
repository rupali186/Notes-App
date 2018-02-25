package com.example.rupali.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by RUPALI on 20-02-2018.
 */

public class NoteAdapter extends BaseAdapter {
    ArrayList<Note> arrayList;
    Context context;
    public NoteAdapter(Context context, ArrayList<Note> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view=convertView;
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, parent, false);
            ViewHolder holder=new ViewHolder();
            holder.titleTextView=view.findViewById(R.id.titleTextView);
            holder.descriptionTextView=view.findViewById(R.id.descriptionTetView);
            view.setTag(holder);
        }
        ViewHolder holder= (ViewHolder) view.getTag();
        Note note=(Note)getItem(i);
        holder.titleTextView.setText(note.getTitle());
        holder.descriptionTextView.setText(note.getDescription());
        return view;
    }
    class ViewHolder{
        TextView titleTextView;
        TextView descriptionTextView;
    }
}

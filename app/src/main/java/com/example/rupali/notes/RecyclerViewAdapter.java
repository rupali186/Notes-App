package com.example.rupali.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by RUPALI on 18-03-2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.UserViewHolder> {
    interface OnItemClickListener{
        void onItemClick(int position);
    }
    interface OnItemLongClickListener{
        boolean onItemLongClick(int position);
    }
    Context context;
    ArrayList<Note> notes;
    OnItemClickListener listener;
    OnItemLongClickListener longClickListener;

    public RecyclerViewAdapter(Context context, ArrayList<Note> notes, OnItemClickListener listener,OnItemLongClickListener longClickListener) {
        this.context = context;
        this.notes = notes;
        this.listener = listener;
        this.longClickListener=longClickListener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.list_item,parent,false);
        UserViewHolder holder=new UserViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {
        Note note=notes.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.descriptionTextView.setText(note.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return longClickListener.onItemLongClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        TextView descriptionTextView;
        View itemView;

        public UserViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            titleTextView=itemView.findViewById(R.id.titleTextView);
            descriptionTextView=itemView.findViewById(R.id.descriptionTetView);
        }
    }
}

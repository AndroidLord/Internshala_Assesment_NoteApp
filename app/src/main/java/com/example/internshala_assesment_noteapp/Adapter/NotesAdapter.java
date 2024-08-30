package com.example.internshala_assesment_noteapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshala_assesment_noteapp.Model.NotesEntity;
import com.example.internshala_assesment_noteapp.R;

import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {


    List<NotesEntity> notesEntities;
    OnItemSelectedListener itemSelectedListener;
    Context context;
    public NotesAdapter(Context context, List<NotesEntity> notesEntities) {
        this.notesEntities = notesEntities;
        this.context = context;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener itemSelectedListener)
    {
        this.itemSelectedListener = itemSelectedListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NotesEntity  notesEntity = notesEntities.get(position);

        holder.title.setText(notesEntity.notesTitle);
        holder.notes.setText(notesEntity.notes);
        holder.date.setText(notesEntity.notesDate);

        if (notesEntity.isBookmarked) {
            holder.fvrtNote.setColorFilter(context.getResources().getColor(R.color.red));
        }

        holder.deleteNote.setOnClickListener(view -> {
            if(itemSelectedListener!=null)
            {
                itemSelectedListener.onNoteDelete(notesEntity.id);
            }
        });

        holder.fvrtNote.setOnClickListener(view -> {
                itemSelectedListener.onNoteBookmark(notesEntity.id, notesEntity.isBookmarked);
        });



        holder.itemView.setOnClickListener(view -> {
            if(itemSelectedListener!=null)
            {
                Log.d("TAG", "onBindViewHolder: itemSelected: " + itemSelectedListener);
                Log.d("TAG", "onBindViewHolder: Note: " + notesEntity.notesTitle);
                itemSelectedListener.onNoteClicked(notesEntity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesEntities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,notes,date;
        ImageView deleteNote, fvrtNote;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.itemTitle);
            notes =  itemView.findViewById(R.id.itemNote);
            date = itemView.findViewById(R.id.itemDate);
            deleteNote = itemView.findViewById(R.id.deleteNote);
            fvrtNote = itemView.findViewById(R.id.fvrtNote);
        }
    }

    public interface OnItemSelectedListener{
        public void onNoteClicked(NotesEntity notesEntity);
        public void onNoteDelete(int noteId);
        public void onNoteBookmark(int noteId, boolean isBookmarked);

    }
}

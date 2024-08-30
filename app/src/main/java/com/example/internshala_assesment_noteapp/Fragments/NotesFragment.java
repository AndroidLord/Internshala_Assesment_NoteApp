package com.example.internshala_assesment_noteapp.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshala_assesment_noteapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.internshala_assesment_noteapp.Adapter.NotesAdapter;
import com.example.internshala_assesment_noteapp.Model.NotesEntity;
import com.example.internshala_assesment_noteapp.ViewModel.NotesViewModel;


public class NotesFragment extends Fragment implements NotesAdapter.OnItemSelectedListener {
    FloatingActionButton newNotebutton;
    NotesViewModel notesViewModel;
    RecyclerView recyclerView;
    NotesAdapter adapter;
    NotesEntity notesEntity, notesEntity1;

    FragmentManager fragmentManager;

    public NotesFragment() {
        // Required empty public constructor
    }

    public NotesFragment(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        newNotebutton = view.findViewById(R.id.newnotebtn);
        recyclerView = view.findViewById(R.id.recyclerview);

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        TextView notesText = view.findViewById(R.id.notesTxt);

        newNotebutton.setOnClickListener(v -> {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new CreateNoteFragment(fragmentManager)).addToBackStack(null).commit();
        });

        notesViewModel.getAllNotes.observe(getViewLifecycleOwner(), notesEntities -> {

            if (notesEntities.size() == 0) {
                notesText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Log.d("NotesFrag", "onCreateView: The notes is empty");
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                notesText.setVisibility(View.GONE);
                Log.d("NotesFrag", "onCreateView: The notes is not empty");
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            adapter = new NotesAdapter(getContext(), notesEntities);
            adapter.setOnItemSelectedListener(this);
            recyclerView.setAdapter(adapter);


        });

        return view;
    }

    @Override
    public void onNoteClicked(NotesEntity notesEntity) {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new CreateNoteFragment(notesEntity)).addToBackStack(null).commit();
    }

    @Override
    public void onNoteDelete(int id) {
        notesViewModel.deleteNotes(id);
        Toast.makeText(getContext(), "Note Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoteBookmark(int noteId, boolean isBookmarked) {

        if(noteId <= 0) {
            Toast.makeText(getContext(), "Invalid note ID", Toast.LENGTH_SHORT).show();
            return;
        }
        if(isBookmarked) {
            notesViewModel.updateBookmarkStatus(noteId, false);
            return;
        }

        notesViewModel.updateBookmarkStatus(noteId, true);
    }
}
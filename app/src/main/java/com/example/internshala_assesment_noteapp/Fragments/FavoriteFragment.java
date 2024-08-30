package com.example.internshala_assesment_noteapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internshala_assesment_noteapp.Adapter.NotesAdapter;
import com.example.internshala_assesment_noteapp.Database.SessionManager;
import com.example.internshala_assesment_noteapp.Model.NotesEntity;
import com.example.internshala_assesment_noteapp.R;
import com.example.internshala_assesment_noteapp.ViewModel.NotesViewModel;


public class FavoriteFragment extends Fragment implements  NotesAdapter.OnItemSelectedListener {

    NotesViewModel notesViewModel;
    RecyclerView recyclerView;
    TextView notesText;
    NotesAdapter adapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        notesText = view.findViewById(R.id.emptyFavoriteTxt);
        recyclerView = view.findViewById(R.id.recyclerviewFavorite);
        SessionManager sessionManager = new SessionManager(requireContext());
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        notesViewModel.getBookmarkedNotes(
                sessionManager.getUserId()
        ).observe(getViewLifecycleOwner(), notesEntities -> {

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
        // show toast that favorite notes can't be edited
        Toast.makeText(getContext(), "Favorite notes can't be edited in this screen", Toast.LENGTH_SHORT).show();
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
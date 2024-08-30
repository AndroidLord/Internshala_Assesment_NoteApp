package com.example.internshala_assesment_noteapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.internshala_assesment_noteapp.Model.NotesEntity;
import com.example.internshala_assesment_noteapp.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository repository;
    public LiveData<List<NotesEntity>> getAllNotes;
    public NotesViewModel(@NonNull Application application) {
        super(application);

        repository = new NotesRepository(application);

        getAllNotes = repository.getAllNotes();

    }



    public void insertNotes(NotesEntity notes)
    {
        repository.insertNotes(notes);

    }


   public  void deleteNotes(int id)
    {
        repository.deleteNotes(id);

    }


   public  void updateNotes(NotesEntity notes)
    {
        repository.updateNotes(notes);

    }

    // Bookmarked Notes
    public void updateBookmarkStatus(int id, boolean isBookmarked) {
        repository.updateBookmarkStatus(id, isBookmarked);
    }

    public LiveData<List<NotesEntity>> getBookmarkedNotes(String userId) {
        return repository.getBookmarkedNotes(userId);
    }
}

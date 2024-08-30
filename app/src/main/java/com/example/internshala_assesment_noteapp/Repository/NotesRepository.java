package com.example.internshala_assesment_noteapp.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.internshala_assesment_noteapp.Dao.NotesDao;
import com.example.internshala_assesment_noteapp.Database.NotesDatabase;
import com.example.internshala_assesment_noteapp.Model.NotesEntity;
import com.example.internshala_assesment_noteapp.Database.SessionManager;

import java.util.List;

public class NotesRepository {

    private final NotesDao notesDao;
    private final LiveData<List<NotesEntity>> allNotes;

    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDao = database.notesDao();
        SessionManager sessionManager = new SessionManager(application);
        allNotes = notesDao.getAllNotes(sessionManager.getUserId());
    }

    public LiveData<List<NotesEntity>> getAllNotes() {
        return allNotes;
    }

    public void insertNotes(NotesEntity notes) {
        NotesDatabase.databaseWriteExecutor.execute(() -> {
            notesDao.insertNotes(notes);
        });
    }

    public void deleteNotes(int id) {
        NotesDatabase.databaseWriteExecutor.execute(() -> {
            notesDao.deleteNotes(id);
        });
    }

    public void updateNotes(NotesEntity notes) {
        NotesDatabase.databaseWriteExecutor.execute(() -> {
            notesDao.updateNotes(notes);
        });
    }

    // Bookmarked Notes

    public void updateBookmarkStatus(int id, boolean isBookmarked) {
        NotesDatabase.databaseWriteExecutor.execute(() -> {
            notesDao.updateBookmarkStatus(id, isBookmarked);
        });
    }

    public LiveData<List<NotesEntity>> getBookmarkedNotes(String userId) {
        return notesDao.getBookmarkedNotes(userId);
    }

}

package com.example.internshala_assesment_noteapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.internshala_assesment_noteapp.Model.NotesEntity;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM `Notes Database` where note_Uid = :userId")
    LiveData<List<NotesEntity>> getAllNotes(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotes(NotesEntity... notes);

    @Query("DELETE FROM `Notes Database` WHERE id = :id")
    void deleteNotes(int id);

    @Update
    void updateNotes(NotesEntity notes);


    // Bookmarked Notes
    @Query("SELECT * FROM `Notes Database` WHERE note_Uid = :userId AND is_bookmarked = 1")
    LiveData<List<NotesEntity>> getBookmarkedNotes(String userId);

    @Query("UPDATE `Notes Database` SET is_bookmarked = :isBookmarked WHERE id = :id")
    void updateBookmarkStatus(int id, boolean isBookmarked);
}

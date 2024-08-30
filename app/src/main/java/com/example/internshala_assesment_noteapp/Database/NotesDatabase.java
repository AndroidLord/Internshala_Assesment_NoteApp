package com.example.internshala_assesment_noteapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.internshala_assesment_noteapp.Dao.NotesDao;
import com.example.internshala_assesment_noteapp.Model.NotesEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {NotesEntity.class},version = 1,exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();
    public static NotesDatabase  INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized NotesDatabase getDatabaseInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotesDatabase.class, "Notes Database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

}
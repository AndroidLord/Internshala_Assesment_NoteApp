package com.example.internshala_assesment_noteapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes Database")
public class NotesEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "note_Uid")
    public String userId;

    @ColumnInfo(name = "notes_title")
    public String notesTitle;

    @ColumnInfo(name = "notes")
    public String notes;

    @ColumnInfo(name = "notes_Date")
    public String notesDate;

    @ColumnInfo(name = "notes_priority")
    public String notesPriority;

    @ColumnInfo(name = "is_bookmarked", defaultValue = "0")
    public boolean isBookmarked = false;

}

package com.example.notemvvm.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notemvvm.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Note")
    List<Note> getAllNotes();

    @Insert
    void insertNote(Note... notes);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);
}

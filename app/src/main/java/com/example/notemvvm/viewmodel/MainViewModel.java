package com.example.notemvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notemvvm.db.NoteDatabase;
import com.example.notemvvm.model.Note;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

//    private NoteRepository repository;
//    private LiveData<List<Note>> mNoteAll;
//
//    public MainViewModel (Application application) {
//        super(application);
//        repository = new NoteRepository(application);
//        mNoteAll = repository.getAllNote();
//    }
//
//    LiveData<List<Note>> getAllNotes() {
//        return mNoteAll;
//    }
//
//    public void insert(Note note) {
//        repository.insert(note);
//    }

    private MutableLiveData<List<Note>> listOfNote;
    private NoteDatabase noteDatabase;

    public MainViewModel(@NonNull Application application) {
        super(application);

        listOfNote = new MutableLiveData<>();

        noteDatabase = NoteDatabase.getDatabase(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Note>> getNoteListObserver() {
        return listOfNote;
    }

    public void getAllNote() {
        List<Note> noteList = noteDatabase.noteDao().getAllNotes();
        if (noteList.size() > 0) {
            listOfNote.postValue(noteList);
        } else {
            listOfNote.postValue(null);
        }
    }

    public void insertNote(String title, String describe) {
        Note note = new Note();
        note.title = title;
        note.description = describe;

        noteDatabase.noteDao().insertNote(note);
        getAllNote();
    }

    public void updateNote(Note note) {
        noteDatabase.noteDao().updateNote(note);
        getAllNote();
    }

    public void deleteNote(Note note) {
        noteDatabase.noteDao().deleteNote(note);
        getAllNote();
    }
}

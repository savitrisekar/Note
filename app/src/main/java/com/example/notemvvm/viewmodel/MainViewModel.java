package com.example.notemvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.example.notemvvm.BR;
import com.example.notemvvm.db.NoteDatabase;
import com.example.notemvvm.model.Note;

import java.util.List;

public class MainViewModel extends ObservableViewModel {

    public static final String EMPTY_DATA = "empty_data";
    private MutableLiveData<List<Note>> listOfNote;
    private NoteDatabase noteDatabase;
    private ViewListener listener;
    private String state = "";

    public Note note = new Note();
    private Note oldNote = new Note();

    private boolean isEditMode = false;

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

    public void refreshData() {
        getAllNote();
    }

    @Bindable
    public boolean isEditMode() {
        return isEditMode;
    }

    @Bindable
    public boolean isEmptyInput() {
        return state.equals(EMPTY_DATA);
    }

    public void processData() {
        if (!note.isEmpty()) {
            if (oldNote.isEmpty()) {
                this.insertNote(note);
            } else {
                this.updateNote(note);
            }
        } else {
            state = EMPTY_DATA;
            notifyPropertyChanged(BR.emptyInput);
        }
    }

    public void insertNote(Note note) {
        noteDatabase.noteDao().insertNote(note);
        refreshData();
    }

    public void updateNote(Note note) {
        noteDatabase.noteDao().updateNote(note);
        refreshData();
    }

    public void deleteNote(Note note) {
        noteDatabase.noteDao().deleteNote(note);
        refreshData();
    }

    public void setNote(Note note) {
        this.oldNote = note;
        this.note = note;
        isEditMode = !note.isEmpty();
        notifyPropertyChanged(BR.editMode);
    }

    public interface ViewListener {
        void notify(String string);
    }
}

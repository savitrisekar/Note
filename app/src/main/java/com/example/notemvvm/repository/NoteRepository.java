package com.example.notemvvm.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.notemvvm.db.NoteDao;
import com.example.notemvvm.db.NoteDatabase;
import com.example.notemvvm.model.Note;

import java.util.List;

//public class NoteRepository {
//
//    private NoteDao mNoteDao;
//    private LiveData<List<Note>> mNoteAll;
//
//    public NoteRepository(Application application) {
//        NoteDatabase database = NoteDatabase.getDatabase(application);
//        mNoteDao = database.noteDao();
//        mNoteAll = mNoteDao.getAllNotes();
//    }
//
//    LiveData<List<Note>> getAllNote() {
//        return mNoteAll;
//    }
//
//    public void insert(Note note) {
//        new insertAsyncTask(mNoteDao).execute(note);
//    }
//
//    private static class insertAsyncTask extends AsyncTask<Note, Void, Void> {
//
//        private NoteDao asyncTaskDao;
//
//        insertAsyncTask(NoteDao noteDao) {
//            asyncTaskDao = noteDao;
//        }
//
//        @Override
//        protected Void doInBackground(final Note... notes) {
//            asyncTaskDao.insert(notes[0]);
//            return null;
//        }
//    }
//}

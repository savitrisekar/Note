package com.example.notemvvm.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.notemvvm.R;
import com.example.notemvvm.adapter.MainAdapter;
import com.example.notemvvm.databinding.ActivityAddDialogBinding;
import com.example.notemvvm.databinding.ActivityMainBinding;
import com.example.notemvvm.db.NoteDatabase;
import com.example.notemvvm.model.Note;
import com.example.notemvvm.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.HandleNoteClick {

    //    private MaterialToolbar toolbar;
    ActivityMainBinding mainBinding;
    ActivityAddDialogBinding addDialogBinding;
    private MainViewModel viewModel;
    private MainAdapter mainAdapter;
    private AlertDialog addDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();
        addDialog = new AlertDialog.Builder(this).create();
        addDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_add_dialog, null, false);
        addDialog.setView(addDialogBinding.getRoot());

        mainBinding.fabAddNote.setOnClickListener(view1 -> {
//                startActivity(new Intent(MainActivity.this, AddActivity.class));
            viewModel.setNote(new Note());
            addDialog.show();
        });

        initViewModel();
        initRecycler();
    }

    private void initRecycler() {
        mainAdapter = new MainAdapter(this, this);
        mainBinding.rvItemNote.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.rvItemNote.setAdapter(mainAdapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getNoteListObserver().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (notes == null) {
                    mainBinding.tvEmpty.setVisibility(View.VISIBLE);
                    mainBinding.rvItemNote.setVisibility(View.GONE);
                } else {
                    //show in recyclerView
                    mainAdapter.setNoteList(notes);
                    mainBinding.rvItemNote.setVisibility(View.VISIBLE);
                    mainBinding.tvEmpty.setVisibility(View.GONE);
                }
            }
        });

        addDialogBinding.setViewModel(viewModel);
    }

    @Override
    public void editClick(Note note) {
        viewModel.setNote(note);
        addDialog.show();
    }

    @Override
    public void removeClick(Note note) {
        viewModel.deleteNote(note);
    }

    @Override
    protected void onDestroy() {
        NoteDatabase.destroyInstance();
        super.onDestroy();
    }
}
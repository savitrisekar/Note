package com.example.notemvvm.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notemvvm.R;
import com.example.notemvvm.adapter.MainAdapter;
import com.example.notemvvm.databinding.ActivityMainBinding;
import com.example.notemvvm.databinding.ActivityAddDialogBinding;
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
    private Note noteEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();

        mainBinding.fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, AddActivity.class));
                showNote(false);
            }
        });

        initViewModel();
        initRecycler();
    }

    private void initRecycler() {
        mainBinding.rvItemNote.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(this, this);
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
    }

    private void showNote(boolean isEdited) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
//        addDialogBinding = ActivityAddDialogBinding.inflate(getLayoutInflater());
        addDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(dialog.getContext()),
                R.layout.activity_add_dialog, null, false);
        View view = addDialogBinding.getRoot();

        if (isEdited) {
            addDialogBinding.btnSave.setText("Update");
            addDialogBinding.edtTitleAdd.setText(noteEdit.title);
            addDialogBinding.edtDscAdd.setText(noteEdit.description);
        }

        addDialogBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        addDialogBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = addDialogBinding.edtTitleAdd.getText().toString();
                String describe = addDialogBinding.edtDscAdd.getText().toString();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(describe)) {
                    Toast.makeText(MainActivity.this, "Please fill your note", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isEdited) {
                    noteEdit.title = title;
                    noteEdit.description = describe;
                    //update viewModel
                    viewModel.updateNote(noteEdit);
                } else {
                    //insert viewModel
                    viewModel.insertNote(title, describe);
                }
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void editClick(Note note) {
        this.noteEdit = note;
        showNote(true);
    }

    @Override
    public void removeClick(Note note) {
        viewModel.deleteNote(note);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showNote(false);
    }

    @Override
    protected void onDestroy() {
        NoteDatabase.destroyInstance();
        super.onDestroy();
    }
}
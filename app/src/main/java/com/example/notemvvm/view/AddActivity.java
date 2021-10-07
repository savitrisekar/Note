package com.example.notemvvm.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.notemvvm.R;
import com.example.notemvvm.model.Note;
import com.example.notemvvm.viewmodel.MainViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AddActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TextInputEditText inputName, inputDsc;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

//        getSupportActionBar().hide();

        toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        inputName = findViewById(R.id.edt_title_add);
        inputDsc = findViewById(R.id.edt_dsc_add);

        initViewModel();

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getNoteListObserver().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (notes == null) {

                } else {

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //handle item selection
        switch (item.getItemId()) {
            case R.id.save:
                addNote();
                return true;
            case R.id.delete:
                deleteNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addNote() {
        String name = inputName.getText().toString();
        String describe = inputDsc.getText().toString();

        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(describe)) {
            Toast.makeText(this, "Please fill your note", Toast.LENGTH_SHORT).show();
            return;
        }

        //insert viewModel
    }

    private void deleteNote() {
        onBackPressed();
    }
}
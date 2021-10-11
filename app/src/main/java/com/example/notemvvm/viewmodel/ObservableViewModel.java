package com.example.notemvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

public class ObservableViewModel extends AndroidViewModel implements Observable {

    private final PropertyChangeRegistry registry = new PropertyChangeRegistry();

    public ObservableViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.remove(callback);
    }

    public void notifyChange() {
        registry.notifyCallbacks(this, 0, null);
    }

    public void notifyPropertyChanged(int fieldId) {
        registry.notifyCallbacks(this, fieldId, null);
    }
}

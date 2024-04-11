package com.gero.newpass.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gero.newpass.repository.ResourceRepository;
import com.gero.newpass.viewmodel.AddViewModel;

public class AddViewModelFactory implements ViewModelProvider.Factory {
    private ResourceRepository resourceRepository;

    public AddViewModelFactory(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddViewModel.class)) {
            return (T) new AddViewModel(resourceRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}


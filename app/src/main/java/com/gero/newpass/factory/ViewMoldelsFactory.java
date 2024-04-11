package com.gero.newpass.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gero.newpass.repository.ResourceRepository;
import com.gero.newpass.viewmodel.AddViewModel;
import com.gero.newpass.viewmodel.LoginViewModel;
import com.gero.newpass.viewmodel.UpdateViewModel;

public class ViewMoldelsFactory implements ViewModelProvider.Factory {
    private ResourceRepository resourceRepository;

    public ViewMoldelsFactory(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddViewModel.class)) {
            return (T) new AddViewModel(resourceRepository);
        }

        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(resourceRepository);
        }

        if (modelClass.isAssignableFrom(UpdateViewModel.class)) {
            return (T) new UpdateViewModel(resourceRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}


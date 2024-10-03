package com.example.foodrecipes;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.foodrecipes.Data.DataRepository;
import com.example.foodrecipes.Data.FoodList.FoodListViewModel;
import com.example.foodrecipes.Main.MainViewModel;
import com.example.foodrecipes.Recipe.RecipeViewModel;

public class Factory implements ViewModelProvider.Factory {

    DataRepository repo;

    public Factory(DataRepository repo) {
        this.repo = repo;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        if (modelClass.isAssignableFrom(MainViewModel.class)){
            return (T) new MainViewModel(repo);
        } else if (modelClass.isAssignableFrom(RecipeViewModel.class)) {
            return (T) new RecipeViewModel(repo);
        } else if (modelClass.isAssignableFrom(FoodListViewModel.class)) {
            return (T) new FoodListViewModel(repo);
        } else
            throw new IllegalArgumentException("ViewModel is not valid!");
    }
}

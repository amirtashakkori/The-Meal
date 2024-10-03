package com.example.foodrecipes.Recipe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodrecipes.Data.DataRepository;
import com.example.foodrecipes.Data.Models.Recipe;
import com.example.foodrecipes.Data.Models.RecipeResponse;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecipeViewModel extends ViewModel {

    DataRepository repo;
    MutableLiveData<Recipe> recipeInfo = new MutableLiveData<>();
    MutableLiveData<String> errors = new MutableLiveData<>();
    Disposable disposable;

    public RecipeViewModel(DataRepository repo) {
        this.repo = repo;
    }

    public void fetchRecipe(String id){
        repo.getRecipe(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RecipeResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(RecipeResponse recipeResponse) {
                        recipeInfo.setValue(recipeResponse.meals.get(0));
                    }

                    @Override
                    public void onError(Throwable e) {
                        errors.setValue(e + "");
                    }
                });
    }

    public LiveData<Recipe> getRecipe(){
        return recipeInfo;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();

    }
}

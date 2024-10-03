package com.example.foodrecipes.Data.FoodList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodrecipes.Data.DataRepository;
import com.example.foodrecipes.Data.Models.Food;
import com.example.foodrecipes.Data.Models.FoodResponse;
import com.example.foodrecipes.Data.Models.Recipe;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FoodListViewModel extends ViewModel {

    DataRepository repo;
    MutableLiveData<List<Food>> foods = new MutableLiveData<>();
    MutableLiveData<String> errors = new MutableLiveData<>();
    Disposable disposable;

    public FoodListViewModel(DataRepository repo) {
        this.repo = repo;
    }

    public void fetchFoods(String foodKind){
        repo.getFoods(foodKind)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FoodResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(FoodResponse foodResponse) {
                        foods.setValue(foodResponse.meals);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errors.setValue(e + "");
                    }
                });
    }

    public LiveData<List<Food>> getFoods(){
        return foods;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}

package com.example.foodrecipes.Main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodrecipes.Data.DataRepository;
import com.example.foodrecipes.Data.Models.Food;
import com.example.foodrecipes.Data.Models.FoodResponse;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    DataRepository repo;
    MutableLiveData<List<Food>> beefFoods = new MutableLiveData<>();
    MutableLiveData<List<Food>> chickenFoods = new MutableLiveData<>();
    MutableLiveData<List<Food>> seaFoods = new MutableLiveData<>();

    MutableLiveData<List<Food>> searchedFoods = new MutableLiveData<>();

    MutableLiveData<String> errors = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(DataRepository repo) {
        this.repo = repo;
        getFoods();
    }

    public void getFoods(){
        repo.getFoods("beef")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FoodResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(FoodResponse foods) {
                        beefFoods.setValue(foods.meals);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errors.setValue(e + "");
                    }
                });

        repo.getFoods("chicken")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FoodResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(FoodResponse foods) {
                        chickenFoods.setValue(foods.meals);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errors.setValue(e + "");
                    }
                });

        repo.getFoods("seafood")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FoodResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(FoodResponse foods) {
                        seaFoods.setValue(foods.meals);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errors.setValue(e + "");
                    }
                });


    }

    public void search(String query){
        repo.search(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FoodResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(FoodResponse foodResponse) {
                        if (foodResponse.meals != null){
                            searchedFoods.setValue(foodResponse.meals);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        errors.setValue(e + "");
                    }
                });
    }

    public LiveData<List<Food>> getBeefs(){
        return beefFoods;
    }

    public LiveData<List<Food>> getChickens(){
        return chickenFoods;
    }

    public LiveData<List<Food>> getSeaFoods(){
        return seaFoods;
    }

    public LiveData<List<Food>> getSearchedFoods(){
        return searchedFoods;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}

package com.example.foodrecipes.Data;

import androidx.lifecycle.LiveData;

import com.example.foodrecipes.Data.Models.Food;
import com.example.foodrecipes.Data.Api.ApiService;
import com.example.foodrecipes.Data.Models.FoodResponse;
import com.example.foodrecipes.Data.Models.Recipe;
import com.example.foodrecipes.Data.Models.RecipeResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DataRepository {

    public ApiService apiService;

    public DataRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Single<FoodResponse> getFoods(String mainIngredient){
        return apiService.getFoods(mainIngredient);
    }

    public Single<FoodResponse> search(String query){
        return apiService.search(query);
    }

    public Single<RecipeResponse> getRecipe(String id){
        return apiService.getFoodRecipe(id);
    }

}

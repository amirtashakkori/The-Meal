package com.example.foodrecipes.Data.Api;

import androidx.lifecycle.LiveData;

import com.example.foodrecipes.Data.Models.Category;
import com.example.foodrecipes.Data.Models.Food;
import com.example.foodrecipes.Data.Models.FoodResponse;
import com.example.foodrecipes.Data.Models.Recipe;
import com.example.foodrecipes.Data.Models.RecipeResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("categories.php")
    Single<List<Category>> getCategories();

    @GET("filter.php")
    Single<FoodResponse> getFoods(@Query("c") String category);

    @GET("search.php")
    Single<FoodResponse> search(@Query("s") String search);

    @GET("lookup.php")
    Single<RecipeResponse> getFoodRecipe(@Query("i") String id);

}

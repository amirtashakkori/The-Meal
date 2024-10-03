package com.example.foodrecipes.Recipe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodrecipes.Data.Api.ApiServiceProvider;
import com.example.foodrecipes.Data.DataRepository;
import com.example.foodrecipes.Data.Models.Recipe;
import com.example.foodrecipes.Factory;
import com.example.foodrecipes.R;
import com.squareup.picasso.Picasso;

public class RecipeActivity extends AppCompatActivity {

    RecipeViewModel recipeViewModel;

    LinearLayout anim_layout, info_layout;
    ImageView foodImg;
    TextView foodNameTv, tag1, tag2, ingredientTv, instructionsTv;
    CardView backBtn;

    String ingredients;

    public void cast(){
        anim_layout = findViewById(R.id.anim_layout);
        info_layout = findViewById(R.id.info_layout);
        foodImg = findViewById(R.id.foodImg);
        foodNameTv = findViewById(R.id.foodNameTv);
        tag1 = findViewById(R.id.tag1);
        tag2 = findViewById(R.id.tag2);
        ingredientTv = findViewById(R.id.ingredientTv);
        instructionsTv = findViewById(R.id.instructionsTv);
        backBtn = findViewById(R.id.backBtn);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cast();

        String id = getIntent().getStringExtra("recipeId");

        recipeViewModel = new ViewModelProvider(this, new Factory(
                new DataRepository(ApiServiceProvider.getApiService()))).get(RecipeViewModel.class);
        recipeViewModel.fetchRecipe(id);
        recipeViewModel.getRecipe().observe(this, recipe -> {
            anim_layout.setVisibility(View.GONE);
            info_layout.setVisibility(View.VISIBLE);
            setRecipe(recipe);
        });

        backBtn.setOnClickListener(view -> finish());

    }

    @SuppressLint("SetTextI18n")
    public void setRecipe(Recipe recipe){
        Picasso.get().load(recipe.getStrMealThumb()).into(foodImg);
        foodNameTv.setText(recipe.getStrMeal());
        tag1.setText(recipe.getStrCategory());
        tag2.setText(", " + recipe.getStrArea());
        ingredientTv.setText(getIngredients(recipe));
        instructionsTv.setText(recipe.getStrInstructions());
    }

    public String getIngredients(Recipe recipe){
        String ingredient1 = recipe.getStrMeasure1() + " " + recipe.getStrIngredient1();
        String ingredient2 = recipe.getStrMeasure2() + " " + recipe.getStrIngredient2();
        String ingredient3 = recipe.getStrMeasure3() + " " + recipe.getStrIngredient3();
        String ingredient4 = recipe.getStrMeasure4() + " " + recipe.getStrIngredient4();
        ingredients = ingredient1  + ", " +  ingredient2 + ", " +  ingredient3 + ", " +  ingredient4;
        String ingredient5, ingredient6, ingredient7;

        if (!recipe.getStrIngredient5().isEmpty()) {
            ingredient5 = recipe.getStrMeasure5() + " " + recipe.getStrIngredient5();
            ingredients = ingredients + ", " +  ingredient5;
        } if (!recipe.getStrIngredient6().isEmpty()) {
            ingredient6 = recipe.getStrMeasure6() + " " + recipe.getStrIngredient6();
            ingredients = ingredients + ", " +  ingredient6;
        } if (!recipe.getStrIngredient7().isEmpty()){
            ingredient7 = recipe.getStrMeasure7() + " " + recipe.getStrIngredient7();
            ingredients = ingredients + ", " +  ingredient7;
        }

        return ingredients;
    }

}
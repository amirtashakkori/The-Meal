package com.example.foodrecipes.Main;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipes.Data.Models.Food;
import com.example.foodrecipes.R;
import com.example.foodrecipes.Recipe.RecipeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.item> {

    Context c;
    List<Food> foods;
    boolean isGride = false;

    public FoodAdapter(Context c, List<Food> foods, boolean isGride) {
        this.c = c;
        this.foods = foods;
        this.isGride = isGride;
    }

    @NonNull
    @Override
    public item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new item(LayoutInflater.from(c).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull item holder, int position) {
        holder.bindFoods(foods.get(position));
    }

    @Override
    public int getItemCount() {
        if (isGride)
            return foods.size();
        else
            return 5;
    }

    public class item extends RecyclerView.ViewHolder{
        ConstraintLayout parent;
        ImageView foodImg;
        TextView nameTv;
        public item(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            foodImg = itemView.findViewById(R.id.foodImg);
            nameTv = itemView.findViewById(R.id.nameTv);
        }

        public void bindFoods(Food food){
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            if (isGride){
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            }

            Picasso.get().load(food.getStrMealThumb()).into(foodImg);
            nameTv.setText(food.getStrMeal());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(c, RecipeActivity.class);
                    intent.putExtra("recipeId", food.getIdMeal());
                    c.startActivity(intent);
                }
            });

        }

    }
}

package com.example.foodrecipes.Data.FoodList;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipes.Data.Api.ApiService;
import com.example.foodrecipes.Data.Api.ApiServiceProvider;
import com.example.foodrecipes.Data.DataRepository;
import com.example.foodrecipes.Factory;
import com.example.foodrecipes.Main.FoodAdapter;
import com.example.foodrecipes.R;

public class FoodListActivity extends AppCompatActivity {

    FoodListViewModel foodListViewModel;
    FoodAdapter adapter;

    LinearLayout anim_layout;
    ImageView backBtn;
    TextView foodKindTv;
    RecyclerView foodRv;

    String foodKind;

    public void cast(){
        anim_layout = findViewById(R.id.anim_layout);
        backBtn = findViewById(R.id.backBtn);
        foodKindTv = findViewById(R.id.foodKindTv);
        foodRv = findViewById(R.id.foodRv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cast();

        foodKind = getIntent().getStringExtra("foodKind");
        assert foodKind != null;
        foodKindTv.setText(foodKind.toUpperCase());

        backBtn.setOnClickListener(view -> finish());

        foodListViewModel = new ViewModelProvider(this, new Factory(
                new DataRepository(ApiServiceProvider.getApiService()))).get(FoodListViewModel.class);
        foodListViewModel.fetchFoods(foodKind);
        foodListViewModel.getFoods().observe(this, foods -> {
            foodRv.setLayoutManager(new GridLayoutManager(FoodListActivity.this, 2 , LinearLayoutManager.VERTICAL, false));
            adapter = new FoodAdapter(FoodListActivity.this, foods, true);
            anim_layout.setVisibility(View.GONE);
            foodRv.setVisibility(View.VISIBLE);
            foodRv.setAdapter(adapter);
        });

    }
}
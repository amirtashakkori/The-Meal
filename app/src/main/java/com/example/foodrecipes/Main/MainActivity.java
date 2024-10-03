package com.example.foodrecipes.Main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrecipes.Data.DataRepository;
import com.example.foodrecipes.Data.FoodList.FoodListActivity;
import com.example.foodrecipes.Factory;
import com.example.foodrecipes.Data.Api.ApiServiceProvider;
import com.example.foodrecipes.R;

public class MainActivity extends AppCompatActivity {

    MainViewModel mainViewModel;

    EditText searchEdt;
    LinearLayout homeScreen;
    RecyclerView searchRv, beefRv, chickenRv, seaRv;
    LinearLayout beefBtn, chickenBtn, seaBtn;
    FoodAdapter adapter;

    public void cast(){
        homeScreen = findViewById(R.id.homeScreen);
        searchEdt = findViewById(R.id.searchEdt);
        searchRv = findViewById(R.id.searchRv);
        beefRv = findViewById(R.id.beefRv);
        chickenRv = findViewById(R.id.chickenRv);
        seaRv = findViewById(R.id.seaRv);
        beefBtn = findViewById(R.id.beefBtn);
        chickenBtn = findViewById(R.id.chickenBtn);
        seaBtn = findViewById(R.id.seaBtn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cast();

        mainViewModel = new ViewModelProvider(this,
                new Factory(new DataRepository(ApiServiceProvider.getApiService()))).get(MainViewModel.class);

        getFoods();
        search();
        seeAllBtnClicked();
        onBackPress();

    }

    public void search(){
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString();
                if (!searchText.isEmpty()){
                    mainViewModel.search(searchText);
                    mainViewModel.getSearchedFoods().observe(MainActivity.this, foods -> {
                        if (!foods.isEmpty()){
                            searchRvVisibility(true);
                        } else
                            searchRvVisibility(true);
                        adapter = new FoodAdapter(MainActivity.this, foods, true);
                        searchRv.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                        searchRv.setAdapter(adapter);
                    });
                } else {
                    searchRvVisibility(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void getFoods(){
        mainViewModel.getBeefs().observe(this, foods -> {
            if (foods != null) {
                Log.d("LiveData", "Beef meals observed: " + foods.size());
                beefRv.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

                adapter = new FoodAdapter(MainActivity.this, foods, false);
                beefRv.setAdapter(adapter);
            } else {
                Log.d("LiveData", "No beef meals available");
            }});

        mainViewModel.getChickens().observe(this, foods -> {
            if (foods != null) {
                Log.d("LiveData", "Chicken meals observed: " + foods.size());
                chickenRv.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                adapter = new FoodAdapter(MainActivity.this, foods, false);
                chickenRv.setAdapter(adapter);
            } else {
                Log.d("LiveData", "No Chicken meals available");
            }});

        mainViewModel.getSeaFoods().observe(this, foods -> {
            if (foods != null) {
                Log.d("LiveData", "Seafood meals observed: " + foods.size());
                seaRv.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                adapter = new FoodAdapter(MainActivity.this, foods, false);
                seaRv.setAdapter(adapter);
            } else {
                Log.d("LiveData", "No Seafood meals available");
            }});
    }

    public void seeAllBtnClicked(){

        beefBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FoodListActivity.class);
            intent.putExtra("foodKind", "beef");
            startActivity(intent);
        });

        chickenBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FoodListActivity.class);
            intent.putExtra("foodKind", "chicken");
            startActivity(intent);
        });

        seaBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FoodListActivity.class);
            intent.putExtra("foodKind", "seafood");
            startActivity(intent);
        });


    }

    public void searchRvVisibility(boolean visible){
        if (visible){
            searchRv.setVisibility(View.VISIBLE);
            homeScreen.setVisibility(View.GONE);
        }else {
            searchRv.setVisibility(View.GONE);
            homeScreen.setVisibility(View.VISIBLE);
        }
    }

    public void onBackPress(){
        getOnBackPressedDispatcher().addCallback(MainActivity.this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(!searchEdt.getText().toString().isEmpty()){
                    searchEdt.clearFocus();
                    searchEdt.setText("");
                    searchRvVisibility(false);
                } else
                    finish();
            }
        });
    }

}
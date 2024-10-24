package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.adapters.DishAdapter;
import com.example.myapplication.models.Dish;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ListView menuListView = findViewById(R.id.menuListView);
        Intent intent = getIntent();
        String selected_cuisine= intent.getStringExtra("selectedCuisine");

        Toast.makeText(MenuActivity.this,selected_cuisine,Toast.LENGTH_SHORT).show();

        ArrayList<Dish> menus= new ArrayList<Dish>();

        menus.add(new Dish("Marocaine","Couscous", "A classic morracan pasta dish", "$50"));
        menus.add(new Dish("Marocaine","Tajine", "A classic morracan dish that has a wide range of use", "$50"));
        menus.add(new Dish("Orientale","test", "A classic test", "$50"));


        ArrayList<Dish> selected_menu = new ArrayList<>();
        for (Dish dish : menus){
            if(dish.getCuisine().equalsIgnoreCase(selected_cuisine)){
                selected_menu.add(dish);
            }
        }
        DishAdapter adapter= new DishAdapter(this,R.layout.menu_card,selected_menu);

        menuListView.setAdapter(adapter);

    }
}
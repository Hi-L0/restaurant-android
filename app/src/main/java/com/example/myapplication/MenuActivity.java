package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
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
        TextView cuisineName = findViewById(R.id.cuisineName);
        String selected_cuisine= intent.getStringExtra("selectedCuisine");
        cuisineName.setText(selected_cuisine);
        Toast.makeText(MenuActivity.this,selected_cuisine,Toast.LENGTH_SHORT).show();

        ArrayList<Dish> menus= new ArrayList<Dish>();

        menus.add(new Dish("Marocaine","Couscous", "A classic morracan pasta dish", "$50"));
        menus.add(new Dish("Marocaine", "Tagine", "Slow-cooked stew with meat, vegetables, and spices", "$45"));
        menus.add(new Dish("Marocaine", "Harira", "Traditional soup with lentils, chickpeas, and spices", "$30"));
        menus.add(new Dish("Orientale", "Shakshuka", "A spicy tomato and poached egg dish", "$30"));
        menus.add(new Dish("Orientale", "Falafel", "Fried chickpea balls, often served in pita", "$25"));
        menus.add(new Dish("Orientale", "Hummus", "Creamy chickpea spread with tahini and olive oil", "$20"));
        menus.add(new Dish("Espagnole", "Paella", "Rice dish with seafood, chicken, and vegetables", "$60"));
        menus.add(new Dish("Espagnole", "Gazpacho", "Cold tomato-based vegetable soup", "$20"));
        menus.add(new Dish("Espagnole", "Tortilla Española", "Spanish omelette with potatoes and onions", "$25"));
        menus.add(new Dish("Asiatique", "Sushi", "Vinegared rice with seafood, vegetables, or egg", "$40"));
        menus.add(new Dish("Asiatique", "Pad Thai", "Stir-fried rice noodles with shrimp, tofu, and peanuts", "$35"));
        menus.add(new Dish("Asiatique", "Spring Rolls", "Crispy rolls filled with vegetables or meat", "$15"));

        ArrayList<Dish> selected_menu = new ArrayList<>();
        for (Dish dish : menus){
            if(dish.getCuisine().equalsIgnoreCase(selected_cuisine)){
                selected_menu.add(dish);
            }
        }
        DishAdapter adapter= new DishAdapter(this,R.layout.menu_card,selected_menu);

        menuListView.setAdapter(adapter);

//        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });

    }
}
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class CuisineListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusine_list);
        ListView cuisinesList = findViewById(R.id.cuisineLists);

        String[] cuisineArray = {
            "Orientale", "Marocaine","Asiatique","Espagnole"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.cuisine_single_item,cuisineArray);
        cuisinesList.setAdapter(adapter);
        cuisinesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCuisine = (String) parent.getItemAtPosition(position);
                System.out.println(selectedCuisine);

                Intent intent = new Intent(CuisineListActivity.this,MenuActivity.class);
                intent.putExtra("selectedCuisine",selectedCuisine);
                startActivity(intent);
            }
        });
    }
}
package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.models.Dish;

import java.util.ArrayList;

public class DishAdapter extends ArrayAdapter<Dish> {
    private ArrayList<Dish> dishes;

    public DishAdapter(@NonNull Context context, int resource, ArrayList<Dish> dishes) {
        super(context, resource, dishes);
        this.dishes=dishes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.menu_card,parent,false);
        }

        Dish dish=(Dish) getItem(position);

        TextView menuTitleViewText=(TextView) convertView.findViewById(R.id.menuTitle);
        menuTitleViewText.setText(dish.getName());

        TextView menuDescViewText=(TextView) convertView.findViewById(R.id.menuDesc);
        menuDescViewText.setText(dish.getDescription());

        TextView priceTextView= (TextView) convertView.findViewById(R.id.menuPrice);
        priceTextView.setText(dish.getPrice());

        return convertView;
    }
}

package com.example.photouplode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Food> foodList;

    public FoodListAdapter(Context context, int layout, ArrayList<Food> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName,txtPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);

            viewHolder.txtName = (TextView)row.findViewById(R.id.txtName);
            viewHolder.txtPrice = (TextView)row.findViewById(R.id.txtPrice);
            viewHolder.imageView = (ImageView) row.findViewById(R.id.imageView);
            row.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)row.getTag();
        }
        Food food = foodList.get(position);
        viewHolder.txtName.setText(food.getName());
        viewHolder.txtPrice.setText(food.getPrice());

        byte[] foodImage = food.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage,0,foodImage.length);
        viewHolder.imageView.setImageBitmap(bitmap);


        return row;
    }
}

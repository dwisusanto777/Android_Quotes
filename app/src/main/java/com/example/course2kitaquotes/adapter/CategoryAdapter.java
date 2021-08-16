package com.example.course2kitaquotes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.course2kitaquotes.R;
import com.example.course2kitaquotes.model.CategoryModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<CategoryModel> models;

    public CategoryAdapter(Context context, List<CategoryModel> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View tampilan;
        CategoryHolder categoryHolder;
        tampilan = convertView;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        CategoryModel m = models.get(position);
        if(tampilan==null){
            tampilan = inflater.inflate(R.layout.row_layout, null);
            categoryHolder = new CategoryHolder();
            categoryHolder.title = (TextView) tampilan.findViewById(R.id.titleRowLayout);
            categoryHolder.imageView = (ImageView) tampilan.findViewById(R.id.imageRowLayout);
            tampilan.setTag(categoryHolder);
        }else{
            categoryHolder = (CategoryHolder)tampilan.getTag();
        }
        CategoryModel model = (CategoryModel)this.getItem(position);
        categoryHolder.title.setText(model.getCategoryName());
        String urlImage = model.getCategoryImage();
        Picasso.get().load(urlImage).into(categoryHolder.imageView);
        return  tampilan;
    }

    static class CategoryHolder{
        TextView title;
        ImageView imageView;
    }
}

package com.example.course2kitaquotes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.course2kitaquotes.R;
import com.example.course2kitaquotes.model.AuthorModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AuthorAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<AuthorModel> models;

    public AuthorAdapter(Context context, List<AuthorModel> models) {
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
        AuthorAdapter.AuthorHolder authorHolder;
        tampilan = convertView;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        CategoryModel m = models.get(position);
        if(tampilan==null){
            tampilan = inflater.inflate(R.layout.row_layout, null);
            authorHolder = new AuthorAdapter.AuthorHolder();
            authorHolder.title = (TextView) tampilan.findViewById(R.id.titleRowLayout);
            authorHolder.imageView = (ImageView) tampilan.findViewById(R.id.imageRowLayout);
            tampilan.setTag(authorHolder);
        }else{
            authorHolder = (AuthorAdapter.AuthorHolder)tampilan.getTag();
        }
        AuthorModel model = (AuthorModel) this.getItem(position);
        authorHolder.title.setText(model.getAuthorName());
        String urlImage = model.getAuthorImage();
        Picasso.get().load(urlImage).into(authorHolder.imageView);
        return  tampilan;
    }

    static class AuthorHolder{
        TextView title;
        ImageView imageView;
    }
}

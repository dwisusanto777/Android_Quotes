package com.example.course2kitaquotes.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.course2kitaquotes.MainActivity;
import com.example.course2kitaquotes.ParameterRegister;
import com.example.course2kitaquotes.R;
import com.example.course2kitaquotes.model.CategoryModel;

import java.util.ArrayList;

public class FragmentCategoryRefresh extends Fragment {

    Bundle b = new Bundle();
    ListView listView;
    String quotesJson = "";
    Resources resources;
    Context context;
    AdapterView.OnItemClickListener onItemClickListener;
    ProgressDialog dialog;
    String quoteName, quoteImage;
    int quoteId;
    View view;
    Fragment fragment;
    CategoryAdapter categoryAdapter;
    AuthorAdapter authorAdapter;
    ArrayList<CategoryModel> categoryModalArrayList;
    ArrayList<AuthorModel> authorModalArrayList;

    public static FragmentCategoryRefresh newInstance(Bundle bundle){
        FragmentCategoryRefresh fragment = new FragmentCategoryRefresh();
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        categoryModalArrayList = new ArrayList<CategoryModal>();
        authorModalArrayList = new ArrayList<AuthoeModal>();
        categoryAdapter = null;
        authorAdapter = null;
        listView = view.findViewById(R.id.list);
        final int value = getArguments().getInt(ParameterRegister.SELECT_INDEX);
        context = getActivity().getApplicationContext();
        resources = getResources();
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Silahkan tunggu . . .");
        dialog.setMessage("Menunggu data . . .");
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        //Koneksi
        onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                b.putBoolean(ParameterRegister.FROM_LIST, true);
                b.putInt(ParameterRegister.SELECT_INDEX, value);
                if(value==2){
                    // Data dari fragment 2
                    int categoryArray = categoryModalArrayList.get(position).getCategoryId();
//                    b.putInt();
                }else if(value==3){
                    // Data dari fragment 3
                    int authorArrau = authorModalArrayList.get(position).getAuthorId();
//                    b.putInt();
                }
                fragment = FragmentQuotesShow.newInstance(b);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.linearLayout, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        };
        listView.setOnItemClickListener(onItemClickListener);
    }
}

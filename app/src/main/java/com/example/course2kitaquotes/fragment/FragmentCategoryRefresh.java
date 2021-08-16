package com.example.course2kitaquotes.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.course2kitaquotes.MainActivity;
import com.example.course2kitaquotes.ParameterRegister;
import com.example.course2kitaquotes.R;
import com.example.course2kitaquotes.Util.ConnectionApi;
import com.example.course2kitaquotes.adapter.AuthorAdapter;
import com.example.course2kitaquotes.adapter.CategoryAdapter;
import com.example.course2kitaquotes.model.AuthorModel;
import com.example.course2kitaquotes.model.CategoryModel;
import com.google.gson.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        categoryModalArrayList = new ArrayList<CategoryModel>();
        authorModalArrayList = new ArrayList<AuthorModel>();
        categoryAdapter = new CategoryAdapter(getActivity(), categoryModalArrayList);
        authorAdapter = new AuthorAdapter(getActivity(), authorModalArrayList);
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
        ConnectionApi connectionApi = new ConnectionApi(getActivity());
        if(connectionApi.isConnectingToInternet()){
            if(value==2){
                //Category
                GetCategoryAllQuotes gcaq = new GetCategoryAllQuotes();
                gcaq.onPostExecute("http://192.168.100.25:8060/kita_quotes/api/getallcategory.php");
            } else if(value==3){
                //Author
                GetAuthotAllQuotes gaaq = new GetAuthotAllQuotes();
                gaaq.onPostExecute("http://192.168.100.25:8060/kita_quotes/api/getallauthor.php");
            }
        }else{
            Toast.makeText(context, "Tidak ada jaringan", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        }

        onItemClickListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                b.putBoolean(ParameterRegister.FROM_LIST, true);
                b.putInt(ParameterRegister.SELECT_INDEX, value);
                if(value==2){
                    // Data dari fragment 2
                    int categoryArray = categoryModalArrayList.get(position).getCategoryId();
                    b.putInt(MainActivity.categoryId, categoryArray);
                }else if(value==3){
                    // Data dari fragment 3
                    int authorArrau = authorModalArrayList.get(position).getAuthorId();
                    b.putInt(ParameterRegister.AUTHOR_ID, authorArrau);
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

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_refresh, container, false );
    }

    class GetAuthotAllQuotes extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            quotesJson = "";
            for (String url : strings){
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try{
                    HttpResponse response = client.execute(httpGet);
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String hasil = "";
                    while ((hasil = reader.readLine())!=null){
                        quotesJson += hasil;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    JSONArray jsonArray = new JSONArray(quotesJson);
                    int count = 0;
                    while (count < jsonArray.length()){
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        quoteName = jsonObject.getString("authname");
                        quoteImage = jsonObject.getString("authimage");
                        quoteId = jsonObject.getInt("authid");
                        AuthorModel m = new AuthorModel(quoteId, quoteName, quoteImage);
                        authorModalArrayList.add(m);
                        count++;
                    }
                }catch (JSONException e){
                    Log.d("AdaError", e.getMessage());
                }
            }
            return quotesJson;
        }

        @Override
        protected void onPostExecute(String s) {
            listView.setAdapter(authorAdapter);
            dialog.cancel();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    class GetCategoryAllQuotes extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            quotesJson = "";
            for (String url : strings){
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try{
                    HttpResponse response = client.execute(httpGet);
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String hasil = "";
                    while ((hasil = reader.readLine())!=null){
                        quotesJson += hasil;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    JSONArray jsonArray = new JSONArray(quotesJson);
                    int count = 0;
                    while (count < jsonArray.length()){
                        JSONObject jsonObject = jsonArray.getJSONObject(count);
                        quoteName = jsonObject.getString("categoryname");
                        quoteImage = jsonObject.getString("categorimage");
                        quoteId = jsonObject.getInt("categoryid");
                        CategoryModel m = new CategoryModel(quoteId, quoteName, quoteImage);
                        categoryModalArrayList.add(m);
                        count++;
                    }
                }catch (JSONException e){
                    Log.d("AdaError", e.getMessage());
                }
            }
            return quotesJson;
        }

        @Override
        protected void onPostExecute(String s) {
            listView.setAdapter(categoryAdapter);
            dialog.cancel();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}

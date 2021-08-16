package com.example.course2kitaquotes.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.course2kitaquotes.MainActivity;
import com.example.course2kitaquotes.ParameterRegister;
import com.example.course2kitaquotes.R;
import com.example.course2kitaquotes.Util.ConnectionApi;
import com.example.course2kitaquotes.model.AuthorModel;
import com.example.course2kitaquotes.model.KitaQuotes;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FragmentQuotesShow extends Fragment implements View.OnClickListener, View.OnTouchListener{

    private boolean isBookMark = false;
    private float x1 = 0, x2 = 0, x3 = 0, y1 = 0, y2 = 0, y3 = 0;
    RelativeLayout relativeLayout;
    Context context;
    TextView textViewQuote, textViewCount;
    ImageView buttonNext, buttonPrev, buttonBacklist, buttonShare, buttonBookmark;
    private float moveCount;
    SharedPreferences sharedPreferences;
    SharedPreferences setting;
    SharedPreferences.Editor editor;
    int selectedQuotes = 0;
    Intent intent;
    Animation in, out;
    ProgressDialog dialog;
    String quotesJson = "";
    ArrayList<String> myListApi;
    Set<String> set = null;
    ArrayList<KitaQuotes> listQuotes = new ArrayList<KitaQuotes>();
    View view;
    int currentIndexPosition = 0;

    public static FragmentQuotesShow newInstance(Bundle bundle){
        FragmentQuotesShow fragment = new FragmentQuotesShow();
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quote_show, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        dialog = new ProgressDialog(getActivity());
        getAllBundleArgument();
        setAnimation();
        setView(view);

        set = setting.getStringSet("key", null);
        if(set==null){
            Log.d("benar", "BENAR");
        }else{
            myListApi = new ArrayList<>();
        }
        if(isBookMark){
            buttonBookmark.setImageResource(R.mipmap.ic_bookmark);
            textViewQuote.setText(getQuotesBookmark(0));
            textViewCount.setText(selectedQuotes+1+" dari "+getQuoteListener());
        }else{
            Bundle bundle = getArguments();
            boolean isFormList = bundle.getBoolean(ParameterRegister.FROM_LIST);
            int value = getArguments().getInt(ParameterRegister.SELECT_INDEX);
            int categoryId = getArguments().getInt(MainActivity.categoryId);
            int authorId = getArguments().getInt(ParameterRegister.AUTHOR_ID);

            dialog.setTitle("Silahkan tunggu . . .");
            dialog.setMessage("Menunggu data . . .");
            dialog.setCancelable(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();

            ConnectionApi cApi = new ConnectionApi(getActivity());
            if(cApi.isConnectingToInternet()){
                if(value==1){
                    // GET ALL QUOTES
                    GetAllQuotesFromInternet dataIntrnet = new GetAllQuotesFromInternet();
                    dataIntrnet.execute("http://192.168.100.25:8060/kita_quotes/api/quote.php");
                }
                if(isFormList){
                    if(value==2){
                        //CATEGORY
                        GetCategoryQuotesFromInternet dataIntrnet = new GetCategoryQuotesFromInternet();
                        dataIntrnet.execute("http://localhost:8060/kita_quotes/api/getallauthor.php");
                    }else if(value==3){
                        //AUTHOR
                        GetAuthorQuotesFromInternet dataIntrnet = new GetAuthorQuotesFromInternet();
                        dataIntrnet.execute("http://localhost:8060/kita_quotes/api/getallauthor.php");
                    }
                }
            }else{
                Toast.makeText(context, "Tidak konek dengan internet", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump:
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                EditText inputDialog = new EditText(getActivity());
                inputDialog.setKeyListener(new DigitsKeyListener());
                alert.setView(inputDialog);
                alert.setTitle("Pergi Ke")
                        .setMessage("Masukkan nomornya")
                        .setPositiveButton("Pergi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Editable pergi = inputDialog.getText();
                                if(pergi.toString()!=null && pergi.toString().length()>=1){
                                    int pergiQuote = Integer.parseInt(pergi.toString().trim());
                                    if(isBookMark){
                                        if(pergiQuote>0 && pergiQuote <= getSize()){
                                            selectedQuotes = pergiQuote-1;
                                            setSelectBookmark();
                                            if(pergiQuote==getSize()){
                                                buttonNext.setEnabled(false);
                                                buttonPrev.setEnabled(true);
                                            }
                                            if(pergiQuote==1){
                                                buttonPrev.setEnabled(false);
                                                buttonNext.setEnabled(true);
                                            }
                                        }
                                    }else{
                                        if(pergiQuote>0 && pergiQuote <= getQuoteListener()){
                                            selectedQuotes = pergiQuote-1;
                                            setSelectQuotes();
                                            if(pergiQuote==getQuoteListener()){
                                                buttonNext.setEnabled(false);
                                                buttonPrev.setEnabled(true);
                                            }
                                            if(pergiQuote==1){
                                                buttonPrev.setEnabled(false);
                                                buttonNext.setEnabled(true);
                                            }
                                        }
                                    }
                                }
                            }
                        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
                break;
            case R.id.btn_share:
                intent = new Intent(Intent.ACTION_SEND);
                if(isBookMark){
                    intent.putExtra(Intent.EXTRA_TEXT, getQuotesBookmark(selectedQuotes));
                }else{
                    intent.putExtra(Intent.EXTRA_TEXT, getQuoteListData(selectedQuotes));
                }
                intent.setType("text/plain");
                startActivity(intent);
                break;
            case R.id.btn_next:
                setNextAllQuotes();
                break;
            case R.id.btn_prev:
                setPrevAllQuotes();
                break;
            case R.id.btn_bookmark:
                if(isBookMark){
                    myListApi.remove(getQuotesBookmark(selectedQuotes));
                    removeBookMark();
                    setSelectBookmark();
                }else{
                    myListApi.add(getQuotesBookmark(selectedQuotes));
                    removeBookMark();
                    setSelectQuotes();
                }
                break;
        }
    }

    public void removeBookMark(){
        editor = setting.edit();
        Set<String> set = new HashSet<>();
        set.addAll(myListApi);
        editor.putStringSet(ParameterRegister.KEY_LIST_API, set);
        editor.commit();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.linearLayout:
                touchQuotesScreen(v,event);
                return true;
            case R.id.textquote:
                touchQuotesScreen(v,event);
                return true;
            default:
                break;
        }
        return  false;
    }

    public boolean touchQuotesScreen(View view, MotionEvent event){
        switch ((event.getAction())){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                y2 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x3 = event.getX();
                y3 = event.getY();
                if(x1<x3){
                    moveCount = x3-x1;
                    if(moveCount>150){
                        setPrevAllQuotes();
                    }
                }else if(x1>x3){
                    moveCount = x1-x3;
                    if(moveCount>150){
                        setNextAllQuotes();
                    }
                }
                break;
        }
        return false;
    }

    private void setPrevAllQuotes(){
        int prevQuote = selectedQuotes-1;
        if(isBookMark){
            if(prevQuote > -1){
                selectedQuotes--;
                setSelectBookmark();
                buttonNext.setEnabled(true);
            }
        }else{
            if(prevQuote < -1){
                selectedQuotes--;
                setSelectQuotes();
                buttonNext.setEnabled(true);
            }
        }
    }

    private void setNextAllQuotes(){
        int nextQuote = selectedQuotes+1;
        if(isBookMark){
            if(nextQuote < getSize()){
                selectedQuotes++;
                setSelectBookmark();
                buttonPrev.setEnabled(true);
            }
        }else{
            if(nextQuote < getQuoteListener()){
                selectedQuotes++;
                setSelectQuotes();
                buttonPrev.setEnabled(true);
            }
        }
    }

    public void setSelectBookmark(){
        textViewQuote.setText(getQuotesBookmark(selectedQuotes));
        textViewCount.setText(selectedQuotes+ 1 +" dari "+ getSize());
        YoYo.with(Techniques.FlipOutY).duration(600).playOn(textViewQuote);
        if(myListApi!=null && myListApi.size()>0){
            textViewCount.setText(selectedQuotes + 1 + " dari "+getSize());
        }else{
            textViewCount.setVisibility(View.INVISIBLE);
        }
        if(selectedQuotes<1){
            buttonPrev.setEnabled(false);
        }
        if(selectedQuotes==getSize()){
            buttonNext.setEnabled(false);
        }
    }

    public void setSelectQuotes(){
        textViewQuote.setText(getQuoteListData(selectedQuotes));
        textViewCount.setText(selectedQuotes + 1 + " dari "+getQuoteListener());
        YoYo.with(Techniques.FlipOutY).duration(600).playOn(textViewQuote);
        if(listQuotes!=null && listQuotes.size()>0){
            textViewCount.setText(selectedQuotes + 1 + " dari "+getQuoteListener());
        }else{
            textViewCount.setVisibility(View.INVISIBLE);
        }
        if(selectedQuotes < 1){
            buttonPrev.setEnabled(false);
        }
        if(selectedQuotes==getQuoteListener()){
            buttonNext.setEnabled(false);
        }
    }

    private String getQuoteListData(int currentIndexPosition){
        if(listQuotes==null || listQuotes.size()<=0){
            return "Kita Quotes Tidak Ada Data";
        }
        return ((KitaQuotes)listQuotes.get(currentIndexPosition)).getName();
    }

    class GetAuthorQuotesFromInternet extends AsyncTask<String, Void, String> {
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
                        KitaQuotes quotes = new KitaQuotes();
                        quotes.setId(jsonObject.getInt("authorid"));
                        quotes.setName(jsonObject.getString("quote"));
                        listQuotes.add(quotes);
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
            if(listQuotes!=null && listQuotes.size()>=1){
                textViewQuote.setText(listQuotes.get(0).getName());
                textViewCount.setText(selectedQuotes+ 1 + " dari "+ getQuoteListener());
            }else{
                textViewQuote.setText("No Quotes Found");
            }
            dialog.cancel();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    class GetCategoryQuotesFromInternet extends AsyncTask<String, Void, String> {
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
                        KitaQuotes quotes = new KitaQuotes();
                        quotes.setId(jsonObject.getInt("quoteid"));
                        quotes.setName(jsonObject.getString("quote"));
                        listQuotes.add(quotes);
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
            if(listQuotes!=null && listQuotes.size()>=1){
                textViewQuote.setText(listQuotes.get(0).getName());
                textViewCount.setText(selectedQuotes+ 1 + " dari "+ getQuoteListener());
            }else{
                textViewQuote.setText("No Quotes Found");
            }
            dialog.cancel();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    class GetAllQuotesFromInternet extends AsyncTask<String, Void, String> {


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
                        KitaQuotes quotes = new KitaQuotes();
                        quotes.setId(jsonObject.getInt("quoteid"));
                        quotes.setName(jsonObject.getString("quote"));
                        listQuotes.add(quotes);
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
            if(listQuotes!=null && listQuotes.size()>=1){
                textViewQuote.setText(listQuotes.get(0).getName());
                textViewCount.setText(selectedQuotes+ 1 + " dari "+ getQuoteListener());
            }else{
                textViewQuote.setText("No Quotes Found");
            }
            dialog.cancel();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private int getQuoteListener(){
        if(listQuotes!=null) {
            return listQuotes.size();
        }else{
            return 0;
        }

    }

    private void getAllBundleArgument(){
        Bundle bundle = getArguments();
        isBookMark = bundle.getBoolean(MainActivity.isBookMark);
    }

    private void setAnimation(){
        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(900);
        out = new AlphaAnimation(1.0f, 0.0F);
        out.setDuration(900);
    }

    private int getSize(){
        if (myListApi != null) {
            return myListApi.size();
        }else{
            return 0;
        }
    }

    private String getQuotesBookmark(int id){
        if(myListApi==null || myListApi.size()<=0){
            buttonBookmark.setVisibility(View.INVISIBLE);
            buttonBacklist.setVisibility(View.INVISIBLE);
            buttonShare.setVisibility(View.INVISIBLE);
            textViewCount.setVisibility(View.INVISIBLE);
            return "Belum ada bookmark";
        }else{
            if(myListApi.size()>currentIndexPosition){
                return myListApi.get(currentIndexPosition).toString();
            }else{
                selectedQuotes = myListApi.size()-1;
                return myListApi.get(selectedQuotes).toString();
            }
        }
    }

    private void setView(View view){
        textViewQuote = view.findViewById(R.id.textquote);
        textViewQuote.setOnTouchListener(this);
        textViewCount = view.findViewById(R.id.textcount);
        buttonNext = view.findViewById(R.id.btn_next);
        buttonPrev = view.findViewById(R.id.btn_prev);
        buttonBacklist = view.findViewById(R.id.btn_jump);
        buttonShare = view.findViewById(R.id.btn_share);
        buttonBookmark = view.findViewById(R.id.btn_bookmark);
        relativeLayout= view.findViewById(R.id.rl);
        context = getActivity().getApplicationContext();
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        setting = getActivity().getSharedPreferences(MainActivity.pref_name, 0);
        relativeLayout.setOnTouchListener(this);
        buttonNext.setOnClickListener(this);
        buttonPrev.setOnClickListener(this);
        buttonBacklist.setOnClickListener(this);
        buttonBookmark.setOnClickListener(this);
        buttonShare.setOnClickListener(this);
        myListApi = new ArrayList<>();
    }
}

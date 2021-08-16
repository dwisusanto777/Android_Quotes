package com.example.course2kitaquotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.ims.ImsMmTelManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.course2kitaquotes.custom.SquareButton;
import com.example.course2kitaquotes.databinding.MainActivityBinding;
import com.example.course2kitaquotes.fragment.FragmentAboutApp;
import com.example.course2kitaquotes.fragment.FragmentCategoryRefresh;
import com.example.course2kitaquotes.fragment.FragmentQuotesShow;

public class MainActivity extends FragmentActivity implements Animation.AnimationListener, View.OnClickListener {

    MainActivityBinding binding;
    private Toolbar toolbar;
    private Fragment fragment;
    SquareButton button1, button2, button3, button4, button5, button6;
    Animation animationZoomInOut;
    int buttonIndex = 1;
    final Handler handler1 = new Handler();
    final Handler handler2 = new Handler();
    final Handler handler3 = new Handler();
    final Handler handler4 = new Handler();
    final Handler handler5 = new Handler();
    final Handler handler6 = new Handler();
    final int buttonIndex1 = 1;
    final int buttonIndex2 = 2;
    final int buttonIndex3 = 3;
    final int buttonIndex4 = 4;
    final int buttonIndex5 = 5;
    final int buttonIndex6 = 6;
    public static final String isBookMark = "isBookMark";
    public static final String categoryId = "category_id";
    public static final String pref_name = "preferences";
    Bundle bundle = new Bundle();
    int[] time_frame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        toolbar = findViewById(R.id.toolbar);
        animationZoomInOut = AnimationUtils.loadAnimation(this, R.anim.my_animation);
        animationZoomInOut.setAnimationListener(this);
        int index = 6;
        time_frame = getApplicationContext().getResources().getIntArray(R.array.time_frame_1);
        switch (index){
            case 0:
                time_frame = getApplicationContext().getResources().getIntArray(R.array.time_frame_1);
                break;
            case 1:
                time_frame = getApplicationContext().getResources().getIntArray(R.array.time_frame_2);
                break;
            case 2:
                time_frame = getApplicationContext().getResources().getIntArray(R.array.time_frame_3);
                break;
            case 3:
                time_frame = getApplicationContext().getResources().getIntArray(R.array.time_frame_4);
                break;
            case 4:
                time_frame = getApplicationContext().getResources().getIntArray(R.array.time_frame_5);
                break;
            case 5:
                time_frame = getApplicationContext().getResources().getIntArray(R.array.time_frame_6);
                break;
            case 6:
                time_frame = getApplicationContext().getResources().getIntArray(R.array.time_frame_7);
                break;
        }
        button1 = findViewById(R.id.btn1);
        button1.setOnClickListener(this);
        button1.setVisibility(View.VISIBLE);
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.BounceInDown).duration(555).playOn(button1);
                button1.setVisibility(View.VISIBLE);
            }
        }, time_frame[0]);
        button2 = findViewById(R.id.btn2);
        button2.setOnClickListener(this);
        button2.setVisibility(View.VISIBLE);
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.BounceInDown).duration(555).playOn(button2);
                button2.setVisibility(View.VISIBLE);
            }
        }, time_frame[1]);
        button3 = findViewById(R.id.btn3);
        button3.setOnClickListener(this);
        button3.setVisibility(View.VISIBLE);
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.BounceInDown).duration(555).playOn(button3);
                button3.setVisibility(View.VISIBLE);
            }
        }, time_frame[2]);
        button4 = findViewById(R.id.btn4);
        button4.setOnClickListener(this);
        button4.setVisibility(View.VISIBLE);
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.BounceInDown).duration(555).playOn(button4);
                button4.setVisibility(View.VISIBLE);
            }
        }, time_frame[3]);
        button5 = findViewById(R.id.btn5);
        button5.setOnClickListener(this);
        button5.setVisibility(View.VISIBLE);
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.BounceInDown).duration(555).playOn(button5);
                button5.setVisibility(View.VISIBLE);
            }
        }, time_frame[4]);
        button6 = findViewById(R.id.btn6);
        button6.setOnClickListener(this);
        button6.setVisibility(View.VISIBLE);
        handler6.postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.BounceInDown).duration(555).playOn(button6);
                button6.setVisibility(View.VISIBLE);
            }
        }, time_frame[5]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                button1.startAnimation(animationZoomInOut);
                buttonIndex = buttonIndex1;
                break;
            case R.id.btn2:
                button2.startAnimation(animationZoomInOut);
                buttonIndex = buttonIndex2;
                break;
            case R.id.btn3:
                button3.startAnimation(animationZoomInOut);
                buttonIndex = buttonIndex3;
                break;
            case R.id.btn4:
                button4.startAnimation(animationZoomInOut);
                buttonIndex = buttonIndex4;
                break;
            case R.id.btn5:
                button5.startAnimation(animationZoomInOut);
                buttonIndex = buttonIndex5;
                break;
            case R.id.btn6:
                button6.startAnimation(animationZoomInOut);
                buttonIndex = buttonIndex6;
                break;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        switch (buttonIndex){
            case buttonIndex1:
                //All Quotes
                if(animation == animationZoomInOut){
                    bundle.putInt(ParameterRegister.SELECT_INDEX, buttonIndex1);
                    Log.d("On Animed  END", button1+" All Quotes");
                    fragment = FragmentQuotesShow.newInstance(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, fragment)
                            .addToBackStack("tag")
                            .commit();
                }
                break;
            case buttonIndex2:
                // Category
                if(animation == animationZoomInOut){
                    bundle.putInt(ParameterRegister.SELECT_INDEX, buttonIndex2);
                    Log.d("On Animed  END", button2+" All Category");
                    fragment = FragmentCategoryRefresh.newInstance(bundle);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.linearLayout, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                break;
            case buttonIndex3:
                //Author
                if(animation == animationZoomInOut){
                    bundle.putInt(ParameterRegister.SELECT_INDEX, buttonIndex3);
                    Log.d("On Animed  END", button3+" All Author");
                    fragment = FragmentCategoryRefresh.newInstance(bundle);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.linearLayout, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                break;
            case buttonIndex4:
                if(animation == animationZoomInOut){
                    Bundle b = new Bundle();
                    b.putBoolean(isBookMark, true);
                    fragment = FragmentQuotesShow.newInstance(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, fragment)
                            .addToBackStack("tag")
                            .commit();
                }
                break;
            case buttonIndex5:
                if(animation == animationZoomInOut){
                    Bundle b = new Bundle();
                    fragment = FragmentAboutApp.newInstance(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, fragment)
                            .addToBackStack("tag")
                            .commit();
                }
                break;
            case buttonIndex6:
                if(animation == animationZoomInOut){
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://app.semenbima.com"));
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    startActivity(i);
                }
                break;
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
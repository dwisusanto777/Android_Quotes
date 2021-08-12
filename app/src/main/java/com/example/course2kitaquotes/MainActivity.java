package com.example.course2kitaquotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;

import com.example.course2kitaquotes.custom.SquareButton;

public class MainActivity extends FragmentActivity implements Animation.AnimationListener, View.OnClickListener {


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
    int buttonIndex1 = 1;
    int buttonIndex2 = 2;
    int buttonIndex3 = 3;
    int buttonIndex4 = 4;
    int buttonIndex5 = 5;
    int buttonIndex6 = 6;
    final String isBookMark = "isBookMark";
    final String categoryId = "category_id";
    final String pref_name = "preferences";
    Bundle bundle = new Bundle();
    int[] time_frame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
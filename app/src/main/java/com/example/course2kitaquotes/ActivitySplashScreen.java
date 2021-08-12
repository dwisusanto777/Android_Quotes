package com.example.course2kitaquotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.course2kitaquotes.databinding.ActivitySplashBinding;

public class ActivitySplashScreen extends Activity {

    protected boolean _active = true;
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Thread timeThread = new Thread(){
            public void run(){
                try{
                    sleep(2500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startMainScreen();
                }
            }
        };
        timeThread.start();
    }

    private void startMainScreen() {
        startActivity(new Intent(ActivitySplashScreen.this, MainActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            _active = false;
        }
        return  true;
    }
}

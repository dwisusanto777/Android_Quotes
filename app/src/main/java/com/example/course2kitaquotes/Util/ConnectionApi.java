package com.example.course2kitaquotes.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionApi {
    private Context context;

    public ConnectionApi(Context context) {
        this.context = context;
    }

    public boolean isConnectingToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo[] infos = connectivityManager.getAllNetworkInfo();
            if(infos!=null){
                for (int i = 0; i < infos.length ; i++) {
                    if(infos[i].getState()== NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

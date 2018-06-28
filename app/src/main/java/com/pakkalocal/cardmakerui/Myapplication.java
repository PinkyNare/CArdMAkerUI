package com.pakkalocal.cardmakerui;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.pakkalocal.cardmakerui.utilsdata.ConnectivityReceiver;


/**
 * Created by 2117 on 4/17/2018.
 */


public class Myapplication extends Application {
    private static Myapplication mInstance;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        try {
            Class.forName("android.os.AsyncTask");
        } catch (Throwable ignore) {
        }
        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.App_id));

        mInstance = this;

    }

    public static synchronized Myapplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {


        ConnectivityReceiver.connectivityReceiverListener = listener;
    }


}

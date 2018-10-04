package com.example.tyanai.myteacher2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkManager {
    /**
     * ネットワーク接続が確立されているか
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null ){
            return connectivityManager.getActiveNetworkInfo().isConnected();
        }

        return false;
    }
}

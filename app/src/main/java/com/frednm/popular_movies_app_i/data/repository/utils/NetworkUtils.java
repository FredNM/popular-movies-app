package com.frednm.popular_movies_app_i.data.repository.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import javax.inject.Inject;

/**
 * To check if there is a network connection.
 * See https://stackoverflow.com/questions/57277759/getactivenetworkinfo-is-deprecated-in-api-29
 * Question: When I was injecting this in the repository, I only saw the network state when user starts the app. But when the
 * network states was changing, passing from true to false, the hasNewtworkConnection() was still returning true !
 */

public class NetworkUtils {

    private Context context;

    @Inject
    public NetworkUtils(Context context) {
        this.context = context;
    }

    public boolean hasNetworkConnection(){
        if(context == null)  return false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // For newer SDk version
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    }  else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                        return true;
                    }
                }
            }

            else {  // For older SDK version
                try {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
                        Log.i("update_statut", "Network is available : true");
                        return true;
                    }
                } catch (Exception e) {
                    Log.i("update_statut", "" + e.getMessage());
                }
            }
        }
        Log.i("update_statut","Network is available : FALSE ");
        return false;
    }
}


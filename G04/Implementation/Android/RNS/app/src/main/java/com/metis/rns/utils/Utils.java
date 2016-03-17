package com.metis.rns.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by triplez on 16-3-14.
 */
public class Utils {

    public static boolean hasNetwork(Context context) {
        ConnectivityManager connectivity;
        try {
            connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        } catch (Exception e) {
            return false;
        }
        NetworkInfo[] info = null;
        try {
            info = connectivity.getAllNetworkInfo();
        } catch (Exception e) {

        }
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        return false;
    }
}

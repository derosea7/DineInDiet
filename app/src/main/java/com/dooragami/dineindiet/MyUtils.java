package com.dooragami.dineindiet;

import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by derosea7 on 2/9/2016.
 */
public final class MyUtils {

    private MyUtils(){} // prevents initilization, as this class will only contain static methods

    public static void sMethod() {
        // can be called directly from other classes without initializing an object
    }

    private static boolean mDebuggingStatus = true;

    public static boolean getDebuggingStatus() {
        return mDebuggingStatus;
    }

}

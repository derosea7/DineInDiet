package com.dooragami.dineindiet;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class HelpFrag extends Fragment {


    public HelpFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        String appPurpose = "The purpose of this app is to allow users the ability to track the cost of dieting." +
                "\n\nAny suggestions to make the app easier to use would be greatly appreciated!" +
                "\n\nPlease email me at derosea7@gmail.com. Thanks!";
        ((TextView) view.findViewById(R.id.appPurpose)).setText(appPurpose);

        String strPrivacyPolicy =
                "By using Dine iN Diet (The Application), you are agreeing " +
                "to its privacy policy as stated below.\n\n" +

                "The Application makes use of the Google Adsense ad-serving engine. Third party " +
                "vendors, including Google, use cookies to serve ads based on your prior " +
                "interaction with The Application.\n\n" +

                "Google's use of the DoubleClick cookie enables it and its partners to " +
                "serve ads to you based on your session activity in The Application and " +
                "other applications that make use of these same vendors.\n\n" +

                "You may opt out of the use of the DoubleClick cookie for interest-based " +
                "advertising by visiting www.google.com/ads/preferences.\n\n" +

                "Additionally, The Application employs Google Analytics, " +
                "which also uses DoubleClick cookies to gain insight to user behavior in " +
                "The Application. ";
        ((TextView) view.findViewById(R.id.tvPrivacyPolicy)).setText(strPrivacyPolicy);

        if (!MainActivity.buDeggin) {
            // [START shared_tracker]
            // Obtain the shared Tracker instance.
            Analytics application = (Analytics) getActivity().getApplication();
            mTracker = application.getDefaultTracker();
            // [END shared_tracker]

            sendScreenName();
        }

        return view;
    }

    private Tracker mTracker;

    private void sendScreenName() {
        if (!MainActivity.buDeggin) {
            String screenName = "Help";

            // [START screen_view_hit]
            mTracker.setScreenName("Screen~" + screenName);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
            // [END screen_view_hit]
        }
    }

}

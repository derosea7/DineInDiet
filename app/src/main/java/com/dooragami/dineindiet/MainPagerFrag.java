package com.dooragami.dineindiet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by derosea7 on 1/23/2016.
 */
public class MainPagerFrag extends Fragment {

    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_pager_frag, container, false);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_main);
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_track_changes_black_24dp));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_kitchen_black_24dp));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_local_grocery_store_black_24dp));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_flag_black_24dp));

        tabLayout.addTab(tabLayout.newTab().setText("Overview"));
        tabLayout.addTab(tabLayout.newTab().setText("Consumption"));
        tabLayout.addTab(tabLayout.newTab().setText("Purchases"));
        //tabLayout.addTab(tabLayout.newTab().setText("4"));

        viewPager = (ViewPager) view.findViewById(R.id.pager_main);
        final PagerAdapter adapter = new MainPagerAdapter(getActivity().getSupportFragmentManager(),
                tabLayout.getTabCount());

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.i(TAG, "current tab getPostition: " + tab.getPosition());
                Log.i(TAG, "current tab name: " + tab.getText().toString());
                //tab.
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
    public static final String TAG = "tabs";
    public void setTab(int position) {
        switch (position) {
            case 1:
                viewPager.setCurrentItem(position);

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

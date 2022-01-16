package com.dooragami.dineindiet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by derosea7 on 1/23/2016.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    int mTabCount;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.mTabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                // get ref to tab class
                // return that ref
                OverviewFrag overviewFrag = new OverviewFrag();
                return overviewFrag;
            case 1:
                ConsumptionFrag consumptionFrag = new ConsumptionFrag();
                return consumptionFrag;

            case 2:
                PurchasesFrag purchasesFrag = new PurchasesFrag();
                return purchasesFrag;

            default:
                return null;

        }


    }

    @Override
    public int getCount() {
        return mTabCount;
    }
}

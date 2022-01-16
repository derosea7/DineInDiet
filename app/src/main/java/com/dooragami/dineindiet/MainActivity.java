package com.dooragami.dineindiet;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dooragami.dineindiet.models.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final boolean buDeggin = true;

    public static final String DATE_FORMAT_DB = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DISPLAY = "ccc, MMMM d, yyyy";


    public static final String BS_TAG = "BackStack Tag";
    public static final String FEATURE_USE = "Feature Use";
    private FragmentManager fm;

    public static final String STR_USER_NAME = "STR_USER_NAME";
    public static final String INT_AGE_PROGRESS = "INT_AGE_PROGRESS";
    public static final String HEIGHT_INCHES = "HEIGHT_INCHES";
    public static final String STR_GENDER = "STR_GENDER";
    public static final String GENDER_MALE = "GENDER_MALE";
    public static final String GENDER_FEMALE = "GENDER_FEMALE";
    public static final String GENDER_NO_ANSWER = "GENDER_NO_ANSWER";
    public static final String STR_GENDER_ADMOBS = "STR_GENDER_ADMOBS";

    public static final String SHRD_PREFS_SETUP = "setupValues";
    public static final String FIRSTRUN = "firstrun";
    SharedPreferences prefs = null;

    private static boolean USER_NOT_SETUP = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




//        fm = getSupportFragmentManager();
//        // fm.addOnBackStackChangedListener(this);
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        User user = User.getInstance();
//
//        // this line of code is buggy
//        user.initializeUser(getApplicationContext());
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        /*
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        prefs = getSharedPreferences(SHRD_PREFS_SETUP, Context.MODE_PRIVATE);
//
//        // might not end up needing this
//        USER_NOT_SETUP = prefs.getBoolean(FIRSTRUN, true);
//        */
//
////        if (user.isFirstRun() == true) {
////            // Do first run stuff here then set 'firstrun' as false
////        } else {
////
////        }
//
//        // add navigation header to navigationView at runtime so we can then access
//        // views in the header by id
//        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
//        TextView tvHeaderUsername = (TextView) headerView.findViewById(R.id.tvHeaderUsername);
//
//        // tvHeaderUsername.setText(prefs.getString(MainActivity.STR_USER_NAME, "Dine iN Diner"));
//        //tvHeaderUsername.setText(user.getName());
//
//        // Replace place-holder frames with content-filled fragments.
//        // if there is nothing loaded already, then load the overview frag
//        if (savedInstanceState == null) {
//
//            //loadFragInstantly(new OverviewFrag());
//
//            loadFrags_Dynamically(isLargeScreen(getApplicationContext()), new MyInfoFrag(),
//                    new ConsumptionDetailFrag(), R.id.frame, R.id.frame2);
//
//        }
//        // else, somehow this activity just re-attaches(?) the fragment that was already loaded

    }

    /*
    private User getUserInfo(){
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        prefs = getSharedPreferences(SHRD_PREFS_SETUP, Context.MODE_PRIVATE);

        // get instance of the static User object.
        User user = User.getInstance();

        user.setName(prefs.getString(MainActivity.STR_USER_NAME, "Dieter"));

        return user;
    }
    */

    private static boolean isLargeScreen(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Simple statement that allows for loading a new instance of a given fragment
     *
     * @param mFrag a new instance of the fragment i'm navigating to
     */
    public void loadFragInstantly(Fragment mFrag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, mFrag)
                .addToBackStack("loadFragInstantly via replace").commit();


    }

    /**
     * Purpose:
     */
    private void loadFrags_Dynamically(boolean isLargeScreen, Fragment frag1, Fragment frag2,
                                       int frame1, int frame2) {

        /** load fragment resources differently based on screen size  */
        if (isLargeScreen) {
            /** Load Fragment 1 */
            getSupportFragmentManager().beginTransaction().add(
                    frame2, frag2
            ).addToBackStack("transaction:frag1inframe").commit();

            /** Load Fragment 2 */
            getSupportFragmentManager().beginTransaction().add(
                    frame1, frag1
            ).addToBackStack("transaction:frag2inframe2").commit();


        } else {


            getSupportFragmentManager().beginTransaction().add(
                    frame1, frag1
            ).addToBackStack("transactoin:singlereplacement").commit();
        }

        // unconditional steps go here. Runs every time fragment transactions occur.

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.frame, new SettingsFrag())
//                        .commit();
//
//                getSupportFragmentManager().beginTransaction().
//                        remove(R.id.frame, new SettingsFrag()).commit()

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_overview) {
            loadFrags_Dynamically(isLargeScreen(getApplicationContext()), new OverviewFrag(),
                    new ConsumptionDetailFrag(), R.id.frame, R.id.frame2);
            //loadFragInstantly(new OverviewFrag());
        } else if (id == R.id.nav_consumption) {
            loadFragInstantly(new ConsumptionFrag());
        } else if (id == R.id.nav_purchases) {
            loadFragInstantly(new PurchasesFrag());
        } else if (id == R.id.nav_goals) {
            loadFragInstantly(new GoalsAdjustFrag());
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_my_info) {
            loadFragInstantly(new MyInfoFrag());
        } else if (id == R.id.nav_help) {
            loadFragInstantly(new HelpFrag());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    public void onBackStackChanged() {
//
//        Log.i("Change", "Change");
//        int count = fm.getBackStackEntryCount();
//
//        for (int i = count-1; i >= 0; i--) {
//            FragmentManager.BackStackEntry entry = fm.getBackStackEntryAt(i);
//            Log.i(BS_TAG, "" + entry.getName() + " ("
//                    + String.valueOf(i + 1) + "/"
//                    + String.valueOf(count) + ")");
//        }
//
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//    }

}

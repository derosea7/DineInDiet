package com.dooragami.dineindiet;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by derosea7 on 1/23/2016.
 */
public class PurchasesFrag extends Fragment {

    DBAdapter myDB;
    ListView lvPurch;

    Calendar calPurchaseDate;

    ImageView ivPurch_DateIncrement;
    ImageView ivPurch_DateDecrement;
    TextView tvPurch_HeaderDate;

//    private String dateFormatDisplay;
    private SimpleDateFormat sdf;

    private SimpleDateFormat sdfDB;
    private String strDateOfPurchase_db;

    DecimalFormat currency = new DecimalFormat("$###,##0.00");


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.purchases_frag, container, false);

        if (!MainActivity.buDeggin) {
            // [START shared_tracker]
            // Obtain the shared Tracker instance.
            Analytics application = (Analytics) getActivity().getApplication();
            mTracker = application.getDefaultTracker();
            // [END shared_tracker]

            sendScreenName();

            int intAdMobsGender = getActivity()
                    .getSharedPreferences(MainActivity.SHRD_PREFS_SETUP, Context.MODE_PRIVATE)
                    .getInt(MainActivity.STR_GENDER_ADMOBS, 0); // where 0 is unknknow for admobs

            AdView adViewBanner = (AdView) view.findViewById(R.id.adViewBanner_AddPurch);
            AdRequest adRequest = new
                    AdRequest.Builder()
                    //.addTestDevice("C0C8EFF2ECB4B49B5E3B300DCFB6BB64")
                    .setGender(intAdMobsGender)
                    .build();
            adViewBanner.loadAd(adRequest);
        }

        calPurchaseDate = Calendar.getInstance();
        sdf = new SimpleDateFormat(MainActivity.DATE_FORMAT_DISPLAY, Locale.US);
        sdfDB = new SimpleDateFormat(MainActivity.DATE_FORMAT_DB, Locale.US);

        ivPurch_DateIncrement = (ImageView) view.findViewById(R.id.ivPurch_DateIncrement);
        ivPurch_DateDecrement = (ImageView) view.findViewById(R.id.ivPurch_DateDecrement);
        tvPurch_HeaderDate = (TextView) view.findViewById(R.id.tvPurch_HeaderDate);

        // set the Header Date to the current time
        tvPurch_HeaderDate.setText(sdf.format(calPurchaseDate.getTime()));
        strDateOfPurchase_db = "'" + sdfDB.format(calPurchaseDate.getTime()) + "'";

        // Increase the date show on the header, which indicates date of purchase
        ivPurch_DateIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calPurchaseDate.add(Calendar.DAY_OF_MONTH, 1);
                tvPurch_HeaderDate.setText(sdf.format(calPurchaseDate.getTime()));
                // save time in SQL DATE format
                strDateOfPurchase_db = "'" + sdfDB.format(calPurchaseDate.getTime()) + "'";
                populateListView_Purchase(strDateOfPurchase_db);
            }
        });

        // Decrease the date show on the header, which indicates date of purchase
        ivPurch_DateDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calPurchaseDate.add(Calendar.DAY_OF_MONTH, -1);
                tvPurch_HeaderDate.setText(sdf.format(calPurchaseDate.getTime()));
                // save time in SQL DATE format
                strDateOfPurchase_db = "'" + sdfDB.format(calPurchaseDate.getTime()) + "'";
                populateListView_Purchase(strDateOfPurchase_db);
            }
        });

        lvPurch = (ListView) view.findViewById(R.id.lvPurch);
        // Log.i(TAG, "onCreate pre-openDB, pre-populateListView_Purchase");
        openDB();
        populateListView_Purchase(strDateOfPurchase_db);
        listViewItemLongClick();

        Button btnAddPurch = (Button) view.findViewById(R.id.btnAddPurch);
        btnAddPurch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventHit(MainActivity.FEATURE_USE, "Add Purch");
                PurchasesDetailFrag purch = new PurchasesDetailFrag();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                // fm.popBackStack();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame, purch);
                ft.addToBackStack("loadMainPagerFrag via replace");
                ft.commit();
            }
        });



        return view;
    }

    private void eventHit(String category, String action) {
        if (!MainActivity.buDeggin) {
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category) // ex: "Button"
                    .setAction(action) // ex: "Click"
                    .build());
        }
    }

    /**
     * Sets up a SimpleCursorAdapter with a Result Set from an SQLite query specifying DateOfPurchase
     * @param whereDATE a string formated in SQLite DATE format for WHERE clause; DateOfPurchase
     */
    private void populateListView_Purchase(String whereDATE) {

        // query that sorts results by foodname
        Cursor cursor = myDB.getPurchasesByDate(whereDATE);

        String[] fromFieldNames = new String[] {DBAdapter.KEY_PURCH_FOODNAME,
                DBAdapter.KEY_PURCH_PLACEOFPURCHASE, DBAdapter.KEY_PURCH_COST,
                DBAdapter.KEY_PURCH_SERVINGSIZE, DBAdapter.KEY_PURCH_SERVINGS,
                DBAdapter.KEY_PURCH_TOTALFAT, DBAdapter.KEY_PURCH_TOTALCARBS,
                DBAdapter.KEY_PURCH_PROTEIN};

        int[] toViewIDs = new int[] {R.id.tvPurchaseLayout_FoodName,
                R.id.tvPurchaseLayout_Place, R.id.tvPurchaseLayout_Cost,
                R.id.tvPurchaseLayout_ServingSize, R.id.tvPurchaseLayout_Servings,
                R.id.tvPurchaseLayout_TotalFat, R.id.tvPurchaseLayout_TotalCarbs,
                R.id.tvPurchaseLayout_Protein};

        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(),
                R.layout.purchases_list_layout, cursor, fromFieldNames, toViewIDs);

        /**
         * Sets the TextView of a the cost TextView to show the currency.format.. the $ sign
         */
        myCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

            /**
             * This method allows me to @Override the way the SimpleCursorAdapter
             * dishes out values to the Views -- in this case I want the cost to
             * be formatted in with a dollar sign, but don't want to have to store
             * it in the DB that way.
             * @param view - as each View is set, I can access it via this variable
             * @param cursor - returns the cursor I specified when creating this adapter
             * @param columnIndex - returns the columnIndex BASED ON CURSOR popuating adapter
             * @return
             */
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                // since this cursor has all keys, can use the constant COL_PURCH_COST
                if (columnIndex == DBAdapter.COL_PURCH_COST) {
                    TextView tvCost = (TextView) view;
                    tvCost.setText(String.valueOf(currency.format(cursor.getDouble(columnIndex))));
                    return true;
                }

                return false;
            }
        });

        lvPurch.setAdapter(myCursorAdapter);
        // cursor.close();

    }

    private Tracker mTracker;

    private void sendScreenName() {
        if (!MainActivity.buDeggin) {
            String screenName = "ConsDialog";

            // [START screen_view_hit]
            mTracker.setScreenName("Screen~" + screenName);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
            // [END screen_view_hit]
        }
    }

    private ActionMode mActionMode;
    long dbRowIDClicked;
    private void listViewItemLongClick() {
        lvPurch.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                MyActionModeCallback callback = new MyActionModeCallback();
                mActionMode = getActivity().startActionMode(callback);
                mActionMode.setTitle("Edit List");
                dbRowIDClicked = id;
                return true;
            }
        });
    }

    public class MyActionModeCallback implements ActionMode.Callback{

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_context_purchase, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.removeItem_Purchase:
                    // run deleteSpecifiedRow()
                    // run populateListViewAgain()
                    myDB.deleteRowByTable(dbRowIDClicked, myDB.TBL_PURCH, myDB.KEY_PURCH_ROWID);
                    populateListView_Purchase(strDateOfPurchase_db);
                    mode.finish();
                    return true;
                case R.id.clearSelection_Purchase:
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    // getWritable
    private void openDB() {
        myDB = new DBAdapter(getActivity());
        myDB.open();
    }

    private void closeDB() {
        myDB.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closeDB();
        // Log.i(TAG, "Tab 1 destroyed, closeDB complete");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}

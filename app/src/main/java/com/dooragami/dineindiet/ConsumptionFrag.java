package com.dooragami.dineindiet;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by derosea7 on 1/23/2016.
 */
public class ConsumptionFrag extends Fragment {

    Button btnAddCons;

    DBAdapter myDB;
    ListView lvCons;

    Calendar calConsDate;

    ImageView ivCons_DateIncrement;
    ImageView ivCons_DateDecrement;
    TextView tvCons_HeaderDate;

    // private String dateFormatDisplay;
    private SimpleDateFormat sdf;

    private SimpleDateFormat sdfDB;
    private String strDateOfCons_db;

    DecimalFormat currency = new DecimalFormat("$###,###.00");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consumption_frag, container, false);

        if (!MainActivity.buDeggin) {
            // [START shared_tracker]
            // Obtain the shared Tracker instance.
            Analytics application = (Analytics) getActivity().getApplication();
            mTracker = application.getDefaultTracker();
            // [END shared_tracker]

            sendScreenName();
        }

        calConsDate = Calendar.getInstance();

        ivCons_DateIncrement = (ImageView) view.findViewById(R.id.ivCons_DateIncrement);
        ivCons_DateDecrement = (ImageView) view.findViewById(R.id.ivCons_DateDecrement);
        tvCons_HeaderDate = (TextView) view.findViewById(R.id.tvCons_HeaderDate);

        sdf = new SimpleDateFormat(MainActivity.DATE_FORMAT_DISPLAY, Locale.US);
        sdfDB = new SimpleDateFormat(MainActivity.DATE_FORMAT_DB, Locale.US);

        tvCons_HeaderDate.setText(sdf.format(calConsDate.getTime()));
        // passed into SQLite query
        strDateOfCons_db = "'" + sdfDB.format(calConsDate.getTime()) + "'";

        // Increase the date show on the header, which indicates date of purchase
        ivCons_DateIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calConsDate.add(Calendar.DAY_OF_MONTH, 1);
                tvCons_HeaderDate.setText(sdf.format(calConsDate.getTime()));
                // save time in SQL DATE format
                strDateOfCons_db = "'" + sdfDB.format(calConsDate.getTime()) + "'";
                populateListView_Cons(strDateOfCons_db);
            }
        });

        ivCons_DateDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calConsDate.add(Calendar.DAY_OF_MONTH, -1);
                tvCons_HeaderDate.setText(sdf.format(calConsDate.getTime()));
                // save time in SQL DATE format
                strDateOfCons_db = "'" + sdfDB.format(calConsDate.getTime()) + "'";
                populateListView_Cons(strDateOfCons_db);
            }
        });

        openDB();

        lvCons = (ListView) view.findViewById(R.id.lvCons);

        populateListView_Cons(strDateOfCons_db);

        btnAddCons = (Button) view.findViewById(R.id.btnAddCons);
        btnAddCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.buDeggin) {
                    eventHit(MainActivity.FEATURE_USE, "Click Add Cons");
                }
                loadConsumptionDetailFrag();
            }
        });

        listViewItemLongClick();

        return view;
    }

    private Tracker mTracker;

    private void sendScreenName() {
        String screenName = "SetupActivity";

        // [START screen_view_hit]
        mTracker.setScreenName("Screen~" + screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }

    private void eventHit(String category, String action){
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category) // ex: "Button"
                .setAction(action) // ex: "Click"
                .build());
    }

    /**
     * Sets up a SimpleCursorAdapter with a Result Set from an SQLite query specifying DateOfCons
     * @param whereDATE a string formated in SQLite DATE format for WHERE clause; DateOfCons
     */
    private void populateListView_Cons(String whereDATE) {

        // query that sorts results by foodname
        Cursor cursor = myDB.getConsByDate(whereDATE);

        String[] fromFieldNames = new String[] {DBAdapter.KEY_CONS_FOODNAME,
                DBAdapter.KEY_CONS_PLACEOFPURCHASE, DBAdapter.KEY_CONS_COSTOFCONS,
                DBAdapter.KEY_CONS_SERVINGSIZE, DBAdapter.KEY_CONS_SERVINGS,
                DBAdapter.KEY_CONS_TOTALFAT, DBAdapter.KEY_CONS_TOTALCARBS,
                DBAdapter.KEY_CONS_PROTEIN};

        int[] toViewIDs = new int[] {R.id.tvConsLayout_FoodName,
                R.id.tvConsLayout_Place, R.id.tvConsLayout_CostOfCons,
                R.id.tvConsLayout_ServingSize, R.id.tvConsLayout_Servings,
                R.id.tvConsLayout_TotalFat, R.id.tvConsLayout_TotalCarbs,
                R.id.tvConsLayout_Protein};

        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(),
                R.layout.consumption_list_layout, cursor, fromFieldNames, toViewIDs);

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
                if (columnIndex == DBAdapter.COL_CONS_COSTOFCONS) {
                    TextView tvCost = (TextView) view;
                    tvCost.setText(String.valueOf(currency.format(cursor.getDouble(columnIndex))));
                    return true;
                }

                return false;
            }
        });

        lvCons.setAdapter(myCursorAdapter);
        // cursor.close();

    }

    private ActionMode mActionMode;
    long dbRowIDClicked;
    private void listViewItemLongClick() {
        lvCons.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MyActionModeCallback callback = new MyActionModeCallback();
                mActionMode = getActivity().startActionMode(callback);
                mActionMode.setTitle("Edit Consumption List");
                dbRowIDClicked = id;
                return true;
            }
        });
    }

    public class MyActionModeCallback implements ActionMode.Callback{
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // seems like it should be ok to reuse this xml menu file
            // TODO: monitor this incase it causes any issues
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
                    myDB.deleteRowByTable(dbRowIDClicked, myDB.TBL_CONS, myDB.KEY_CONS_ROWID);
                    populateListView_Cons(strDateOfCons_db);
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

    public void loadConsumptionDetailFrag() {

        ConsumptionDetailFrag consDetail = new ConsumptionDetailFrag();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame, consDetail).commit();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}

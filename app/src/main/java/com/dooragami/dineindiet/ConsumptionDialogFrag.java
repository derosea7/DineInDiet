package com.dooragami.dineindiet;

//import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.DecimalFormat;

/**
 * Created by derosea7 on 1/17/2016.
 */
public class ConsumptionDialogFrag extends DialogFragment {

    DBAdapter myDB;

    Button btnAddCons_Dismiss;
    ListView lvAddCons_DialogFood;
    SearchView searchView;

    DecimalFormat currency = new DecimalFormat("$###,###.00");

    /**
     * store the row ID of the selected foodname from the dialog's ListView.
     */
    long mID;

    /**
     * initialize the dialog, setOnQueryTextListner for searchView, setOnClickListener for
     * dismiss button, setOnItemClickListener for ListView in dialog that returns a row ID
     * which I then query to retreive information about that row (name, cost, calories, etc.)
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consumption_dialog_frag, null);
        // get refs to Views upon which I'll affect changes to based on item chosen in dialog
        getDialog().setTitle("Choose Food");
        lvAddCons_DialogFood = (ListView) view.findViewById(R.id.lvAddCons_DialogFood);
        btnAddCons_Dismiss = (Button) view.findViewById(R.id.btnAddCons_Dismiss);
        searchView = (SearchView) view.findViewById(R.id.searchView);
        openDB();
        populateLV_ConsumptionDialog();
        searchView.setQueryHint("Search saved foods..");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myCursorAdapter.getFilter().filter(newText);
                return false;
            }
        });
        // set on click listener for button
        btnAddCons_Dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // store foodname selected in a variable and pass that to a TextView on the screen,
        // then populate basic nutritional facts about that food to allow user to add that
        // consumption
        lvAddCons_DialogFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mID = id;
                Toast.makeText(getActivity().getBaseContext(), "mID = " + mID + ", " +
                        "position = " + position, Toast.LENGTH_SHORT).show();
                // this method stores the mId in an intent, then passes it back
                // to the TargetFragment.
                passBackRowId(mID);
                dismiss();
            }
        });

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        Analytics application = (Analytics) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]

        sendScreenName();

        return view;
    }

    private Tracker mTracker;

    private void sendScreenName() {
        String screenName = "ConsDialog";

        // [START screen_view_hit]
        mTracker.setScreenName("Screen~" + screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }

    public static final String ROWID_PICKED_INTENT_KEY = "ROWID_PICKED_INTENT_KEY";
    public static final int ROWID_PICKED_RESULT_CODE = 123;

    private void passBackRowId(long RowId) {
        Intent intent = new Intent();
        intent.putExtra(ROWID_PICKED_INTENT_KEY, RowId);
        getTargetFragment().onActivityResult(getTargetRequestCode(), ROWID_PICKED_RESULT_CODE, intent);
    }

    SimpleCursorAdapter myCursorAdapter;

    private void populateLV_ConsumptionDialog() {
        Cursor cursor = myDB.getFoodnamesForDialog();
        String[] fromFieldNames = new String[] {DBAdapter.KEY_PURCH_FOODNAME,
                DBAdapter.KEY_PURCH_DATEOFPURCHASE, DBAdapter.KEY_PURCH_COST};
        int[] toViewIDs = new int[] {R.id.tvConsDialog_Foodname, R.id.tvConsDialog_Date,
                R.id.tvConsDialog_Cost};

        myCursorAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(),
                R.layout.consumption_dialog_list_layout, cursor, fromFieldNames, toViewIDs);

        myCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

                if (columnIndex == 3) {
                    TextView tvCost = (TextView) view;
                    tvCost.setText(String.valueOf(currency.format(cursor.getDouble(columnIndex))));
                    return true;
                }

                return false;
            }
        });

        lvAddCons_DialogFood.setAdapter(myCursorAdapter);
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
    }
}
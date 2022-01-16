package com.dooragami.dineindiet;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class PurchasesDetailFrag extends Fragment {

    DBAdapter myDB;

    EditText etAddPurch_Foodname;
    EditText etAddPurch_PlaceOfPurchase;
    Button etAddPurch_DateOfPurchase;
    SeekBar sbAddPurch_Dollars;
    SeekBar sbAddPurch_Cents;
    EditText etPurch_ServingSize;
    EditText etPurch_Servings;
    EditText etPurch_TotalFat;
    EditText etPurch_TotalCarbs;
    EditText etPurch_Protein;

    TextView lblAddPurch_Cost;

    Calendar calPurchaseDate;

    private String dateFormatDisplay;
    private SimpleDateFormat sdf;

    private SimpleDateFormat sdfDB;
    private String strDateOfPurchase_db;

    DecimalFormat currency = new DecimalFormat("$###,##0.00");

    // stores cost as input by user into SeekBar
    double mDollarsProgress;
    double mCentsProgress;
    double mCostProgress; // beleive i should be able to check if this = 0 w/o initializing it to 0
    String mCurrentDate; // use this to store Entry Date (Date Added) record for db
    private Button btnAddPurch;
    private boolean blnDateChosen = false;
    private boolean mFormReady = false;

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
        View view = inflater.inflate(R.layout.purchases_detail_frag, container, false);

        if (!MainActivity.buDeggin) {
            // [START shared_tracker]
            // Obtain the shared Tracker instance.
            Analytics application = (Analytics) getActivity().getApplication();
            mTracker = application.getDefaultTracker();
            // [END shared_tracker]
            sendScreenName();
        }

        openDB();

        calPurchaseDate = Calendar.getInstance();

        sdf = new SimpleDateFormat(MainActivity.DATE_FORMAT_DISPLAY, Locale.US);
        sdfDB = new SimpleDateFormat(MainActivity.DATE_FORMAT_DB, Locale.US);

        mCurrentDate = sdfDB.format(calPurchaseDate.getTime());

        etAddPurch_Foodname = (EditText) view.findViewById(R.id.etAddPurch_Foodname);
        etAddPurch_PlaceOfPurchase = (EditText) view.findViewById(R.id.etAddPurch_PlaceOfPurchase);
        etAddPurch_DateOfPurchase = (Button) view.findViewById(R.id.etAddPurch_DateOfPurchase);
        sbAddPurch_Dollars = (SeekBar) view.findViewById(R.id.sbAddPurch_Dollars);
        sbAddPurch_Cents = (SeekBar) view.findViewById(R.id.sbAddPurch_Cents);
        etPurch_ServingSize = (EditText) view.findViewById(R.id.etPurch_ServingSize);
        etPurch_Servings = (EditText) view.findViewById(R.id.etPurch_Servings);
        etPurch_TotalFat = (EditText) view.findViewById(R.id.etPurch_TotalFat);
        etPurch_TotalCarbs = (EditText) view.findViewById(R.id.etPurch_TotalCarbs);
        etPurch_Protein = (EditText) view.findViewById(R.id.etPurch_Protein);
        lblAddPurch_Cost = (TextView) view.findViewById(R.id.lblAddPurch_Cost);

        btnAddPurch = (Button) view.findViewById(R.id.btnAddPurch);

        // sets up listener for btnAddPurch, which puts info into db
        addPurchaseListener();

        // sets listener that launches DatePickerDialog
        dateOfPurchaseListener();

        // set up SeekBar listeners for Cost
        setSbCost();

        return view;
    }


    private void eventHit(String category, String action, String label, long lngValue) {
        if (!MainActivity.buDeggin) {
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category) // ex: "Button"
                    .setAction(action) // ex: "Click"
                    .setLabel(label) // ex: "CoolButton"
                    .setValue(lngValue)
                    .build());
        }
    }

    private Tracker mTracker;

    private void sendScreenName() {
        if (!MainActivity.buDeggin) {
        }else {
            String screenName = "PurchDetail";

            // [START screen_view_hit]
            mTracker.setScreenName("Screen~" + screenName);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
            // [END screen_view_hit]
        }
    }

    public void setSbCost() {
        // calculates the dollar portion of Cost via a SeekBar then adds to the lblAddPurch_Cost
        sbAddPurch_Dollars.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mDollarsProgress = (double) progress;
                mCostProgress = mDollarsProgress + mCentsProgress;
                lblAddPurch_Cost.setText(String.valueOf(currency.format(mCostProgress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // calculates the change portion of Cost via a SeekBar then adds to the lblAddPurch_Cost
        sbAddPurch_Cents.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCentsProgress = (double) progress / 100;
                mCostProgress = mCentsProgress + mDollarsProgress;
                lblAddPurch_Cost.setText(String.valueOf(currency.format(mCostProgress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void clearFormAfterSave() {

        etAddPurch_Foodname.setText("");
        etAddPurch_Foodname.setHint("Food name*");

        etAddPurch_PlaceOfPurchase.setText("");
        etAddPurch_PlaceOfPurchase.setHint("Place purchased*");

        etAddPurch_DateOfPurchase.setText("Date purchased*");

        sbAddPurch_Dollars.setProgress(0);
        sbAddPurch_Cents.setProgress(0);
        lblAddPurch_Cost.setText("Cost*");

        etPurch_ServingSize.setText("");
        etPurch_ServingSize.setHint("Serving Size*");

        etPurch_Servings.setText("");
        etPurch_Servings.setHint("Servings*");

        etPurch_TotalFat.setText("");
        etPurch_TotalFat.setHint("Total Fat");

        etPurch_TotalCarbs.setText("");
        etPurch_TotalCarbs.setHint("Total Carbs");

        etPurch_Protein.setText("");
        etPurch_Protein.setHint("Protein");

        mFormReady = false;

    }

    // sets lisnter that launches DatePickerDialog
    public void dateOfPurchaseListener() {
        etAddPurch_DateOfPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDialogFrag();
            }
        });
    }

    // set listener for btnAddPurch
    public void addPurchaseListener() {
        // TODO: set restrictiion so that blank records can't be entered
        btnAddPurch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckFormReadiness();
                if (mFormReady) {
                    eventHit(MainActivity.FEATURE_USE, "Add Purch", "Cost", (long) mCostProgress);
                    setCVS_AddPurch();
                    Toast.makeText(getActivity(), "Successfully saved!", Toast.LENGTH_LONG).show();
                    // TODO: clear out Views once added
                    clearFormAfterSave();
                } else {
                    Toast.makeText(getActivity(), "All fields with an * must be filled out",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void mCheckFormReadiness() {
        if (isEmpty(etAddPurch_Foodname) || isEmpty(etAddPurch_PlaceOfPurchase)
                || !blnDateChosen || mCostProgress == 0 || isEmpty(etPurch_ServingSize)
                || isEmpty(etPurch_Servings)) {
            // then the rqr'd fields are filled out
            mFormReady = false;
        } else {
            mFormReady = true;
        }
    }

    public final static int REQUEST_CODE_PURCHASE_DATE = 2016;

    private void launchDialogFrag() {
        DialogFragment myDatePickerFragment = new MyDatePickerFragment();

        myDatePickerFragment.setTargetFragment(this, REQUEST_CODE_PURCHASE_DATE);
        myDatePickerFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

    }

    /**
     * This mehtod is called from the Consumption_DialogFragFoods dialog
     * @param requestCode 123, as set in the dialog
     * @param resultCode "ROWID_PICKED_RESULT_CODE" as set in the dialog
     * @param data an intent that stores the RowId as a long
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PURCHASE_DATE &&
                resultCode == MyDatePickerFragment.RESULT_CODE_DATEDIALOG) {
            //long myRowId = data.getLongExtra(MyDatePickerFragment.ROWID_PICKED_INTENT_KEY_DATE, 1);

            int yearPicked = data.getIntExtra(MyDatePickerFragment.KEY_PURCH_DATE_YEAR, 1);
            int monthPicked = data.getIntExtra(MyDatePickerFragment.KEY_PURCH_DATE_MONTH, 1);
            int dayPicked = data.getIntExtra(MyDatePickerFragment.KEY_PURCH_DATE_DAY, 1);

            calPurchaseDate.set(yearPicked, monthPicked, dayPicked);
            String dateChosen = sdf.format(calPurchaseDate.getTime());

            etAddPurch_DateOfPurchase.setText(dateChosen);
            strDateOfPurchase_db = sdfDB.format(calPurchaseDate.getTime());

            blnDateChosen = true;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    ContentValues cvs;
    public void setCVS_AddPurch() {

        cvs = new ContentValues();
        cvs.put(myDB.KEY_PURCH_FOODNAME, etAddPurch_Foodname.getText().toString());
        cvs.put(myDB.KEY_PURCH_PLACEOFPURCHASE, etAddPurch_PlaceOfPurchase.getText().toString());
        cvs.put(myDB.KEY_PURCH_DATEOFPURCHASE, strDateOfPurchase_db);

        cvs.put(myDB.KEY_PURCH_DATEADDED, mCurrentDate);

        cvs.put(myDB.KEY_PURCH_COST, String.valueOf(mCostProgress));
        cvs.put(myDB.KEY_PURCH_SERVINGSIZE, etPurch_ServingSize.getText().toString());
        cvs.put(myDB.KEY_PURCH_SERVINGS, etPurch_Servings.getText().toString());

        if (isEmpty(etPurch_TotalFat)) {
            cvs.put(myDB.KEY_PURCH_TOTALFAT, 0);
        } else {
            cvs.put(myDB.KEY_PURCH_TOTALFAT, etPurch_TotalFat.getText().toString());
        }

        if (isEmpty(etPurch_TotalCarbs)) {
            cvs.put(myDB.KEY_PURCH_TOTALCARBS, 0);
        } else {
            cvs.put(myDB.KEY_PURCH_TOTALCARBS, etPurch_TotalCarbs.getText().toString());
        }

        if (isEmpty(etPurch_Protein)) {
            cvs.put(myDB.KEY_PURCH_PROTEIN, 0);
        } else {
            cvs.put(myDB.KEY_PURCH_PROTEIN, etPurch_Protein.getText().toString());
        }

        myDB.insertCvsPurch(cvs);

    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
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

}

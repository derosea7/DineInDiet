package com.dooragami.dineindiet;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by derosea7 on 1/23/2016.
 */
public class ConsumptionDetailFrag extends Fragment {

    public static final double SINGLE_SERVING = 1;

    // TODO: think about making sure the 0 before the decimal appears, such as "$#0.00"...
    DecimalFormat currency = new DecimalFormat("$###,###.00");
    DecimalFormat comma = new DecimalFormat("#,###.00");
    DecimalFormat hundredsPlace = new DecimalFormat("#.00");

    TextView tvAddCons_PlaceOfPurchase;
    TextView etAddCons_DateOfPurchase;
    TextView etAddCons_Cost;

    TextView tvPerServ_ServingSize;
    TextView tvPerServ_Servings;
    TextView tvPerServ_TotalFat;
    TextView tvPerServ_TotalCarbs;
    TextView tvPerServ_Protein;
    TextView tvPerServ_Calories;
    TextView tvPerServ_Cost;

    EditText etMyCons_ServingSize;
    EditText etMyCons_Servings;
    TextView tvMyCons_Calories;
    TextView tvMyCons_TotalFat;
    TextView tvMyCons_TotalCarbs;
    TextView tvMyCons_Protein;
    TextView tvMyCons_Cost;

    RadioButton rbMyCons_Servings;
    RadioButton rbMyCons_ServingSize;

    Button btnSaveConsDetail;

    private ActionMode mActionMode;
    Button etAddCons_Foodname;
    private double perServ_servingSize;
    private double perServ_totalFat;
    private double perServ_totalCarbs;
    private double perServ_Protein;
    private double perServ_calories;
    private double perServ_Cost;

    double dblMyCons_TotalFat;
    double dblMyCons_TotalCarbs;
    double dblMyCons_Protein;
    double dblMyCons_Calories;
    double dblMyCons_Cost;

    double dblMyCons_ServingSize;
    double dblMyCons_Servings;

    public static final double KCAL_FAT = 9;
    public static final double KCAL_CARBS = 4;
    public static final double KCAL_PROTEIN = 4;

    DBAdapter myDB;
    Cursor c;

    Calendar calConsDetailDate;

    ImageView ivConsDetail_DateIncrement;
    ImageView ivConsDetails_DateDecrement;
    TextView tvConsDetail_HeaderDate;

    //    private String dateFormatDisplay;
    private SimpleDateFormat sdf;

    private SimpleDateFormat sdfDB;
    private String strDateOfCons_db;
    private static String strDateAdded_db;


    public boolean mMyConsFood_Selected = false;

    InterstitialAd mInterstitialAd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consumption_detail_frag, container, false);

        if (!MainActivity.buDeggin) {
            // [START shared_tracker]
            // Obtain the shared Tracker instance.
            Analytics application = (Analytics) getActivity().getApplication();
            mTracker = application.getDefaultTracker();
            // [END shared_tracker]
            sendScreenName();
        }

        calConsDetailDate = Calendar.getInstance();
        sdf = new SimpleDateFormat(MainActivity.DATE_FORMAT_DISPLAY, Locale.US);
        sdfDB = new SimpleDateFormat(MainActivity.DATE_FORMAT_DB, Locale.US);

        ivConsDetail_DateIncrement = (ImageView) view.findViewById(R.id.ivConsDetail_DateIncrement);
        ivConsDetails_DateDecrement = (ImageView) view.findViewById(R.id.ivConsDetail_DateDecrement);
        tvConsDetail_HeaderDate = (TextView) view.findViewById(R.id.tvConsDetail_HeaderDate);

        tvConsDetail_HeaderDate.setText(sdf.format(calConsDetailDate.getTime()));
        strDateOfCons_db = sdfDB.format(calConsDetailDate.getTime());

        strDateAdded_db = strDateOfCons_db;

        ivConsDetail_DateIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calConsDetailDate.add(Calendar.DAY_OF_MONTH, 1);
                tvConsDetail_HeaderDate.setText(sdf.format(calConsDetailDate.getTime()));
                strDateOfCons_db = sdfDB.format(calConsDetailDate.getTime());
            }
        });

        ivConsDetails_DateDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calConsDetailDate.add(Calendar.DAY_OF_MONTH, -1);
                tvConsDetail_HeaderDate.setText(sdf.format(calConsDetailDate.getTime()));
                strDateOfCons_db = sdfDB.format(calConsDetailDate.getTime());
            }
        });

        openDB();

        btnSaveConsDetail = (Button) view.findViewById(R.id.btnSaveConsDetail);
        etAddCons_Foodname = (Button) view.findViewById(R.id.etAddCons_Foodname);
        onClickToAddFoodName();

        tvAddCons_PlaceOfPurchase = (TextView) view.findViewById(R.id.tvAddCons_PlaceOfPurchase);
        etAddCons_DateOfPurchase = (TextView) view.findViewById(R.id.etAddCons_DateOfPurchase);
        etAddCons_Cost = (TextView) view.findViewById(R.id.etAddCons_Cost);

        tvPerServ_ServingSize = (TextView) view.findViewById(R.id.tvPerServ_ServingSize);
        tvPerServ_Servings = (TextView) view.findViewById(R.id.tvPerServ_Servings);
        tvPerServ_TotalFat = (TextView) view.findViewById(R.id.tvPerServ_TotalFat);
        tvPerServ_TotalCarbs = (TextView) view.findViewById(R.id.tvPerServ_TotalCarbs);
        tvPerServ_Protein = (TextView) view.findViewById(R.id.tvPerServ_Protein);
        tvPerServ_Calories = (TextView) view.findViewById(R.id.tvPerServ_Calories);
        tvPerServ_Cost = (TextView) view.findViewById(R.id.tvPerServ_Cost);

        /**
         *  A C T U A L   C O N S U M P T I O N   R E F E R E N C E S
         */
        etMyCons_ServingSize = (EditText) view.findViewById(R.id.etMyCons_ServingSize);
        etMyCons_Servings = (EditText) view.findViewById(R.id.etMyCons_Servings);
        tvMyCons_Calories = (TextView) view.findViewById(R.id.tvMyCons_Calories);
        tvMyCons_TotalFat = (TextView) view.findViewById(R.id.tvMyCons_TotalFat);
        tvMyCons_TotalCarbs = (TextView) view.findViewById(R.id.tvMyCons_TotalCarbs);
        tvMyCons_Protein = (TextView) view.findViewById(R.id.tvMyCons_Protein);
        tvMyCons_Cost = (TextView) view.findViewById(R.id.tvMyCons_Cost);

        rbMyCons_Servings = (RadioButton) view.findViewById(R.id.rbMyCons_Servings);
        rbMyCons_ServingSize = (RadioButton) view.findViewById(R.id.rbMyCons_ServingSize);

        rbMyCons_Servings.setOnCheckedChangeListener(rdMyConsListner);
        rbMyCons_ServingSize.setOnCheckedChangeListener(rdMyConsListner);

        // disable both Servings and ServingSize until user selects a food
        rbMyCons_ServingSize.setEnabled(false);
        etMyCons_ServingSize.setEnabled(false);

        rbMyCons_Servings.setEnabled(false);
        etMyCons_Servings.setEnabled(false);

//        final AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
//        alphaAnimation.setDuration(2000);

        btnSaveConsDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // may want to add a dialog to confirm that user actually is ready to input
                if (mMyConsFood_Selected && dblMyCons_ServingSize > 0) {
                    putMyConsInDB();
                    eventHit(MainActivity.FEATURE_USE, "Logged Consumption", "Cost", (long) dblMyCons_Cost);
                    if (mInterstitialAd.isLoaded()) {
//                        Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.animations);
//                        tvAddCons_PlaceOfPurchase.startAnimation(fadeOut);
                        mInterstitialAd.show();
                        // Toast.makeText(getActivity(), "Successfully saved!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Successfully saved!", Toast.LENGTH_SHORT).show();
                    }
                } else if (!mMyConsFood_Selected) {
                    Toast.makeText(getActivity(), "Please select a food", Toast.LENGTH_SHORT).show();
                } else if (dblMyCons_ServingSize <= 0) { //
                    Toast.makeText(getActivity(), "Please enter amount eaten",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-5538618854621637/6907555108");

        // don't think i'll need the listener, as i intend to only show an i.s. once per user sess
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // show this toast becuase add would be closed after succesful save
                Toast.makeText(getActivity(), "Successfully saved!", Toast.LENGTH_LONG).show();
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();

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

    private void requestNewInterstitial() {
        if (!MainActivity.buDeggin) {
            int intAdMobsGender = getActivity()
                    .getSharedPreferences(MainActivity.SHRD_PREFS_SETUP, Context.MODE_PRIVATE)
                    .getInt(MainActivity.STR_GENDER_ADMOBS, 0); // where 0 is unknknow for admobs

            AdRequest adRequest = new AdRequest.Builder()
                    //.addTestDevice("C0C8EFF2ECB4B49B5E3B300DCFB6BB64")
                    .setGender(intAdMobsGender)
                    .build();

            mInterstitialAd.loadAd(adRequest);
        }
    }

    private Tracker mTracker;

    private void sendScreenName() {
        if (!MainActivity.buDeggin) {
            String screenName = "ConsDetail";

            // [START screen_view_hit]
            mTracker.setScreenName("Screen~" + screenName);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
            // [END screen_view_hit]
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void onClickToAddFoodName() {
        etAddCons_Foodname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDialogFrag();
            }
        });

    }

    public final static String KEY_TEST_BUNDLE = "Key Test Bundle";
    public final static int ORIGINATORS_REQUEST_CODE = 3016;
    private void launchDialogFrag() {

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ConsumptionDialogFrag consumptionDialog = new ConsumptionDialogFrag();

        Bundle extras = new Bundle();
        long lngTestExtra = 2016;
        extras.putLong(KEY_TEST_BUNDLE, lngTestExtra);
        consumptionDialog.setArguments(extras);
        consumptionDialog.setTargetFragment(this, ORIGINATORS_REQUEST_CODE);
        consumptionDialog.show(fragmentManager, "tag");

    }

    /**
     * This mehtod is called from the Consumption_DialogFragFoods dialog
     * @param requestCode 123, as set in the dialog
     * @param resultCode "ROWID_PICKED_RESULT_CODE" as set in the dialog
     * @param data an intent that stores the RowId as a long
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ORIGINATORS_REQUEST_CODE &&
                resultCode == ConsumptionDialogFrag.ROWID_PICKED_RESULT_CODE) {
            long myRowId = data.getLongExtra(ConsumptionDialogFrag.ROWID_PICKED_INTENT_KEY, 1);
            receiveRowId(myRowId);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * Processes the RowId returned by onActivityResult and populates the proper Views
     * @param RowId culled from hours, upon hours, of SO research and attempted implementation
     */
    public void receiveRowId(long RowId) {

        mMyConsFood_Selected = true;

        // now allow user to input ServingSize or select rb of Servings
        rbMyCons_ServingSize.setEnabled(true);
        rbMyCons_Servings.setEnabled(true);
        etMyCons_ServingSize.setEnabled(true);

        c = myDB.getInfoToAddConsumption(RowId);
        // set the various TextViews on this Godforsaken tab
        etAddCons_Foodname.setText(c.getString(myDB.COL_PURCH_FOODNAME));
        tvAddCons_PlaceOfPurchase.setText(c.getString(myDB.COL_PURCH_PLACEOFPURCHASE));

        // TODO: need to be able to take db-formatted date and display nicely to user
        // may be able to do by querying the DATE using an SQL function..........
        etAddCons_DateOfPurchase.setText(c.getString(myDB.COL_PURCH_DATEOFPURCHASE));

        double purch_Cost = c.getDouble(myDB.COL_PURCH_COST);
        etAddCons_Cost.setText(String.valueOf(currency.format(purch_Cost)));

        perServ_servingSize = c.getDouble(myDB.COL_PURCH_SERVINGSIZE);
        tvPerServ_ServingSize.setText(String.valueOf(perServ_servingSize));


        tvPerServ_Servings.setText(String.valueOf(SINGLE_SERVING));

        perServ_totalFat = c.getDouble(myDB.COL_PURCH_TOTALFAT);
        tvPerServ_TotalFat.setText(String.valueOf(perServ_totalFat));

        perServ_totalCarbs = c.getDouble(myDB.COL_PURCH_TOTALCARBS);
        tvPerServ_TotalCarbs.setText(String.valueOf(perServ_totalCarbs));

        perServ_Protein = c.getDouble(myDB.COL_PURCH_PROTEIN);
        tvPerServ_Protein.setText(String.valueOf(perServ_Protein));

        double purch_Servings = c.getDouble(myDB.COL_PURCH_SERVINGS);

        perServ_calories = findCalories(
                perServ_totalFat, perServ_totalCarbs, perServ_Protein, SINGLE_SERVING);
        tvPerServ_Calories.setText(String.valueOf(perServ_calories));

        perServ_Cost = purch_Cost / purch_Servings;
        tvPerServ_Cost.setText(String.valueOf(currency.format(perServ_Cost)));

        servingsInputListener();
        servingSizeInputListener();

    }



    /**
     * updates double values that hold servings and serving size, then kicks off update of TextViews
     */
    public void servingsInputListener() {

        etMyCons_Servings.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {
                    // THEN user has backspaced to where the CharSequence = ""
                    // get java.lang.NumberFormatException: Invalid double: ""
                    dblMyCons_Servings = 0;
                    // may also need to set ServingSize = 0...........
                } else {
                    dblMyCons_Servings = Double.parseDouble(String.valueOf(s));
                    // tvMyCons_TotalFat.setText(String.valueOf(dblServings));
                    dblMyCons_ServingSize =
                            perServ_servingSize * (dblMyCons_Servings / SINGLE_SERVING);
                    updateActualConsumption(dblMyCons_Servings, dblMyCons_ServingSize);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * updates double values that hold servings and serving size, then kicks of update of TextViews
     */
    public void servingSizeInputListener() {
        etMyCons_ServingSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {
                    // THEN user has backspaced to where the CharSequence = ""
                    // get java.lang.NumberFormatException: Invalid double: ""
                    dblMyCons_ServingSize = 0;
                    // may just want a function that zeros out My Consumption
                } else {
                    dblMyCons_ServingSize = Double.parseDouble(String.valueOf(s));
//                    tvMyCons_TotalFat.setText(String.valueOf(dblMyCons_ServingSize));
                    dblMyCons_Servings = dblMyCons_ServingSize / perServ_servingSize;

                    updateActualConsumption(dblMyCons_Servings, dblMyCons_ServingSize);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * Sets up UI Views with newly calculated information after user updates amount consumed
     * These views are all nicely updated with comma formatting, while ServingSize and Servings arnt
     * TODO: consider that comma fomratting may cause issues in db ("2,000.00" .. not sure about ",")
     * @param actualServings
     * @param actualSize
     */
    public void updateActualConsumption(double actualServings, double actualSize) {
        if (mMyConsFood_Selected) {
            dblMyCons_TotalFat = perServ_totalFat * actualServings;
            tvMyCons_TotalFat.setText(String.valueOf(comma.format(dblMyCons_TotalFat)));

            dblMyCons_TotalCarbs = perServ_totalCarbs * actualServings;
            tvMyCons_TotalCarbs.setText(String.valueOf(comma.format(dblMyCons_TotalCarbs)));

            dblMyCons_Protein = perServ_Protein * actualServings;
            tvMyCons_Protein.setText(String.valueOf(comma.format(dblMyCons_Protein)));

            dblMyCons_Calories = perServ_calories * actualServings;
            tvMyCons_Calories.setText(String.valueOf(comma.format(dblMyCons_Calories)));

            if (actualServings != 0) {
                dblMyCons_Cost = perServ_Cost * actualServings;
                tvMyCons_Cost.setText(String.valueOf(currency.format(dblMyCons_Cost)));
            } else {
                tvMyCons_Cost.setText(String.valueOf(currency.format(0)));
            } //did this becuase was showing infinity symb

        }

    }

    // TODO: think about ways to make this db more relational to save space
    public void putMyConsInDB() {
        ContentValues cvs;

        cvs = new ContentValues();
        cvs.put(myDB.KEY_CONS_FOODNAME, etAddCons_Foodname.getText().toString());
        cvs.put(myDB.KEY_CONS_PLACEOFPURCHASE, tvAddCons_PlaceOfPurchase.getText().toString());
        cvs.put(myDB.KEY_CONS_DATEOFPURCHASE, etAddCons_DateOfPurchase.getText().toString());

        // this should be an instance of today's date (AND time, but that will come later)
        cvs.put(myDB.KEY_CONS_DATEADDED, strDateAdded_db);

        cvs.put(myDB.KEY_CONS_DATECONSUMED, strDateOfCons_db);

        // need to take unformatted dbl version of cost into DB, or else i take in the "$"
        cvs.put(myDB.KEY_CONS_COSTOFCONS, String.valueOf(dblMyCons_Cost));

        // formatting ServingSize and Servings in "#.00" before cvs.put
        cvs.put(myDB.KEY_CONS_SERVINGSIZE, String.valueOf(hundredsPlace.format(dblMyCons_ServingSize)));
        cvs.put(myDB.KEY_CONS_SERVINGS, String.valueOf(hundredsPlace.format(dblMyCons_Servings)));

        cvs.put(myDB.KEY_CONS_TOTALFAT, tvMyCons_TotalFat.getText().toString());
        cvs.put(myDB.KEY_CONS_TOTALCARBS, tvMyCons_TotalCarbs.getText().toString());
        cvs.put(myDB.KEY_CONS_PROTEIN, tvMyCons_Protein.getText().toString());

        myDB.insertCvsMyCons(cvs);
    }

    /**
     * logic that allows a user to select a radio button indicating whether they want to input
     * EITHER servings or serving size
     */
    private CompoundButton.OnCheckedChangeListener rdMyConsListner =
            new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //boolean checked = ((RadioButton) buttonView).isChecked();

                    // Check which radio button was clicked
                    switch(buttonView.getId()) {
                        case R.id.rbMyCons_Servings:
                            if (isChecked) {
                                // disable Size
                                rbMyCons_ServingSize.setChecked(false);
                                etMyCons_ServingSize.setEnabled(false);

                                // enable Amount
                                rbMyCons_Servings.setChecked(true);
                                etMyCons_Servings.setEnabled(true);

                                // if not empty, repopulate My Consumption values based on
                                // what the user had previously entered in etServings
                                if (!isEmpty(etMyCons_Servings)) {
                                    dblMyCons_Servings =
                                            Double.parseDouble(
                                                    etMyCons_Servings.getText().toString());
                                    dblMyCons_ServingSize =
                                            perServ_servingSize * (dblMyCons_Servings / SINGLE_SERVING);
                                    updateActualConsumption(dblMyCons_Servings, dblMyCons_ServingSize);
                                }// may want else that zeros out My Cons, since isEmpty would = true
                            }

                            break;

                        case R.id.rbMyCons_ServingSize:
                            if (isChecked) {
                                // disable Amount
                                rbMyCons_Servings.setChecked(false);
                                etMyCons_Servings.setEnabled(false);

                                // enable Size
                                rbMyCons_ServingSize.setChecked(true);
                                etMyCons_ServingSize.setEnabled(true);

                                // if not empty, repopulate My Consumption values based on
                                // what the user had previously entered in etServingSize
                                if (!isEmpty(etMyCons_ServingSize)) {
                                    dblMyCons_ServingSize =
                                            Double.parseDouble(
                                                    etMyCons_ServingSize.getText().toString());
                                    dblMyCons_Servings = dblMyCons_ServingSize / perServ_servingSize;
                                    updateActualConsumption(dblMyCons_Servings, dblMyCons_ServingSize);
                                }// may want else that zeros out My Cons, since isEmpty would = true

                            }


                            break;
                    }
                }
            };



    /**
     * checks to see if EditText contains anything
     * @param etText
     * @return
     */
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public static double findCalories(
            double totalFat, double totalCarbs, double protein, double servings) {
        return servings *
                ((totalFat * KCAL_FAT) + (totalCarbs * KCAL_CARBS) + (protein * KCAL_PROTEIN));
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
    }


}

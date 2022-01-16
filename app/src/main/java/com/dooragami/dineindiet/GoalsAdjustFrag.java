package com.dooragami.dineindiet;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dooragami.dineindiet.models.Goal;
import com.dooragami.dineindiet.models.Macro;

import com.dooragami.dineindiet.models.User;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by derosea7 on 2/3/2016.
 */
public class GoalsAdjustFrag extends Fragment {

    DBAdapter myDB;

    Calendar cal = Calendar.getInstance();
    private SimpleDateFormat sdfDB = new SimpleDateFormat(MainActivity.DATE_FORMAT_DB, Locale.US);
    DecimalFormat percent = new DecimalFormat("0.0%");
    DecimalFormat hunPl = new DecimalFormat("#0.00");

    SeekBar sbWeight_Actual;
    TextView tvWeightProgress_Actual;
    //double dblWeightProgress_Actual;

    SeekBar sbWaist_Actual;
    TextView tvWaistProgress_Actual;
    double dblWaistProgress_Actual;

    SeekBar sbActivity_Actual;
    TextView tvActivityProgress_Actual;
    int intActivityProgress_Actual;

    SeekBar sbWeight_Goal;
    TextView tvWeightProgress_Goal;
    double dblWeightProgress_Goal;

    SeekBar sbWaist_Goal;
    TextView tvWaistProgress_Goal;
    double dblWaistProgress_Goal;

    SeekBar sbFoodExpense_Goal;
    TextView tvFoodExpenseProgress_Goal;
    double dblFoodExpenseProgress_Goal;

    SeekBar sbTotalFat_Macro;
    TextView tvTotalFatProgress_Macro;
    double dblTotalFatProgress_Macro;

    SeekBar sbTotalCarbs_Macro;
    TextView tvTotalCarbsProgress_Macro;
    double dblTotalCarbsProgress_Macro;

    SeekBar sbProtein_Macro;
    TextView tvProteinProgress_Macro;
    double dblProteinProgress_Macro;

    Button btnSaveGoals;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goals_adjust_frag, container, false);

        user = getUserInfo();

        if (!MainActivity.buDeggin) {
            // [START shared_tracker]
            // Obtain the shared Tracker instance.
            Analytics application = (Analytics) getActivity().getApplication();
            mTracker = application.getDefaultTracker();
            // [END shared_tracker]

            sendScreenName();
        }

        // TODO: load previously saved goals when first opening this thing

        tvWeightProgress_Actual = (TextView) view.findViewById(R.id.tvWeightProgress_Actual);
        sbWeight_Actual = (SeekBar) view.findViewById(R.id.sbWeight_Actual);
        setupWeightActual();

        tvWaistProgress_Actual = (TextView) view.findViewById(R.id.tvWaistProgress_Actual);
        sbWaist_Actual = (SeekBar) view.findViewById(R.id.sbWaist_Actual);
        setupWaistActual();

        tvActivityProgress_Actual = (TextView) view.findViewById(R.id.tvActivityProgress_Actual);
        sbActivity_Actual = (SeekBar) view.findViewById(R.id.sbActivity_Actual);
        setupActivityActual();

        tvWeightProgress_Goal = (TextView) view.findViewById(R.id.tvWeightProgress_Goal);
        sbWeight_Goal = (SeekBar) view.findViewById(R.id.sbWeight_Goal);
        setupWeightGoal();

        tvWaistProgress_Goal = (TextView) view.findViewById(R.id.tvWaistProgress_Goal);
        sbWaist_Goal = (SeekBar) view.findViewById(R.id.sbWaist_Goal);
        setupWaistGoal();

        tvFoodExpenseProgress_Goal = (TextView) view.findViewById(R.id.tvFoodExpenseProgress_Goal);
        sbFoodExpense_Goal = (SeekBar) view.findViewById(R.id.sbFoodExpense_Goal);
        setupFoodExpenseGoal();

        tvTotalFatProgress_Macro = (TextView) view.findViewById(R.id.tvTotalFatProgress_Macro);
        sbTotalFat_Macro = (SeekBar) view.findViewById(R.id.sbTotalFat_Macro);

        tvTotalCarbsProgress_Macro = (TextView) view.findViewById(R.id.tvTotalCarbsProgress_Macro);
        sbTotalCarbs_Macro = (SeekBar) view.findViewById(R.id.sbTotalCarbs_Macro);

        tvProteinProgress_Macro = (TextView) view.findViewById(R.id.tvProteinProgress_Macro);
        sbProtein_Macro = (SeekBar) view.findViewById(R.id.sbProtein_Macro);

        setupTotalFatMacro();
        setupTotalCarbsMacro();
        setupProteinMacro();

        openDB();

        // put values in from DB, overwrites existing values
//        updateUiFromDb();

        setUi();

        btnSaveGoals = (Button) view.findViewById(R.id.btnSaveGoals);
        setupSaveGoals();

        return view;
    }

    public User getUserInfo(){
        Cursor cGoals = myDB.getLatestGoal();

        Goal goals = new Goal();
        goals.setWeight(cGoals.getInt(myDB.COL_GOAL_WEIGHT_GOAL));
        user.setGoals(goals);
        user.setWeight_g(cGoals.getInt(myDB.COL_GOAL_WEIGHT_ACTUAL));
        user.setActivityLevel(cGoals.getInt(myDB.COL_GOAL_ACTIVITYLEVEL));
        //user.setGoals();

        return user;
    }

    public void setupSaveGoals() {
        btnSaveGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventHit(MainActivity.FEATURE_USE, "Save goals");
                putGoalsInDb();
                testDbCursor();
            }
        });
    }

    private void eventHit(String category, String action) {
        if (!MainActivity.buDeggin) {
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category) // ex: "Button"
                    .setAction(action) // ex: "Click"
                    .build());
        }
    }

    private Tracker mTracker;

    private void sendScreenName() {
        if (!MainActivity.buDeggin) {
            String screenName = "GoalsAdjustFrag";

            // [START screen_view_hit]
            mTracker.setScreenName("Screen~" + screenName);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
            // [END screen_view_hit]
        }
    }

    private void setUi(){
        sbWeight_Goal.setProgress((int) user.getGoals().getWeight());
        sbWeight_Actual.setProgress((int) user.getWeight_g());
        sbActivity_Actual.setProgress(user.getActivityLevel());
    }

    // gets the last goal entered then updates the ui with it
//    private void updateUiFromDb() {
//        Cursor cLastestGoal = myDB.getLatestGoal();
//
//        if (cLastestGoal.getCount() > 0) {
//            sbWeight_Goal.setProgress(cLastestGoal.getInt(myDB.COL_GOAL_WEIGHT_GOAL));
//            sbWeight_Actual.setProgress(cLastestGoal.getInt(myDB.COL_GOAL_WEIGHT_ACTUAL));
//            sbActivity_Actual.setProgress(cLastestGoal.getInt(myDB.COL_GOAL_ACTIVITYLEVEL));
//
//            // other values on this frag were not set during setup, therefor will be default
//
//        }
//    }

    private void getPrefsForUi() {

    }

    public void testDbCursor() {
        if (!MainActivity.buDeggin) {
            String whereDate = sdfDB.format(cal.getTime());
            Cursor cCheck = myDB.checkGoalEntryDate(whereDate);
            Toast.makeText(getActivity(), "Cursor cCheck - getCount() returns " +
                    cCheck.getCount() +
                    "\nCursor cCheck - moveToFirst() returns " + cCheck.moveToFirst()
                    , Toast.LENGTH_LONG).show();
        }
    }

    public void putGoalsInDb() {

        String whereDate = sdfDB.format(cal.getTime());

        ContentValues cvs = new ContentValues();

        cvs.put(myDB.KEY_GOAL_ENTRY_DATE, sdfDB.format(cal.getTime()));
        //cvs.put(myDB.KEY_GOAL_WEIGHT_ACTUAL, dblWeightProgress_Actual);
        cvs.put(myDB.KEY_GOAL_WEIGHT_ACTUAL, user.getWeight_g());
        cvs.put(myDB.KEY_GOAL_WEIGHT_GOAL, dblWeightProgress_Goal);
        cvs.put(myDB.KEY_GOAL_WAIST_ACTUAL, dblWaistProgress_Actual);
        cvs.put(myDB.KEY_GOAL_WAIST_GOAL, dblWaistProgress_Goal);
        cvs.put(myDB.KEY_GOAL_ACTIVITYLEVEL, intActivityProgress_Actual);
        cvs.put(myDB.KEY_GOAL_DAILYFOODEXP, dblFoodExpenseProgress_Goal);
        cvs.put(myDB.KEY_GOAL_FAT_MACRO, hunPl.format(dblTotalFat_Macro));
        cvs.put(myDB.KEY_GOAL_CARBS_MACRO, hunPl.format(dblTotalCarbs_Macro));
        cvs.put(myDB.KEY_GOAL_PROTEIN_MACRO, hunPl.format(dblProtein_Macro));

        // TODO: monitor for erros on nullPointerException or cursor out of bounds or w.e.
        // query that checks for a row matching the given date, @param whereDate
        // note that this query only returns a single row, which in theory should be ok
        // if i'm only allowing a user to input a single goal
        Cursor cCheck = myDB.checkGoalEntryDate(whereDate);
        if (!cCheck.moveToFirst()) { // there are no values yet for today

            myDB.insertCvsMyGoals(cvs);
            Toast.makeText(getActivity(), "Goals saved successfully!", Toast.LENGTH_SHORT).show();

        }else if (cCheck.moveToFirst()) { // there are values, need to overwrite

            // TODO: need AlertDialog asking user if overwrite is ok
            String rowID = cCheck.getString(0); // returns _rowID of matching value
            Toast.makeText(getActivity(), "has one already " + rowID, Toast.LENGTH_SHORT).show();
            myDB.updateGoalEntry(cCheck.getLong(0), cvs);
            Toast.makeText(getActivity(), "Goals updated successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    /// <summary>
    /// Helper method to quckly intialize a SeekBar.
    private void initSeekBars(SeekBar seekBar)
    {

    }

    public void setupWeightActual() {
        sbWeight_Actual.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //dblWeightProgress_Actual = (double) progress;
                user.setWeight_g((float) progress);
                tvWeightProgress_Actual.setText("" + progress +"/" + sbWeight_Actual.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        //dblWeightProgress_Actual = (double) sbWeight_Actual.getProgress();
        // user.setWeight_g((float) sbWeight_Actual.getProgress());
        tvWeightProgress_Actual.setText("" + sbWeight_Actual.getProgress() + "/" + sbWeight_Actual.getMax());

    }

    public void setupWaistActual() {
        sbWaist_Actual.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dblWaistProgress_Actual = (double) progress;
                tvWaistProgress_Actual.setText("" + progress +"/" + sbWaist_Actual.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        dblWaistProgress_Actual = (double) sbWaist_Actual.getProgress();
        tvWaistProgress_Actual.setText("" + sbWaist_Actual.getProgress() + "/" + sbWaist_Actual.getMax());

    }

    /// <summary>
    /// Initializes a SeekBar for display on the UI
    public void setupActivityActual() {
        sbActivity_Actual.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                intActivityProgress_Actual = progress;
                tvActivityProgress_Actual.setText("" + progress +"/" + sbActivity_Actual.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                switch (intActivityProgress_Actual) {
                    case 0:
                        t("Limited mobility").show();
                        return;
                    case 1:
                        t("Couch tomato").show();
                        return;
                    case 2:
                        t("Sweat at some point this week").show();
                        return;
                    case 3:
                        t("Active").show();
                        return;
                    case 4:
                        t("Olympic").show();
                        return;
                    default:

                }
            }
        });
        intActivityProgress_Actual = sbActivity_Actual.getProgress();
        tvActivityProgress_Actual.setText("" + sbActivity_Actual.getProgress() +
                "/" + sbActivity_Actual.getMax());

    }

    public void setupWeightGoal() {
        sbWeight_Goal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dblWeightProgress_Goal = (double) progress;
                tvWeightProgress_Goal.setText("" + progress + "/" + sbWeight_Goal.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        dblWeightProgress_Goal = (double) sbWeight_Goal.getProgress();
        tvWeightProgress_Goal.setText("" + sbWeight_Goal.getProgress() +
                "/" + sbWeight_Goal.getMax());

    }

    public void setupWaistGoal() {
        sbWaist_Goal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dblWaistProgress_Goal = (double) progress;
                tvWaistProgress_Goal.setText("" + progress +"/" + sbWaist_Goal.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        dblWaistProgress_Goal = (double) sbWaist_Goal.getProgress();
        tvWaistProgress_Goal.setText("" + sbWaist_Goal.getProgress() +
                "/" + sbWaist_Goal.getMax());

    }

    public void setupFoodExpenseGoal() {
        sbFoodExpense_Goal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dblFoodExpenseProgress_Goal = (double) progress;
                tvFoodExpenseProgress_Goal.setText("" + progress +"/" + sbFoodExpense_Goal.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        dblFoodExpenseProgress_Goal = (double) sbFoodExpense_Goal.getProgress();
        tvFoodExpenseProgress_Goal.setText("" + sbFoodExpense_Goal.getProgress() +
                "/" + sbFoodExpense_Goal.getMax());

    }

    /**
     * M A C R O S
     */
    private Macro macro = new Macro();

    double dblTotalFat_Macro;
    double dblTotalCarbs_Macro;
    double dblProtein_Macro;
    /**
     *
     */
    public void calcMacroPercents(Macro macro) {
        macro.setFat(sbTotalFat_Macro.getProgress());
        macro.setCarbohydrates(sbTotalCarbs_Macro.getProgress());
        macro.setProtein(sbProtein_Macro.getProgress());

        int mAlottedMacros = sbTotalFat_Macro.getProgress() +
                sbTotalCarbs_Macro.getProgress() + sbProtein_Macro.getProgress();

        dblTotalFat_Macro = ((double) sbTotalFat_Macro.getProgress() /
                (double) mAlottedMacros);
        tvTotalFatProgress_Macro.setText(String.valueOf(percent.format(dblTotalFat_Macro)));

        dblTotalCarbs_Macro = ((double) sbTotalCarbs_Macro.getProgress() /
                (double) mAlottedMacros);
        tvTotalCarbsProgress_Macro.setText(String.valueOf(percent.format(dblTotalCarbs_Macro)));

        dblProtein_Macro = ((double) sbProtein_Macro.getProgress() /
                (double) mAlottedMacros);
        tvProteinProgress_Macro.setText(String.valueOf(percent.format(dblProtein_Macro)));

    }

    public void setupTotalFatMacro() {
        sbTotalFat_Macro.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {/*calcMacroPercents();*/}
        });
        calcMacroPercents(macro);
    }

    public void setupTotalCarbsMacro() {
        sbTotalCarbs_Macro.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {/*calcMacroPercents();*/}
        });
        //calcMacroPercents();
    }

    public void setupProteinMacro() {
        sbProtein_Macro.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {/*calcMacroPercents();*/}
        });
        //calcMacroPercents();
    }



    /**
     * Convience method for making Toasts nice and short
     * @param msg
     * @return
     */
    public Toast t(String msg) {
        return Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
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

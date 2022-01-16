package com.dooragami.dineindiet;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dooragami.dineindiet.models.Consumption;
import com.dooragami.dineindiet.models.DailyDietGoal;
import com.dooragami.dineindiet.models.Goal;
import com.dooragami.dineindiet.models.User;
import com.dooragami.dineindiet.services.OverviewService;

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
public class OverviewFrag extends Fragment {
  private TextView tvOverview_headerDate;
  private TextView tvOverview_DayDisplay;
  private ImageView ivOverview_Increment;
  private ImageView ivOverview_Decrement;
  private TextView tvIntake_Calories;
  private TextView tvIntake_Cost;
  private TextView tvIntake_TotalFat;
  private TextView tvIntake_TotalCarbs;
  private TextView tvIntake_Protein;
  private TextView tvIntake_CPC;

  private TextView tvGoal_Calories;
  private TextView tvGoal_Cost;
  private TextView tvGoal_TotalFat;
  private TextView tvGoal_TotalCarbs;
  private TextView tvGoal_Protein;
  private TextView tvGoal_CPC;

  private TextView tvLeft_Calories;
  private TextView tvLeft_Cost;
  private TextView tvLeft_TotalFat;
  private TextView tvLeft_TotalCarbs;
  private TextView tvLeft_Protein;
  private TextView tvLeft_CPC;

  SharedPreferences prefs = null;
  private int mGoalMinusActual;
  private int mTotalCalAdjustment;
  private int mDailyCalAdjustment;
  private int mDailyCalGoal;

  private ImageView ivOverview_Add;

  DBAdapter myDB;
  final Calendar calOverview = Calendar.getInstance();

  private SimpleDateFormat sdf;
  private SimpleDateFormat sdfDB;
  private String strDateOfPurchase_db;

  DecimalFormat currency = new DecimalFormat("$###,##0.00");
  // Cost per Calorie, which is typically a percentage of a penny
  DecimalFormat CPC = new DecimalFormat("$###,##0.0000");

  DecimalFormat tensPlace = new DecimalFormat("0.0");

  // using tensPl down below
  // DecimalFormat hunPl = new DecimalFormat("#0.00");

  // google analytics
  private Tracker mTracker;

  /**
   * The {@link Tracker} used to record screen views.
   */


  @Override
  public void onActivityCreated(Bundle savedInstanceState)
  {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.overview_frag, container, false);

    // on show ads and send analytics if release mode
    if (!MainActivity.buDeggin)
    {

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        Analytics application = (Analytics) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]

        sendScreenName();
    }

    // the right-hand side can be replaced with user.getGenderForAdmobs
    int intAdMobsGender = getActivity()
                .getSharedPreferences(MainActivity.SHRD_PREFS_SETUP, Context.MODE_PRIVATE)
                .getInt(MainActivity.STR_GENDER_ADMOBS, 0); // where 0 is unknknow for admobs

    AdView adViewBanner = (AdView) view.findViewById(R.id.adViewBanner);
    if (!MainActivity.buDeggin)
    {
      AdRequest adRequest = new
          AdRequest.Builder()
          //.addTestDevice("C0C8EFF2ECB4B49B5E3B300DCFB6BB64")
          .setGender(intAdMobsGender)
          .build();
      adViewBanner.loadAd(adRequest);
    }
    else
    {
      AdRequest adRequest = new
          AdRequest.Builder()
          .addTestDevice("C0C8EFF2ECB4B49B5E3B300DCFB6BB64")
          .setGender(intAdMobsGender)
          .build();
      adViewBanner.loadAd(adRequest);
    }

    sdf = new SimpleDateFormat(MainActivity.DATE_FORMAT_DISPLAY, Locale.US);
    sdfDB = new SimpleDateFormat(MainActivity.DATE_FORMAT_DB, Locale.US);

    tvOverview_headerDate = (TextView) view.findViewById(R.id.tvOverview_HeaderDate);
    tvOverview_DayDisplay = (TextView) view.findViewById(R.id.tvOverview_DayDisplay);
    ivOverview_Increment  = (ImageView) view.findViewById(R.id.ivOverview_Increment);
    ivOverview_Decrement  = (ImageView) view.findViewById(R.id.ivOverview_Decrement);

    tvOverview_DayDisplay.setText(String.valueOf(calOverview.get(Calendar.DAY_OF_MONTH)));
    tvOverview_headerDate.setText(sdf.format(calOverview.getTime()));

    strDateOfPurchase_db = "'" + sdfDB.format(calOverview.getTime()) + "'";

    incrementDate_Header();
    decrementDate_Header();

    tvIntake_Calories   = (TextView) view.findViewById(R.id.tvIntake_Calories);
    tvIntake_Cost       = (TextView) view.findViewById(R.id.tvIntake_Cost);
    tvIntake_TotalFat   = (TextView) view.findViewById(R.id.tvIntake_TotalFat);
    tvIntake_TotalCarbs = (TextView) view.findViewById(R.id.tvIntake_TotalCarbs);
    tvIntake_Protein    = (TextView) view.findViewById(R.id.tvIntake_Protein);
    tvIntake_CPC        = (TextView) view.findViewById(R.id.tvIntake_CPC);

    tvLeft_Calories     = (TextView) view.findViewById(R.id.tvLeft_Calories);
    tvLeft_Cost         = (TextView) view.findViewById(R.id.tvLeft_Cost);
    tvLeft_TotalFat     = (TextView) view.findViewById(R.id.tvLeft_TotalFat);
    tvLeft_TotalCarbs   = (TextView) view.findViewById(R.id.tvLeft_TotalCarbs);
    tvLeft_Protein      = (TextView) view.findViewById(R.id.tvLeft_Protein);
    tvLeft_CPC          = (TextView) view.findViewById(R.id.tvLeft_CPC);

    tvGoal_Calories     = (TextView) view.findViewById(R.id.tvGoal_Calories);
    tvGoal_Cost         = (TextView) view.findViewById(R.id.tvGoal_Cost);
    tvGoal_TotalFat     = (TextView) view.findViewById(R.id.tvGoal_TotalFat);
    tvGoal_TotalCarbs   = (TextView) view.findViewById(R.id.tvGoal_TotalCarbs);
    tvGoal_Protein      = (TextView) view.findViewById(R.id.tvGoal_Protein);
    tvGoal_CPC          = (TextView) view.findViewById(R.id.tvGoal_CPC);

    ivOverview_Add      = (ImageView) view.findViewById(R.id.ivOverview_Add);

    openDB();
    // updates the UI with Consumption info for given day
    updateOverview(strDateOfPurchase_db);

    // what does this do?
    setupQuickAdd();

    return view;
  }

  private void sendScreenName() {
          String screenName = "Overview";
          // [START screen_view_hit]
          mTracker.setScreenName("Screen~" + screenName);
          mTracker.send(new HitBuilders.ScreenViewBuilder().build());
          // [END screen_view_hit]
  }


  private DailyDietGoal mapGoalFromDb(Cursor cursor) {
    DailyDietGoal g = new DailyDietGoal();

    g.setWeight(cursor.getDouble(myDB.COL_GOAL_WEIGHT_GOAL));
    g.setDailyFoodExpense(cursor.getDouble(myDB.COL_GOAL_DAILYFOODEXP));

    int weightActual = cursor.getInt(myDB.COL_GOAL_WEIGHT_ACTUAL);
    int weightGoal = cursor.getInt(myDB.COL_GOAL_WEIGHT_GOAL);
    double fatMacFactor = cursor.getDouble(myDB.COL_GOAL_FAT_MACRO);
    double carbsMacFactor = cursor.getDouble(myDB.COL_GOAL_CARBS_MACRO);
    double proteinMacFactor = cursor.getDouble(myDB.COL_GOAL_PROTEIN_MACRO);

    return g;
  }
  //TODO: factor out into DAL.
  private Consumption getConsumptionByDate(final String dateConsumed) {
    Consumption consumption = new Consumption() {{
      Cursor cursor = myDB.getConsOverview(dateConsumed);

      setCost(cursor.getFloat(myDB.COL_CONS_COSTOFCONS));
      setTotalFat(cursor.getFloat(myDB.COL_CONS_TOTALFAT));
      setTotalCarb(cursor.getFloat(myDB.COL_CONS_TOTALCARBS));
      setProtein(cursor.getFloat(myDB.COL_CONS_PROTEIN));
      cursor.close();
    }};
    return consumption;
  }
  private void mapConsumptionToView(Consumption c) {
    tvIntake_Calories.setText("" +  tensPlace.format(c.getTotalCalories()));
    tvIntake_TotalFat.setText("" + tensPlace.format(c.getTotalFat()));
    tvIntake_TotalCarbs.setText("" + tensPlace.format(c.getTotalCarb()));
    tvIntake_Protein.setText("" + tensPlace.format(c.getProtein()));
    tvIntake_Cost.setText("" + currency.format(c.getCost()));
    tvIntake_CPC.setText("" + CPC.format(c.getCostPerCalorie()));
  }
  private void mapGoalToView(DailyDietGoal g) {
    tvGoal_Cost.setText("" + currency.format(g.getDailyFoodExpense()));
    tvGoal_Calories.setText("" + tensPlace.format(g.getTotalCalories()));
    tvGoal_TotalFat.setText("" + tensPlace.format(g.getTotalFat()));
    tvGoal_TotalCarbs.setText("" + tensPlace.format(g.getTotalCarbs()));
    tvGoal_Protein.setText("" + tensPlace.format(g.getProtein()));
    tvGoal_CPC.setText("" + tensPlace.format("" + g.getDailyFoodExpense() / g.getTotalCalories()));
  }

  //TODO: fix bug in this method.
  //TODO: factor out data-access code from this method. keep in this class for now.
  //TODO: call factored-out data access code and wrap in a try/catch.
  // Refreshes users views after form submittion.
  public void updateOverview(String whereDate)
  {
    CalorieCalculator calc = new CalorieCalculator();
    Consumption c = getConsumptionByDate(whereDate);
    c.setTotalCalories(calc.calculate(c.getTotalFat(), c.getTotalCarb(), c.getProtein()));
    mapConsumptionToView(c);

    /**
     * G O A L S
     */
    prefs = getActivity().getSharedPreferences(
            MainActivity.SHRD_PREFS_SETUP, Context.MODE_PRIVATE);
    //User u = User.getInstance();

    int heightInches = prefs.getInt(MainActivity.HEIGHT_INCHES, 68);
    int age = prefs.getInt(MainActivity.INT_AGE_PROGRESS, 27);
    String gender = prefs.getString(MainActivity.STR_GENDER, MainActivity.GENDER_NO_ANSWER);

    Cursor cursor = myDB.getLatestGoal();
    if (cursor.getCount() > 0) {
      DailyDietGoal ddg = mapGoalFromDb(cursor);

      int weightActual = cursor.getInt(myDB.COL_GOAL_WEIGHT_ACTUAL);
      int weightGoal = cursor.getInt(myDB.COL_GOAL_WEIGHT_GOAL);

      int restingMetabolicRate = calc.calcRmr(180, heightInches, age, gender);
      int myTee = calc.calcTee(
          restingMetabolicRate,
          cursor.getInt(myDB.COL_GOAL_ACTIVITYLEVEL),
          gender);
      int myTef = calc.calcTef(restingMetabolicRate, myTee);
      int totalCaloricExpidenture = restingMetabolicRate + myTee + myTef;

      // if the user wants to lose more than a pound, then set daily weight loss at 1 lb
      // 1 lb is recommended.. i think, as the most rapid pace to lose weight in a week
      // TODO: give user the ability to choose the weight lose/gain pace (between 0.5 - 2 lbs)
      if (mTotalCalAdjustment <= -3500)
      {
        mDailyCalAdjustment = -500;
      }
      else if (mTotalCalAdjustment >= 3500)
      {
        mDailyCalAdjustment = 500;
      }
      else
      {
        // call adjustments fall within 1 pound,
        // which is not possible in current setup, so leave mDailyCalAdjustment alone
      }

      mDailyCalGoal = mDailyCalAdjustment + totalCaloricExpidenture;
//      double mDaily_FatGoal = (mDailyCalGoal * fatMacFactor) / 9;
//      double mDaily_CarbGoal = (mDailyCalGoal * carbsMacFactor) / 4;
//      double mDaily_ProteinGoal = (mDailyCalGoal * proteinMacFactor) / 4;

      // Note that mGaolMinusActual will return negative (-) if there is weight to be lost.
      // returns overall calories to lose
      mTotalCalAdjustment = calc.calcCaloriesToLose(weightActual, weightGoal);

      mapGoalToView(ddg);

      /**
       * L E F T
       */
//      tvLeft_Calories.setText("" + tensPl.format(((double) mDailyCalGoal - totalCals_SUM)));
//      tvLeft_TotalFat.setText("" + tensPl.format(mDaily_FatGoal - totalFat_SUM));
//      tvLeft_TotalCarbs.setText("" + tensPl.format(mDaily_CarbGoal - totalCarbs_SUM));
//      tvLeft_Protein.setText("" + tensPl.format(mDaily_ProteinGoal - protein_SUM));
//      tvLeft_Cost.setText("" + currency.format(dblDailyFoodExp - cost_SUM));
//      tvLeft_CPC.setText(" - ");
    }
    else
    { // not sure this ever reaches else, since goals are entered on intro now
      tvGoal_Cost.setText(String.valueOf(0)); // TODO: give meaningful initial val
    }
  }

  /// <summary>
  ///
  /// </summary>
  private void setupQuickAdd()
  {

    // allows user to quickly add a nutrient
    ivOverview_Add.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        ConsumptionDetailFrag consDetail = new ConsumptionDetailFrag();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame, consDetail).commit();
      }
    });
  }

  private void incrementDate_Header()
  {

    // Increase the date show on the header, which indicates date of purchase
    ivOverview_Increment.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        calOverview.add(Calendar.DAY_OF_MONTH, 1);

        // set UI
        tvOverview_DayDisplay.setText
          (
            String.valueOf(calOverview.get(Calendar.DAY_OF_MONTH))
          );
        tvOverview_headerDate.setText(sdf.format(calOverview.getTime()));

        // save time in SQL DATE format
        strDateOfPurchase_db = "'" + sdfDB.format(calOverview.getTime()) + "'";
        updateOverview(strDateOfPurchase_db);
      }
    });
  }

  private void decrementDate_Header()
  {
    // Decrease the date show on the header, which indicates date of purchase
    ivOverview_Decrement.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        calOverview.add(Calendar.DAY_OF_MONTH, -1);

        // set UI
        tvOverview_DayDisplay.setText(
            String.valueOf(calOverview.get(Calendar.DAY_OF_MONTH)));
        tvOverview_headerDate.setText(sdf.format(calOverview.getTime()));

        // save time in SQL DATE format
        strDateOfPurchase_db = "'" + sdfDB.format(calOverview.getTime()) + "'";
        updateOverview(strDateOfPurchase_db);
      }
    });
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

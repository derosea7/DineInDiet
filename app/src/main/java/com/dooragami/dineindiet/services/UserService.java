package com.dooragami.dineindiet.services;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.dooragami.dineindiet.DBAdapter;
import com.dooragami.dineindiet.MainActivity;
import com.dooragami.dineindiet.models.BodyComposition;
import com.dooragami.dineindiet.models.ChemicalProfile;
import com.dooragami.dineindiet.models.Goal;
import com.dooragami.dineindiet.models.Nutrient;
import com.dooragami.dineindiet.models.User;

/**
 * Created by derosea7 on 8/26/2016.
 */
public class UserService {

  private static final String TAG = "UserService";

  private DBAdapter mDbAdapter;
  private SharedPreferences mPrefs;

  public UserService()
  {

  }

  /// <summary>
  /// Populates a static User instance with information from the database during initialization.
  /// </summary>
  public User getUserInfo(Context context)
  {
    User user = User.getInstance();

    SharedPrefsAdapter adapter = new SharedPrefsAdapter();
    SharedPreferences prefs = adapter.getPrefs(context);

    user.setAge((byte) prefs.getInt(SharedPrefsAdapter.SP_AGE, -1));
    user.setIsFirstRun(prefs.getBoolean(SharedPrefsAdapter.SP_IS_FIRST_RUN, false));
    user.setName(prefs.getString(MainActivity.STR_USER_NAME, "Name"));

    mDbAdapter = new DBAdapter(context); // returns DBAdapter, sets context and myDBHelper
    mDbAdapter.open();

    mDbAdapter.close();

    return user;
  }

  /// <summary>
  /// Convenience method to populate a Goal object with information from that database.
  /// </summary>
  public Goal getGoal(Context context)
  {
    Goal goal = new Goal();

    mDbAdapter = new DBAdapter(context); // returns DBAdapter, sets context and myDBHelper
    mDbAdapter.open();

    Cursor cursor = mDbAdapter.getLatestGoal();
    if (cursor.getCount() > 0)
    { //then we got something
      // double dblWeightGoal = cGoal.getDouble(myDB.COL_GOAL_WEIGHT_GOAL);

      //goals.setWeight_g((float) cursor.getDouble(mDbAdapter.COL_GOAL_DAILYFOODEXP));

      goal.setWeight(cursor.getFloat(mDbAdapter.COL_GOAL_WEIGHT_GOAL));
      goal.setDailyFoodExpense(cursor.getFloat(mDbAdapter.COL_GOAL_DAILYFOODEXP));


      double dblDailyFoodExp  = cursor.getDouble(mDbAdapter.COL_GOAL_DAILYFOODEXP);
      int weightActual        = cursor.getInt(mDbAdapter.COL_GOAL_WEIGHT_ACTUAL);
      int weightGoal          = cursor.getInt(mDbAdapter.COL_GOAL_WEIGHT_GOAL);
      double fatMacFactor     = cursor.getDouble(mDbAdapter.COL_GOAL_FAT_MACRO);
      double carbsMacFactor   = cursor.getDouble(mDbAdapter.COL_GOAL_CARBS_MACRO);
      double proteinMacFactor = cursor.getDouble(mDbAdapter.COL_GOAL_PROTEIN_MACRO);
    }
    mDbAdapter.close();

    return goal;
  }

  /// <summary>
  ///
  /// </summary>
  private void digest(Nutrient nutrient, Context context)
  {
    BodyComposition bc = new BodyComposition();
    //bc.setChemicalProfile(nutrient);

    DBAdapter adapter = new DBAdapter(context);
    adapter.open();
    ContentValues contentValues = adapter.getBodyCompositionChemicalProfile();
    adapter.close();

    ///bc.setBodyComposition(contentValues.get(adapter.KEY_BODY_COMP_CHEMICAL_PROFILE_ID));
    ChemicalProfile chemicalProfile = new ChemicalProfile();

    bc.setChemicalProfile(chemicalProfile);
  }

    // getWritable
//    private void openDB(Context context) {
//        try
//        {
//            mDbAdapter = new DBAdapter(context);
//        }
//        catch (Exception e)
//        {
//            throw e;
//        }
//        mDbAdapter.open();
//    }
//
//    private void closeDB() {
//        mDbAdapter.close();
//    }

}

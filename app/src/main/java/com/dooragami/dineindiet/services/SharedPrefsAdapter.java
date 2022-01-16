package com.dooragami.dineindiet.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dooragami.dineindiet.R;
import com.dooragami.dineindiet.models.User;

/**
 * Created by derosea7 on 8/27/2016.
 */
public class SharedPrefsAdapter
{
  private SharedPreferences mPrefs = null;
  private Context mCtx;

  private final String SHARED_PREFS = "DINEINDIET";

  public static final String SP_IS_FIRST_RUN = "ISFIRSTRUN";
  public static final String SP_IS_SETUP = "ISSETUP";
  public static final String SP_AGE = "AGE";
  public static final String SP_USER_NAME = "STR_USER_NAME";
  public static final String SP_HEIGHT_INCHES = "HEIGHT_INCHES";
  public static final String SP_GENDER = "STR_GENDER";

  public SharedPreferences getPrefs(Context ctx)
  {
    if (mPrefs == null)
    {
      PreferenceManager.setDefaultValues(ctx, R.xml.preferences, false);
      mPrefs = ctx.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }
    return mPrefs;
  }

  public void setPrefs(SharedPreferences mPrefs)
  {
      this.mPrefs = mPrefs;
  }

  /// <summary>
  ///
  /// </summary>
  public void setUserInfo()
  {
    User user = User.getInstance();
    mPrefs.edit().putString(SP_USER_NAME, user.getName());
    mPrefs.edit().putInt(SP_AGE, user.getAge());

    //mPrefs.edit().putInt(SP_HEIGHT_INCHES, user.getHeight_cm());
    //mPrefs.edit().putFloat(SP_HEIGHT_INCHES, user.getHeight_cm());
    mPrefs.edit().putInt(SP_HEIGHT_INCHES, (int) user.getHeight_cm());
    mPrefs.edit().putString(SP_GENDER, user.getGender());
  }
}

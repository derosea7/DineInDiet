package com.dooragami.dineindiet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dooragami.dineindiet.models.User;
import com.dooragami.dineindiet.services.SharedPrefsAdapter;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by derosea7 on 2/6/2016.
 */
public class MyInfoFrag extends Fragment
{
  DBAdapter myDB;

  Calendar cal = Calendar.getInstance();
  private SimpleDateFormat sdfDB = new SimpleDateFormat(MainActivity.DATE_FORMAT_DB, Locale.US);

  RadioGroup rgGender;
  SeekBar sbMyInfo_Age;
  SeekBar sbMyInfo_Inches;
  TextView tvMyInfo_AgeProgress;
  TextView tvMyInfo_HeightProgress;
  EditText etMyInfo_Name;

  Button btnSaveMyInfo;

  int mAge;
  String mGender;
  int mHeightInches;
  private SharedPreferences prefs;

  User user;

  // for analytics
  private Tracker mTracker;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.my_info_frag, container, false);
//
//    user = User.getInstance();
//
//    if (!MainActivity.buDeggin)
//    {
//
//      // [START shared_tracker]
//      // Obtain the shared Tracker instance.
//      Analytics application = (Analytics) getActivity().getApplication();
//      mTracker = application.getDefaultTracker();
//      // [END shared_tracker]
//
//      sendScreenName();
//    }
//
//    rgGender                = (RadioGroup) view.findViewById(R.id.rgMyInfo_Gender);
//    sbMyInfo_Age            = (SeekBar) view.findViewById(R.id.sbMyInfo_Age);
//    sbMyInfo_Inches         = (SeekBar) view.findViewById(R.id.sbMyInfo_Inches);
//    tvMyInfo_AgeProgress    = (TextView) view.findViewById(R.id.tvMyInfo_AgeProgress);
//    tvMyInfo_HeightProgress = (TextView) view.findViewById(R.id.tvMyInfo_HeightProgress);
//    etMyInfo_Name           = (EditText) view.findViewById(R.id.etMyInfo_Name);
//    btnSaveMyInfo           = (Button) view.findViewById(R.id.btnSaveMyInfo);
//
//    prefs = getActivity().getSharedPreferences(MainActivity.SHRD_PREFS_SETUP, Context.MODE_PRIVATE);
//
//    // initialize user interface
//    initializeUi();
//
//    // TODO: load these values from prefs
//    // weightGoal, weightActual and activityLevel are in tblGoals,
//    // gender, age, height (in inches), and name is in LauncherActivity.SHRD_PREFS_SETUP prefs
//    // TODO: give option to adjust in cm
//
//    initializeSeekBarListeners();
//
//    openDb();

    return view;
  }

  /// <summary>
  /// Intializes default or most recent up all SeekBar listeners for the ui.
  /// </summary>
  private void initializeSeekBarListeners()
  {
    setupAgeListener();
    setupHeightListener();
    rgGenderListener();
    setupSubmitButtonOnClickListener();
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

  private void sendScreenName()
  {
    if (!MainActivity.buDeggin)
    {
      String screenName = "MyInfo";

      // [START screen_view_hit]
      mTracker.setScreenName("Screen~" + screenName);
      mTracker.send(new HitBuilders.ScreenViewBuilder().build());
      // [END screen_view_hit]
    }
  }

  // gets the values for User's info from prefs (saved during setup process) and populates
  // UI so the user is then just adjusting their previously set values
  private void initializeUi()
  {
    etMyInfo_Name.setText(user.getName());
    sbMyInfo_Age.setProgress(user.getAge());
    tvMyInfo_AgeProgress.setText(user.getAge());
    sbMyInfo_Inches.setProgress((int) user.getHeight_cm());

    // etMyInfo_Name.setText(prefs.getString(MainActivity.STR_USER_NAME, "Name"));
    //sbMyInfo_Age.setProgress(prefs.getInt(MainActivity.INT_AGE_PROGRESS, 27));
    //tvMyInfo_AgeProgress.setText("" + sbMyInfo_Age.getProgress());
    //sbMyInfo_Inches.setProgress(prefs.getInt(MainActivity.HEIGHT_INCHES, 68));

    calcHeightFromSeekBars(sbMyInfo_Inches.getProgress());

    mGender = prefs.getString(MainActivity.STR_GENDER, MainActivity.GENDER_NO_ANSWER);
    switch (mGender)
    {
      case MainActivity.GENDER_MALE:
        rgGender.check(R.id.rbMyInfo_Male);
        break;
      case MainActivity.GENDER_FEMALE:
        rgGender.check(R.id.rbMyInfo_Female);
        break;
      case MainActivity.GENDER_NO_ANSWER:
        rgGender.check(R.id.rbMyInfo_NoAnswer);
        break;
    }
  }

  public void setupAgeListener()
  {
    sbMyInfo_Age.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
      {
        mAge = progress;
        tvMyInfo_AgeProgress.setText("" + mAge);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar)
      {
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar)
      {
      }
    });
  }

  public void setupHeightListener()
  {
    sbMyInfo_Inches.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
      {
        mHeightInches = progress;
        calcHeightFromSeekBars(mHeightInches);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar)
      {
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar)
      {
      }
    });
  }

  public void calcHeightFromSeekBars(int inches)
  {
    tvMyInfo_HeightProgress.setText("" + inches / 12 + "'" + inches % 12 + "\"");
  }

  public void rgGenderListener()
  {
    rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
    {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId)
      {
        switch (checkedId)
        {
          case R.id.rbMyInfo_Male:
            mGender = MainActivity.GENDER_MALE;
            break;
          case R.id.rbMyInfo_Female:
            mGender = MainActivity.GENDER_FEMALE;
            break;
          case R.id.rbMyInfo_NoAnswer:
            mGender = MainActivity.GENDER_NO_ANSWER;
            break;
        }
      }
    });
  }

  private void setupSubmitButtonOnClickListener()
  {
    btnSaveMyInfo.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        // note that to put prefs into db, i must prepopulate this frag
        // with the info the user had in the prefs already, and return that
        // to the db

        //putValuesInPrefs();
        SharedPrefsAdapter sharedPrefsAdapter = new SharedPrefsAdapter();
        sharedPrefsAdapter.setUserInfo();

        eventHit(MainActivity.FEATURE_USE, "Save MyInfo", "Aje", (long) sbMyInfo_Age.getProgress());
        Toast.makeText(getActivity(), "Info saved successfully!", Toast.LENGTH_SHORT).show();
      }
    });
  }

//  private void putValuesInPrefs()
//  {
//
//    prefs.edit().putString(MainActivity.STR_USER_NAME, etMyInfo_Name.getText().toString()).commit();
//    prefs.edit().putInt(MainActivity.INT_AGE_PROGRESS, sbMyInfo_Age.getProgress()).commit();
//    prefs.edit().putInt(MainActivity.HEIGHT_INCHES, sbMyInfo_Inches.getProgress()).commit();
//
//    // mGender is set upon loading prefs into the UI, so can be used here to save-back gender
//    prefs.edit().putString(MainActivity.STR_GENDER, mGender).commit();
//  }

  // getWritable
  private void openDb()
  {
    myDB = new DBAdapter(getActivity());
    myDB.open();
  }

  private void closeDB()
  {
    myDB.close();
  }

  @Override
  public void onDestroyView()
  {
    super.onDestroyView();
    closeDB();
  }
}

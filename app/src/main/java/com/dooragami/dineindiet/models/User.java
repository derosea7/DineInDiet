package com.dooragami.dineindiet.models;

import android.content.Context;

import com.dooragami.dineindiet.services.UserService;

/**
 * Created by derosea7 on 8/21/2016.
 */
public class User
{
  public static final String GENDER_MALE = "GENDER_MALE";
  public static final String GENDER_FEMALE = "GENDER_FEMALE";
  public static final String GENDER_NO_ANSWER = "GENDER_NO_ANSWER";

  public enum GENDER
  {
    MALE,
    FEMALE,
    NOANSWER // default
  }

  private String          mName;
  private String          mGender;
  private byte            mAge;
  private float           mHeight_cm;
  private float           mWeight_g;
  private float           mWaistSize_cm;
  private float           mNeckSize_cm;
  private float           mUpperTorsoSize_cm;
  private float           mBicepSize_cm;
  private float           mForearmSize_cm;
  private float           mThighSize_cm;
  private float           mCalfSize_cm;
  private Goal            mGoals;
  private int             mActivityLevel;
  private boolean         mIsFirstRun;
  private BodyComposition mBodyComposition;

  public static User instance = null;

  private User()
  {
  }

  /// <summary>
  /// Lazy instantiation--instance only created when needed.
  /// </summary>
  public static User getInstance()
  {
    if (instance == null)
    {
      instance = new User();
    }
    return instance;
  }

  /// <summary>
  /// Populates static User instance fields with the corresponding database table-fields.
  /// </summary>
  public User initializeUser(Context context)
  {
    UserService userService = new UserService();
    instance = userService.getUserInfo(context);

    return instance;
  }

  public String getName()
  {
    return mName;
  }

  public void setName(String name)
  {
    this.mName = name;
  }

  public byte getAge()
  {
    return mAge;
  }

  public void setAge(byte mAge)
  {
    this.mAge = mAge;
  }

  public float getWeight_g()
  {
    return mWeight_g;
  }

  public void setWeight_g(float weight_g)
  {
    this.mWeight_g = weight_g;
  }

  public float getWaistSize_cm(){
    return mWaistSize_cm;
  }

  public void setWaistSize_cm(float waistSize_cm)
  {
    this.mWaistSize_cm = waistSize_cm;
  }

  public float getNeckSize_cm()
  {
    return mNeckSize_cm;
  }

  public void setNeckSize_cm(float neckSize_cm)
  {
    this.mNeckSize_cm = neckSize_cm;
  }

  public float getUpperTorsoSize_cm()
  {
    return mUpperTorsoSize_cm;
  }

  public void setUpperTorsoSize_cm(float mUpperTorsoSize_cm)
  {
    this.mUpperTorsoSize_cm = mUpperTorsoSize_cm;
  }

  public float getBicepSize_cm()
  {
    return mBicepSize_cm;
  }

  public void setBicepSize_cm(float bicepSize_cm)
  {
    this.mBicepSize_cm = bicepSize_cm;
  }

  public float getForearmSize_cm(){
    return mForearmSize_cm;
  }

  public void setForearmSize_cm(float forearmSize_cm)
  {
    this.mForearmSize_cm = forearmSize_cm;
  }

  public float getThighSize_cm()
  {
    return mThighSize_cm;
  }

  public void setThighSize_cm(float thighSize_cm)
  {
    this.mThighSize_cm = thighSize_cm;
  }

  public float getCalfSize_cm()
  {
    return mCalfSize_cm;
  }

  public void setCalfSize_cm(float mCalfSize_cm)
  {
    this.mCalfSize_cm = mCalfSize_cm;
  }

  public String getGender()
  {
    return mGender;
  }

  public void setGender(String gender)
  {
    this.mGender = gender;
  }

  public Goal getGoals()
  {
    return mGoals;
  }

  public void setGoals(Goal goals)
  {
    this.mGoals = goals;
  }

  public Integer getActivityLevel()
  {
    return mActivityLevel;
  }

  public void setActivityLevel(Integer activityLevel)
  {
    this.mActivityLevel = activityLevel;
  }

  public boolean isFirstRun()
  {
    return mIsFirstRun;
  }

  public void setIsFirstRun(boolean isFirstRun)
  {
    this.mIsFirstRun = isFirstRun;
  }

  public BodyComposition getBodyComposition()
  {
    return mBodyComposition;
  }

  public void setBodyComposition(BodyComposition mBodyComposition)
  {
    this.mBodyComposition = mBodyComposition;
  }

  public float getHeight_cm()
  {
    return mHeight_cm;
  }

  public void setHeight_cm(float mHeight_cm)
  {
    this.mHeight_cm = mHeight_cm;
  }
}

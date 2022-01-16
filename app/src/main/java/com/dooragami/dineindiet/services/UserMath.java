package com.dooragami.dineindiet.services;

import com.dooragami.dineindiet.models.ChemicalProfile;

/**
 * Created by derosea7 on 8/26/2016.
 * Represents algorithms used by the user in this app. Will be static with static methods.
 */
/// <summary>
/// Static Utility Class that provides algorithms a User will need.
/// </summary>
public class UserMath
{
  public static UserMath instance = null;

  private UserMath()
  {
      //mBodyComposition;
  }

  /// <summary>
  /// Lazy instantiation--instance only created when needed.
  /// </summary>
  public static UserMath getInstance()
  {
      if (instance == null)
      {
          instance = new UserMath();
      }
      return instance;
  }

  /// <summary>
  /// Returns calories in a ChemicalProfile.
  /// </summary>
  public static int calculateCalories(ChemicalProfile chemicalProfile)
  {
      double f = chemicalProfile.getFat_G();
      double p = chemicalProfile.getProtein_G();
      //double c = chemicalProfile.getCarbohydrates_G(); // correct val
      double c = 4.0; // debugging val
      int calories = (int) Math.floor( f + p + c);

      return calories;
  }

}

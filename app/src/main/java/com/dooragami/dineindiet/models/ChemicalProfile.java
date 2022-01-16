package com.dooragami.dineindiet.models;

/**
 * Created by derosea7 on 8/26/2016.
 */
/// <summary>
/// Represents a profile of the Chemicals in a substance.
/// </summary>
public class ChemicalProfile
{
  private double mWater_G;
  private double mFat_G;
  private double mProtein_G;
  private double mCarbohydrates_G;
  private double mSalt_Mg;

  public double getWater_G() {
      return mWater_G;
  }

  public void setWater_G(double mWater_G) {
      this.mWater_G = mWater_G;
  }

  public double getFat_G() {
      return mFat_G;
  }

  public void setFat_G(double mFat_G) {
      this.mFat_G = mFat_G;
  }

  public double getProtein_G() {
      return mProtein_G;
  }

  public void setProtein_G(double mProtein_G) {
      this.mProtein_G = mProtein_G;
  }

  public double getSalt_Mg() {
      return mSalt_Mg;
  }

  public void setSalt_Mg(double mSalt_Mg) {
      this.mSalt_Mg = mSalt_Mg;
  }
}

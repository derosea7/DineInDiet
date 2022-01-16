package com.dooragami.dineindiet.models;

/**
 * Created by derosea7 on 8/26/2016.
 */
public class BodyComposition
{

  // essentially this class will act as a repository for a dynamic chemical profile
  // of the User's entire body.
  private ChemicalProfile mChemicalProfile;

  public ChemicalProfile getChemicalProfile() {
    return mChemicalProfile;
  }

  public void setChemicalProfile(ChemicalProfile chemicalProfile) {
    this.mChemicalProfile = chemicalProfile;
  }

//    // This method could be expanded to allow for distibute of fat store accouting.
//    public void setChemicalProfile(ChemicalProfile bodyComposition)
//    {
//        mChemicalProfile = bodyComposition;
//    }
//
//    public void setChemicalProfile(Nutrient nutrient)
//    {
//        mChemicalProfile = nutrient.mChemicalProfile;
//    }
//
//    public void addToComposition(ChemicalProfile chemicalProfile)
//    {
//
//    }

}

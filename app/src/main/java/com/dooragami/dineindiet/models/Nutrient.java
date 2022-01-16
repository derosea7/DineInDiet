package com.dooragami.dineindiet.models;

/**
 * Created by derosea7 on 8/26/2016.
 * <summary>
 *     Attempts to represent its physical counterpart.
 * </summary>
 * <remarks>
 *     This class acts as a wrapper of ChemicalProfile, giving the ChemicalProfile
 *     metadata of sorts.
 * </remarks>
 */
public class Nutrient
{
    static enum NutrientType
    {
        Foodstuff,
        Medicine,
        Water
    }

    NutrientType mNutrientType;
    ChemicalProfile mChemicalProfile;

    public Nutrient (ChemicalProfile chemProfile)
    {
        mChemicalProfile = chemProfile;
    }

    public Nutrient (ChemicalProfile chemicalProfile, NutrientType type)
    {
        mChemicalProfile = chemicalProfile;
        mNutrientType = type;
    }

}

package com.dooragami.dineindiet.models;

/**
 * Created by derosea7 on 8/26/2016.
 */
public class Macro
{
    private double mFat;
    private double mProtein;
    private double mCarbohydrates;

    private double mFat_Percentage;
    private double mCarbohydrates_Percentage;
    private double mProtein_Percentage;

    public Macro()
    {

    }
    public Macro(double fat, double carbohydrates, double protien)
    {
        mFat = fat;
        setmCarbohydrates(carbohydrates);
        mProtein = protien;
    }

    public double getFat() {
        return mFat;
    }

    public void setFat(double fat) {
        this.mFat = fat;
    }

    public double getProtein() {
        return mProtein;
    }

    public void setProtein(double protein) {
        this.mProtein = protein;
    }

    public double getCarbohydrates() {
        return getmCarbohydrates();
    }

    public void setCarbohydrates(double carbohydrates) {
        this.setmCarbohydrates(carbohydrates);
    }

    public double getmCarbohydrates() {
        return mCarbohydrates;
    }

    public void setmCarbohydrates(double mCarbohydrates) {
        this.mCarbohydrates = mCarbohydrates;
    }

    public double getmFat_Percentage() {
        return mFat_Percentage;
    }

    public void setmFat_Percentage(double mFat_Percentage) {
        this.mFat_Percentage = mFat_Percentage;
    }

    public double getmCarbohydrates_Percentage() {
        return mCarbohydrates_Percentage;
    }

    public void setmCarbohydrates_Percentage(double mCarbohydrates_Percentage) {
        this.mCarbohydrates_Percentage = mCarbohydrates_Percentage;
    }

    public double getmProtein_Percentage() {
        return mProtein_Percentage;
    }

    public void setmProtein_Percentage(double mProtein_Percentage) {
        this.mProtein_Percentage = mProtein_Percentage;
    }

}

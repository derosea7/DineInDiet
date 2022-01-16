package com.dooragami.dineindiet;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Telephony;
import android.widget.Switch;

/**
 * Created by derosea7 on 2/3/2016.
 */
public class CalorieCalculator {

    public static final int CALORIES_PER_LB = 3500;

    // if negative, then means the person is aiming to lose wieght, if postivie, then aiming to gain
    public int calcCaloriesToLose(int weightActual, int weightGoal) {
        return (weightGoal - weightActual) * 3500;
    }

    public double calculate(double totalFat, double totalCarbs, double protein) {
        return (totalFat * 9) + (totalCarbs * 4) + (protein * 4);
    }

    // calculates Cost per Calorie
    public double costPerCalorie(double cost, double calories) {
        if (calories > 0) {
            return cost / calories;
        } else {
            return 0;
        }
    }

    // calculates Resting metabolic rate; a less stringent metric than BMR
    public int calcRmr(int weightActual, int heightInches, int age, String gender) {
        int bmr = 0; // don't have to init, just to get rid of error on return for now
        // mass(kg) * height(cm) * age + gender

        // mass: tblGoals
        // height, age, gender: LauncherActivity prefs

        double heightCm = convertInchToCm(heightInches);
        double weightKg = convertLbsToKgs(weightActual);

        int intS;
        switch (gender) {
            case MainActivity.GENDER_MALE:
                intS = 5;
                break;
            case MainActivity.GENDER_FEMALE:
                intS = -161;
                 break;
            case MainActivity.GENDER_NO_ANSWER:
                intS = -70;
                break;
            default:
                intS = -70;
                break;
        }

        bmr = (int) ((weightKg * 10) + (heightCm * 6.25) - (age * 5) + intS);

        return bmr;
    }

    // calculate the Thermal Effect of Exercise
    // values obtained from PDF from Hawaii.edu on calculating total caloric expenditure
    public int calcTee(int bmr, int activityLevel, String gender) {
        double activityFactor;
        switch (activityLevel) {
            case 0:
                activityFactor = 0.3;
                break;
            case 1:
                if (gender == MainActivity.GENDER_MALE) {
                    activityFactor = 0.6;
                } else if (gender == MainActivity.GENDER_FEMALE) {
                    activityFactor = 0.5;
                } else {
                    activityFactor = 0.55;
                }
                break;
            case 2:
                if (gender == MainActivity.GENDER_MALE) {
                    activityFactor = 0.7;
                } else if (gender == MainActivity.GENDER_FEMALE) {
                    activityFactor = 0.6;
                } else {
                    activityFactor = 0.65;
                }
                break;
            case 3:
                if (gender == MainActivity.GENDER_MALE) {
                    activityFactor = 1.1;
                } else if (gender == MainActivity.GENDER_FEMALE) {
                    activityFactor = 0.9;
                } else {
                    activityFactor = 1.0;
                }
                break;
            case 4:
                if (gender == MainActivity.GENDER_MALE) {
                    activityFactor = 1.4;
                } else if (gender == MainActivity.GENDER_FEMALE) {
                    activityFactor = 1.2;
                } else {
                    activityFactor = 1.3;
                }
                break;
            default:
                activityFactor = 0.3;
                break;

        }

        return (int) (bmr * activityFactor);
    }

    // calculate Themral Effect of Food
    public int calcTef(int rmr, int tee) {

        return (int) ((rmr + tee) * 0.10);
    }

    public double convertLbsToKgs(double lbs) {
        return lbs / 2.2045;
    }

    public double convertInchToCm(double inches) {
        return inches * 2.54;
    }

}

package com.dooragami.dineindiet.models;

/**
 * Created by derosea7 on 8/21/2016.
 */
public class Goal
{
    private double weight;
    private double dailyFoodExpense;
    private double dailyTotalFat;
    private double dailyTotalCarbs;
    private double dailyProtein;
  private double dailyCalorieGoal;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDailyFoodExpense() {
        return dailyFoodExpense;
    }

    public void setDailyFoodExpense(double dailyFoodExpense) {
        this.dailyFoodExpense = dailyFoodExpense;
    }

    public double getDailyTotalFat() {
        return dailyTotalFat;
    }

    public void setDailyTotalFat(double dailyTotalFat) {
        this.dailyTotalFat = dailyTotalFat;
    }

    public double getDailyTotalCarbs() {
        return dailyTotalCarbs;
    }

    public void setDailyTotalCarbs(double dailyTotalCarbs) {
        this.dailyTotalCarbs = dailyTotalCarbs;
    }

    public double getDailyProtein() {
        return dailyProtein;
    }

    public void setDailyProtein(double dailyProtein) {
        this.dailyProtein = dailyProtein;
    }
}

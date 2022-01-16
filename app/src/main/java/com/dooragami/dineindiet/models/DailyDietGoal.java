package com.dooragami.dineindiet.models;

/**
 * Created by derosea7 on 8/7/2018.
 */
public class DailyDietGoal {
  private double totalCalories;
  private double totalFat;
  private double totalCarbs;
  private double protein;
  private double dailyFoodExpense;
  private double costPerCalorie;
  private double weight;

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public double getTotalCalories() {
    return totalCalories;
  }

  public void setTotalCalories(double totalCalories) {
    this.totalCalories = totalCalories;
  }

  public double getTotalFat() {
    return totalFat;
  }

  public void setTotalFat(double totalFat) {
    this.totalFat = totalFat;
  }

  public double getTotalCarbs() {
    return totalCarbs;
  }

  public void setTotalCarbs(double totalCarbs) {
    this.totalCarbs = totalCarbs;
  }

  public double getProtein() {
    return protein;
  }

  public void setProtein(double protein) {
    this.protein = protein;
  }

  public double getDailyFoodExpense() {
    return dailyFoodExpense;
  }

  public void setDailyFoodExpense(double dailyFoodExpense) {
    this.dailyFoodExpense = dailyFoodExpense;
  }

  public double getCostPerCalorie() {
    return costPerCalorie;
  }

  public void setCostPerCalorie(double costPerCalorie) {
    this.costPerCalorie = costPerCalorie;
  }
}

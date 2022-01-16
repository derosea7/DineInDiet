package com.dooragami.dineindiet.models;

import java.text.DecimalFormat;

/**
 * Created by derosea7 on 8/28/2016.
 */

/// <summary>
/// Represents a summary of a consumption event (e.g. eating food).
/// </summary>
public class Consumption
{
//  private float cost;
//  private float fatSum_g;
//  private float carbSum_g;
//  private float proteinSum_g;
  private float totalFat;
  private float totalCarb;
  private float protein;
  private float cost;

  private double totalCalories;
  private double costPerCalorie;

  public double getTotalCalories() {
    return totalCalories;
  }

  public void setTotalCalories(double totalCalories) {
    this.totalCalories = totalCalories;
  }

  public double getCostPerCalorie() {
    return costPerCalorie;
  }

  public void setCostPerCalorie(double costPerCalorie) {
    this.costPerCalorie = costPerCalorie;
  }

  public float getTotalCarb() {
    return totalCarb;
  }

  public void setTotalCarb(float totalCarb) {
    this.totalCarb = totalCarb;
  }

  public float getCost() {
    return cost;
  }

  public void setCost(float cost) {
    this.cost = cost;
  }

  public float getTotalFat() {
    return totalFat;
  }

  public void setTotalFat(float totalFat) {
    this.totalFat = totalFat;
  }

  public float getProtein() {
    return protein;
  }

  public void setProtein(float protein) {
    this.protein = protein;
  }
}

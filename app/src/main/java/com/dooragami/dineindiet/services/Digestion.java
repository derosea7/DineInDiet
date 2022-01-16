package com.dooragami.dineindiet.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.dooragami.dineindiet.DBAdapter;
import com.dooragami.dineindiet.models.BodyComposition;
import com.dooragami.dineindiet.models.ChemicalProfile;
import com.dooragami.dineindiet.models.Nutrient;

/**
 * Created by derosea7 on 8/26/2016.
 */
public class Digestion
{
  double mNutrient;

  // Nutrient is required for digestion. [Not technically, as the smell of food can trigger digestion.]
  public Digestion(Nutrient nutrient)
  {
    // DigestionService, if you will. The digest method will analyze the chemical profile
    // and determine which
    // digest(nutrient);
  }

//  /// <summary>
//  ///
//  /// </summary>
//  private void digest(Nutrient nutrient, Context context)
//  {
//    BodyComposition bc = new BodyComposition();
//    //bc.setChemicalProfile(nutrient);
//
//    DBAdapter adapter = new DBAdapter(context);
//    adapter.open();
//    ContentValues contentValues = adapter.getBodyComposition();
//    adapter.close();
//
//    ///bc.setBodyComposition(contentValues.get(adapter.KEY_BODY_COMP_CHEMICAL_PROFILE_ID));
//    bc.setBodyComposition();
//  }

}

package com.dooragami.dineindiet.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by derosea7 on 8/27/2016.
 */

/// <summary>
/// Represents an adapter to an embedded SQLite database.
/// </summary
public class DatabaseAdapter
{
  private static final String DATABASE_NAME = "dbDineInDiet";
  private static final int DATABASE_VERSION = 0x0;

  private static class DatabaseAdapterHelper extends SQLiteOpenHelper
  {

    DatabaseAdapterHelper(Context context)
    {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
      // db.execSQL(CREATE_TABLE_);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
  }
}

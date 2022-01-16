package com.dooragami.dineindiet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/// <summary>
/// Represents an adapter to an embedded SQLite database.
/// </summary>
/// <remarks>
///
/// <remarks>
public class DBAdapter {

	private static final String TAG = "DBAdapter"; //used for logging database version changes

  public static final String DATABASE_NAME = "dbDineNDiet.db";
  public static final int DATABASE_VERSION = 4;

  private final Context context;

  private DatabaseHelper myDBHelper;
  private SQLiteDatabase db;

  public DBAdapter(Context ctx)
  {
    this.context = ctx;
    myDBHelper = new DatabaseHelper(context);
  }

  // Open the database connection.
  public DBAdapter open()
  {
    db = myDBHelper.getWritableDatabase();
    return this;
  }

  // Close the database connection.
  public void close()
  {
    myDBHelper.close();
  }

  public static final String TBL_GOALS      = "tblGoals";
  public static final String TBL_PURCH      = "tblPurch";
  public static final String TBL_CONS       = "tblCons";
  public static final String TBL_MYINFO     = "tblMyInfo";
  public static final String TBL_BODY_COMP  = "tblBodyComp";

  /**
   *  G O A L S
   */
	// Field Names: MyHealthGoals
	public static final String KEY_GOAL_ROWID = "_id";

  public static final String KEY_GOAL_ENTRY_DATE    = "entryDate";
  public static final String KEY_GOAL_WEIGHT_GOAL   = "weightGoal";
  public static final String KEY_GOAL_WEIGHT_ACTUAL = "weightActual";
  public static final String KEY_GOAL_WAIST_GOAL    = "waistGoal";
  public static final String KEY_GOAL_WAIST_ACTUAL  = "waistActual";
  public static final String KEY_GOAL_ACTIVITYLEVEL = "activityLevel";
  public static final String KEY_GOAL_DAILYFOODEXP  = "dailyFoodExp";
  public static final String KEY_GOAL_FAT_MACRO     = "fatMacro";
  public static final String KEY_GOAL_CARBS_MACRO   = "carbsMacro";
  public static final String KEY_GOAL_PROTEIN_MACRO = "proteinMacro";

  public static final String[] KEYS_GOALS = new String[]
      {
        KEY_GOAL_ROWID,
        KEY_GOAL_ENTRY_DATE,
        KEY_GOAL_WEIGHT_GOAL,
        KEY_GOAL_WEIGHT_ACTUAL,
        KEY_GOAL_WAIST_GOAL,
        KEY_GOAL_WAIST_ACTUAL,
        KEY_GOAL_ACTIVITYLEVEL,
        KEY_GOAL_DAILYFOODEXP,
        KEY_GOAL_FAT_MACRO,
        KEY_GOAL_CARBS_MACRO,
        KEY_GOAL_PROTEIN_MACRO
      };

  // Column numbers for each Field Name: Weight Goals
  public static final int COL_GOAL_ROWID          = 0;
  public static final int COL_GOAL_ENTRY_DATE     = 1;
  public static final int COL_GOAL_WEIGHT_GOAL    = 2;
  public static final int COL_GOAL_WEIGHT_ACTUAL  = 3;
  public static final int COL_GOAL_WAIST_GOAL     = 4;
  public static final int COL_GOAL_WAIST_ACTUAL   = 5;
  public static final int COL_GOAL_ACTIVITYLEVEL  = 6;
  public static final int COL_GOAL_DAILYFOODEXP   = 7;
  public static final int COL_GOAL_FAT_MACRO      = 8;
  public static final int COL_GOAL_CARBS_MACRO    = 9;
  public static final int COL_GOAL_PROTEIN_MACRO  = 10;
    //SQL statement to create database
    private static final String CREATE_TABLE_GOALS =
        "CREATE TABLE " + TBL_GOALS
        + " ("
            + KEY_GOAL_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_GOAL_ENTRY_DATE       + " DATE NULL, "
            + KEY_GOAL_WEIGHT_GOAL      + " TEXT NULL, "
            + KEY_GOAL_WEIGHT_ACTUAL    + " TEXT NULL, "
            + KEY_GOAL_WAIST_GOAL       + " TEXT NULL, "
            + KEY_GOAL_WAIST_ACTUAL     + " TEXT NULL, "
            + KEY_GOAL_ACTIVITYLEVEL    + " TEXT NULL, "
            + KEY_GOAL_DAILYFOODEXP     + " TEXT NULL, "
            + KEY_GOAL_FAT_MACRO        + " TEXT NULL, "
            + KEY_GOAL_CARBS_MACRO      + " TEXT NULL, "
            + KEY_GOAL_PROTEIN_MACRO    + " TEXT NULL"
        + ");";

    /**
     *  P U R C H A S E S
     */
    // field names for Add Purchase (Detailed)
    public static final String KEY_PURCH_ROWID = "_id";

  public static final String KEY_PURCH_FOODNAME         = "foodName";
  public static final String KEY_PURCH_PLACEOFPURCHASE  = "placeOfPurchase";
  public static final String KEY_PURCH_DATEOFPURCHASE   = "dateOfPurchase";
  public static final String KEY_PURCH_DATEADDED        = "dateAdded"; // record of record entry date
  public static final String KEY_PURCH_COST             = "cost";
  public static final String KEY_PURCH_SERVINGSIZE      = "servingSize";
  public static final String KEY_PURCH_SERVINGS         = "servings";
  public static final String KEY_PURCH_TOTALFAT         = "totalFat";
  public static final String KEY_PURCH_TOTALCARBS       = "totalCarbs";
  public static final String KEY_PURCH_PROTEIN          = "protein";

  public static final String[] KEYS_PURCH = new String[]
      {
        KEY_PURCH_ROWID,
        KEY_PURCH_FOODNAME,
        KEY_PURCH_PLACEOFPURCHASE,
        KEY_PURCH_DATEOFPURCHASE,
        KEY_PURCH_DATEADDED,
        KEY_PURCH_COST,
        KEY_PURCH_SERVINGSIZE,
        KEY_PURCH_SERVINGS,
        KEY_PURCH_TOTALFAT,
        KEY_PURCH_TOTALCARBS,
        KEY_PURCH_PROTEIN
      };

  public static final int COL_PURCH_ROWID = 0;
  public static final int COL_PURCH_FOODNAME = 1;
  public static final int COL_PURCH_PLACEOFPURCHASE = 2;
  public static final int COL_PURCH_DATEOFPURCHASE = 3;
  public static final int COL_PURCH_DATEADDED = 4;
  public static final int COL_PURCH_COST = 5;
  public static final int COL_PURCH_SERVINGSIZE = 6;
  public static final int COL_PURCH_SERVINGS = 7;
  public static final int COL_PURCH_TOTALFAT = 8;
  public static final int COL_PURCH_TOTALCARBS = 9;
  public static final int COL_PURCH_PROTEIN = 10;

  private static final String CREATE_TABLE_PURCH =
      "CREATE TABLE " + TBL_PURCH
      + "("
          + KEY_PURCH_ROWID           + " INTEGER PRIMARY KEY AUTOINCREMENT, "
          + KEY_PURCH_FOODNAME        + " TEXT NOT NULL, "
          + KEY_PURCH_PLACEOFPURCHASE + " TEXT NULL, "
          + KEY_PURCH_DATEOFPURCHASE  + " DATE NULL, "
          + KEY_PURCH_DATEADDED       + " DATE NULL, "
          + KEY_PURCH_COST            + " DOUBLE NOT NULL, "
          + KEY_PURCH_SERVINGSIZE     + " DOUBLE NOT NULL, "
          + KEY_PURCH_SERVINGS        + " DOUBLE NOT NULL, "
          + KEY_PURCH_TOTALFAT        + " DOUBLE NULL, "
          + KEY_PURCH_TOTALCARBS      + " DOUBLE NULL, "
          + KEY_PURCH_PROTEIN         + " DOUBLE NULL"
      + ");";

  /**
   *  C O N S U M P T I O N
   */
  // field names for Add Purchase (Detailed)
  public static final String KEY_CONS_ROWID           = "_id";
  public static final String KEY_CONS_FOODNAME        = "foodName";
  public static final String KEY_CONS_PLACEOFPURCHASE = "placeOfPurchase";
  public static final String KEY_CONS_DATEOFPURCHASE  = "dateOfPurchase";
  public static final String KEY_CONS_DATEADDED       = "dateAdded";
  public static final String KEY_CONS_DATECONSUMED    = "dateConsumed";
  public static final String KEY_CONS_COSTOFCONS      = "costOfCons";
  public static final String KEY_CONS_SERVINGSIZE     = "servingSize";
  public static final String KEY_CONS_SERVINGS        = "servings";
  public static final String KEY_CONS_TOTALFAT        = "totalFat";
  public static final String KEY_CONS_TOTALCARBS      = "totalCarbs";
  public static final String KEY_CONS_PROTEIN         = "protein";

  public static final String[] KEYS_CONS = new String[]
      {
        KEY_CONS_ROWID,
        KEY_CONS_FOODNAME,
        KEY_CONS_PLACEOFPURCHASE,
        KEY_CONS_DATEOFPURCHASE,
        KEY_CONS_DATEADDED,
        KEY_CONS_DATECONSUMED,
        KEY_CONS_COSTOFCONS,
        KEY_CONS_SERVINGSIZE,
        KEY_CONS_SERVINGS,
        KEY_CONS_TOTALFAT,
        KEY_CONS_TOTALCARBS,
        KEY_CONS_PROTEIN
      };

  public static final int COL_CONS_ROWID            = 0;
  public static final int COL_CONS_FOODNAME         = 1;
  public static final int COL_CONS_PLACEOFPURCHASE  = 2;
  public static final int COL_CONS_DATEOFPURCHASE   = 3;
  public static final int COL_CONS_DATECONSUMED     = 4;
  public static final int COL_CONS_DATEADDED        = 5;
  public static final int COL_CONS_COSTOFCONS       = 6;
  public static final int COL_CONS_SERVINGSIZE      = 7;
  public static final int COL_CONS_SERVINGS         = 8;
  public static final int COL_CONS_TOTALFAT         = 9;
  public static final int COL_CONS_TOTALCARBS       = 10;
  public static final int COL_CONS_PROTEIN          = 11;

  private static final String CREATE_TABLE_CONS =
      "CREATE TABLE " + TBL_CONS
      + " ("
          + KEY_CONS_ROWID            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
          + KEY_CONS_FOODNAME         + " TEXT NOT NULL, "
          + KEY_CONS_PLACEOFPURCHASE  + " TEXT NULL, "
          + KEY_CONS_DATEOFPURCHASE   + " DATE NULL, "
          + KEY_CONS_DATEADDED        + " DATE NULL, "
          + KEY_CONS_DATECONSUMED     + " DATE NULL, "
          + KEY_CONS_COSTOFCONS       + " DOUBLE NOT NULL, "
          + KEY_CONS_SERVINGSIZE      + " DOUBLE NOT NULL, "
          + KEY_CONS_SERVINGS         + " DOUBLE NOT NULL, "
          + KEY_CONS_TOTALFAT         + " DOUBLE NULL, "
          + KEY_CONS_TOTALCARBS       + " DOUBLE NULL, "
          + KEY_CONS_PROTEIN          + " DOUBLE NULL"
      + ");";

  /**
   * TABLE:
   */
  public static final String KEY_BODY_COMP_ID                  = "_id";
  public static final String KEY_BODY_COMP_CHEMICAL_PROFILE_ID = "ChemicalProfileID";
  public static final String KEY_BODY_COMP_CREATED_ON          = "CreatedOn";

  private static final String[] KEYS_BODY_COMP = new String[]
      {
        KEY_BODY_COMP_ID,
        KEY_BODY_COMP_CHEMICAL_PROFILE_ID,
        KEY_BODY_COMP_CREATED_ON
      };

  private static final int COL_BODY_COMP_ID = 0;
  private static final int COL_BODY_COMP_CHEMICAL_PROFILE_ID = 1;
  private static final int COL_BODY_COMP_CREATED_ON = 2;

  // TEXT as ISO8601 strings ("YYYY-MM-DD HH:MM:SS.SSS").
  private static final String CREATE_TABLE_BODY_COMP =
      "CREATE TABLE " + TBL_BODY_COMP
      + " ("
          + KEY_BODY_COMP_ID                  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
          + KEY_BODY_COMP_CHEMICAL_PROFILE_ID + " INTEGER NOT NULL, "
          + KEY_BODY_COMP_CREATED_ON          + " DATE NOT NULL "
      + " );";


	// Add a new set of values to be inserted into the database.

//	public long insertRow(String weight, String date) {
//		ContentValues initialValues = new ContentValues();
//		initialValues.put(KEY_WEIGHT, weight);
//		initialValues.put(KEY_DATE, date);
//
//		// Insert the data into the database.
//		return db.insert(DATABASE_TABLE, null, initialValues);
//	}
//    public long insertRowPurchase(String foodName, String date, String price) {

//        ContentValues cvs = new ContentValues();
//        cvs.put(KEY_PURCH_FOODNAME, foodName);
//        cvs.put(KEY_PURCH_DATE, date);
//        cvs.put(KEY_PURCH_PRICE, price);
//
//        return db.insert(DATABASE_TABLE_PURCH, null, cvs);
//    }
    /**
     * used to insert data obtained from Tab2Frag_Consumption into appropriate db
     */
	public long insertCvsPurch(ContentValues cvs) {
		return db.insert(TBL_PURCH, null, cvs);
	}

  public long insertCvsMyCons(ContentValues cvs) {
      return db.insert(TBL_CONS, null, cvs);
  }

  public long insertCvsMyGoals(ContentValues cvs) {
      return db.insert(TBL_GOALS, null, cvs);
  }

    /**
     *  D E L E T E
     */

    // Delete a row from the database, by rowId (primary key)
//	public boolean deleteRow(long rowId) {
//		String where = KEY_ROWID + "=" + rowId;
//		return db.delete(DATABASE_TABLE, where, null) != 0;
//	}

	public boolean deleteRowByTable(long rowId, String tablename, String rowIDKey) {
		String where = rowIDKey + "=" + rowId;
		return db.delete(tablename, where, null) != 0;
	}

//	public void deleteAll() {

//		Cursor c = getAllRows();
//		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
//		if (c.moveToFirst()) {
//			do {
//				deleteRow(c.getLong((int) rowId));
//			} while (c.moveToNext());
//		}
//		c.close();
//	}
  /**
   *  G E T
   */

//  /// <summary>
//  /// Helper method to convert a Cursor into a ContentValues DTO object.
//  /// </summary>
//  public static ContentValues convertCursorToContentValues(Cursor cursor)
//  {
//    ContentValues contentValues = new ContentValues();
//    String[] columns = cursor.getColumnNames();
//    for (int i = 0; i < columns.length; i++)
//    {
//      switch (cursor.getType(i))
//      {
//        case Cursor.FIELD_TYPE_STRING:
//          contentValues.put(columns[i], cursor.getString(i));
//          break;
//        case Cursor.FIELD_TYPE_INTEGER:
//          contentValues.put(columns[i], cursor.getInt(i));
//          break;
//        case Cursor.FIELD_TYPE_FLOAT:
//          contentValues.put(columns[i], cursor.getFloat(i));
//      }
//    }
//    return contentValues;
//  }

  /// <summary>
  /// Gets the current ChemicalProfileId.
  /// </summary>
  public ContentValues getBodyCompositionChemicalProfile()
  {
    StringBuilder sb = new StringBuilder();
    sb.append(";SELECT TOP 1 ");
    sb.append(" FROM ").append(TBL_BODY_COMP + " ");
    sb.append(" ORDER BY  " + KEY_BODY_COMP_CREATED_ON + " DESC;");

    /// Contains the integer id of the chemical profile of the body comp.
    Cursor cursor = db.rawQuery(sb.toString(), null);
    int chemicalProfileId = cursor.getInt(DBAdapter.COL_BODY_COMP_CHEMICAL_PROFILE_ID);


    ContentValues contentValues = new ContentValues();

    //return new ContentValues(convertCursorToContentValues(cursor));
    android.database.DatabaseUtils.cursorRowToContentValues(cursor, contentValues);
    cursor.close();

    return contentValues;
  }

  /// <summary>

//  public ContentValues getBodyComposition()
//  {
//    StringBuilder sb = new StringBuilder();
//    sb.append(";SELECT TOP 1 ");
//    sb.append(" FROM ").append(TBL_BODY_COMP + " ");
//    sb.append(" ORDER BY  " + KEY_BODY_COMP_CREATED_ON + " DESC;");
//
//    Cursor c = db.rawQuery(sb.toString(), null);
//    //Cursor c = db.query(TBL_BODY_COMP, KEYS_BODY_COMP, "", null, null, null, null);
//
//    return c;
//  }

  public Cursor getPurchasesByDate(String whereDate) {
      String WHERE = KEY_PURCH_DATEOFPURCHASE + " = " + whereDate;
      String orderBy = "" + KEY_PURCH_FOODNAME + " ASC";
      Cursor c = db.query(true, TBL_PURCH, KEYS_PURCH, WHERE, null, null, null, orderBy, null);
      if (c != null) {
          c.moveToFirst();
      }
      return c;
  }

  public Cursor getConsByDate(String whereDate) {
      String WHERE = KEY_CONS_DATECONSUMED + " = " + whereDate;
      String orderBy = "" + KEY_CONS_FOODNAME + " ASC";
      Cursor c = db.query(true, TBL_CONS, KEYS_CONS, WHERE, null, null, null, orderBy, null);
      if (c != null) {
          c.moveToFirst();
      }
      return c;
  }

  public Cursor getConsOverview(String whereDate) {
      String WHERE = KEY_CONS_DATECONSUMED + " = " + whereDate;

      // TODO: use selectionArgs to prevent SQL injections.. as if its a security risk haha
      String sqlOverview =
              "SELECT SUM(costOfCons) AS costOfCons, SUM(totalFat) AS totalFat" +
              ", SUM(totalCarbs) AS totalCarbs, SUM(protein) AS protein" +
              " FROM tblCons" +
              " WHERE dateConsumed = " + whereDate + ";";

      Cursor c = db.rawQuery(sqlOverview, null);
      if (c != null) {
          c.moveToFirst();
      }
      return c;
  }

  public Cursor checkGoalEntryDate(String whereDate) {

    // TODO: use selectionArgs
    String sqlOverview =
            "SELECT _id, entryDate" +
                    " FROM tblGoals" +
                    " WHERE entryDate = " + "'" + whereDate + "'" + ";";

    Cursor c = db.rawQuery(sqlOverview, null);
    if (c != null) {
        c.moveToFirst();
    } else {
        // c is null and there are no matching entries for the given date
        // meaning that the user has not entered any goals today
    }
    return c;
  }

  public Cursor getLatestGoal()
  {

    ContentValues cvs = new ContentValues();

    String sqlLatestGoal = "SELECT * FROM tblGoals ORDER BY entryDate DESC LIMIT 1;";
    Cursor c = db.rawQuery(sqlLatestGoal, null);
    if (c != null) {
      c.moveToFirst();
    }
    return c;



  }

    /**
     *  Method returns a cursor object containing 3 keys of information regarding purchases:
     *  food name, purchase date, purchase cost. These will then be used to populate a ListView
     *  in a dialog fragment that allows the user to choose a food to add to their consumption
     *  table.
     */
    public Cursor getFoodnamesForDialog()
    {
      String[] foodnamesForDialog = new String[]
          {
            KEY_PURCH_ROWID,
            KEY_PURCH_FOODNAME,
            KEY_PURCH_DATEOFPURCHASE,
            KEY_PURCH_COST
          };

      Cursor c = db.query(true, TBL_PURCH, foodnamesForDialog, null, null, null, null, null, null);

      if (c != null) c.moveToFirst();

      return c;
    }

    /**
     * this method will query data needed to populate the TextViews on Tab1_Frag_Consumption
     * TODO:  changed 3rd param of db.query to only pick desired columns instead of KEYS_PURCH
     */
    public Cursor getInfoToAddConsumption(long rowID) {
        String[] foodnamesForDialog = new String[] {KEY_PURCH_ROWID, KEY_PURCH_FOODNAME};
        String where = KEY_PURCH_ROWID + " = " + String.valueOf(rowID);
        Cursor c = db.query(true, TBL_PURCH, KEYS_PURCH, where, null, null, null, null, null);
        if (c != null) c.moveToFirst();
        return c;
    }

	/**
	 * Query the given URL, returning a {@link Cursor} over the result set.
	 *
	 * param distinct true if you want each row to be unique, false otherwise.
	 * param table The table name to compile the query against.
	 * param columns A list of which columns to return. Passing null will
	 *           return all columns, which is discouraged to prevent reading
	 *           data from storage that isn't going to be used.
	 * param selection A filter declaring which rows to return, formatted as an
	 *           SQL WHERE clause (excluding the WHERE itself). Passing null
	 *           will return all rows for the given table.
	 * param selectionArgs You may include ?s in selection, which will be
	 *        replaced by the values from selectionArgs, in order that they
	 *        appear in the selection. The values will be bound as Strings.
	 * param groupBy A filter declaring how to group rows, formatted as an SQL
	 *           GROUP BY clause (excluding the GROUP BY itself). Passing null
	 *           will cause the rows to not be grouped.
	 * param having A filter declare which row groups to include in the cursor,
	 *           if row grouping is being used, formatted as an SQL HAVING
	 *           clause (excluding the HAVING itself). Passing null will cause
	 *           all row groups to be included, and is required when row
	 *           grouping is not being used.
	 * param orderBy How to order the rows, formatted as an SQL ORDER BY clause
	 *           (excluding the ORDER BY itself). Passing null will use the
	 *           default sort order, which may be unordered.
	 * param limit Limits the number of rows returned by the query,
	 *           formatted as LIMIT clause. Passing null denotes no LIMIT clause.
	 * return A {link Cursor} object, which is positioned before the first entry. Note that
	 * {link Cursor}s are not synchronized, see the documentation for more details.
	 * see Cursor
	 */
    public Cursor getSpecificRowsByTable(String tablename, String[] allKeys) {
        String where = null;
		String orderBy = null;
        Cursor c = db.query(true, tablename, allKeys, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
	public void updateGoalEntry(long rowId, ContentValues cvs) {
		String where = KEY_GOAL_ROWID + "=" + rowId;
		// Insert it into the database.
		db.update(TBL_GOALS, cvs, where, null);
	}

	// Get a specific row (by rowId)


//	public Cursor getRow(long rowId) {
//		String where = KEY_ROWID + "=" + rowId;
//		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
//						where, null, null, null, null, null);
//		if (c != null) {
//			c.moveToFirst();
//		}
//		return c;
//	}
	// Change an existing row to be equal to new data.

//	public boolean updateRow(long rowId, String weight, String date) {
//		String where = KEY_ROWID + "=" + rowId;
//		ContentValues newValues = new ContentValues();
//		newValues.put(KEY_WEIGHT, weight);
//		newValues.put(KEY_DATE, date);
//		// Insert it into the database.
//		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
//	}
  /**
   *  I N S E R T
   */

//end region
/// <summary>
/// Nested static inner class to prevent memory leaks.
/// </summary>
  private static class DatabaseHelper extends SQLiteOpenHelper
  {
    DatabaseHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase _db) {
      _db.execSQL(CREATE_TABLE_GOALS);
      _db.execSQL(CREATE_TABLE_PURCH);
      _db.execSQL(CREATE_TABLE_CONS);

      // added in db ver 5
      _db.execSQL(CREATE_TABLE_BODY_COMP);

    }

    // TODO: figure out how to write ALTER TABLE statement rather than DROP TABLE so
    // when updating the app after commerical release there is no lost data
    @Override
    public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
      Log.w(TAG, "Upgrading application's database from version " + oldVersion
          + " to " + newVersion + ", which will destroy all old data!");

      // Destroy old database:
      //_db.execSQL("DROP TABLE IF EXISTS " + TBL_GOALS);
      //_db.execSQL("DROP TABLE IF EXISTS " + TBL_PURCH);
      //_db.execSQL("DROP TABLE IF EXISTS " + TBL_CONS);

      // Checks version, then ADDs column
      // note that can only add one column at a time
//            if (oldVersion < 1) {
//                _db.execSQL(ALTER_TABLE_GOALS_1);
//            }

      /**
       * An example code to distinctly handle updgrading a db based on db version.
       * Following code is an example taken from:
       * http://stackoverflow.com/questions/14579175/sqlite-onupgrade?rq=1
       *
       * onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       *   switch(oldVersion) {
       *      case 1:
       *        db.execSQL(DATABASE_CREATE_color);
       *        // we want both updates, so no break statement here...
       *      case 2:
       *        db.execSQL(DATABASE_CREATE_someothertable);
       *   }
       * }
       *
       */

      /**
       * // Recreate new database:
       * onCreate(_db);
       */
    }
  }

}


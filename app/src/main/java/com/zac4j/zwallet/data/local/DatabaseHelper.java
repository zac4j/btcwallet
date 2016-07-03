package com.zac4j.zwallet.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import javax.inject.Singleton;

/**
 * SQLite Database Helper
 * Created by zac on 16-7-3.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

  private static final String DATABASE_NAME = "z-wallet.db";
  private static final int DATABASE_VERSION = 1;

  public static final String TABLE_NAME = "";

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
        ");";
    sqLiteDatabase.execSQL(sql);
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
    sqLiteDatabase.execSQL(sql);
  }
}

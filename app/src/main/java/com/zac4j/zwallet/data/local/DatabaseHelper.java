package com.zac4j.zwallet.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import javax.inject.Singleton;

/**
 * SQLite Database Helper
 * Created by zac on 16-7-3.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "z-wallet.db";
  private static final int DATABASE_VERSION = 1;

  public static final String TABLE_ACCOUNT = "account";

  public static final String FIELD_ID = "_id";
  public static final String FIELD_TOTAL_ASSET = "total_asset";
  public static final String FIELD_NET_ASSET = "net_asset";
  public static final String FIELD_CNY_ASSET = "cny_asset";
  public static final String FIELD_LTC_ASSET = "ltc_asset";
  public static final String FIELD_BTC_ASSET = "btc_asset";
  public static final String FIELD_CNY_FROZEN = "cny_frozen";
  public static final String FIELD_LTC_FROZEN = "ltc_frozen";
  public static final String FIELD_BTC_FROZEN = "btc_frozen";

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNT + "(" +
        FIELD_ID + " INTEGER PRIMARY KEY," +
        FIELD_TOTAL_ASSET + " TEXT," +
        FIELD_NET_ASSET + " TEXT," +
        FIELD_CNY_ASSET + " TEXT," +
        FIELD_BTC_ASSET + " TEXT," +
        FIELD_LTC_ASSET + " TEXT," +
        FIELD_CNY_FROZEN + " TEXT," +
        FIELD_BTC_FROZEN + " TEXT," +
        FIELD_LTC_FROZEN + " TEXT);";
    sqLiteDatabase.execSQL(sql);
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    String sql = "DROP TABLE IF EXISTS " + TABLE_ACCOUNT;
    sqLiteDatabase.execSQL(sql);
  }
}

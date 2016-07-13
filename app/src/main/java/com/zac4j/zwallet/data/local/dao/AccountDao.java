package com.zac4j.zwallet.data.local.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.zac4j.zwallet.data.local.DatabaseHelper;
import com.zac4j.zwallet.di.ApplicationContext;
import com.zac4j.zwallet.model.response.AccountInfo;
import com.zac4j.zwallet.util.Utils;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Single;

/**
 * Account info data access object
 * Created by zac on 16-7-13.
 */

@Singleton public class AccountDao {

  private DatabaseHelper mDatabaseHelper;

  @Inject public AccountDao(@ApplicationContext Context context) {
    mDatabaseHelper = new DatabaseHelper(context);
  }

  /**
   * Save account info
   *
   * @param accountInfo account info
   */
  public void setAccountInfo(AccountInfo accountInfo) {
    SQLiteDatabase database = null;

    try {
      database = mDatabaseHelper.getWritableDatabase();
      if (database != null) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_NET_ASSET, accountInfo.getNetAssets());
        values.put(DatabaseHelper.FIELD_TOTAL_ASSET, accountInfo.getTotal());
        values.put(DatabaseHelper.FIELD_CNY_ASSET, accountInfo.getAvailableCNY());
        values.put(DatabaseHelper.FIELD_BTC_ASSET, accountInfo.getAvailableBTC());
        values.put(DatabaseHelper.FIELD_LTC_ASSET, accountInfo.getAvailableLTC());
        values.put(DatabaseHelper.FIELD_CNY_FROZEN, accountInfo.getFrozenCNY());
        values.put(DatabaseHelper.FIELD_BTC_FROZEN, accountInfo.getFrozenBTC());
        values.put(DatabaseHelper.FIELD_LTC_FROZEN, accountInfo.getFrozenLTC());

        database.insert(DatabaseHelper.TABLE_ACCOUNT, null, values);
      }
    } finally {
      Utils.close(database);
    }
  }

  /**
   * Get account total asset info
   *
   * @return total asset
   */
  public Single<String> getTotalAsset() {
    return Single.defer(new Callable<Single<String>>() {
      @Override public Single<String> call() throws Exception {
        String totalAsset = "";
        String[] columns = { DatabaseHelper.FIELD_TOTAL_ASSET };
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {
          database = mDatabaseHelper.getReadableDatabase();
          if (database != null) {
            cursor =
                database.query(DatabaseHelper.TABLE_ACCOUNT, columns, null, null, null, null, null);
            if (cursor.moveToFirst()) {
              totalAsset = cursor.getString(0);
            }
          }
          return Single.just(totalAsset);
        } finally {
          Utils.close(cursor);
          Utils.close(database);
        }
      }
    });
  }

  /**
   * Get account cny asset info
   *
   * @return cny asset
   */
  public Single<String> getCNYAsset() {
    return Single.defer(new Callable<Single<String>>() {
      @Override public Single<String> call() throws Exception {
        String totalAsset = "";
        String[] columns = { DatabaseHelper.FIELD_CNY_ASSET };
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {
          database = mDatabaseHelper.getReadableDatabase();
          if (database != null) {
            cursor =
                database.query(DatabaseHelper.TABLE_ACCOUNT, columns, null, null, null, null, null);
            if (cursor.moveToFirst()) {
              totalAsset = cursor.getString(0);
            }
          }
          return Single.just(totalAsset);
        } finally {
          Utils.close(cursor);
          Utils.close(database);
        }
      }
    });
  }

  /**
   * Clear all database data
   */
  public void clearAll() {
    SQLiteDatabase database = null;

    try {
      database = mDatabaseHelper.getWritableDatabase();
      if (database != null) {
        database.delete(DatabaseHelper.TABLE_ACCOUNT, null, null);
      }
    } finally {
      Utils.close(database);
    }
  }
}

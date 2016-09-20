package com.zac4j.zwallet.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import com.zac4j.zwallet.di.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * SharedPreferences Helper
 * Created by zac on 16-7-3.
 */

@Singleton
public class PreferencesHelper {

  private static final String PREFS_FILENAME = "prefs_file";

  private SharedPreferences mPrefs;

  @Inject public PreferencesHelper(@ApplicationContext Context context) {
    mPrefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE);
  }

  public SharedPreferences getPrefs() {
    if (mPrefs == null) {
      throw new IllegalStateException("PreferencesHelper had not initialized!");
    }
    return mPrefs;
  }

  public SharedPreferences.Editor getEditor() {
    if (mPrefs == null) {
      throw new IllegalStateException("PreferencesHelper had not initialized!");
    }
    return mPrefs.edit();
  }

  public void clear() {
    mPrefs.edit().clear().apply();
  }

}

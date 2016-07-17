package com.zac4j.zwallet.di.module;

import android.app.Activity;
import android.content.Context;
import com.zac4j.zwallet.di.ActivityContext;
import dagger.Module;
import dagger.Provides;

/**
 * Activity Module
 * Created by zac on 16-7-3.
 */

@Module public class ActivityModule {

  private Activity mActivity;

  public ActivityModule(Activity activity) {
    mActivity = activity;
  }

  @Provides Activity provideActivity() {
    return mActivity;
  }

  @Provides @ActivityContext Context provideContext() {
    if (mActivity == null) {
      throw new IllegalStateException("ActivityModule had not initialized!");
    }
    return mActivity;
  }
}

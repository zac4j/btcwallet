package com.zac4j.zwallet.di.module;

import android.content.Context;
import com.zac4j.zwallet.di.ActivityContext;
import dagger.Module;
import dagger.Provides;

/**
 * Activity Module
 * Created by zac on 16-7-3.
 */

@Module
public class ActivityModule {

  private Context mContext;

  public ActivityModule(Context context){
    mContext = context;
  }

  @Provides @ActivityContext Context provideContext() {
    if (mContext == null) {
      throw new IllegalStateException("ActivityModule had not initialized!");
    }
    return mContext;
  }

}

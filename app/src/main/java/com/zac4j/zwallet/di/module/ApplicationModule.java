package com.zac4j.zwallet.di.module;

import android.app.Application;
import android.content.Context;
import com.google.gson.Gson;
import com.zac4j.zwallet.data.local.PreferencesHelper;
import com.zac4j.zwallet.di.ApplicationContext;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Application Module
 * Created by zac on 16-7-3.
 */

@Module
public class ApplicationModule {

  private Application mApplication;

  public ApplicationModule(Application application) {
    mApplication = application;
  }

  @Provides Application provideApplication() {
    return mApplication;
  }

  @Provides @ApplicationContext Context provideContext() {
    return mApplication;
  }

  @Provides @Singleton PreferencesHelper providePrefsHelper() {
    return new PreferencesHelper(mApplication);
  }
}

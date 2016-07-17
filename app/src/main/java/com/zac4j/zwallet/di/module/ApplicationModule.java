package com.zac4j.zwallet.di.module;

import android.app.Application;
import android.content.Context;
import com.google.gson.Gson;
import com.zac4j.zwallet.data.local.PreferencesHelper;
import com.zac4j.zwallet.data.local.dao.AccountDao;
import com.zac4j.zwallet.data.remote.HttpClient;
import com.zac4j.zwallet.data.remote.WebService;
import com.zac4j.zwallet.di.ApplicationContext;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit2.Retrofit;

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

  @Provides Gson provideGson() {
    return new Gson();
  }

  @Provides @Singleton WebService provideWebService() {
    return WebService.Creator.create();
  }

}

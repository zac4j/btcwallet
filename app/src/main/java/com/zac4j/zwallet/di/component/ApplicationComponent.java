package com.zac4j.zwallet.di.component;

import android.content.Context;

import com.google.gson.Gson;
import com.zac4j.zwallet.data.local.PreferencesHelper;
import com.zac4j.zwallet.data.remote.HttpClient;
import com.zac4j.zwallet.data.remote.WebService;
import com.zac4j.zwallet.di.ApplicationContext;
import com.zac4j.zwallet.di.module.ApplicationModule;
import com.zac4j.zwallet.viewmodel.MainViewModel;
import com.zac4j.zwallet.viewmodel.SendFundsViewModel;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Application Component
 * Created by zac on 16-7-3.
 */

@Singleton @Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

  void inject(MainViewModel mainViewModel);
  void inject(SendFundsViewModel sendFundsViewModel);

  @ApplicationContext Context context();
  PreferencesHelper prefsHelper();
  Gson gson();
  HttpClient httpClient();
  WebService webService();
}

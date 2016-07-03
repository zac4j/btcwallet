package com.zac4j.zwallet.di.component;

import android.content.Context;

import com.zac4j.zwallet.data.local.PreferencesHelper;
import com.zac4j.zwallet.di.ApplicationContext;
import com.zac4j.zwallet.di.module.ApplicationModule;
import com.zac4j.zwallet.viewmodel.MainViewModel;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Application Component
 * Created by zac on 16-7-3.
 */

@Singleton @Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

  void inject(MainViewModel mainViewModel);

  @ApplicationContext Context context();
  PreferencesHelper prefsHelper();
}

package com.zac4j.zwallet;

import android.app.Application;
import android.content.Context;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.zac4j.zwallet.di.ActivityContext;
import com.zac4j.zwallet.di.component.ApplicationComponent;
import com.zac4j.zwallet.di.component.DaggerApplicationComponent;
import com.zac4j.zwallet.di.module.ApplicationModule;

/**
 * Customize App
 * Created by zac on 16-7-3.
 */

public class App extends Application {

  private ApplicationComponent mApplicationComponent;

  @Override public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);

    if (BuildConfig.DEBUG) {
      Stetho.initializeWithDefaults(this);
    }
  }

  public static App get(@ActivityContext Context context) {
    return (App) context.getApplicationContext();
  }

  public ApplicationComponent getApplicationComponent() {
    if (mApplicationComponent == null) {
      mApplicationComponent = DaggerApplicationComponent.builder()
          .applicationModule(new ApplicationModule(this))
          .build();
    }
    return mApplicationComponent;
  }
}

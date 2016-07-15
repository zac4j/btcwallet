package com.zac4j.zwallet.view;

import android.support.v7.app.AppCompatActivity;
import com.zac4j.zwallet.App;
import com.zac4j.zwallet.di.component.ActivityComponent;
import com.zac4j.zwallet.di.component.DaggerActivityComponent;
import com.zac4j.zwallet.di.module.ActivityModule;

/**
 * Base Activity
 * Created by zac on 16-7-15.
 */

public class BaseActivity extends AppCompatActivity {

  private ActivityComponent mActivityComponent;

  protected ActivityComponent getActivityComponent() {
    if (mActivityComponent == null) {
      mActivityComponent = DaggerActivityComponent.builder()
          .applicationComponent(App.get(this).getApplicationComponent())
          .activityModule(new ActivityModule(this))
          .build();
    }
    return mActivityComponent;
  }

}

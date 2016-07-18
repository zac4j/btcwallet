package com.zac4j.zwallet.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AppCompatActivity;
import com.zac4j.zwallet.App;
import com.zac4j.zwallet.di.component.ActivityComponent;
import com.zac4j.zwallet.di.component.DaggerPerConfigComponent;
import com.zac4j.zwallet.di.component.PerConfigComponent;
import com.zac4j.zwallet.di.module.ActivityModule;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Base Activity
 * Created by zac on 16-7-15.
 */

public class BaseActivity extends AppCompatActivity {

  private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
  private static final AtomicLong NEXT_ID = new AtomicLong(0);
  private static final LongSparseArray<PerConfigComponent> sComponentsArray =
      new LongSparseArray<>();

  private ActivityComponent mActivityComponent;
  private long mActivityId;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
    // being called after a configuration change.
    mActivityId = savedInstanceState != null ? savedInstanceState.getLong(KEY_ACTIVITY_ID)
        : NEXT_ID.getAndIncrement();
    PerConfigComponent viewModelComponent;
    if (sComponentsArray.indexOfKey(mActivityId) < 0) {
      viewModelComponent = DaggerPerConfigComponent.builder()
          .applicationComponent(App.get(this).getApplicationComponent())
          .build();
      sComponentsArray.put(mActivityId, viewModelComponent);
    } else {
      viewModelComponent = sComponentsArray.get(mActivityId);
    }
    mActivityComponent = viewModelComponent.activityComponent(new ActivityModule(this));
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putLong(KEY_ACTIVITY_ID, mActivityId);
  }

  @Override protected void onDestroy() {
    if (!isChangingConfigurations()) { // If not configuration change, remove it.
      sComponentsArray.remove(mActivityId);
    }
    super.onDestroy();
  }

  ActivityComponent getActivityComponent() {
    return mActivityComponent;
  }
}

package com.zac4j.zwallet.di.component;

import android.content.Context;
import com.zac4j.zwallet.di.ActivityContext;
import com.zac4j.zwallet.di.PerActivity;
import com.zac4j.zwallet.di.module.ActivityModule;
import dagger.Component;

/**
 * Activity Component
 * Created by zac on 16-7-3.
 */

@PerActivity @Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  @ActivityContext Context context();
}

package com.zac4j.zwallet.di.component;

import com.zac4j.zwallet.di.PerConfig;
import com.zac4j.zwallet.di.module.ActivityModule;
import com.zac4j.zwallet.view.BaseActivity;
import dagger.Component;

/**
 * A dagger component that live during the lifecycle of an Activity but it won't be destroy
 * during configuration changes.Check {@link BaseActivity} to see how this components
 * survives configuration changes.
 * Use the {@link PerConfig} scope to annotate dependencies that need to survive
 * configuration changes (for example ViewModels).
 *
 * Created by zac on 16-7-17.
 */

@PerConfig
@Component(dependencies = ApplicationComponent.class)
public interface PerConfigComponent {
  ActivityComponent activityComponent(ActivityModule activityModule);
}

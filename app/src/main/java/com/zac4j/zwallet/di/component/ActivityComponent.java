package com.zac4j.zwallet.di.component;

import android.content.Context;
import com.zac4j.zwallet.di.ActivityContext;
import com.zac4j.zwallet.di.PerActivity;
import com.zac4j.zwallet.di.module.ActivityModule;
import com.zac4j.zwallet.view.CoinTradeActivity;
import com.zac4j.zwallet.view.DashboardActivity;
import com.zac4j.zwallet.view.MainActivity;
import com.zac4j.zwallet.view.MyWalletActivity;
import com.zac4j.zwallet.view.OrderDetailDialogFragment;
import com.zac4j.zwallet.view.SettingsActivity;
import com.zac4j.zwallet.view.TransactionActivity;
import dagger.Subcomponent;

/**
 * Activity Component
 * Created by zac on 16-7-3.
 */

@PerActivity @Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
  @ActivityContext Context context();
  void inject(MainActivity activity);
  void inject(DashboardActivity activity);
  void inject(CoinTradeActivity activity);
  void inject(TransactionActivity activity);
  void inject(MyWalletActivity activity);
  void inject(OrderDetailDialogFragment fragment);
  void inject(SettingsActivity activity);
}

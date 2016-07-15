package com.zac4j.zwallet.di.component;

import android.content.Context;
import com.zac4j.zwallet.di.ActivityContext;
import com.zac4j.zwallet.di.PerActivity;
import com.zac4j.zwallet.di.module.ActivityModule;
import com.zac4j.zwallet.view.CoinTradeActivity;
import com.zac4j.zwallet.view.MainActivity;
import com.zac4j.zwallet.view.MyWalletActivity;
import com.zac4j.zwallet.view.OrderDetailDialogFragment;
import com.zac4j.zwallet.view.TransactionActivity;
import com.zac4j.zwallet.viewmodel.CoinTradeViewModel;
import com.zac4j.zwallet.viewmodel.MainViewModel;
import com.zac4j.zwallet.viewmodel.MyWalletViewModel;
import com.zac4j.zwallet.viewmodel.OrderDetailViewModel;
import com.zac4j.zwallet.viewmodel.TransactionViewModel;
import dagger.Component;

/**
 * Activity Component
 * Created by zac on 16-7-3.
 */

@PerActivity @Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

  void inject(MainActivity activity);
  void inject(CoinTradeActivity activity);
  void inject(TransactionActivity activity);
  void inject(MyWalletActivity activity);
  void inject(OrderDetailDialogFragment fragment);

  @ActivityContext Context context();

  MainViewModel mainViewModel();
  CoinTradeViewModel coinTradeViewModel();
  TransactionViewModel transactionViewModel();
  MyWalletViewModel myWalletViewModel();
  OrderDetailViewModel orderDetailViewModel();
}

package com.zac4j.zwallet.di.module;

import android.content.Context;
import com.zac4j.zwallet.di.ActivityContext;
import com.zac4j.zwallet.viewmodel.CoinTradeViewModel;
import com.zac4j.zwallet.viewmodel.MainViewModel;
import com.zac4j.zwallet.viewmodel.MyWalletViewModel;
import com.zac4j.zwallet.viewmodel.OrderDetailViewModel;
import com.zac4j.zwallet.viewmodel.TransactionViewModel;
import dagger.Module;
import dagger.Provides;

/**
 * Activity Module
 * Created by zac on 16-7-3.
 */

@Module
public class ActivityModule {

  private Context mContext;

  public ActivityModule(Context context){
    mContext = context;
  }

  @Provides @ActivityContext Context provideContext() {
    if (mContext == null) {
      throw new IllegalStateException("ActivityModule had not initialized!");
    }
    return mContext;
  }

  @Provides MainViewModel provideMainViewModel() {
    return new MainViewModel(mContext);
  }

  @Provides CoinTradeViewModel provideCoinTradeViewModel() {
    return new CoinTradeViewModel(mContext);
  }

  @Provides TransactionViewModel provideTransactionViewModel() {
    return new TransactionViewModel(mContext);
  }

  @Provides MyWalletViewModel provideMyWalletViewModel() {
    return new MyWalletViewModel(mContext);
  }

  @Provides OrderDetailViewModel provideOrderDetailViewModel() {
    return new OrderDetailViewModel(mContext);
  }

}

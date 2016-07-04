package com.zac4j.zwallet.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import android.widget.Toast;
import com.zac4j.zwallet.App;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.data.local.PreferencesHelper;
import com.zac4j.zwallet.data.remote.WebService;
import com.zac4j.zwallet.model.response.AccountInfo;
import com.zac4j.zwallet.model.response.DealOrder;
import com.zac4j.zwallet.util.Constants;
import com.zac4j.zwallet.util.RxUtils;
import com.zac4j.zwallet.util.Utils;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;

/**
 * Main page view model
 * Created by zac on 16-7-3.
 */

public class MainViewModel implements ViewModel {

  private static final String GET_ACCOUNT_INFO = "get_account_info";
  private static final String GET_RECENT_ORDERS = "get_new_deal_orders";

  public ObservableInt progressVisibility;
  public ObservableInt ordersVisibility;
  public ObservableField<String> totalAsset;
  public ObservableField<String> coinAsset;

  private Context mContext;
  private Subscription mSubscription;
  private int coinType;
  private String requestTime;

  @Inject PreferencesHelper mPrefsHelper;
  @Inject WebService mWebService;

  public interface OnDataChangedListener {
    void onGetRecentOrders(List<DealOrder> dealOrderList);
  }

  private OnDataChangedListener mDataChangedListener;

  public void setOnDataChangedListener(OnDataChangedListener listener) {
    mDataChangedListener = listener;
  }

  public MainViewModel(Context context) {
    mContext = context;
    progressVisibility = new ObservableInt(View.INVISIBLE);
    ordersVisibility = new ObservableInt(View.INVISIBLE);

    coinAsset = new ObservableField<>();
    totalAsset = new ObservableField<>();
    App.get(mContext).getApplicationComponent().inject(this);

    coinType =
        mPrefsHelper.getPrefs().getInt(Constants.CURRENT_SELECT_COIN, Constants.COIN_TYPE_LTC);

    // Get data while showing
    requestTime = String.valueOf(System.currentTimeMillis()).substring(0, 10);
    getAccountInfo(requestTime);
    getRecentOrders(requestTime);
  }

  public void onRefresh(View view) {
    getAccountInfo(requestTime);
    getRecentOrders(requestTime);
  }

  private void getAccountInfo(String time) {
    progressVisibility.set(View.VISIBLE);

    mSubscription = mWebService.getAccountInfo(GET_ACCOUNT_INFO, Utils.ACCESS_KEY, time,
        Utils.generateSign(GET_ACCOUNT_INFO, time))
        .compose(RxUtils.<AccountInfo>applySchedulers())
        .subscribe(new Subscriber<AccountInfo>() {
          @Override public void onCompleted() {
            progressVisibility.set(View.INVISIBLE);
          }

          @Override public void onError(Throwable e) {
            progressVisibility.set(View.INVISIBLE);
            Toast.makeText(mContext, mContext.getString(R.string.request_error_network),
                Toast.LENGTH_SHORT).show();
          }

          @Override public void onNext(AccountInfo accountInfo) {
            if (accountInfo != null) {
              NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.CHINA);
              String totalAssets = formatter.format(Double.parseDouble(accountInfo.getTotal()));
              totalAsset.set(totalAssets);

              String coinAssets;
              if (coinType == Constants.COIN_TYPE_BTC) {
                coinAssets = mContext.getString(R.string.unit_btc, accountInfo.getAvailableBTC());
              } else {
                coinAssets = mContext.getString(R.string.unit_ltc, accountInfo.getAvailableLTC());
              }
              coinAsset.set(coinAssets);
            }
          }
        });
  }

  private void getRecentOrders(String time) {
    progressVisibility.set(View.VISIBLE);

    mSubscription =
        mWebService.getRecentOrders(GET_RECENT_ORDERS, Utils.ACCESS_KEY, String.valueOf(coinType),
            time, Utils.generateSign(GET_RECENT_ORDERS, String.valueOf(coinType), time))
            .compose(RxUtils.<List<DealOrder>>applySchedulers())
            .subscribe(new Subscriber<List<DealOrder>>() {
              @Override public void onCompleted() {
                progressVisibility.set(View.INVISIBLE);
              }

              @Override public void onError(Throwable e) {
                progressVisibility.set(View.INVISIBLE);
              }

              @Override public void onNext(List<DealOrder> dealOrders) {
                if (dealOrders != null && !dealOrders.isEmpty()) {
                  mDataChangedListener.onGetRecentOrders(dealOrders);
                  ordersVisibility.set(View.VISIBLE);
                }
              }
            });
  }

  @Override public void destroy() {
    if (mSubscription != null && !mSubscription.isUnsubscribed()) {
      mSubscription.unsubscribe();
    }
  }
}

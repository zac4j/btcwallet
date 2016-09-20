package com.zac4j.zwallet.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.Toast;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.data.local.PreferencesHelper;
import com.zac4j.zwallet.data.local.dao.AccountDao;
import com.zac4j.zwallet.data.remote.WebService;
import com.zac4j.zwallet.di.ApplicationContext;
import com.zac4j.zwallet.di.PerConfig;
import com.zac4j.zwallet.model.response.AccountInfo;
import com.zac4j.zwallet.model.response.DealOrder;
import com.zac4j.zwallet.model.response.RealTimeEntity;
import com.zac4j.zwallet.util.Constants;
import com.zac4j.zwallet.util.RxUtils;
import com.zac4j.zwallet.util.Utils;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.zac4j.zwallet.util.Utils.ACCESS_KEY;

/**
 * Main page view model
 * Created by zac on 16-7-3.
 */

@PerConfig public class MainViewModel implements ViewModel {

  private static final String GET_ACCOUNT_INFO = "get_account_info";
  private static final String GET_RECENT_ORDERS = "get_new_deal_orders";

  public static final int DEFAULT_INTERVAL_PERIOD = 30;

  public ObservableInt progressVisibility;
  public ObservableInt ordersVisibility;
  public ObservableInt sortUpVisibility;
  public ObservableInt sortDownVisibility;
  public ObservableInt errorDisplayVisibility;
  public ObservableField<String> coinPrice;
  public ObservableField<String> priceVariation;
  public ObservableField<String> errorDisplay;

  private Context mContext;
  private Subscription mSubscription;
  private String mRealTime;

  private WebService mWebService;
  private AccountDao mAccountDao;
  private PreferencesHelper mPrefsHelper;

  public interface OnDataChangedListener {
    void onGetRecentOrders(List<DealOrder> dealOrderList);
  }

  private OnDataChangedListener mDataChangedListener;

  public void setOnDataChangedListener(OnDataChangedListener listener) {
    mDataChangedListener = listener;
  }

  @Inject MainViewModel(@ApplicationContext Context context, PreferencesHelper prefsHelper,
      WebService webService, AccountDao dao) {
    mContext = context;
    mWebService = webService;
    mAccountDao = dao;
    mPrefsHelper = prefsHelper;

    progressVisibility = new ObservableInt(View.INVISIBLE);
    ordersVisibility = new ObservableInt(View.INVISIBLE);
    sortUpVisibility = new ObservableInt(View.INVISIBLE);
    sortDownVisibility = new ObservableInt(View.INVISIBLE);
    errorDisplayVisibility = new ObservableInt(View.INVISIBLE);

    coinPrice = new ObservableField<>();
    priceVariation = new ObservableField<>();
    errorDisplay = new ObservableField<>();

    updateAccountInfo();
  }

  private void updateAccountInfo() {
    // Get data while showing
    int coinType =
        mPrefsHelper.getPrefs().getInt(Constants.CURRENT_SELECT_COIN, Constants.COIN_TYPE_LTC);
    String requestTime = String.valueOf(System.currentTimeMillis()).substring(0, 10);
    getAccountInfo(requestTime);
    getRecentOrders(requestTime, coinType);
    pollCoinPrice(coinType);
  }

  /**
   * 轮询Coin实时价格
   *
   * @param coinType 1.BitCoin 2.LiteCoin
   */
  private void pollCoinPrice(int coinType) {
    final String type = coinType == Constants.COIN_TYPE_BTC ? "btc" : "ltc";
    final NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.CHINA);

    final int intervalPeriod =
        mPrefsHelper.getPrefs().getInt(Constants.INTERVAL_PERIOD, DEFAULT_INTERVAL_PERIOD);

    Observable.interval(0, intervalPeriod, TimeUnit.SECONDS, Schedulers.io())
        .flatMap(new Func1<Long, Observable<RealTimeEntity>>() {
          @Override public Observable<RealTimeEntity> call(Long aLong) {
            return mWebService.getRealTimeData(type);
          }
        })
        .doOnError(new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            coinPrice.set(formatter.format(0.00d));
            priceVariation.set(formatter.format(0.00d));
          }
        })
        .retry()
        .distinct()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<RealTimeEntity>() {
          @Override public void call(RealTimeEntity realTimeEntity) {
            mRealTime = realTimeEntity.getTime();
            double lastPrice = realTimeEntity.getTicker().getLast();
            double openPrice = realTimeEntity.getTicker().getOpen();
            double variation = lastPrice - openPrice;
            if (variation > 0) {
              sortUpVisibility.set(View.VISIBLE);
              sortDownVisibility.set(View.INVISIBLE);
            } else if (variation == 0) {
              sortUpVisibility.set(View.INVISIBLE);
              sortDownVisibility.set(View.INVISIBLE);
            } else {
              sortDownVisibility.set(View.VISIBLE);
              sortUpVisibility.set(View.INVISIBLE);
            }
            coinPrice.set(formatter.format(lastPrice));
            priceVariation.set(formatter.format(Math.abs(variation)));
          }
        });
  }

  private void getAccountInfo(String time) {
    progressVisibility.set(View.VISIBLE);

    List<Pair<String, String>> parameterPairs = new ArrayList<>();
    Pair<String, String> createdTimePair = new Pair<>(Constants.CREATED_TIME, time);
    Pair<String, String> methodNamePair = new Pair<>(Constants.METHOD_NAME, GET_ACCOUNT_INFO);

    parameterPairs.add(createdTimePair);
    parameterPairs.add(methodNamePair);

    mSubscription = mWebService.getAccountInfo(GET_ACCOUNT_INFO, ACCESS_KEY, time,
        Utils.generateSign(parameterPairs))
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
              mAccountDao.clearAll();
              mAccountDao.setAccountInfo(accountInfo);
            }
          }
        });
  }

  private void getRecentOrders(String time, int coinType) {
    List<Pair<String, String>> parameterPairs = new ArrayList<>();
    Pair<String, String> methodNamePair = new Pair<>(Constants.METHOD_NAME, GET_RECENT_ORDERS);
    Pair<String, String> createdTimePair = new Pair<>(Constants.CREATED_TIME, time);
    Pair<String, String> coinTypePair = new Pair<>(Constants.COIN_TYPE, String.valueOf(coinType));

    parameterPairs.add(coinTypePair);
    parameterPairs.add(createdTimePair);
    parameterPairs.add(methodNamePair);

    mSubscription = mWebService.getOrders(GET_RECENT_ORDERS, ACCESS_KEY, coinType, time,
        Utils.generateSign(parameterPairs))
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
            } else {
              errorDisplay.set(mContext.getString(R.string.empty_data));
              errorDisplayVisibility.set(View.VISIBLE);
            }
          }
        });
  }

  @Override public void destroy() {
    RxUtils.unsubscribe(mSubscription);
  }
}

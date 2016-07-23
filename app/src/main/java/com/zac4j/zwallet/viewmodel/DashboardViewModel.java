package com.zac4j.zwallet.viewmodel;

import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.zac4j.zwallet.data.local.PreferencesHelper;
import com.zac4j.zwallet.data.remote.WebService;
import com.zac4j.zwallet.di.PerConfig;
import com.zac4j.zwallet.model.local.KLineEntity;
import com.zac4j.zwallet.util.Constants;
import com.zac4j.zwallet.util.RxUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Dashboard view model
 * Created by zac on 16-7-17.
 */

@PerConfig public class DashboardViewModel implements ViewModel {

  private static final String TAG = "DashboardViewModel";

  public interface OnDataChangedListener {
    void onDataChanged(List<KLineEntity> entityList);
  }

  public ObservableInt progressVisibility;

  private String[] intervalTimes = { "060", "100", "200", "300", "400" };

  private WebService mWebService;
  private Subscription mSubscription;

  private String mIntervalTime;
  private int mCoinType;

  private OnDataChangedListener mListener;

  public void setOnDataChangedListener(OnDataChangedListener listener) {
    mListener = listener;
  }

  @Inject DashboardViewModel(WebService webService, PreferencesHelper prefsHelper) {

    progressVisibility = new ObservableInt(View.GONE);

    mWebService = webService;

    mIntervalTime = intervalTimes[0]; // default interval time

    mCoinType =
        prefsHelper.getPrefs().getInt(Constants.CURRENT_SELECT_COIN, Constants.COIN_TYPE_LTC);
  }

  public AdapterView.OnItemSelectedListener getOnItemSelectedListener() {
    return new AdapterView.OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i < intervalTimes.length) {
          mIntervalTime = intervalTimes[i];
          getIntervalTimeData(mCoinType, mIntervalTime);
        }
      }

      @Override public void onNothingSelected(AdapterView<?> adapterView) {

      }
    };
  }

  /**
   * 获取分时交易数据
   *
   * @param coinType 币种 1.btc BitCoin 2.ltc LiteCoin
   * @param intervalTime 分时间隔时间
   */
  private void getIntervalTimeData(int coinType, String intervalTime) {
    progressVisibility.set(View.VISIBLE);
    String type = coinType == Constants.COIN_TYPE_BTC ? "btc" : "ltc";
    mSubscription = mWebService.getIntervalTimeData(type, intervalTime)
        .toObservable()
        .flatMapIterable(new Func1<List<List>, Iterable<List>>() {
          @Override public Iterable<List> call(List<List> lists) {
            return lists;
          }
        })
        .map(new Func1<List, KLineEntity>() {
          @Override public KLineEntity call(List list) {
            String time = list.get(0).toString();
            String openPrice = list.get(1).toString();
            String highestPrice = list.get(2).toString();
            String lowestPrice = list.get(3).toString();
            String closingPrice = list.get(4).toString();
            String tradingVolume = list.get(5).toString();

            String date = time.substring(0, 8);
            if (mIntervalTime.equals(intervalTimes[0])) {
              time = time.substring(8,10) + ":" + time.substring(10,12);
            } else {
              time = time.substring(4,6) + "-" + time.substring(6,8);
            }

            return new KLineEntity(date, time, openPrice, highestPrice, lowestPrice, closingPrice,
                tradingVolume);
          }
        })
        .toList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<List<KLineEntity>>() {
          @Override public void onCompleted() {
            progressVisibility.set(View.GONE);
          }

          @Override public void onError(Throwable e) {
            progressVisibility.set(View.GONE);
            Log.e(TAG, "onError: " + e.getMessage());
          }

          @Override public void onNext(List<KLineEntity> entityList) {
            if (entityList != null && !entityList.isEmpty()) {
              mListener.onDataChanged(entityList);
            }
          }
        });
  }

  @Override public void destroy() {
    RxUtils.unsubscribe(mSubscription);
  }
}

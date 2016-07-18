package com.zac4j.zwallet.viewmodel;

import android.view.View;
import android.widget.AdapterView;
import com.zac4j.zwallet.data.local.PreferencesHelper;
import com.zac4j.zwallet.data.remote.WebService;
import com.zac4j.zwallet.di.PerConfig;
import com.zac4j.zwallet.util.Constants;
import com.zac4j.zwallet.util.RxUtils;
import java.util.List;
import javax.inject.Inject;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Dashboard view model
 * Created by zac on 16-7-17.
 */

@PerConfig public class DashboardViewModel implements ViewModel {
  private String[] intervalTimes = { "060", "100", "200", "300", "400" };

  private WebService mWebService;
  private PreferencesHelper mPrefsHelper;

  private Subscription mSubscription;

  @Inject DashboardViewModel(WebService webService, PreferencesHelper prefsHelper) {
    mWebService = webService;
    mPrefsHelper = prefsHelper;

    getIntervalTimeData(Constants.COIN_TYPE_BTC, "400");
  }

  public AdapterView.OnItemSelectedListener getOnItemSelectedListener() {
    return new AdapterView.OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
          case 0:
            break;
          case 1:
            break;
          case 2:
            break;
          case 3:
            break;
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
    String type = coinType == Constants.COIN_TYPE_BTC ? "btc" : "ltc";
    mSubscription = mWebService.getIntervalTimeData(type, intervalTime)
        .compose(RxUtils.<List<List>>applySchedulers())
        .subscribe(new Action1<List<List>>() {
          @Override public void call(List<List> s) {

            System.out.println("fuck you!" + s.get(0).get(0).toString());
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            System.out.println("fuck you!" + throwable.getMessage());
          }
        });
  }

  /**
   * 获取实时交易数据
   *
   * @param coinType 币种 1.btc BitCoin 2.ltc LiteCoin
   */
  private void getRealTimeData(int coinType) {
    String type = coinType == Constants.COIN_TYPE_BTC ? "btc" : "ltc";

  }

  @Override public void destroy() {
    RxUtils.unsubscribe(mSubscription);
  }
}

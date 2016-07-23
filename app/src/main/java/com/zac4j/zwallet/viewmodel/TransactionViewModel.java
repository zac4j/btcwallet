package com.zac4j.zwallet.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.util.Pair;
import android.view.View;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.data.local.PreferencesHelper;
import com.zac4j.zwallet.data.remote.WebService;
import com.zac4j.zwallet.di.ApplicationContext;
import com.zac4j.zwallet.di.PerConfig;
import com.zac4j.zwallet.model.local.Transaction;
import com.zac4j.zwallet.model.response.DealOrder;
import com.zac4j.zwallet.util.Constants;
import com.zac4j.zwallet.util.RxUtils;
import com.zac4j.zwallet.util.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;

/**
 * Transaction (pending/processed) View Model
 * Created by zac on 16-7-15.
 */
@PerConfig public class TransactionViewModel implements ViewModel {

  private static final String GET_RECENT_ORDERS = "get_new_deal_orders";
  private static final String GET_PENDING_ORDERS = "get_orders";

  public interface OnDataChangedListener {
    void onDataChanged(List<DealOrder> orderList);
  }

  public ObservableInt progressVisibility;
  public ObservableInt ordersVisibility;
  public ObservableInt errorDisplayVisibility;
  public ObservableField<String> errorDisplay;

  private OnDataChangedListener mListener;
  private Subscription mSubscription;

  private Context mContext;
  private WebService mWebService;
  private PreferencesHelper mPrefsHelper;

  @Inject TransactionViewModel(@ApplicationContext Context context, WebService webService,
      PreferencesHelper prefsHelper) {
    mContext = context;
    mWebService = webService;
    mPrefsHelper = prefsHelper;

    progressVisibility = new ObservableInt(View.VISIBLE);
    ordersVisibility = new ObservableInt(View.GONE);
    errorDisplayVisibility = new ObservableInt(View.GONE);
    errorDisplay = new ObservableField<>();
  }

  public void setOnDataChangedListener(OnDataChangedListener listener) {
    mListener = listener;
  }

  public void getOrders(int transactionType) {
    String methodName =
        transactionType == Transaction.PENDING ? GET_PENDING_ORDERS : GET_RECENT_ORDERS;
    String time = String.valueOf(System.currentTimeMillis()).substring(0, 10);
    int coinType =
        mPrefsHelper.getPrefs().getInt(Constants.CURRENT_SELECT_COIN, Constants.COIN_TYPE_LTC);

    List<Pair<String, String>> parameterPairs = new ArrayList<>();
    Pair<String, String> methodNamePair = new Pair<>(Constants.METHOD_NAME, methodName);
    Pair<String, String> createdTimePair = new Pair<>(Constants.CREATED_TIME, time);
    Pair<String, String> coinTypePair = new Pair<>(Constants.COIN_TYPE, String.valueOf(coinType));

    parameterPairs.add(coinTypePair);
    parameterPairs.add(createdTimePair);
    parameterPairs.add(methodNamePair);

    mSubscription = mWebService.getOrders(methodName, Utils.ACCESS_KEY, coinType, time,
        Utils.generateSign(parameterPairs))
        .compose(RxUtils.<List<DealOrder>>applySchedulers())
        .subscribe(new Subscriber<List<DealOrder>>() {
          @Override public void onCompleted() {
            progressVisibility.set(View.GONE);
          }

          @Override public void onError(Throwable e) {
            progressVisibility.set(View.GONE);
            errorDisplay.set(mContext.getString(R.string.network_error));
            errorDisplayVisibility.set(View.VISIBLE);
          }

          @Override public void onNext(List<DealOrder> orderList) {
            if (orderList != null && !orderList.isEmpty()) {
              ordersVisibility.set(View.VISIBLE);
              mListener.onDataChanged(orderList);
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

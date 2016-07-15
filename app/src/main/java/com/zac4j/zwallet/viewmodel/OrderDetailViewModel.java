package com.zac4j.zwallet.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.util.Pair;
import android.view.View;
import com.zac4j.zwallet.App;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.data.local.PreferencesHelper;
import com.zac4j.zwallet.data.remote.WebService;
import com.zac4j.zwallet.model.response.OrderInfo;
import com.zac4j.zwallet.util.Constants;
import com.zac4j.zwallet.util.RxUtils;
import com.zac4j.zwallet.util.Utils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;

/**
 * DealOrder Detail View Model
 * Created by zac on 16-7-14.
 */

public class OrderDetailViewModel implements ViewModel {

  private static final String METHOD_GET_ORDER_INFO = "order_info";
  private Context mContext;

  public ObservableInt progressVisibility;
  public ObservableField<String> mOrderStatus;
  public ObservableField<String> mProcessTime;
  public ObservableField<String> mTotalPayment;
  public ObservableField<String> mPriceAmount;
  public ObservableField<String> mSubtotal;
  public ObservableField<String> mFee;
  public ObservableField<String> mTotal;

  private Subscription mSubscription;
  private String[] orderStatus;

  @Inject WebService mWebService;
  @Inject PreferencesHelper mPrefsHelper;

  public OrderDetailViewModel(Context context, String orderId) {

    mContext = context;

    progressVisibility = new ObservableInt(View.VISIBLE);
    mOrderStatus = new ObservableField<>();
    mProcessTime = new ObservableField<>();
    mTotalPayment = new ObservableField<>();
    mPriceAmount = new ObservableField<>();
    mSubtotal = new ObservableField<>();
    mFee = new ObservableField<>();
    mTotal = new ObservableField<>();

    App.get(mContext).getApplicationComponent().inject(this);

    orderStatus = mContext.getResources().getStringArray(R.array.order_status);
    getOrderDetail(orderId);
  }

  public void setProcessTime(String time) {
    mProcessTime.set(time);
  }

  private void getOrderDetail(final String orderId) {

    String methodName = METHOD_GET_ORDER_INFO;
    String time = String.valueOf(System.currentTimeMillis()).substring(0, 10);
    final int coinType =
        mPrefsHelper.getPrefs().getInt(Constants.COIN_TYPE, Constants.COIN_TYPE_LTC);

    List<Pair<String, String>> parameterPairs = new ArrayList<>();
    Pair<String, String> methodNamePair = new Pair<>(Constants.METHOD_NAME, methodName);
    Pair<String, String> createdTimePair = new Pair<>(Constants.CREATED_TIME, time);
    Pair<String, String> coinTypePair = new Pair<>(Constants.COIN_TYPE, String.valueOf(coinType));
    Pair<String, String> orderIdPair = new Pair<>(Constants.ID, orderId);

    parameterPairs.add(coinTypePair);
    parameterPairs.add(createdTimePair);
    parameterPairs.add(orderIdPair);
    parameterPairs.add(methodNamePair);

    mSubscription = mWebService.getOrderInfo(methodName, Utils.ACCESS_KEY, coinType, orderId, time,
        Utils.generateSign(parameterPairs))
        .compose(RxUtils.<OrderInfo>applySchedulers())
        .subscribe(new Subscriber<OrderInfo>() {
          @Override public void onCompleted() {
            progressVisibility.set(View.GONE);
          }

          @Override public void onError(Throwable e) {
            progressVisibility.set(View.GONE);
          }

          @Override public void onNext(OrderInfo orderInfo) {
            mOrderStatus.set(orderStatus[orderInfo.getStatus()]);
            String totalPayment = mContext.getString(R.string.unit_price, orderInfo.getTotal());
            mTotalPayment.set(totalPayment);

            String amount = mContext.getString(
                coinType == Constants.COIN_TYPE_LTC ? R.string.unit_ltc : R.string.unit_btc,
                orderInfo.getProcessedAmount());
            String price = mContext.getString(R.string.unit_price, orderInfo.getProcessedPrice());
            String priceAmount =
                mContext.getString(R.string.order_detail_price_amount, amount, price);
            mPriceAmount.set(priceAmount);

            mSubtotal.set(mContext.getString(R.string.unit_price, orderInfo.getVot()));
            mFee.set(mContext.getString(R.string.unit_price, orderInfo.getFee()));
            mTotal.set(mContext.getString(R.string.unit_price, orderInfo.getTotal()));
          }
        });
  }

  @Override public void destroy() {
    if (mSubscription != null && !mSubscription.isUnsubscribed()) {
      mSubscription.unsubscribe();
    }
  }
}

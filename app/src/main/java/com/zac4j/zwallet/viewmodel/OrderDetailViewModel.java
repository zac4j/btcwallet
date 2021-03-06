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
import com.zac4j.zwallet.model.response.OrderInfo;
import com.zac4j.zwallet.util.Constants;
import com.zac4j.zwallet.util.RxUtils;
import com.zac4j.zwallet.util.Utils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.Subscription;

/**
 * DealOrder Detail View Model
 * Created by zac on 16-7-14.
 */
@PerConfig public class OrderDetailViewModel implements ViewModel {

  private static final String METHOD_GET_ORDER_INFO = "order_info";

  public ObservableInt progressVisibility;
  public ObservableField<String> orderStatus;
  public ObservableField<String> processTime;
  public ObservableField<String> totalPayment;
  public ObservableField<String> priceAmount;
  public ObservableField<String> subtotal;
  public ObservableField<String> fee;
  public ObservableField<String> total;

  private Subscription mSubscription;
  private String[] mOrderStatus;

  private Context mContext;
  private WebService mWebService;
  private PreferencesHelper mPrefsHelper;

  @Inject OrderDetailViewModel(@ApplicationContext Context context, WebService webService,
      PreferencesHelper prefsHelper) {
    mContext = context;
    mWebService = webService;
    mPrefsHelper = prefsHelper;

    progressVisibility = new ObservableInt(View.VISIBLE);
    orderStatus = new ObservableField<>();
    processTime = new ObservableField<>();
    totalPayment = new ObservableField<>();
    priceAmount = new ObservableField<>();
    subtotal = new ObservableField<>();
    fee = new ObservableField<>();
    total = new ObservableField<>();

    mOrderStatus = mContext.getResources().getStringArray(R.array.order_status);
  }

  public void setProcessTime(String time) {
    processTime.set(time);
  }

  public void getOrderDetail(final String orderId) {

    String methodName = METHOD_GET_ORDER_INFO;
    String time = String.valueOf(System.currentTimeMillis()).substring(0, 10);
    final int coinType =
        mPrefsHelper.getPrefs().getInt(Constants.CURRENT_SELECT_COIN, Constants.COIN_TYPE_LTC);

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
            orderStatus.set(mOrderStatus[orderInfo.getStatus()]);
            String payment;
            int orderStatus = orderInfo.getStatus();
            if (orderStatus == 2) {
              payment = mContext.getString(R.string.unit_price, orderInfo.getTotal());
            } else {
              payment = new BigDecimal(orderInfo.getOrderPrice()).multiply(
                  new BigDecimal(orderInfo.getOrderAmount()))
                  .setScale(2, BigDecimal.ROUND_UP)
                  .toString();
            }
            totalPayment.set(payment);

            String amount = mContext.getString(
                coinType == Constants.COIN_TYPE_LTC ? R.string.unit_ltc : R.string.unit_btc,
                orderStatus == 2 ? orderInfo.getProcessedAmount() : orderInfo.getOrderAmount());
            String price = mContext.getString(R.string.unit_price,
                orderStatus == 2 ? orderInfo.getProcessedPrice() : orderInfo.getOrderPrice());
            String pm = mContext.getString(R.string.order_detail_price_amount, amount, price);
            priceAmount.set(pm);

            subtotal.set(mContext.getString(R.string.unit_price, orderInfo.getVot()));
            fee.set(mContext.getString(R.string.unit_price, orderInfo.getFee()));
            total.set(mContext.getString(R.string.unit_price, orderInfo.getTotal()));
          }
        });
  }

  @Override public void destroy() {
    RxUtils.unsubscribe(mSubscription);
  }
}

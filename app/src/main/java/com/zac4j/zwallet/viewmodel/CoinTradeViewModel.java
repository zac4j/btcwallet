package com.zac4j.zwallet.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.Toast;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.data.local.PreferencesHelper;
import com.zac4j.zwallet.data.remote.WebService;
import com.zac4j.zwallet.model.response.TradeResponse;
import com.zac4j.zwallet.util.Constants;
import com.zac4j.zwallet.util.RxUtils;
import com.zac4j.zwallet.util.Utils;
import com.zac4j.zwallet.view.CoinTradeActivity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.eclipse.jdt.internal.codeassist.complete.CompletionOnMessageSend;
import rx.Subscriber;
import rx.Subscription;

/**
 * Coin Trade view model
 * Created by zac on 16-7-9.
 */

public class CoinTradeViewModel implements ViewModel {
  private static final String METHOD_NAME_BUY = "buy";
  private static final String METHOD_NAME_SELL = "sell";

  private Context mContext;
  private Subscription mSubscription;
  private int mTradeType;

  public ObservableInt progressVisibility;
  public ObservableField<String> mCoinTradeLabel;
  public ObservableField<String> mTargetPrice;
  public ObservableField<String> mTargetPriceError;
  public ObservableField<String> mAssetLabel;
  public ObservableField<String> mCoinAmount;
  public ObservableField<String> mCoinAmountError;
  public ObservableField<String> mTradeFee;
  public ObservableField<String> mTotalPayment;
  public ObservableField<String> mCoinTradeBtnLabel;

  @Inject WebService mWebService;
  @Inject PreferencesHelper mPrefsHelper;

  public CoinTradeViewModel(Context context) {
    this.mContext = context;

    progressVisibility = new ObservableInt(View.GONE);
    String defaultCoinTrade = mContext.getString(R.string.coin_trade_buy_label);
    mCoinTradeLabel = new ObservableField<>(defaultCoinTrade);
    mTargetPrice = new ObservableField<>();
    mTargetPriceError = new ObservableField<>();
    String defaultAssetLabel = mContext.getString(R.string.coin_trade_money_cny_label);
    mAssetLabel = new ObservableField<>(defaultAssetLabel);
    mCoinAmount = new ObservableField<>();
    mCoinAmountError = new ObservableField<>();
    mTradeFee = new ObservableField<>();
    mTotalPayment = new ObservableField<>();
    mCoinTradeBtnLabel = new ObservableField<>();
  }

  public void setTradeType(int tradeType) {
    mTradeType = tradeType;
  }

  /**
   * Set coin trade label
   */
  public void setCoinTradeLabel(String label) {
    mCoinTradeLabel.set(label);
  }

  /**
   * Set coin button action label
   */
  public void setCoinTradeBtnLabel(String label) {
    mCoinTradeBtnLabel.set(label);
  }

  /**
   * 选择最多coin
   */
  public void onMaxAmountClick(View view) {

  }

  /**
   * 执行交易(sell/buy)
   */
  public void onTradeClick(View view) {
    boolean isValid = checkInputsValid();
  }

  private boolean checkInputsValid() {

    return false;
  }

  @Override public void destroy() {
    RxUtils.unsubscribe(mSubscription);
  }

  private void trade(double price, int amount, int tradeType) {
    progressVisibility.set(View.VISIBLE);
    String methodName =
        tradeType == CoinTradeActivity.TRADE_TYPE_BUY ? METHOD_NAME_BUY : METHOD_NAME_SELL;
    String time = String.valueOf(System.currentTimeMillis()).substring(0, 10);
    int coinType = mPrefsHelper.getPrefs().getInt(Constants.COIN_TYPE, Constants.COIN_TYPE_LTC);

    List<Pair<String, String>> parameterPairs = new ArrayList<>();
    Pair<String, String> methodNamePair = new Pair<>(Constants.METHOD_NAME, methodName);
    Pair<String, String> createdTimePair = new Pair<>(Constants.CREATED_TIME, time);
    Pair<String, String> coinTypePair = new Pair<>(Constants.COIN_TYPE, String.valueOf(coinType));
    Pair<String, String> pricePair = new Pair<>(Constants.COIN_AMOUNT, String.valueOf(price));
    Pair<String, String> amountPair = new Pair<>(Constants.COIN_AMOUNT, String.valueOf(amount));

    parameterPairs.add(amountPair);
    parameterPairs.add(coinTypePair);
    parameterPairs.add(createdTimePair);
    parameterPairs.add(methodNamePair);
    parameterPairs.add(pricePair);

    mSubscription = mWebService.trade(methodName, Utils.ACCESS_KEY, coinType, price, amount, time,
        Utils.generateSign(parameterPairs))
        .compose(RxUtils.<TradeResponse>applySchedulers())
        .subscribe(new Subscriber<TradeResponse>() {
          @Override public void onCompleted() {
            progressVisibility.set(View.INVISIBLE);
          }

          @Override public void onError(Throwable e) {
            progressVisibility.set(View.INVISIBLE);
            Toast.makeText(mContext, "交易失败，请检查网络连接!", Toast.LENGTH_SHORT).show();
          }

          @Override public void onNext(TradeResponse tradeResponse) {
            if ("success".equals(tradeResponse.getResult())) {
              Toast.makeText(mContext, "交易成功!", Toast.LENGTH_SHORT).show();
            } else {
              Toast.makeText(mContext, "交易失败!", Toast.LENGTH_SHORT).show();
            }
          }
        });
  }
}

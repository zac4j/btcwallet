package com.zac4j.zwallet.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import com.zac4j.zwallet.App;
import com.zac4j.zwallet.data.remote.WebService;
import javax.inject.Inject;
import rx.Subscription;

/**
 * Send Funds(Coin/CNY etc.)
 * Created by Zac on 2016/7/5.
 */
public class SendFundsViewModel implements ViewModel {

  public ObservableInt progressVisibility;
  public ObservableField<String> recipientAddress;
  public ObservableField<String> recipientAddressError;
  public ObservableField<String> sendAmount;
  public ObservableField<String> sendAmountError;
  public ObservableField<String> noteMessage;

  private Context mContext;
  private Subscription mSubscription;

  @Inject WebService mWebService;

  public SendFundsViewModel(Context context) {
    mContext = context;

    progressVisibility = new ObservableInt(View.GONE);
    recipientAddress = new ObservableField<>();
    recipientAddressError = new ObservableField<>();
    sendAmount = new ObservableField<>();
    sendAmountError = new ObservableField<>();
    noteMessage = new ObservableField<>();

    App.get(mContext).getApplicationComponent().inject(this);
  }

  public TextWatcher getRecipientWatcher() {
    return new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        clearError();
      }

      @Override public void afterTextChanged(Editable editable) {

      }
    };
  }

  public TextWatcher getAmountWatcher() {
    return new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        clearError();
      }

      @Override public void afterTextChanged(Editable editable) {

      }
    };
  }

  public void onSendClick(View view) {
    boolean isValid = checkInputsValid();
    if (isValid) { // TODO send money

    }
  }

  private void clearError() {
    recipientAddressError.set(null);
    sendAmountError.set(null);
  }

  private void setError(ObservableField<String> observableField, String errorMsg) {
    observableField.set(errorMsg);
  }

  private boolean checkInputsValid() {
    boolean result = true;
    if (TextUtils.isEmpty(recipientAddress.get())) {
      setError(recipientAddressError, "收款账户不允许为空!");
      result = false;
    }
    if (TextUtils.isEmpty(sendAmountError.get())) {
      setError(sendAmountError, "金额不允许为空!");
      result = false;
    }
    return result;
  }

  @Override public void destroy() {

  }
}

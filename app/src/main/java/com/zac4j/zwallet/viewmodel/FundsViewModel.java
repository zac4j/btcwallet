package com.zac4j.zwallet.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
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
public class FundsViewModel implements ViewModel {

  public ObservableBoolean isSendFunds;
  public ObservableInt progressVisibility;
  public ObservableField<String> address;
  public ObservableField<String> addressError;
  public ObservableField<String> amount;
  public ObservableField<String> amountError;
  public ObservableField<String> noteMessage;
  public ObservableField<String> fundsLabel;
  public ObservableField<String> fundsBtnLabel;

  private Context mContext;
  private Subscription mSubscription;

  @Inject WebService mWebService;

  public FundsViewModel(Context context) {
    mContext = context;

    isSendFunds = new ObservableBoolean(true);
    progressVisibility = new ObservableInt(View.GONE);
    address = new ObservableField<>();
    addressError = new ObservableField<>();
    amount = new ObservableField<>();
    amountError = new ObservableField<>();
    noteMessage = new ObservableField<>();
    fundsLabel = new ObservableField<>();
    fundsBtnLabel = new ObservableField<>();

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

  public void onClick(View view) {
    boolean isValid = checkInputsValid();
    if (isValid) {
      if (isSendFunds.get()) { // TODO send funds action

      } else { // TODO send funds action

      }
    }
  }

  private void clearError() {
    addressError.set(null);
    amountError.set(null);
  }

  private void setError(ObservableField<String> observableField, String errorMsg) {
    observableField.set(errorMsg);
  }

  private boolean checkInputsValid() {
    boolean result = true;
    if (TextUtils.isEmpty(address.get())) {
      setError(addressError, "收款账户不允许为空!");
      result = false;
    }
    if (TextUtils.isEmpty(amountError.get())) {
      setError(amountError, "金额不允许为空!");
      result = false;
    }
    return result;
  }

  @Override public void destroy() {

  }
}

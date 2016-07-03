package com.zac4j.zwallet.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

/**
 * Main page view model
 * Created by zac on 16-7-3.
 */

public class MainViewModel implements ViewModel{

  public ObservableInt progressVisibility;
  public ObservableInt transactionsVisibility;
  public ObservableField<String> coinQuantity;
  public ObservableField<String> moneyAmount;

  private Context mContext;
  private DataChangedListener mDataChangedListener;

  public void setDataChangedListener(DataChangedListener listener) {
    mDataChangedListener = listener;
  }

  public MainViewModel(Context context) {
    mContext = context;
    progressVisibility = new ObservableInt(View.INVISIBLE);
    transactionsVisibility = new ObservableInt(View.INVISIBLE);
  }

  @Override public void destroy() {

  }

  public interface DataChangedListener{

  }
}

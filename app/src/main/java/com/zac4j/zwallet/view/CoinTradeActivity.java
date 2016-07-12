package com.zac4j.zwallet.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.databinding.ActivityCoinTradeBinding;
import com.zac4j.zwallet.viewmodel.CoinTradeViewModel;

/**
 * Coin Sell/Buy UI
 * Created by zac on 16-7-11.
 */

public class CoinTradeActivity extends AppCompatActivity {

  public static final String EXTRA_TRADE = "extra_trade";

  public static final int TRADE_TYPE_BUY = 0xa1;
  public static final int TRADE_TYPE_SELL = 0xa2;

  private int tradeType;
  private ActivityCoinTradeBinding mBinding;
  private CoinTradeViewModel mViewModel;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_coin_trade);
    mViewModel = new CoinTradeViewModel(this);
    mBinding.setViewModel(mViewModel);

    tradeType = getIntent().getIntExtra(EXTRA_TRADE, 0);
    mViewModel.setCoinTradeLabel(getTradeLabel(tradeType));
    mViewModel.setCoinTradeBtnLabel(getTradeTitle(tradeType));

    setupActionBar(mBinding.actionbar.toolbar);
  }

  private void setupActionBar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      if (tradeType != 0) {
        actionBar.setTitle(getTradeTitle(tradeType));
      }
    }
  }

  private String getTradeTitle(int tradeType) {
    return tradeType == TRADE_TYPE_BUY ? getString(R.string.menu_trade_buy)
        : getString(R.string.menu_trade_sell);
  }

  private String getTradeLabel(int tradeType) {
    return tradeType == TRADE_TYPE_BUY ? getString(R.string.coin_trade_buy_label)
        : getString(R.string.coin_trade_sell_label);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        break;
    }

    return super.onOptionsItemSelected(item);
  }
}

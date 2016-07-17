package com.zac4j.zwallet.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.adapter.OrderAdapter;
import com.zac4j.zwallet.data.local.PreferencesHelper;
import com.zac4j.zwallet.databinding.ActivityMainBinding;
import com.zac4j.zwallet.model.local.Trade;
import com.zac4j.zwallet.model.local.Transaction;
import com.zac4j.zwallet.model.response.DealOrder;
import com.zac4j.zwallet.util.Constants;
import com.zac4j.zwallet.view.widget.DividerItemDecoration;
import com.zac4j.zwallet.viewmodel.MainViewModel;
import java.util.List;
import javax.inject.Inject;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener,
    MainViewModel.OnDataChangedListener {

  private DrawerLayout mDrawerLayout;
  private NavigationView mNavigationView;
  private ActivityMainBinding mBinding;
  private MenuItem mCoinSwitchItem;

  @Inject MainViewModel mViewModel;
  @Inject PreferencesHelper mPrefsHelper;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    mBinding.setViewModel(mViewModel);
    setupDrawer(mBinding);
    setupActionBar(mBinding.toolbar);
    setupRecyclerView(mBinding.mainRvOrderList);
    mViewModel.setOnDataChangedListener(this);
  }

  private void setupRecyclerView(RecyclerView recyclerView) {
    OrderAdapter adapter = new OrderAdapter();
    recyclerView.setAdapter(adapter);
    recyclerView.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mViewModel.destroy();
  }

  private void setupActionBar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle("");
    }
  }

  private void setupDrawer(ActivityMainBinding binding) {
    mDrawerLayout = binding.drawerMainLayout;
    mNavigationView = binding.drawerMainNavView;
    mNavigationView.setNavigationItemSelectedListener(this);
    mCoinSwitchItem = mNavigationView.getMenu().findItem(R.id.action_coin_switch);
    Switch coinSwitch = (Switch) MenuItemCompat.getActionView(mCoinSwitchItem)
        .findViewById(R.id.drawer_coin_switch);

    coinSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int titleRes;
        int coinType;
        if (b) {
          titleRes = R.string.menu_coin_btc;
          coinType = Constants.COIN_TYPE_BTC;
        } else {
          titleRes = R.string.menu_coin_ltc;
          coinType = Constants.COIN_TYPE_LTC;
        }

        mCoinSwitchItem.setTitle(titleRes);
        mPrefsHelper.getPrefs().edit().putInt(Constants.CURRENT_SELECT_COIN, coinType).apply();
      }
    });
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        mDrawerLayout.openDrawer(GravityCompat.START);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onNavigationItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case R.id.action_trade_buy:
        startActivity(new Intent(MainActivity.this, CoinTradeActivity.class).putExtra(
            CoinTradeActivity.EXTRA_TRADE, Trade.BUY));
        break;
      case R.id.action_trade_sell:
        startActivity(new Intent(MainActivity.this, CoinTradeActivity.class).putExtra(
            CoinTradeActivity.EXTRA_TRADE, Trade.SELL));
        break;
      case R.id.action_account_wallet:
        startActivity(new Intent(MainActivity.this, MyWalletActivity.class));
        break;
      case R.id.action_transaction_pend:
        startActivity(new Intent(MainActivity.this, TransactionActivity.class).putExtra(
            TransactionActivity.EXTRA_TRANS_TYPE, Transaction.PENDING));
        break;
      case R.id.action_transaction_process:
        startActivity(new Intent(MainActivity.this, TransactionActivity.class).putExtra(
            TransactionActivity.EXTRA_TRANS_TYPE, Transaction.PROCESSED));
        break;
    }

    mNavigationView.setCheckedItem(item.getItemId());
    mDrawerLayout.closeDrawers();
    return true;
  }

  @Override public void onGetRecentOrders(List<DealOrder> dealOrderList) {
    OrderAdapter adapter = (OrderAdapter) mBinding.mainRvOrderList.getAdapter();
    adapter.addAll(dealOrderList);
    adapter.notifyDataSetChanged();
  }
}

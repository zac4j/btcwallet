package com.zac4j.zwallet.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.databinding.ActivityMyWalletBinding;
import com.zac4j.zwallet.viewmodel.MyWalletViewModel;

/**
 * Send Funds Activity
 * Created by Zac on 2016/7/5.
 */
public class MyWalletActivity extends AppCompatActivity {

  private ActivityMyWalletBinding mBinding;
  private MyWalletViewModel mViewModel;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_wallet);
    mViewModel = new MyWalletViewModel(this);
    mBinding.setViewModel(mViewModel);
    setupActionBar(mBinding.actionbar.toolbar);
    setupTabLayout(mBinding.tabLayout);
  }

  private void setupTabLayout(TabLayout tabLayout) {
    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override public void onTabSelected(TabLayout.Tab tab) {
        int selectIndex = tab.getPosition();
        if (selectIndex == 0) {
          mViewModel.isSendFunds.set(true);
          mViewModel.fundsLabel.set(getString(R.string.wallet_label_send));
          mViewModel.fundsBtnLabel.set(getString(R.string.wallet_btn_label_send));
        } else {
          mViewModel.isSendFunds.set(false);
          mViewModel.fundsLabel.set(getString(R.string.wallet_label_request));
          mViewModel.fundsBtnLabel.set(getString(R.string.wallet_btn_label_request));
        }
      }

      @Override public void onTabUnselected(TabLayout.Tab tab) {
      }

      @Override public void onTabReselected(TabLayout.Tab tab) {
      }
    });
  }

  private void setupActionBar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle(R.string.menu_accounts_wallet);
    }
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

package com.zac4j.zwallet.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.databinding.ActivitySendFundsBinding;
import com.zac4j.zwallet.viewmodel.SendFundsViewModel;

/**
 * Send Funds Activity
 * Created by Zac on 2016/7/5.
 */
public class SendFundsActivity extends AppCompatActivity {

  private ActivitySendFundsBinding mBinding;
  private SendFundsViewModel mViewModel;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_send_funds);
    mBinding.setViewModel(new SendFundsViewModel(this));
    setupActionBar(mBinding.actionbar.toolbar);
  }

  private void setupActionBar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle("Recipient");
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

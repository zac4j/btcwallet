package com.zac4j.zwallet.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.databinding.ActivityDashboardBinding;
import com.zac4j.zwallet.viewmodel.DashboardViewModel;
import javax.inject.Inject;

/**
 * Dashboard ui
 * Created by zac on 16-7-17.
 */

public class DashboardActivity extends BaseActivity {

  @Inject DashboardViewModel mViewModel;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);
    ActivityDashboardBinding binding =
        DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
    setupActionBar(binding);
    binding.setViewModel(mViewModel);
  }

  private void setupActionBar(ActivityDashboardBinding binding) {
    setSupportActionBar(binding.toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle(R.string.menu_dashboard);
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

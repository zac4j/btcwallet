package com.zac4j.zwallet.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.data.local.PreferencesHelper;
import com.zac4j.zwallet.databinding.ActivitySettingsBinding;
import com.zac4j.zwallet.util.Constants;
import javax.inject.Inject;

/**
 * Settings UI
 * Created by zac on 16-9-20.
 */
public class SettingsActivity extends BaseActivity {

  private ActivitySettingsBinding mBinding;

  @Inject PreferencesHelper mPrefsHelper;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

    setupActionBar(mBinding.actionbar.toolbar);

    mBinding.frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String[] frequencies = getResources().getStringArray(R.array.refresh_frequency);
        String intervalStr = frequencies[i].substring(0, 2);
        mPrefsHelper.getEditor()
            .putInt(Constants.INTERVAL_PERIOD, Integer.parseInt(intervalStr))
            .apply();
      }

      @Override public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });
  }

  private void setupActionBar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle(R.string.settings);
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

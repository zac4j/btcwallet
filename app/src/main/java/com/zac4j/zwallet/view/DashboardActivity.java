package com.zac4j.zwallet.view;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.databinding.ActivityDashboardBinding;
import com.zac4j.zwallet.model.local.KLineEntity;
import com.zac4j.zwallet.viewmodel.DashboardViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Dashboard ui
 * Created by zac on 16-7-17.
 */

public class DashboardActivity extends BaseActivity
    implements DashboardViewModel.OnDataChangedListener {

  @Inject DashboardViewModel mViewModel;
  private CandleStickChart mChart;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);
    ActivityDashboardBinding binding =
        DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
    setupActionBar(binding);
    setupCandleChart(binding);
    mViewModel.setOnDataChangedListener(this);
    binding.setViewModel(mViewModel);
  }

  private void setupCandleChart(ActivityDashboardBinding binding) {
    mChart = binding.dashboardKlineChart;

    mChart.setDescription("");

    // if more than 60 entries are displayed in the chart, no values will be drawn
    mChart.setMaxVisibleValueCount(60);

    // scaling can now only be done on x- and y-axis separately
    mChart.setPinchZoom(false);

    mChart.setDrawGridBackground(false);
    XAxis xAxis = mChart.getXAxis();
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    xAxis.setDrawGridLines(false);

    YAxis leftAxis = mChart.getAxisLeft();
    leftAxis.setLabelCount(7, false);
    leftAxis.setDrawGridLines(false);
    leftAxis.setDrawAxisLine(false);

    YAxis rightAxis = mChart.getAxisRight();
    rightAxis.setEnabled(false);

    mChart.getLegend().setEnabled(false);
  }

  private void setupActionBar(ActivityDashboardBinding binding) {
    setSupportActionBar(binding.toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle(R.string.menu_dashboard);
    }
  }

  private void drawKLine(List<KLineEntity> entityList) {
    mChart.resetTracking();
    List<CandleEntry> yVals = new ArrayList<>();
    for (int i = 0; i < entityList.size(); i++) {
      String time = entityList.get(i).getTime();
      String highestPrice = entityList.get(i).getHighestPrice();
      String lowestPrice = entityList.get(i).getLowestPrice();
      String openingPrice = entityList.get(i).getOpeningPrice();
      String closingPrice = entityList.get(i).getClosingPrice();
      yVals.add(new CandleEntry(i, Float.parseFloat(highestPrice),
          Float.parseFloat(lowestPrice), Float.parseFloat(openingPrice),
          Float.parseFloat(closingPrice)));
    }

    CandleDataSet dataSet = new CandleDataSet(yVals, "Data Set");
    dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
    dataSet.setShadowColor(Color.DKGRAY);
    dataSet.setShadowWidth(0.7f);
    dataSet.setDecreasingColor(Color.RED);
    dataSet.setDecreasingPaintStyle(Paint.Style.FILL);
    dataSet.setIncreasingColor(Color.GREEN);
    dataSet.setIncreasingPaintStyle(Paint.Style.FILL);
    dataSet.setNeutralColor(Color.BLUE);

    CandleData data = new CandleData(dataSet);
    mChart.setData(data);
    mChart.invalidate();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onDataChanged(List<KLineEntity> entityList) {
    drawKLine(entityList);
  }
}

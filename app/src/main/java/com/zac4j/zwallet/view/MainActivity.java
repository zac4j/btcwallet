package com.zac4j.zwallet.view;

import android.databinding.DataBindingUtil;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.adapter.DealOrderAdapter;
import com.zac4j.zwallet.databinding.ActivityMainBinding;
import com.zac4j.zwallet.model.response.DealOrder;
import com.zac4j.zwallet.view.widget.DividerItemDecoration;
import com.zac4j.zwallet.viewmodel.MainViewModel;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,
    MainViewModel.OnDataChangedListener {

  private DrawerLayout mDrawerLayout;
  private NavigationView mNavigationView;
  private MainViewModel mViewModel;
  private ActivityMainBinding mBinding;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    mViewModel = new MainViewModel(this);
    mBinding.setViewModel(mViewModel);
    setupDrawer(mBinding);
    setupActionBar(mBinding.toolbar);
    setupRecyclerView(mBinding.mainRvOrderList);
    mViewModel.setOnDataChangedListener(this);
  }

  private void setupRecyclerView(RecyclerView recyclerView) {
    DealOrderAdapter adapter = new DealOrderAdapter();
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
    mNavigationView.setCheckedItem(item.getItemId());
    mDrawerLayout.closeDrawers();
    return true;
  }

  @Override public void onGetRecentOrders(List<DealOrder> dealOrderList) {
    DealOrderAdapter adapter = (DealOrderAdapter) mBinding.mainRvOrderList.getAdapter();
    adapter.addAll(dealOrderList);
    adapter.notifyDataSetChanged();
  }
}

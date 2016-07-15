package com.zac4j.zwallet.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.adapter.OrderAdapter;
import com.zac4j.zwallet.databinding.ActivityTransactionBinding;
import com.zac4j.zwallet.model.local.Transaction;
import com.zac4j.zwallet.model.response.DealOrder;
import com.zac4j.zwallet.view.widget.DividerItemDecoration;
import com.zac4j.zwallet.viewmodel.TransactionViewModel;
import java.util.List;
import javax.inject.Inject;

/**
 * Transaction (pending/processed) UI
 * Created by zac on 16-7-15.
 */

public class TransactionActivity extends BaseActivity
    implements TransactionViewModel.OnDataChangedListener {

  static final String EXTRA_TRANS_TYPE = "trans_type";

  private OrderAdapter mOrderAdapter;
  @Inject TransactionViewModel mViewModel;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityTransactionBinding binding =
        DataBindingUtil.setContentView(this, R.layout.activity_transaction);

    getActivityComponent().inject(this);

    int transactionType = getIntent().getIntExtra(EXTRA_TRANS_TYPE, -1);

    setupActionBar(binding.actionbar.toolbar, transactionType);
    setupRecyclerView(binding.transactionOrderList);

    mViewModel.setOnDataChangedListener(this);
    mViewModel.getOrders(transactionType);
    binding.setViewModel(mViewModel);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mViewModel.destroy();
  }

  private void setupActionBar(Toolbar toolbar, int transactionType) {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setTitle(transactionType == Transaction.PENDING ? R.string.menu_transaction_pending
          : R.string.menu_transaction_processed);
    }
  }

  private void setupRecyclerView(RecyclerView recyclerView) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    RecyclerView.ItemDecoration decoration =
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
    mOrderAdapter = new OrderAdapter();
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(decoration);
    recyclerView.setAdapter(mOrderAdapter);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * On get server order list info callback
   *
   * @param orderList order list
   */
  @Override public void onDataChanged(List<DealOrder> orderList) {
    mOrderAdapter.addAll(orderList);
  }
}

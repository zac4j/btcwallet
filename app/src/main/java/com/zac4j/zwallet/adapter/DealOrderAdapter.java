package com.zac4j.zwallet.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.databinding.ListItemMainRecentOrdersBinding;
import com.zac4j.zwallet.model.response.DealOrder;
import com.zac4j.zwallet.viewmodel.ItemRecentOrdersViewModel;
import java.util.Collections;
import java.util.List;

/**
 * Deal order RecyclerView adapter
 * Created by Zac on 2016/7/4.
 */
public class DealOrderAdapter extends RecyclerView.Adapter<DealOrderAdapter.DealOrderViewHolder> {

  private List<DealOrder> mDealOrderList;

  public DealOrderAdapter() {
    mDealOrderList = Collections.emptyList();
  }

  public DealOrderAdapter(List<DealOrder> dealOrderList) {
    mDealOrderList = dealOrderList;
  }

  public void addAll(List<DealOrder> dealOrderList) {
    mDealOrderList = dealOrderList;
  }

  @Override public DealOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ListItemMainRecentOrdersBinding binding =
        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
            R.layout.list_item_main_recent_orders, parent, false);
    return new DealOrderViewHolder(binding);
  }

  @Override public void onBindViewHolder(DealOrderViewHolder holder, int position) {
    holder.bindOrder(mDealOrderList.get(position));
  }

  @Override public int getItemCount() {
    return mDealOrderList.size();
  }

  static class DealOrderViewHolder extends RecyclerView.ViewHolder {

    ListItemMainRecentOrdersBinding mBinding;

    public DealOrderViewHolder(ListItemMainRecentOrdersBinding binding) {
      super(binding.cardView);
      mBinding = binding;
    }

    void bindOrder(DealOrder order) {
      if (mBinding.getViewModel() == null) {
        mBinding.setViewModel(new ItemRecentOrdersViewModel(itemView.getContext(), order));
      } else {
        mBinding.getViewModel().setDealOrder(order);
      }
    }
  }
}

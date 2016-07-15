package com.zac4j.zwallet.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import com.zac4j.zwallet.R;
import com.zac4j.zwallet.databinding.DialogFragmentOrderDetailBinding;
import com.zac4j.zwallet.viewmodel.OrderDetailViewModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * DealOrder Detail Dialog Fragment
 * Created by zac on 16-7-14.
 */

public class OrderDetailDialogFragment extends DialogFragment {

  private static final String TAG = "OrderDetail";
  public static final String EXTRA_PROCESS_TIME = "extra_time";
  public static final String EXTRA_ORDER_ID = "extra_id";

  private Context mContext;
  private DialogFragmentOrderDetailBinding mBinding;
  private String orderId;
  private String processTime;

  public static OrderDetailDialogFragment newInstance(String id, String processTime) {
    Bundle args = new Bundle();
    OrderDetailDialogFragment fragment = new OrderDetailDialogFragment();
    args.putString(EXTRA_ORDER_ID, id);
    args.putString(EXTRA_PROCESS_TIME, processTime);
    fragment.setArguments(args);
    System.out.println("new instance << " + id);
    return fragment;
  }

  @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {

    if (getArguments() != null) {
      String processTimeMills = getArguments().getString(EXTRA_PROCESS_TIME);
      orderId = getArguments().getString(EXTRA_ORDER_ID);
      processTime = getFormatTime(processTimeMills);
    }

    System.out.println("view model << " + orderId);

    mContext = getActivity();

    mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
        R.layout.dialog_fragment_order_detail, null, false);
    OrderDetailViewModel viewModel = new OrderDetailViewModel(mContext, orderId);
    mBinding.setViewModel(viewModel);
    viewModel.setProcessTime(processTime);
    View view = mBinding.getRoot();

    AlertDialog.Builder builder = new AlertDialog.Builder(mContext).setView(view)
        .setPositiveButton("OK", null)
        .setCancelable(true);
    return builder.create();
  }

  public void show(FragmentManager fragmentManager) {
    super.show(fragmentManager, TAG);
  }

  private String getFormatTime(String time) {
    long timeMillis = Long.parseLong(time) * 1000L;

    Calendar calendar = Calendar.getInstance(Locale.getDefault());
    calendar.setTimeInMillis(timeMillis);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm EEE", Locale.getDefault());
    return formatter.format(calendar.getTime());
  }
}

package com.zac4j.zwallet.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Data Binding Adapters
 * Created by Zac on 2016/7/4.
 */
public class DataBindingAdapters {

  @BindingAdapter("imageResource")
  public static void setImageResource(ImageView imageView, int resource){
    imageView.setImageResource(resource);
  }

}

package com.zac4j.zwallet.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Data Binding Adapters
 * Created by Zac on 2016/7/4.
 */
public class DataBindingAdapters {

  @BindingAdapter("imageResource")
  public static void setImageResource(SimpleDraweeView draweeView, int resource){
    ImageRequest request = ImageRequestBuilder.newBuilderWithResourceId(resource).build();
    draweeView.setImageURI(request.getSourceUri());
  }

}

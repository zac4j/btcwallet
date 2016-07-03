package com.zac4j.zwallet.util;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RxJava Utilities
 * Created by zac on 16-7-3.
 */

public class RxUtils {

  public static <T> Single.Transformer<T, T> applySchedulers() {
    return new Single.Transformer<T, T>() {
      @Override public Single<T> call(Single<T> tSingle) {
        return tSingle.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

}

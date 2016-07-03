package com.zac4j.zwallet.data.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;

/**
 * Customized Http Client
 * Created by zac on 16-7-3.
 */

@Singleton public class HttpClient {

  private OkHttpClient mOkHttpClient;

  @Inject public HttpClient() {
    mOkHttpClient =
        new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
  }

  public OkHttpClient create() {
    if (mOkHttpClient == null) {
      throw new IllegalStateException("HttpClient did not initialized!");
    }
    return mOkHttpClient;
  }

}

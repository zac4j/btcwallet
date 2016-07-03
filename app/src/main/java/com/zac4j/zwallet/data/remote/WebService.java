package com.zac4j.zwallet.data.remote;

import com.zac4j.zwallet.model.response.AccountInfo;
import com.zac4j.zwallet.model.response.DealOrder;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

/**
 * Web Service
 * Created by zac on 16-7-3.
 */

@Singleton
public interface WebService {

  String BASE_URL = "https://api.huobi.com/apiv3/";

  /**
   * 查询个人账户信息
   * @param accountRequestMap request field map
   * @return 个人账户信息
   */
  @FormUrlEncoded
  @POST(".") Single<AccountInfo> getAccountInfo(@FieldMap Map<String, String> accountRequestMap);

  /**
   * 查询个人最新10条成交订单
   * @param orderRequestMap request field map
   * @return 个人最新10条成交订单
   */
  @FormUrlEncoded
  @POST(".") Single<List<DealOrder>> getRecentOrders(@FieldMap Map<String, String> orderRequestMap);

  class Creator {
    @Inject public WebService create(HttpClient client) {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .client(client.create())
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .build();

      return retrofit.create(WebService.class);
    }
  }

}

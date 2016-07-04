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
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

/**
 * Web Service
 * Created by zac on 16-7-3.
 */

@Singleton public interface WebService {

  String BASE_URL = "https://api.huobi.com/apiv3/";

  /**
   * 查询个人账户信息
   *
   * @param methodName method name
   * @param accessKey public key
   * @param created 提交时间 10位时间戳
   * @param sign md5 generated request body.
   * @return 个人账户信息
   */
  @FormUrlEncoded @POST(".") Single<AccountInfo> getAccountInfo(@Field("method") String methodName,
      @Field("access_key") String accessKey, @Field("created") String created,
      @Field("sign") String sign);

  /**
   * 查询个人最新10条成交订单
   *
   * @param coinType coin type 1 比特币 2 莱特币
   * @return 个人最新10条成交订单
   */
  @FormUrlEncoded @POST(".") Single<List<DealOrder>> getRecentOrders(
      @Field("method") String methodName, @Field("access_key") String accessKey,
      @Field("coin_type") String coinType, @Field("created") String created,
      @Field("sign") String sign);

  class Creator {
    @Inject public WebService create(HttpClient client) {
      Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
          .client(client.create())
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .build();

      return retrofit.create(WebService.class);
    }
  }
}

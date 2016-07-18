package com.zac4j.zwallet.data.remote;

import com.zac4j.zwallet.model.response.AccountInfo;
import com.zac4j.zwallet.model.response.DealOrder;
import com.zac4j.zwallet.model.response.OrderInfo;
import com.zac4j.zwallet.model.response.RealTimeEntity;
import com.zac4j.zwallet.model.response.TradeResponse;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Single;

/**
 * Web Service
 * Created by zac on 16-7-3.
 */

public interface WebService {

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
   * 查询订单
   *
   * @param methodName 1.get_new_deal_orders 个人最新10条成交 2.get_orders 正在进行的委托
   * @param coinType coin type 1 比特币 2 莱特币
   * @return 个人最新10条成交订单
   */
  @FormUrlEncoded @POST(".") Single<List<DealOrder>> getOrders(@Field("method") String methodName,
      @Field("access_key") String accessKey, @Field("coin_type") int coinType,
      @Field("created") String created, @Field("sign") String sign);

  /**
   * 查询委托订单详情
   *
   * @param orderId 委托定单id
   * @return 委托订单详情
   */
  @FormUrlEncoded @POST(".") Single<OrderInfo> getOrderInfo(@Field("method") String methodName,
      @Field("access_key") String accessKey, @Field("coin_type") int coinType,
      @Field("id") String orderId, @Field("created") String created, @Field("sign") String sign);

  /**
   * 买入/卖出
   *
   * @param methodName buy 买入/sell 卖出
   * @param targetPrice 目标价格
   * @param amount 交易数量
   * @return result success 表示买入成功
   */
  @FormUrlEncoded @POST(".") Single<TradeResponse> trade(@Field("method") String methodName,
      @Field("access_key") String accessKey, @Field("coin_type") int coinType,
      @Field("price") String targetPrice, @Field("amount") String amount,
      @Field("created") String created, @Field("sign") String sign);

  /**
   * 取消订单
   *
   * @return success 表示成功取消订单
   */
  @FormUrlEncoded @POST(".") Single<TradeResponse> cancelOrder(@Field("method") String methodName,
      @Field("access_key") String accessKey, @Field("coin_type") int coinType,
      @Field("id") String orderId, @Field("created") String created, @Field("sign") String sign);

  /**
   * 获取分时交易数据
   * @param coinType 1.btc BitCoin 2.ltc LiteCoin
   * @param interval 001~400 https://github.com/huobiapi/API_Docs_en/wiki/REST-Interval
   * @return 所选间隔时间的交易数据
   */
  @GET("/staticmarket/{coin}_kline_{interval}_json.js") Single<List<List>>
      getIntervalTimeData(@Path("coin") String coinType, @Path("interval") String interval);

  /**
   * 获取实时交易数据
   * @param coinType 1.btc BitCoin 2.ltc LiteCoin
   * @return 实时交易数据
   */
  @GET("/staticmarket/ticker_{coin}_json.js") Single<RealTimeEntity>
  getRealTimeData(@Path("coin") String coinType);

  class Creator {
    public static WebService create() {
      Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
          .client(new HttpClient().create())
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .build();

      return retrofit.create(WebService.class);
    }
  }
}

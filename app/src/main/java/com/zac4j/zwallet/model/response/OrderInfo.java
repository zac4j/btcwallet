package com.zac4j.zwallet.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Order Detail Info
 * Created by zac on 16-7-5.
 */

public class OrderInfo {
  private int id;
  private int type; //1限价买　2限价卖　3市价买　4市价卖
  @SerializedName("order_price") private String orderPrice; // 委托价格
  @SerializedName("order_amount") private String orderAmount; // 委托数量(市价买单，代表买入金额)
  @SerializedName("processed_price") private String processedPrice; // 成交平均价格
  @SerializedName("processed_amount") private String processedAmount; //已经完成的数量(市价买单，代表成交金额)
  private String vot; // 交易额
  private String fee; // 手续费
  private String total; // 总交易额
  private int status; //状态　0未成交　1部分成交　2已完成　3已取消 4废弃（该状态已不再使用） 5异常 6部分成交已取消 7队列中

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getOrderPrice() {
    return orderPrice;
  }

  public void setOrderPrice(String orderPrice) {
    this.orderPrice = orderPrice;
  }

  public String getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(String orderAmount) {
    this.orderAmount = orderAmount;
  }

  public String getProcessedPrice() {
    return processedPrice;
  }

  public void setProcessedPrice(String processedPrice) {
    this.processedPrice = processedPrice;
  }

  public String getProcessedAmount() {
    return processedAmount;
  }

  public void setProcessedAmount(String processedAmount) {
    this.processedAmount = processedAmount;
  }

  public String getVot() {
    return vot;
  }

  public void setVot(String vot) {
    this.vot = vot;
  }

  public String getFee() {
    return fee;
  }

  public void setFee(String fee) {
    this.fee = fee;
  }

  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}

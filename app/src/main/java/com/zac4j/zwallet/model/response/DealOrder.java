package com.zac4j.zwallet.model.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Deal order model
 * Created by zac on 16-7-3.
 */

public class DealOrder implements Parcelable{

  /**
   * type: 1限价买 2限价卖 3市价买 4市价卖
   * orderPrice: 委托价格
   * orderAmount: 委托数量
   * processedAmount: 已经完成的数量
   * lastProcessedTime: 最后成交时间
   * orderTime: 委托时间
   * status: 状态　0未成交　1部分成交　2已完成　3已取消
   */

  private String id;
  private String type;
  @SerializedName("order_price")
  private String orderPrice;
  @SerializedName("order_amount")
  private String orderAmount;
  @SerializedName("processed_amount")
  private String processedAmount;
  @SerializedName("last_processed_time")
  private String lastProcessedTime;
  @SerializedName("order_time")
  private String orderTime;
  private String status;

  protected DealOrder(Parcel in) {
    id = in.readString();
    type = in.readString();
    orderPrice = in.readString();
    orderAmount = in.readString();
    processedAmount = in.readString();
    lastProcessedTime = in.readString();
    orderTime = in.readString();
    status = in.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);
    dest.writeString(type);
    dest.writeString(orderPrice);
    dest.writeString(orderAmount);
    dest.writeString(processedAmount);
    dest.writeString(lastProcessedTime);
    dest.writeString(orderTime);
    dest.writeString(status);
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<DealOrder> CREATOR = new Parcelable.Creator<DealOrder>() {
    @Override
    public DealOrder createFromParcel(Parcel in) {
      return new DealOrder(in);
    }

    @Override
    public DealOrder[] newArray(int size) {
      return new DealOrder[size];
    }
  };

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
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

  public String getProcessedAmount() {
    return processedAmount;
  }

  public void setProcessedAmount(String processedAmount) {
    this.processedAmount = processedAmount;
  }

  public String getLastProcessedTime() {
    return lastProcessedTime;
  }

  public void setLastProcessedTime(String lastProcessedTime) {
    this.lastProcessedTime = lastProcessedTime;
  }

  public String getOrderTime() {
    return orderTime;
  }

  public void setOrderTime(String orderTime) {
    this.orderTime = orderTime;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}

package com.zac4j.zwallet.model.local;

/**
 * K-Line Chart data model
 * Created by zac on 16-7-17.
 */

public class KLineEntity {

  private String date;
  private String openingPrice; // 开盘价
  private String highestPrice; // 最高价
  private String lowestPrice; // 最低价
  private String closingPrice; // 收盘价
  private String tradingVolume; // 交易量

  public KLineEntity() {
  }

  public KLineEntity(String date, String openingPrice, String highestPrice, String lowestPrice,
      String closingPrice, String tradingVolume) {
    this.date = date;
    this.openingPrice = openingPrice;
    this.highestPrice = highestPrice;
    this.lowestPrice = lowestPrice;
    this.closingPrice = closingPrice;
    this.tradingVolume = tradingVolume;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getOpeningPrice() {
    return openingPrice;
  }

  public void setOpeningPrice(String openingPrice) {
    this.openingPrice = openingPrice;
  }

  public String getHighestPrice() {
    return highestPrice;
  }

  public void setHighestPrice(String highestPrice) {
    this.highestPrice = highestPrice;
  }

  public String getLowestPrice() {
    return lowestPrice;
  }

  public void setLowestPrice(String lowestPrice) {
    this.lowestPrice = lowestPrice;
  }

  public String getClosingPrice() {
    return closingPrice;
  }

  public void setClosingPrice(String closingPrice) {
    this.closingPrice = closingPrice;
  }

  public String getTradingVolume() {
    return tradingVolume;
  }

  public void setTradingVolume(String tradingVolume) {
    this.tradingVolume = tradingVolume;
  }

  @Override public String toString() {
    return "KLineEntity{" +
        "date='" + date + '\'' +
        ", openingPrice='" + openingPrice + '\'' +
        ", highestPrice='" + highestPrice + '\'' +
        ", lowestPrice='" + lowestPrice + '\'' +
        ", closingPrice='" + closingPrice + '\'' +
        ", tradingVolume='" + tradingVolume + '\'' +
        '}';
  }
}

package com.zac4j.zwallet.model.local;

/**
 * K-Line Chart data model
 * Created by zac on 16-7-17.
 */

public class KLineEntity {
  private String date;
  private String openingPrice;
  private String highestPrice;
  private String lowestPrice;
  private String closingPrice;
  private String tradingVolume;

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
}

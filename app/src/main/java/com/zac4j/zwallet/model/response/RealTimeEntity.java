package com.zac4j.zwallet.model.response;

/**
 * Created by zac on 16-7-17.
 */

public class RealTimeEntity {

  /**
   * time : 1468741783
   * ticker : {"open":27.69,"vol":1.29859368244E7,"symbol":"ltccny","last":27.82,"buy":27.82,"sell":27.83,"high":27.86,"low":27.67}
   */

  private String time;
  /**
   * open : 27.69
   * vol : 1.29859368244E7
   * symbol : ltccny
   * last : 27.82
   * buy : 27.82
   * sell : 27.83
   * high : 27.86
   * low : 27.67
   */

  private TickerBean ticker;

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public TickerBean getTicker() {
    return ticker;
  }

  public void setTicker(TickerBean ticker) {
    this.ticker = ticker;
  }

  public static class TickerBean {
    private double open;
    private double vol;
    private String symbol;
    private double last;
    private double buy;
    private double sell;
    private double high;
    private double low;

    public double getOpen() {
      return open;
    }

    public void setOpen(double open) {
      this.open = open;
    }

    public double getVol() {
      return vol;
    }

    public void setVol(double vol) {
      this.vol = vol;
    }

    public String getSymbol() {
      return symbol;
    }

    public void setSymbol(String symbol) {
      this.symbol = symbol;
    }

    public double getLast() {
      return last;
    }

    public void setLast(double last) {
      this.last = last;
    }

    public double getBuy() {
      return buy;
    }

    public void setBuy(double buy) {
      this.buy = buy;
    }

    public double getSell() {
      return sell;
    }

    public void setSell(double sell) {
      this.sell = sell;
    }

    public double getHigh() {
      return high;
    }

    public void setHigh(double high) {
      this.high = high;
    }

    public double getLow() {
      return low;
    }

    public void setLow(double low) {
      this.low = low;
    }
  }
}

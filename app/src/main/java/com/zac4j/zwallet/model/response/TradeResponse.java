package com.zac4j.zwallet.model.response;

/**
 * Trade(Buy/Sell) order response model
 * Created by zac on 16-7-12.
 */

public class TradeResponse {

  private String result;
  private String id;

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}

package com.zac4j.zwallet.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Account info response model
 * Created by zac on 16-7-3.
 */

public class AccountInfo {
  private String total;
  @SerializedName("net_asset")
  private String netAssets;
  @SerializedName("available_cny_display")
  private String availableCNY;
  @SerializedName("available_btc_display")
  private String availableBTC;
  @SerializedName("available_ltc_display")
  private String availableLTC;
  @SerializedName("frozen_cny_display")
  private String frozenCNY;
  @SerializedName("frozen_btc_display")
  private String frozenBTC;
  @SerializedName("frozen_ltc_display")
  private String frozenLTC;
  @SerializedName("loan_cny_display")
  private String loanCNY;
  @SerializedName("loan_btc_display")
  private String loanBTC;
  @SerializedName("loan_ltc_display")
  private String loanLTC;

  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public String getNetAssets() {
    return netAssets;
  }

  public void setNetAssets(String netAssets) {
    this.netAssets = netAssets;
  }

  public String getAvailableCNY() {
    return availableCNY;
  }

  public void setAvailableCNY(String availableCNY) {
    this.availableCNY = availableCNY;
  }

  public String getAvailableBTC() {
    return availableBTC;
  }

  public void setAvailableBTC(String availableBTC) {
    this.availableBTC = availableBTC;
  }

  public String getAvailableLTC() {
    return availableLTC;
  }

  public void setAvailableLTC(String availableLTC) {
    this.availableLTC = availableLTC;
  }

  public String getFrozenCNY() {
    return frozenCNY;
  }

  public void setFrozenCNY(String frozenCNY) {
    this.frozenCNY = frozenCNY;
  }

  public String getFrozenBTC() {
    return frozenBTC;
  }

  public void setFrozenBTC(String frozenBTC) {
    this.frozenBTC = frozenBTC;
  }

  public String getFrozenLTC() {
    return frozenLTC;
  }

  public void setFrozenLTC(String frozenLTC) {
    this.frozenLTC = frozenLTC;
  }

  public String getLoanCNY() {
    return loanCNY;
  }

  public void setLoanCNY(String loanCNY) {
    this.loanCNY = loanCNY;
  }

  public String getLoanBTC() {
    return loanBTC;
  }

  public void setLoanBTC(String loanBTC) {
    this.loanBTC = loanBTC;
  }

  public String getLoanLTC() {
    return loanLTC;
  }

  public void setLoanLTC(String loanLTC) {
    this.loanLTC = loanLTC;
  }

  @Override public String toString() {
    return "AccountInfo{" +
        "total='" + total + '\'' +
        ", netAssets='" + netAssets + '\'' +
        ", availableCNY='" + availableCNY + '\'' +
        ", availableBTC='" + availableBTC + '\'' +
        ", availableLTC='" + availableLTC + '\'' +
        ", frozenCNY='" + frozenCNY + '\'' +
        ", frozenBTC='" + frozenBTC + '\'' +
        ", frozenLTC='" + frozenLTC + '\'' +
        ", loanCNY='" + loanCNY + '\'' +
        ", loanBTC='" + loanBTC + '\'' +
        ", loanLTC='" + loanLTC + '\'' +
        '}';
  }
}

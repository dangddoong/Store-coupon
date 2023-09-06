package com.make.storecoupon.mart.entity;

import com.make.storecoupon.customer.entity.CustomerRole;
import com.make.storecoupon.customer.entity.CustomerRole.Authority;

public enum MartRole {

  MART(Authority.MART); // 기본 구매자 권한(추후 등급별 권한부여 가능)
  private final String authority;

  MartRole(String authority) {
    this.authority = authority;
  }

  public String getAuthority(){
    return authority;
  }

  public static class Authority{
    public static final String MART = "ROLE_MART";
  }

}

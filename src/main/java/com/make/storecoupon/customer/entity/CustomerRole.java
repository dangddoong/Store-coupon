package com.make.storecoupon.customer.entity;

public enum CustomerRole {
  CUSTOMER(Authority.CUSTOMER); // 기본 구매자 권한(추후 등급별 권한부여 가능)
  private final String authority;

  CustomerRole(String authority) {
    this.authority = authority;
  }

  public String getAuthority(){
    return authority;
  }

  public static class Authority{
    public static final String CUSTOMER = "ROLE_CUSTOMER";
  }

}

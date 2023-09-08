package com.make.storecoupon.coupon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class IssuedCoupon {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long couponId;
  private Long customerId;

  public IssuedCoupon(Long couponId, Long customerId) {
    this.couponId = couponId;
    this.customerId = customerId;
  }
}

package com.make.storecoupon.coupon.service;

import com.make.storecoupon.coupon.dto.CreateCouponRequestDto;
import com.make.storecoupon.customer.entity.Customer;
import com.make.storecoupon.mart.entity.Mart;

public interface CouponService {
  Long createCoupon(CreateCouponRequestDto requestDto, Mart mart);
  Long issueCoupon(Customer customer, Long couponId);
}

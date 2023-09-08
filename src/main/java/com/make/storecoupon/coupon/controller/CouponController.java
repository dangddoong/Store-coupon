package com.make.storecoupon.coupon.controller;

import com.make.storecoupon.common.auth.userDetails.entity.CustomerDetails;
import com.make.storecoupon.common.auth.userDetails.entity.MartDetails;
import com.make.storecoupon.coupon.dto.CreateCouponRequestDto;
import com.make.storecoupon.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponController {
  private final CouponService couponService;

  @PostMapping("/coupons")
  @PreAuthorize("hasRole('MART')")
  public ResponseEntity<Long> createCoupon(@RequestBody CreateCouponRequestDto requestDto,
      @AuthenticationPrincipal MartDetails martDetails){
    Long couponId = couponService.createCoupon(requestDto, martDetails.getMart());
    return ResponseEntity.status(HttpStatus.CREATED).body(couponId);
  }

  @PostMapping("/coupons/{couponId}/issue")
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<Long> issueCoupon(@PathVariable Long couponId,
      @AuthenticationPrincipal CustomerDetails customerDetails){
    Long issuedCouponId = couponService.issueCoupon(customerDetails.getCustomer(), couponId);
    return ResponseEntity.status(HttpStatus.CREATED).body(issuedCouponId);
  }
}

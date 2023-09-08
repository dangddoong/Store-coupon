package com.make.storecoupon.coupon.dto;

import com.make.storecoupon.coupon.entity.CoverageType;
import com.make.storecoupon.coupon.entity.DiscountType;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCouponRequestDto {
  private String name;
  private DiscountType discountType;
  private Long discountAmount;
  private CoverageType coverageType;
  private List<Long> coverageProducts;
}

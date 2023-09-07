package com.make.storecoupon.order.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetailsDto {
  private Long productId;
  private Long quantity;
}

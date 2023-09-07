package com.make.storecoupon.product.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductResponseDto {
  private final String productName;
  private final Long productPrice;

}

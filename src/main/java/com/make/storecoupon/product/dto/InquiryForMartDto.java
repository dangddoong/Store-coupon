package com.make.storecoupon.product.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InquiryForMartDto {
  private final List<ProductResponseDto> productResponseDtos;
  private final boolean hasNextProduct;
}

package com.make.storecoupon.product.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductPriceHistoryResponseDto {
  private final Long productId;
  private final LocalDateTime inquiryTimeStamp;
  private final Long priceAtTimestamp;
}

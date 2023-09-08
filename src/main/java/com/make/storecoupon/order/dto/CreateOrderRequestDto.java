package com.make.storecoupon.order.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateOrderRequestDto {
  private List<OrderDetailsDto> orderDetailsDtos;
  private Long deliveryCharge;
  private Long issuedCouponId;

  public List<Long> getProductIds(){
    List<Long> productIds = new ArrayList<>();
    orderDetailsDtos.forEach(dto -> productIds.add(dto.getProductId()));
    return productIds;
  }
}

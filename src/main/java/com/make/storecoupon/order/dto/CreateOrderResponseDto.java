package com.make.storecoupon.order.dto;

import com.make.storecoupon.order.entity.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class CreateOrderResponseDto {
  private final Long orderId;
  private final Long totalPaymentAmount;

  public CreateOrderResponseDto(Order order) {
    this.orderId = order.getId();
    this.totalPaymentAmount = order.getTotalPaymentAmount();
  }
}
